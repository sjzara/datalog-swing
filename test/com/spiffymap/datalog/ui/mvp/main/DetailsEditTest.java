package com.spiffymap.datalog.ui.mvp.main;

import static com.spiffymap.datalog.ui.mvp.main.Utils.getTabNamed;
import static com.spiffymap.datalog.ui.mvp.main.Utils.waitForDataLoaded;
import java.awt.Component;
import java.awt.Window;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.logging.Logger;
import javax.swing.JDialog; 
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import net.spiffymap.datalog.client.model.Detail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import spiffy.swing.automation.SpiffySwingAutomation;

/**
 *
 * @author steve
 */
public class DetailsEditTest {

    private static final Logger LOG = Logger.getLogger(DetailsEditTest.class.getName());

    public DetailsEditTest() {
    }

    @Test
    public void testTab() {
        SpiffySwingAutomation automation = new SpiffySwingAutomation();
        Window window = automation.openWindow(DatalogFrame.class);
        automation.sleep(100);
        JTabbedPane tabbedPane = automation.findComponentOfClass(window, JTabbedPane.class);
        // Select detail data tab
        tabbedPane.setSelectedIndex(2);
        automation.sleep(100);
        automation.closeAllWindows();
    }

    private void goToDetail(SpiffySwingAutomation automation, Window window, int number) throws InterruptedException {
        JTabbedPane tabbedPane = automation.findComponentOfClass(window, JTabbedPane.class);
        // Select detail data tab
        tabbedPane.setSelectedIndex(1);
        JTable table = (JTable) automation.findComponentNamed(window, "detailDataTable");
        table.requestFocusInWindow();
        automation.typeChar('G');
        Window gotoDialog = automation.waitForWindow(JDialog.class, "Go to");
        Optional<Component> component = automation.findComponent(gotoDialog, c -> c instanceof JTextField);
        JTextField textField = (JTextField) component.get();
        textField.requestFocusInWindow();
        textField.setText(String.valueOf(number));
        automation.sleep(100);
        automation.clickButtonWithText(gotoDialog, "Accept");
        automation.sleep(100);
    }

    @Test
    public void testOpen() throws Exception {
        SpiffySwingAutomation automation = new SpiffySwingAutomation();
        Window window = Utils.open(automation);
        Optional<JPanel> optTab = getTabNamed(automation, window, "detailDataPanel");
        boolean result = false;
        if (optTab.isPresent()) {
            JTable detailDataTable = (JTable) automation.findComponent(optTab.get(),
                    component -> "detailDataTable".equals(component.getName())).get();
            // Check that data loaded
            result = waitForDataLoaded(automation, detailDataTable);
        }
        automation.closeAllWindows();
        assertTrue(result);
    }

    private void testEdit(SpiffySwingAutomation automation, String buttonName, String dialogTitle, String newValue,
            Supplier<String> resultTextSupplier) throws InterruptedException {
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
        String result = resultTextSupplier.get();
        assertEquals(result, newValue);
    }

    private boolean flagTest(String buttonName, Function<Detail, Boolean> resultSupplier) throws Exception {
        SpiffySwingAutomation automation = new SpiffySwingAutomation();
        try {
            Window window = Utils.open(automation);
            JTable detailDataTable = (JTable) automation.findComponentNamed(window, "detailDataTable");
            // Check that data loaded
            automation.waitFor(() -> detailDataTable.getRowCount() == 656, 1000, 10);
            goToDetail(automation, window, 10);
            automation.sleep(100);
            Detail detail = Utils.getSelectedDetail(automation);
            assertEquals(detail.getNumber(), 10);
            automation.clickButtonNamed(window, buttonName);
            automation.sleep(100);
            return resultSupplier.apply(detail);
        } finally {
            automation.closeAllWindows();
        }
    }

    private void editTest(String buttonName, String newValue, Function<Detail, Object> resultSupplier) throws Exception {
        SpiffySwingAutomation automation = new SpiffySwingAutomation();
        try {
            Window window = Utils.open(automation);
            JTable detailDataTable = (JTable) automation.findComponentNamed(window, "detailDataTable");
            // Check that data loaded
            automation.waitFor(() -> detailDataTable.getRowCount() == 656, 1000, 10);
            goToDetail(automation, window, 10);
            automation.sleep(100);
            Detail detail = Utils.getSelectedDetail(automation);
            assertEquals(detail.getNumber(), 10);
            testEdit(automation, buttonName, "Input", newValue, () -> resultSupplier.apply(detail).toString());
        } finally {
            automation.closeAllWindows();
        }
    }

    @Test
    public void testEditLineCode() throws Exception {
        System.out.println("Edit line code");
        editTest("lineCodeButton", "SE99", Detail::getLineCode);
    }

    @Test
    public void testEditStation() throws Exception {
        System.out.println("Edit station");
        editTest("detailStationButton", "22", Detail::getStationNumber);
    }

    @Test
    public void testEditBearing() throws Exception {
        System.out.println("Edit bearing");
        editTest("bearingButton", "102.3200", detail -> detail.getBearing().toDecimalString());
    }

    @Test
    public void testEditDistance() throws Exception {
        System.out.println("Edit distance");
        editTest("distanceButton", "102.32", Detail::getDistance);
    }

    @Test
    public void testEditACode() throws Exception {
        System.out.println("Edit a code");
        editTest("aCodeButton", "102.32", Detail::getaCode);
    }

    @Test
    public void testEditBCode() throws Exception {
        System.out.println("Edit b code");
        editTest("bCodeButton", "102.32", Detail::getbCode);
    }

    @Test
    public void testEditCCode() throws Exception {
        System.out.println("Edit distance");
        editTest("cCodeButton", "102.32", Detail::getcCode);
    }

    @Test
    public void testEditVerticalAngle() throws Exception {
        editTest("verticalAngleButton", "54.1212", detail -> detail.getVerticalAngle().toDecimalString());
    }

    @Test
    public void testEditPrismHeight() throws Exception {
        editTest("prismHeightButton", "1.232", Detail::getPrismHeight);
    }

    @Test
    public void testLevelKill() throws Exception {
        assertTrue(flagTest("levelKilledButton", Detail::isLevelKilled));
    }

    @Test
    public void testDelete() throws Exception {
        assertTrue(flagTest("detailDeleteButton", Detail::isDeleted));
    }
}
