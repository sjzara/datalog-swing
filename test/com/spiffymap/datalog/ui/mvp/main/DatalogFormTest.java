package com.spiffymap.datalog.ui.mvp.main;

import java.awt.Window;
import java.io.File;
import java.util.logging.Logger;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import spiffy.swing.automation.SpiffySwingAutomation;

/** 
 *
 * @author steve
 */
public class DatalogFormTest {

    private static final Logger LOG = Logger.getLogger(DatalogFormTest.class.getName());
    public final static String TEST_FILE = "test" + File.separator + "6700";

    public DatalogFormTest() {
    }

    @Test
    public void testExit() {
        SpiffySwingAutomation automation = new SpiffySwingAutomation();
        Window window = automation.openWindow(DatalogFrame.class);
        automation.sleep(1000);
        assertTrue(automation.clickButtonWithText(window, "Exit"));
        automation.sleep(1000);
        assertNull(automation.getOpenFrame());
    }
}
