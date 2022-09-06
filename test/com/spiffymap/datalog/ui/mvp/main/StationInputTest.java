package com.spiffymap.datalog.ui.mvp.main;

import java.awt.Window;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import net.spiffymap.datalog.client.model.DatalogFile;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import spiffy.swing.automation.SpiffySwingAutomation;
import spiffy.swing.automation.WindowAnalysis;

/**
 *
 * @author steve
 */
public class StationInputTest {

    @Test
    public void testStationInput() throws Exception {
        SpiffySwingAutomation automation = new SpiffySwingAutomation();
        try {
            Window window = Utils.open(automation);
            JTabbedPane tabbedPane = automation.findComponentOfClass(window, JTabbedPane.class);
            // Select detail data tab
            tabbedPane.setSelectedIndex(3);
            JTable table = (JTable) automation.findComponentNamed(window, "stationsTable");
            table.requestFocusInWindow();
            JButton addStationButton = automation.findComponentNamed(window, "addStationButton");
            automation.clickButton(addStationButton);
            Window inputDialog = automation.waitForWindow(JDialog.class, "Input station");
            // Validate input dialog
            List<String> unnamedList = WindowAnalysis.unnamedControls(inputDialog);
            for (String unnamed : unnamedList) {
                System.out.println("Unnamed control: " + unnamed);
            }
            assertTrue(unnamedList.isEmpty());
            Map<String, Object> valuesMap = new HashMap<>();
            valuesMap.put("numberField", "991");
            valuesMap.put("roField", "1");
            valuesMap.put("eastingField", "123.445");
            valuesMap.put("northingField", "170.112");
            valuesMap.put("levelField", "201.10");
            valuesMap.put("instrumentHeightField", "91.502");
            automation.populateDialog("Input station", valuesMap);
            automation.clickButtonNamed(inputDialog, "acceptButton");
            automation.waitForWindowNotVisible(inputDialog);
            DatalogFile file = Utils.getFile(automation, window);
            assertNotNull(file.getStationNumbered("991"));
        } finally {
            automation.closeAllWindows();
        }
    }

}
