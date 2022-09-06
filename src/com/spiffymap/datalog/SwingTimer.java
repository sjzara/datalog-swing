package com.spiffymap.datalog;

import java.util.Timer;
import java.util.TimerTask;
import net.spiffymap.datalog.shared.SpiffyTimer;

/**
 * Implementation of SpiffyTimer for use with Swing.
 *
 * @author steve
 */
public class SwingTimer implements SpiffyTimer {

    private Runnable runCode;
    private Timer timer;

    @Override
    public void setRunCode(Runnable runCode) {
        this.runCode = runCode;
    }

    @Override
    public void scheduleRepeating(int milliseconds) {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runCode.run();
            }
        }, milliseconds, milliseconds);
    }

    @Override
    public void stop() {
        if (timer != null) {
            timer.cancel();
        }
    }

}
