package com.spiffymap.datalog.ui.mvp.main;

import com.spiffymap.datalog.ui.mvp.details.DetailDataListModel;
import com.spiffymap.datalog.ui.mvp.stations.StationTableModel;
import java.awt.Component;
import java.awt.Window;
import java.io.File;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import net.spiffymap.datalog.client.model.DatalogFile;
import net.spiffymap.datalog.client.model.Detail;
import net.spiffymap.datalog.client.model.Station;
import net.spiffymap.datalog.shared.mvp.main.DatalogPresenter;
import net.spiffymap.datalog.shared.mvp.main.DatalogView;
import spiffy.swing.automation.SpiffySwingAutomation;
import static spiffy.swing.automation.SpiffySwingAutomation.runInSwing;

/**
 *
 * @author steve
 */
public class Utils {

    public final static String TEST_FILE = "test" + File.separator + "6700";

    public static Window open(SpiffySwingAutomation automation) throws InterruptedException {
        String filePath = new File(System.getProperty("user.dir") + File.separator + TEST_FILE).getAbsolutePath();
        Window window = Utils.openFile(automation, filePath);
        return window;
    }

    public static Window openFile(SpiffySwingAutomation automation, String filePath) throws InterruptedException {
        final Window window = automation.openWindow(DatalogFrame.class);
        automation.clickButtonWithText(window, "Load");
        Window dialog = automation.waitForWindow(JDialog.class, "Open");
        Optional<Component> component = automation.findComponent(dialog, c -> c instanceof JTextField);
        JTextField textField = (JTextField) component.get();
        textField.requestFocusInWindow();
        textField.setText(filePath);
        automation.clickButtonWithText(dialog, "Open");
        return window;
    }

    public static boolean waitForDataLoaded(SpiffySwingAutomation automation, JTable detailDataTable) throws InterruptedException {
        return automation.waitFor(() -> detailDataTable.getRowCount() == 656, 1000, 10);
    }

    public static Detail getSelectedDetail(SpiffySwingAutomation automation) {
        AtomicReference<Detail> detail = new AtomicReference<>();
        runInSwing(() -> {
            Window window = automation.getVisibleWindowOfClassTitled(DatalogFrame.class, null);
            JList detailDataList = (JList) automation.findComponentNamed(window, "detailDataList");
            DetailDataListModel model = (DetailDataListModel) detailDataList.getModel();
            detail.set(model.getDetails().get(detailDataList.getSelectedIndex()));
        });
        return detail.get();
    }

    public static Station getSelectedStation(SpiffySwingAutomation automation) {
        AtomicReference<Station> station = new AtomicReference<>();
        runInSwing(() -> {
            Window window = automation.getVisibleWindowOfClassTitled(DatalogFrame.class, null);
            JTable stationsTable = (JTable) automation.findComponentNamed(window, "stationsTable");
            StationTableModel model = (StationTableModel) stationsTable.getModel();
            station.set(model.getStations().get(stationsTable.getSelectedRow()));
        });
        return station.get();
    }

    /**
     * Get the file instance
     *
     * @param automation
     * @param mainWindow
     * @return
     */
    public static DatalogFile getFile(SpiffySwingAutomation automation, Window mainWindow) {
        AtomicReference<DatalogFile> file = new AtomicReference<>();
        runInSwing(() -> {
            DatalogView mainView = (DatalogView) automation.getVisibleWindowOfClassTitled(DatalogFrame.class, null);
            DatalogPresenter presenter = mainView.getPresenter();
            file.set(presenter.getFile());
        });
        return file.get();
    }

    public static <T extends Component> Optional<T> getTabNamed(SpiffySwingAutomation automation, Window window, String tabName) {
        JTabbedPane tabbedPane = (JTabbedPane) automation.findComponentNamed(window, "tabbedPane");
        AtomicReference<Optional<T>> tabRef = new AtomicReference<>();
        runInSwing(() -> tabRef.set((Optional<T>) automation.getTabNamed(tabbedPane, tabName)));
        return tabRef.get();
    }

}
