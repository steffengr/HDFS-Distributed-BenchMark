/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package io.hops.experiments.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author salman
 */
public class SlaveArgsReader {

    private int slaveListeningPort = 0;
    
    public SlaveArgsReader(String configFilePath) throws FileNotFoundException, IOException {
        loadPropFile(configFilePath);
    }
    
        private Properties loadPropFile(String configFilePath) throws FileNotFoundException, IOException {
        final String PROP_FILE = configFilePath;
        Properties props = new Properties();
        InputStream input = new FileInputStream(PROP_FILE);
        props.load(input);
        slaveListeningPort = Integer.parseInt(props.getProperty("slave.listening.port"));
        return props;
    }

    public int getSlaveListeningPort() {
        return slaveListeningPort;
    }  
}
