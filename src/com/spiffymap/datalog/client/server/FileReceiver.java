package com.spiffymap.datalog.client.server;

import net.spiffymap.datalog.client.model.DatalogFile;

/**
 * Interface for file reception from server.
 * @author steve
 * @version 27-Nov-2013
 */
public interface FileReceiver {
    
    public void acceptFile(DatalogFile file);
    
    public void error(String errorReport);
}
