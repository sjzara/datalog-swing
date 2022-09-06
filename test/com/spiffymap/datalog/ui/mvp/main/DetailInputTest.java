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
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import spiffy.swing.automation.SpiffySwingAutomation;
import spiffy.swing.automation.WindowAnalysis;

/**
 *
 * @author steve
 */
public class DetailInputTest {

    @Test
    public void testDetailInput() throws Exception {
        SpiffySwingAutomation automation = new SpiffySwingAutomation();
        try {
            Window window = Utils.open(automation);
            JTabbedPane tabbedPane = automation.findComponentOfClass(window, JTabbedPane.class);
            // Select detail data tab
            tabbedPane.setSelectedIndex(1);
            JTable table = (JTable) automation.findComponentNamed(window, "detailDataTable");
            table.requestFocusInWindow();
            JButton addDetailButton = automation.findComponentNamed(window, "addDetailButton");
            automation.clickButton(addDetailButton);
            Window inputDialog = automation.waitForWindow(JDialog.class, "Input detail");
            // Validate input dialog
            List<String> unnamedList = WindowAnalysis.unnamedControls(inputDialog);
            for(String unnamed : unnamedList) {
                System.out.println("Unnamed control: " + unnamed);
            }
            assertTrue(unnamedList.isEmpty());
            Map<String, Object> valuesMap = new HashMap<>();
            valuesMap.put("numberField", "991");
            valuesMap.put("stationField", "1");
            valuesMap.put("codeField", "SE22");
            valuesMap.put("bearingField", "170.1122");
            valuesMap.put("distanceField", "201.10");
            valuesMap.put("verticalAngleField", "91.5027");
            valuesMap.put("prismHeightField", "1.502");
            valuesMap.put("aCodeField", "1.5");
            valuesMap.put("bCodeField", "1.6");
            valuesMap.put("cCodeField", "1.7");
            valuesMap.put("levelKilledCheckBox", true);
            automation.populateDialog("Input detail", valuesMap);
            automation.clickButtonNamed(inputDialog, "acceptButton");
            automation.waitForWindowNotVisible(inputDialog);
            DatalogFile file = Utils.getFile(automation, window);
            assertTrue(file.getDetailWithNumber(991).isPresent());
        } finally {
            automation.closeAllWindows();
        }
    }

}
