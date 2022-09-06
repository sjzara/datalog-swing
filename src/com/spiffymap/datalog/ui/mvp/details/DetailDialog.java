package com.spiffymap.datalog.ui.mvp.details;

import net.spiffymap.datalog.shared.mvp.ValidatingDialog;
import com.spiffymap.server.ui.UIUtils;
import java.awt.Frame;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import com.spiffymap.geo.shared.Angle;
import net.spiffymap.datalog.client.model.DataType;
import static net.spiffymap.datalog.client.model.DataType.ANGLE;
import static net.spiffymap.datalog.client.model.DataType.DISTANCE;
import static net.spiffymap.datalog.client.model.DataType.DOUBLE;
import static net.spiffymap.datalog.client.model.DataType.DOUBLE_OR_EMPTY;
import static net.spiffymap.datalog.client.model.DataType.NUMBER;
import static net.spiffymap.datalog.client.model.DataType.STRING;
import net.spiffymap.datalog.client.model.Detail;
import net.spiffymap.datalog.client.model.validators.AcceptanceValidator;
import net.spiffymap.datalog.client.model.validators.ValidationLink;
import net.spiffymap.datalog.client.model.validators.ValueMap;
import com.spiffymap.geo.shared.TextComponentWrapper;
import com.spiffymap.geo.shared.TextComponentWrapperFactory;
import static net.spiffymap.datalog.shared.mvp.details.DetailPresenter.A_CODE;
import static net.spiffymap.datalog.shared.mvp.details.DetailPresenter.BEARING;
import static net.spiffymap.datalog.shared.mvp.details.DetailPresenter.B_CODE;
import static net.spiffymap.datalog.shared.mvp.details.DetailPresenter.C_CODE;
import static net.spiffymap.datalog.shared.mvp.details.DetailPresenter.DETAIL_DISTANCE;
import static net.spiffymap.datalog.shared.mvp.details.DetailPresenter.DETAIL_NUMBER;
import static net.spiffymap.datalog.shared.mvp.details.DetailPresenter.LEVEL_KILLED;
import static net.spiffymap.datalog.shared.mvp.details.DetailPresenter.LINE_CODE;
import static net.spiffymap.datalog.shared.mvp.details.DetailPresenter.PLOT_BAD_LEVEL;
import static net.spiffymap.datalog.shared.mvp.details.DetailPresenter.PRISM_HEIGHT;
import static net.spiffymap.datalog.shared.mvp.details.DetailPresenter.STATION_NUMBER;
import static net.spiffymap.datalog.shared.mvp.details.DetailPresenter.VERTICAL_ANGLE;
import net.spiffymap.datalog.shared.mvp.details.DetailView;
import com.spiffymap.mvp.shared.ui.Dialogs;

/**
 *
 * @author steve
 */
public class DetailDialog extends JDialog implements DetailView, ValidatingDialog {

    private final static String DISTANCE_FORMAT = "%.3f";
    private boolean newDetail = false;
    private final TextComponentWrapperFactory fieldWrapperFactory;
    private AcceptanceValidator acceptanceValidator;
    private final Map<TextComponentWrapper, ValidationLink> validationMap = new HashMap<>();
    private Dialogs dialogs;
    private boolean accepted;
    private Runnable runIfAccepted;

    /**
     * Creates new form DetailInputDialog
     *
     * @param parent
     * @param fieldWrapperFactory
     * @param dialogs
     */
    public DetailDialog(Frame parent, TextComponentWrapperFactory fieldWrapperFactory, Dialogs dialogs) {
        super(parent, true);
        this.fieldWrapperFactory = fieldWrapperFactory;
        this.dialogs = dialogs;
        initComponents();
        getRootPane().setDefaultButton(acceptButton);
        setSize(500, 500);
        UIUtils.centreOnScreen(DetailDialog.this);
        if (newDetail) {
            setValidationLinkForFields(numberField, NUMBER, numberStatusLabel);
        }
        numberField.setEditable(newDetail);
        setValidationLinkForFields(stationField, STRING, stationStatusLabel);
        setValidationLinkForFields(bearingField, ANGLE, bearingStatusLabel);
        setValidationLinkForFields(distanceField, DISTANCE, distanceStatusLabel);
        setValidationLinkForFields(verticalAngleField, ANGLE, verticalAngleStatusLabel);
        setValidationLinkForFields(prismHeightField, DOUBLE, prismHeightStatusLabel);
        setValidationLinkForFields(aCodeField, DOUBLE_OR_EMPTY, aCodeStatusLabel);
        setValidationLinkForFields(bCodeField, DOUBLE_OR_EMPTY, bCodeStatusLabel);
        setValidationLinkForFields(cCodeField, DOUBLE_OR_EMPTY, cCodeStatusLabel);
    }

    private void setValidationLinkForFields(JTextField field, DataType type, JLabel label) {
        setValidationLink(fieldWrapperFactory.wrap(field), type, fieldWrapperFactory.wrap(label));
    }
    
    @Override
    public Runnable getRunIfAccepted() {
        return runIfAccepted;
    }

    /**
     * Show for editing new detail
     * @param runIfAccepted
     */
    @Override
    public void showView(Runnable runIfAccepted) {
        showView(runIfAccepted, null);
    }

    /**
     * Show. If detail is present, show the values and allow editing.
     * 
     * @param runIfAccepted
     * @param detail
     */
    @Override
    public void showView(Runnable runIfAccepted, Detail detail) {
        if (detail == null) {
            newDetail = true;
            clear();
        } else {
            setDetail(detail);
        }
        setVisible(true);
    }

    @Override
    public void hideView() {
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
        entryPanel = new javax.swing.JPanel();
        numberLabel = new javax.swing.JLabel();
        numberField = new javax.swing.JTextField();
        stationLabel = new javax.swing.JLabel();
        stationField = new javax.swing.JTextField();
        lineCodeLabel = new javax.swing.JLabel();
        lineCodeField = new javax.swing.JTextField();
        bearingLabel = new javax.swing.JLabel();
        bearingField = new javax.swing.JTextField();
        distanceLabel = new javax.swing.JLabel();
        distanceField = new javax.swing.JTextField();
        verticalAngleLabel = new javax.swing.JLabel();
        verticalAngleField = new javax.swing.JTextField();
        prismHeightLabel = new javax.swing.JLabel();
        prismHeightField = new javax.swing.JTextField();
        aCodeLabel = new javax.swing.JLabel();
        aCodeField = new javax.swing.JTextField();
        bCodeLabel = new javax.swing.JLabel();
        bCodeField = new javax.swing.JTextField();
        cCodeLabel = new javax.swing.JLabel();
        cCodeField = new javax.swing.JTextField();
        levelKilledCheckBox = new javax.swing.JCheckBox();
        numberStatusLabel = new javax.swing.JLabel();
        stationStatusLabel = new javax.swing.JLabel();
        lineCodeStatusLabel = new javax.swing.JLabel();
        bearingStatusLabel = new javax.swing.JLabel();
        distanceStatusLabel = new javax.swing.JLabel();
        verticalAngleStatusLabel = new javax.swing.JLabel();
        prismHeightStatusLabel = new javax.swing.JLabel();
        aCodeStatusLabel = new javax.swing.JLabel();
        bCodeStatusLabel = new javax.swing.JLabel();
        cCodeStatusLabel = new javax.swing.JLabel();
        plotBadLevelCheckBox = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Input detail");

        buttonPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        acceptButton.setText("Accept");
        acceptButton.setName("acceptButton"); // NOI18N
        acceptButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                acceptButtonActionPerformed(evt);
            }
        });
        buttonPanel.add(acceptButton);

        cancelButton.setText("Cancel");
        cancelButton.setName("cancelButton"); // NOI18N
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });
        buttonPanel.add(cancelButton);

        getContentPane().add(buttonPanel, java.awt.BorderLayout.SOUTH);

        entryPanel.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                entryPanelKeyTyped(evt);
            }
        });

        numberLabel.setText("Number");

        numberField.setName("numberField"); // NOI18N
        numberField.setPreferredSize(new java.awt.Dimension(100, 24));

        stationLabel.setText("Station");

        stationField.setName("stationField"); // NOI18N
        stationField.setPreferredSize(new java.awt.Dimension(100, 24));

        lineCodeLabel.setText("Line code");

        lineCodeField.setName("codeField"); // NOI18N
        lineCodeField.setPreferredSize(new java.awt.Dimension(75, 24));
        lineCodeField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lineCodeFieldActionPerformed(evt);
            }
        });

        bearingLabel.setText("Bearing");

        bearingField.setMinimumSize(new java.awt.Dimension(150, 24));
        bearingField.setName("bearingField"); // NOI18N
        bearingField.setPreferredSize(new java.awt.Dimension(150, 24));

        distanceLabel.setText("Distance");

        distanceField.setMinimumSize(new java.awt.Dimension(150, 24));
        distanceField.setName("distanceField"); // NOI18N
        distanceField.setPreferredSize(new java.awt.Dimension(150, 24));

        verticalAngleLabel.setText("Vertical angle");

        verticalAngleField.setMinimumSize(new java.awt.Dimension(150, 24));
        verticalAngleField.setName("verticalAngleField"); // NOI18N
        verticalAngleField.setPreferredSize(new java.awt.Dimension(150, 24));
        verticalAngleField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                verticalAngleFieldActionPerformed(evt);
            }
        });

        prismHeightLabel.setText("Prism height");

        prismHeightField.setName("prismHeightField"); // NOI18N
        prismHeightField.setPreferredSize(new java.awt.Dimension(150, 24));

        aCodeLabel.setText("A code");

        aCodeField.setName("aCodeField"); // NOI18N
        aCodeField.setPreferredSize(new java.awt.Dimension(150, 24));

        bCodeLabel.setText("B code");

        bCodeField.setName("bCodeField"); // NOI18N
        bCodeField.setPreferredSize(new java.awt.Dimension(150, 24));

        cCodeLabel.setText("C code");

        cCodeField.setName("cCodeField"); // NOI18N
        cCodeField.setPreferredSize(new java.awt.Dimension(150, 24));

        levelKilledCheckBox.setText("Level killed");
        levelKilledCheckBox.setName("levelKilledCheckBox"); // NOI18N

        numberStatusLabel.setText(" ");

        stationStatusLabel.setText(" ");

        lineCodeStatusLabel.setPreferredSize(new java.awt.Dimension(100, 16));

        bearingStatusLabel.setText(" ");

        distanceStatusLabel.setText(" ");

        verticalAngleStatusLabel.setText(" ");

        prismHeightStatusLabel.setText(" ");

        aCodeStatusLabel.setText(" ");

        bCodeStatusLabel.setText(" ");

        cCodeStatusLabel.setText(" ");

        plotBadLevelCheckBox.setText("Plot even if bad level");
        plotBadLevelCheckBox.setName("plotBadLevelCheckBox"); // NOI18N

        javax.swing.GroupLayout entryPanelLayout = new javax.swing.GroupLayout(entryPanel);
        entryPanel.setLayout(entryPanelLayout);
        entryPanelLayout.setHorizontalGroup(
            entryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(entryPanelLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(entryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(numberLabel, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(stationLabel, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lineCodeLabel, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(bearingLabel, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(distanceLabel, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(verticalAngleLabel, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(prismHeightLabel, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(aCodeLabel, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(bCodeLabel, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(cCodeLabel, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(entryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(plotBadLevelCheckBox)
                    .addGroup(entryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(entryPanelLayout.createSequentialGroup()
                            .addGroup(entryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(cCodeField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(bCodeField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(aCodeField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(levelKilledCheckBox)
                                .addComponent(verticalAngleField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(distanceField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(bearingField, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(entryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(cCodeStatusLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 312, Short.MAX_VALUE)
                                .addComponent(aCodeStatusLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 312, Short.MAX_VALUE)
                                .addComponent(bCodeStatusLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(verticalAngleStatusLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(distanceStatusLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(bearingStatusLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(entryPanelLayout.createSequentialGroup()
                            .addGroup(entryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(numberField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(stationField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(56, 56, 56)
                            .addGroup(entryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(numberStatusLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 273, Short.MAX_VALUE)
                                .addComponent(stationStatusLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(entryPanelLayout.createSequentialGroup()
                            .addComponent(lineCodeField, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(lineCodeStatusLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(entryPanelLayout.createSequentialGroup()
                            .addComponent(prismHeightField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(prismHeightStatusLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(26, Short.MAX_VALUE))
        );
        entryPanelLayout.setVerticalGroup(
            entryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(entryPanelLayout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addGroup(entryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(numberField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(numberLabel)
                    .addComponent(numberStatusLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(entryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(entryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(stationField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(stationLabel))
                    .addComponent(stationStatusLabel))
                .addGap(16, 16, 16)
                .addGroup(entryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lineCodeStatusLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(entryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lineCodeField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lineCodeLabel)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(entryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(bearingLabel)
                    .addGroup(entryPanelLayout.createSequentialGroup()
                        .addGroup(entryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(bearingField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(bearingStatusLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(entryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(distanceField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(distanceLabel)
                            .addComponent(distanceStatusLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(entryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(verticalAngleField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(verticalAngleLabel)
                            .addComponent(verticalAngleStatusLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(entryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(prismHeightLabel)
                            .addGroup(entryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(prismHeightField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(prismHeightStatusLabel)))))
                .addGap(13, 13, 13)
                .addGroup(entryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(aCodeLabel)
                    .addGroup(entryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(aCodeField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(aCodeStatusLabel)))
                .addGap(6, 6, 6)
                .addGroup(entryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(bCodeLabel)
                    .addGroup(entryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(bCodeField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(bCodeStatusLabel)))
                .addGap(6, 6, 6)
                .addGroup(entryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cCodeLabel)
                    .addGroup(entryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cCodeField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cCodeStatusLabel)))
                .addGap(6, 6, 6)
                .addComponent(levelKilledCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(plotBadLevelCheckBox)
                .addContainerGap())
        );

        getContentPane().add(entryPanel, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void entryPanelKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_entryPanelKeyTyped

    }//GEN-LAST:event_entryPanelKeyTyped

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        setVisible(false);
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void acceptButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_acceptButtonActionPerformed
        acceptAction();
    }//GEN-LAST:event_acceptButtonActionPerformed

    private void lineCodeFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lineCodeFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lineCodeFieldActionPerformed

    private void verticalAngleFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_verticalAngleFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_verticalAngleFieldActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField aCodeField;
    private javax.swing.JLabel aCodeLabel;
    private javax.swing.JLabel aCodeStatusLabel;
    private javax.swing.JButton acceptButton;
    private javax.swing.JTextField bCodeField;
    private javax.swing.JLabel bCodeLabel;
    private javax.swing.JLabel bCodeStatusLabel;
    private javax.swing.JTextField bearingField;
    private javax.swing.JLabel bearingLabel;
    private javax.swing.JLabel bearingStatusLabel;
    private javax.swing.JPanel buttonPanel;
    private javax.swing.JTextField cCodeField;
    private javax.swing.JLabel cCodeLabel;
    private javax.swing.JLabel cCodeStatusLabel;
    private javax.swing.JButton cancelButton;
    private javax.swing.JTextField distanceField;
    private javax.swing.JLabel distanceLabel;
    private javax.swing.JLabel distanceStatusLabel;
    private javax.swing.JPanel entryPanel;
    private javax.swing.JCheckBox levelKilledCheckBox;
    private javax.swing.JTextField lineCodeField;
    private javax.swing.JLabel lineCodeLabel;
    private javax.swing.JLabel lineCodeStatusLabel;
    private javax.swing.JTextField numberField;
    private javax.swing.JLabel numberLabel;
    private javax.swing.JLabel numberStatusLabel;
    private javax.swing.JCheckBox plotBadLevelCheckBox;
    private javax.swing.JTextField prismHeightField;
    private javax.swing.JLabel prismHeightLabel;
    private javax.swing.JLabel prismHeightStatusLabel;
    private javax.swing.JTextField stationField;
    private javax.swing.JLabel stationLabel;
    private javax.swing.JLabel stationStatusLabel;
    private javax.swing.JTextField verticalAngleField;
    private javax.swing.JLabel verticalAngleLabel;
    private javax.swing.JLabel verticalAngleStatusLabel;
    // End of variables declaration//GEN-END:variables

    public void setDetail(Detail aDetail) {
        numberField.setText(String.valueOf(aDetail.getNumber()));
        stationField.setText(aDetail.getStationNumber());
        lineCodeField.setText(aDetail.getLineCode());
        bearingField.setText(aDetail.getBearing().toDecimalString());
        distanceField.setText(String.format(DISTANCE_FORMAT, aDetail.getDistance()));
        verticalAngleField.setText(aDetail.getVerticalAngle().toDecimalString());
        prismHeightField.setText(String.format(DISTANCE_FORMAT, aDetail.getPrismHeight()));
        aCodeField.setText(String.valueOf(aDetail.getaCode()));
        bCodeField.setText(String.valueOf(aDetail.getbCode()));
        cCodeField.setText(String.valueOf(aDetail.getcCode()));
        levelKilledCheckBox.setSelected(aDetail.isLevelKilled());
        plotBadLevelCheckBox.setSelected(aDetail.isPlotIfMissingLevel());
    }

    public void clear() {
        Arrays.asList(numberField, stationField, lineCodeField, bearingField, distanceField, verticalAngleField,
                prismHeightField, aCodeField, bCodeField, cCodeField).forEach(field -> field.setText(""));
        Arrays.asList(levelKilledCheckBox, plotBadLevelCheckBox).forEach(field -> field.setSelected(false));
    }

    @Override
    public ValueMap getValueMap() {
        ValueMap values = new ValueMap();
        values.put(DETAIL_NUMBER, Integer.parseInt(numberField.getText()));
        values.put(STATION_NUMBER, stationField.getText());
        values.put(LINE_CODE, lineCodeField.getText());
        values.put(BEARING, Angle.fromDecimalDegrees(Double.parseDouble(bearingField.getText())));
        values.put(DETAIL_DISTANCE, Double.parseDouble(distanceField.getText()));
        values.put(VERTICAL_ANGLE, Angle.fromDecimalDegrees(Double.parseDouble(verticalAngleField.getText())));
        values.put(PRISM_HEIGHT, Double.parseDouble(prismHeightField.getText()));
        values.put(A_CODE, zeroIfEmpty(aCodeField.getText()));
        values.put(B_CODE, zeroIfEmpty(bCodeField.getText()));
        values.put(C_CODE, zeroIfEmpty(bCodeField.getText()));
        values.put(LEVEL_KILLED, levelKilledCheckBox.isSelected());
        values.put(PLOT_BAD_LEVEL, plotBadLevelCheckBox.isSelected());
        return values;
    }

    /**
     * @return the newDetail
     */
    public boolean isNewDetail() {
        return newDetail;
    }

    /**
     * @param newDetail the newDetail to set
     */
    public void setNewDetail(boolean newDetail) {
        this.newDetail = newDetail;
    }

    /**
     * @return the acceptanceValidator
     */
    @Override
    public AcceptanceValidator getAcceptanceValidator() {
        return acceptanceValidator;
    }

    /**
     * @param acceptanceValidator the acceptanceValidator to set
     */
    @Override
    public void setAcceptanceValidator(AcceptanceValidator acceptanceValidator) {
        this.acceptanceValidator = acceptanceValidator;
    }

    /**
     * @return the accepted
     */
    @Override
    public boolean isAccepted() {
        return accepted;
    }

    /**
     * @param accepted the accepted to set
     */
    @Override
    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    @Override
    public Map<TextComponentWrapper, ValidationLink> getValidationMap() {
        return validationMap;
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
    }

    @Override
    public void showMessage(String message, String title) {
        dialogs.showMessageDialog(title, title);
    }

    @Override
    public void cancelAction() {
        accepted = false;
        setVisible(false);
    }

    /**
     * @param dialogs the dialogs to set
     */
    public void setDialogs(Dialogs dialogs) {
        this.dialogs = dialogs;
    }
}
