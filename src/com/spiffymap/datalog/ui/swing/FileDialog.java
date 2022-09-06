package com.spiffymap.datalog.ui.swing;

import com.spiffymap.mvp.swing.FileListerWithProgress;
import com.spiffymap.mvp.swing.FileProgressHandler;
import com.spiffymap.mvp.swing.ShowProgressDialog;
import com.spiffymap.mvp.swing.FilesTableModel;
import com.spiffymap.server.ui.UIUtils;
import java.awt.Frame;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import static java.util.stream.Collectors.toList;
import java.util.stream.StreamSupport;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JToggleButton;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author steve
 */
public class FileDialog extends javax.swing.JDialog {

    private final static Logger LOG = Logger.getLogger(FileDialog.class.getName());

    private final static long DOUBLE_CLICK_MS = 250;
    private Path currentDirectory;
    private List<File> files;
    private Map<String, JToggleButton> driveButtons;
    private final Map<String, Path> pathOnDrive = new HashMap<>();
    private final FilesTableModel model = new FilesTableModel();
    private String selectedFile;
    private boolean accepted;
    private long lastSelectionTime = -1;
    private int lastSelectionIndex;
    private final FileProgressHandler progressDisplay;

    /**
     * Creates new form FileDialog
     *
     * @param parent
     */
    public FileDialog(Frame parent) {
        super(parent, true);
        initComponents();
        progressDisplay = new ShowProgressDialog(null);
        setSize(1000, 800);
        UIUtils.centreOnScreen(FileDialog.this);
        showDrives();
        setDirectoriesDisplay();
        filesTable.setModel(model);
        filesTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent event) {
                handleMouseClick(event);
            }
        });
        TableColumnModel columnModel = filesTable.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(40);
        columnModel.getColumn(1).setPreferredWidth(120);
        columnModel.getColumn(2).setPreferredWidth(120);
        columnModel.getColumn(3).setPreferredWidth(240);
    }

    /**
     * Handle double-click on table - allow it to select.
     *
     * @param event
     */
    private void handleMouseClick(MouseEvent event) {
        int selection = filesTable.getSelectedRow();
        if (selection == -1) {
            return;
        }
        if (event.getClickCount() == 2) {
            processSelection(selection);
        }
    }

    /**
     * Process a selection
     *
     * @param selectionIndex
     */
    private void processSelection(int selectionIndex) {
        if (selectionIndex < 0 || selectionIndex >= files.size()) {
            return;
        }
        File file = files.get(selectionIndex);
        if (file.isFile()) {
            accept();
        } else {
            // Directory
            changeToDirectory(file.toPath());
        }
    }

    /**
     * Select a drive button
     *
     * @param buttonName
     */
    private void selectDriveButton(String buttonName) {
        Set<String> names = driveButtons.keySet();
        // Note current path on current drive
        LOG.log(Level.FINE, "Current directory is {0}", currentDirectory);
        pathOnDrive.put(currentDirectory.getRoot().toString(), currentDirectory);
        // Ensure button with supplied name is selected, and change to drive
        for (String name : names) {
            JToggleButton button = driveButtons.get(name);
            if (name.equals(buttonName)) {
                button.setSelected(true);
                LOG.log(Level.FINE, "Changing to directory {0}", name);
                changeToDrive(name);
            } else {
                button.setSelected(false);
            }
        }
    }

    /**
     * Change to the selected drive.
     *
     * @param driveName
     */
    private void changeToDrive(String driveName) {
        Path drivePath = Paths.get(driveName);
        // If no drive change, do nothing
        if (currentDirectory.getRoot().equals(drivePath)) {
            return;
        }
        Path pathOnNewDrive = pathOnDrive.get(driveName);
        if (pathOnNewDrive != null) {
            setCurrentDirectory(pathOnNewDrive);
        } else {
            setCurrentDirectory(Paths.get(driveName));
        }
        listCurrentDirectory();
    }

    /**
     * Show available drives
     */
    private void showDrives() {
        driveButtons = new HashMap<>();
        FileSystem defaultFileSystem = FileSystems.getDefault();
        drivesPanel.removeAll();
        drivesPanel.add(new JLabel("Drives:"));
        StreamSupport.stream(defaultFileSystem.getRootDirectories().spliterator(), false).
                sorted().
                forEach(path -> {
                    String driveName = path.toString();
                    JToggleButton button = new JToggleButton(driveName);
                    button.addActionListener(event -> {
                        selectDriveButton(((JToggleButton) event.getSource()).getText());
                    });
                    drivesPanel.add(button);
                    driveButtons.put(driveName, button);
                });
    }

    /**
     * Go to the directory indicated by the supplied index to the current path.
     * 0 = root directory, else index subpath end.
     *
     * @param index
     */
    private void selectDirectoryInCurrentPath(int index) {
        // Need to use getRoot to preserve root information
        if (index == 0) {
            changeToDirectory(currentDirectory.getRoot());
        } else {
            changeToDirectory(currentDirectory.getRoot().resolve(currentDirectory.subpath(0, index)));
        }
    }

    private void changeToDirectory(Path directory) {
        setCurrentDirectory(directory);
        // Clear any table selection
        filesTable.getSelectionModel().clearSelection();
        // Show files and directories
        listCurrentDirectory();
    }

    private boolean anyDriveButtonSelected() {
        return driveButtons.values().stream().anyMatch(JToggleButton::isSelected);
    }

    /**
     * Display current directory and directories above it.
     */
    private void setDirectoriesDisplay() {
        if (currentDirectory == null) {
            listCurrentDirectory();
        }
        // Select the current drive if none selected
        if (!anyDriveButtonSelected()) {
            Path root = currentDirectory.getRoot();
            selectDriveButton(root.toString());
        }
        directoriesPanel.removeAll();
        // Add the root directory
        JButton button = new JButton(File.separator);
        button.addActionListener(event -> selectDirectoryInCurrentPath(0));
        directoriesPanel.add(button);
        int names = currentDirectory.getNameCount();
        for (int index = 1; index <= names; index++) {
            int indexIntoPath = index;
            String name = currentDirectory.getName(index - 1).getFileName().toString();
            button = new JButton(name);
            button.addActionListener(event -> selectDirectoryInCurrentPath(indexIntoPath));
            directoriesPanel.add(button);
            if (index < names - 1) {
                directoriesPanel.add(new JLabel(File.separator));
            }
        }
        directoriesPanel.doLayout();
        directoriesPanel.repaint();
    }

    private void listCurrentDirectory() {
        if (currentDirectory == null) {
            setCurrentDirectory(Paths.get(System.getProperty("user.dir")).toAbsolutePath());
        }
        setDirectoriesDisplay();
        setFilesDisplay();
    }

    private void setFilesDisplay() {
        FileListerWithProgress lister = new FileListerWithProgress();
        List<File> filesList = lister.listFiles(currentDirectory.toFile(), progressDisplay);
        files = filesList.stream().
                sorted((f1, f2) -> f1.getName().compareTo(f2.getName())).
                collect(toList());
        model.setFiles(files);
        model.fireTableDataChanged();
    }

    private void accept() {
        int selectedRow = filesTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "No file selected", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        File file = files.get(selectedRow);
        if (file.isDirectory()) {
            JOptionPane.showMessageDialog(this, "Directory selected", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        selectedFile = file.getAbsolutePath();
        accepted = true;
        setVisible(false);
    }

    private void cancel() {
        accepted = false;
        setVisible(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonPanel = new javax.swing.JPanel();
        acceptButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        filesPanel = new javax.swing.JPanel();
        filesScrollPane = new javax.swing.JScrollPane();
        filesTable = new javax.swing.JTable();
        topPanel = new javax.swing.JPanel();
        drivesPanel = new javax.swing.JPanel();
        directoriesPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        buttonPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        acceptButton.setText("Accept");
        acceptButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                acceptButtonActionPerformed(evt);
            }
        });
        buttonPanel.add(acceptButton);

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });
        buttonPanel.add(cancelButton);

        getContentPane().add(buttonPanel, java.awt.BorderLayout.SOUTH);

        filesPanel.setLayout(new java.awt.BorderLayout());

        filesTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        filesTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        filesTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        filesScrollPane.setViewportView(filesTable);

        filesPanel.add(filesScrollPane, java.awt.BorderLayout.CENTER);

        getContentPane().add(filesPanel, java.awt.BorderLayout.CENTER);

        topPanel.setLayout(new java.awt.BorderLayout());

        drivesPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));
        topPanel.add(drivesPanel, java.awt.BorderLayout.CENTER);

        directoriesPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));
        topPanel.add(directoriesPanel, java.awt.BorderLayout.SOUTH);

        getContentPane().add(topPanel, java.awt.BorderLayout.PAGE_START);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void acceptButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_acceptButtonActionPerformed
        accept();
    }//GEN-LAST:event_acceptButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        cancel();
    }//GEN-LAST:event_cancelButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton acceptButton;
    private javax.swing.JPanel buttonPanel;
    private javax.swing.JButton cancelButton;
    private javax.swing.JPanel directoriesPanel;
    private javax.swing.JPanel drivesPanel;
    private javax.swing.JPanel filesPanel;
    private javax.swing.JScrollPane filesScrollPane;
    private javax.swing.JTable filesTable;
    private javax.swing.JPanel topPanel;
    // End of variables declaration//GEN-END:variables

    /**
     * @return the selectedFile
     */
    public String getSelectedFile() {
        return selectedFile;
    }

    /**
     * @return the accepted
     */
    public boolean isAccepted() {
        return accepted;
    }

    /**
     * @param currentDirectory the currentDirectory to set
     */
    private void setCurrentDirectory(Path currentDirectory) {
        this.currentDirectory = currentDirectory;
    }
}
