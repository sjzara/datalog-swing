package com.spiffymap.datalog.ui.mvp.main;

import com.spiffymap.datalog.ui.mvp.stations.StationTableModel;
import java.awt.Component;
import java.awt.Window;
import java.util.Arrays;
import java.util.List;
import java.util.Optional; 
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import net.spiffymap.datalog.client.model.Station;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import spiffy.swing.automation.SpiffySwingAutomation;

/**
 *
 * @author steve
 */
public class StationsEditTest {

    private static final Logger LOG = Logger.getLogger(StationsEditTest.class.getName());

    public StationsEditTest() {
    }

    private void testEdit(SpiffySwingAutomation automation, String buttonName, String dialogTitle, String newValue,
            Predicate<String> test) throws InterruptedException {
        Window window = automation.getVisibleWindowOfClass(DatalogFrame.class);
        automation.clickButtonNamed(window, buttonName);
        Window inputDialog = automation.waitForWindow(JDialog.class, dialogTitle);
        Optional<Component> component = automation.findComponent(inputDialog, c -> c instanceof JTextField);
        JTextField textField = (JTextField) component.get();
        textField.setText(newValue);
        automation.sleep(100);
        automation.clickButtonWithText(inputDialog, "Accept");
        automation.sleep(100);
        automation.waitForWindowNotVisible(inputDialog);
        assertTrue(test.test(newValue));
    }

    private void editTest(String buttonName, String newValue, BiPredicate<Station, String> test) throws Exception {
        SpiffySwingAutomation automation = new SpiffySwingAutomation();
        try {
            Window window = Utils.open(automation);
            Optional<JPanel> optPanel = Utils.getTabNamed(automation, window, "stationsPanel");
            assertTrue(optPanel.isPresent());
            JTable stationsTable = (JTable) automation.findComponentNamed(window, "stationsTable");
            // Check that data loaded
            automation.waitFor(() -> stationsTable.getRowCount() == 9, 1000, 10);
            // Select the second station
            stationsTable.getSelectionModel().setSelectionInterval(1, 1);
            Station station = Utils.getSelectedStation(automation);
            assertEquals(station.getNumber(), "101");
            testEdit(automation, buttonName, "Input", newValue, value -> test.test(station, value));
        } finally {
            automation.closeAllWindows();
        }
    }
    
    @Test
    public void deleteTest() throws Exception {
        final SpiffySwingAutomation automation = new SpiffySwingAutomation();
        try {
            Window window = Utils.open(automation);
            Optional<JPanel> optPanel = Utils.getTabNamed(automation, window, "stationsPanel");
            assertTrue(optPanel.isPresent());
            final JTable stationsTable = (JTable) automation.findComponentNamed(window, "stationsTable");
            // Check that data loaded
            automation.waitFor(() -> stationsTable.getRowCount() == 9, 1000, 10);
            // Select the second station
            stationsTable.getSelectionModel().setSelectionInterval(1, 1);
            Station station = Utils.getSelectedStation(automation);
            assertEquals(station.getNumber(), "101");
            // Delete it
            automation.clickButtonNamed(window, "deleteStationButton");
            Window deleteDialog = automation.waitForWindow(JDialog.class, "Delete");
            automation.clickButtonWithText(deleteDialog, "OK");
            automation.waitForWindowNotVisible(deleteDialog);
            final StationTableModel model = (StationTableModel) stationsTable.getModel();
            // Make sure station is removed
            AtomicBoolean isRemoved = new AtomicBoolean();
            SwingUtilities.invokeAndWait(() -> isRemoved.set(model.getStations().stream().anyMatch(stn -> stn.getNumber().equals("101"))));
            assertTrue(!isRemoved.get());
        } finally {
            automation.closeAllWindows();
        }
    }

    @Test
    public void testEditing() throws Exception {
        List<String> descriptions = Arrays.asList("Edit RO", "Edit easting", "Edit northing", "Edit level",
                "Edit instrument height");
        List<String> buttons = Arrays.asList("roButton", "eastingButton", "northingButton", "levelButton",
                "instrumentHeightButton");
        List<String> values = Arrays.asList("10", "500.000", "401.123", "1.232", "2.5");
        List<BiPredicate<Station, String>> testers = Arrays.asList(
                (station, roValue) -> station.getRo().equals(roValue),
                (station, eastingValue) -> Double.valueOf(eastingValue).equals(station.getEasting()),
                (station, northingValue) -> Double.valueOf(northingValue).equals(station.getNorthing()),
                (station, levelValue) -> Double.valueOf(levelValue).equals(station.getLevel()),
                (station, instrumentHeightValue) -> Double.valueOf(instrumentHeightValue).equals(station.getInstrumentHeight()));
        for (int index = 0; index < descriptions.size(); index++) {
            System.out.println("Test = " + descriptions.get(index));
            String button = buttons.get(index);
            String value = values.get(index);
            BiPredicate<Station, String> tester = testers.get(index);
            editTest(button, value, tester);
        }
    }
}
