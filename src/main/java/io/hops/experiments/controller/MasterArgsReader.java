/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package io.hops.experiments.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;
import io.hops.experiments.coin.MultiFaceCoin;
import io.hops.experiments.benchmarks.common.BenchmarkType;

/**
 *
 * @author salman
 */
public class MasterArgsReader {

    private List<InetAddress> listOfSlaves = null;
    private List<String> nameNodeList = null;
    private Properties props = null;

    private MasterArgsReader() {
    }
    
    public static void printHelp(){
        System.out.println("FU");
    }

    public MasterArgsReader(String file) throws FileNotFoundException, IOException {
        props = loadPropFile(file);
        validateArgs();
    }

    private Properties loadPropFile(String file) throws FileNotFoundException, IOException {
        final String PROP_FILE = file;
        Properties props = new Properties();
        InputStream input = new FileInputStream(PROP_FILE);
        props.load(input);
        return props;
    }

    private void validateArgs() throws UnknownHostException {

        // check for the
        if (getRawCreateFilesPhaseDuration() <= 0 && getBenchMarkType() == BenchmarkType.RAW) {
            throw new IllegalArgumentException("You must write some files before testing other filesystem operations");
        }
        
        if (getInterleavedCreateFilesPercentage() <= 0 && getBenchMarkType() == BenchmarkType.INTERLEAVED) {
            throw new IllegalArgumentException("You must write some files before testing other filesystem operations");
        }
        
        if (getInterleavedCreateFilesPercentage() <= getInterleavedDeleteFilesPercentage() && getBenchMarkType() == BenchmarkType.INTERLEAVED) {
            throw new IllegalArgumentException("Delete operations can not be more than create operations");
        }


        switch (getBenchMarkType()){
            case INTERLEAVED:
            //create a coin to check the percentages
            new MultiFaceCoin(getInterleavedCreateFilesPercentage(), getInterleavedReadFilesPercentage(), getInterleavedRenameFilesPercentage(),
                getInterleavedDeleteFilesPercentage(), getInterleavedStatFilePercentage(),
                getInterleavedStatDirPercentage(), getInterleavedChmodFilesPercentage(), getInterleavedMkdirPercentage());
                break;
        }
    }

    public List<InetAddress> getListOfSlaves() throws UnknownHostException {
        if (listOfSlaves == null) {
            listOfSlaves = new ArrayList<InetAddress>();
            String listOfSlavesString = getString(ConfigKeys.LIST_OF_SLAVES_KEY, ConfigKeys.LIST_OF_SLAVES_DEFAULT);
            StringTokenizer st = new StringTokenizer(listOfSlavesString, ", ");
            while (st.hasMoreTokens()) {
                String slaveAddress = st.nextToken();
                listOfSlaves.add(InetAddress.getByName(slaveAddress));
            }
        }
        return listOfSlaves;
    }
    
//    public List<String> getListOfNameNodes(){
//        if(nameNodeList == null){
//            nameNodeList = new ArrayList<String>();
//            String nameNodeListString = getString(ConfigKeys.NAME_NODE_LIST_KEY, ConfigKeys.NAME_NODE_LIST_DEFAULT);
//            StringTokenizer st = new StringTokenizer(nameNodeListString, ", ");
//            while (st.hasMoreTokens()) {
//                String namenode = st.nextToken();
//                nameNodeList.add(namenode);
//            }
//        }
//        return nameNodeList;
//    }

    public int getSlaveListeningPort() {
        return getInt(ConfigKeys.SLAVE_LISTENING_PORT_KEY, ConfigKeys.SLAVE_LISTENING_PORT_DEFAULT);
    }

    public int getMasterListeningPort() {
        return getInt(ConfigKeys.MASTER_LISTENING_PORT_KEY, ConfigKeys.MASTER_LISTENING_PORT_DEFAULT);
    }

    public BenchmarkType getBenchMarkType() {
        String val = getString(ConfigKeys.BENCHMARK_TYPE_KEY, ConfigKeys.BENCHMARK_TYPE_DEFAULT);
        return BenchmarkType.valueOf(val);
    }

    public long getRawCreateFilesPhaseDuration() {
        return getLong(ConfigKeys.RAW_CREATE_FILES_PHASE_DURATION_KEY, ConfigKeys.RAW_CREATE_FILES_PHASE_DURATION_DEFAULT);
    }

    public int getInterleavedCreateFilesPercentage() {
        return getInt(ConfigKeys.INTLVD_CREATE_FILES_PERCENTAGE_KEY, ConfigKeys.INTLVD_CREATE_FILES_PERCENTAGE_DEFAULT);
    }

    public long getRawCreatePhaseMaxFilesToCreate() {
        return getLong(ConfigKeys.RAW_CREATE_PHASE_MAX_FILES_TO_CRAETE_KEY, ConfigKeys.RAW_CREATE_PHASE_MAX_FILES_TO_CRAETE_DEFAULT);
    }

    public long getRawReadFilesPhaseDuration() {
        return getLong(ConfigKeys.RAW_READ_FILES_PHASE_DURATION_KEY, ConfigKeys.RAW_READ_FILES_PHASE_DURATION_DEFAULT);
    }

    public int getInterleavedReadFilesPercentage() {
        return getInt(ConfigKeys.INTLVD_READ_FILES_PERCENTAGE_KEY, ConfigKeys.INTLVD_READ_FILES_PERCENTAGE_DEFAULT);
    }

    public long getRawRenameFilesPhaseDuration() {
        return getLong(ConfigKeys.RAW_RENAME_FILES_PHASE_DURATION_KEY, ConfigKeys.RAW_RENAME_FILES_PHASE_DURATION_DEFAULT);
    }

    public int getInterleavedRenameFilesPercentage() {
        return getInt(ConfigKeys.INTLVD_RENAME_FILES_PERCENTAGE_KEY, ConfigKeys.INTLVD_RENAME_FILES_PERCENTAGE_DEFAULT);
    }

    public long getRawDeleteFilesPhaseDuration() {
        return getLong(ConfigKeys.RAW_DElETE_FILES_PHASE_DURATION_KEY, ConfigKeys.RAW_DELETE_FILES_PHASE_DURATION_DEFAULT);
    }

    public int getInterleavedDeleteFilesPercentage() {
        return getInt(ConfigKeys.INTLVD_DELETE_FILES_PERCENTAGE_KEY, ConfigKeys.INTLVD_DELETE_FILES_PERCENTAGE_DEFAULT);
    }
    
    public long getRawChmodFilesPhaseDuration() {
        return getLong(ConfigKeys.RAW_CHMOD_FILES_PHASE_DURATION_KEY, ConfigKeys.RAW_CHMOD_FILES_PHASE_DURATION_DEFAULT);
    }

    public int getInterleavedChmodFilesPercentage() {
        return getInt(ConfigKeys.INTLVD_CHMOD_FILES_PERCENTAGE_KEY, ConfigKeys.INTLVD_CHMOD_FILES_PERCENTAGE_DEFAULT);
    }

    public long getRawStatFilePhaseDuration() {
        return getLong(ConfigKeys.RAW_STAT_FILE_PHASE_DURATION_KEY, ConfigKeys.RAW_STAT_FILE_PHASE_DURATION_DEFAULT);
    }

    public int getInterleavedStatFilePercentage() {
        return getInt(ConfigKeys.INTLVD_STAT_FILE_PERCENTAGE_KEY, ConfigKeys.INTLVD_STAT_FILE_PERCENTAGE_DEFAULT);
    }
    
    public long getRawStatDirPhaseDuration() {
        return getLong(ConfigKeys.RAW_STAT_DIR_PHASE_DURATION_KEY, ConfigKeys.RAW_STAT_DIR_PHASE_DURATION_DEFAULT);
    }

    public int getInterleavedStatDirPercentage() {
        return getInt(ConfigKeys.INTLVD_STAT_DIR_PERCENTAGE_KEY, ConfigKeys.INTLVD_STAT_DIR_PERCENTAGE_DEFAULT);
    }
    

    public long getRawMkdirPhaseDuration() {
        return getLong(ConfigKeys.RAW_MKDIR_PHASE_DURATION_KEY, ConfigKeys.RAW_MKDIR_PHASE_DURATION_DEFAULT);
    }

    public int getInterleavedMkdirPercentage() {
        return getInt(ConfigKeys.INTLVD_MKDIR_PERCENTAGE_KEY, ConfigKeys.INTLVD_MKDIR_PERCENTAGE_DEFAULT);
    }

    public int getBLockReportingNumOfReports(){
        return getInt(ConfigKeys.BR_NUM_REPORTS, ConfigKeys
            .BR_NUM_REPORTS_DEFAULT);
    }

    public int getBlockReportingNumOfBlocksPerReport(){
        return getInt(ConfigKeys.BR_NUM_BLOCKS_PER_REPORT, ConfigKeys
            .BR_NUM_BLOCKS_PER_REPORT_DEFAULT);
    }

    public int getBlockReportingNumOfBlocksPerFile(){
        return getInt(ConfigKeys.BR_NUM_BLOCKS_PER_FILE, ConfigKeys
            .BR_NUM_BLOCKS_PER_FILE_DEFAULT);
    }

    public int getBlockReportingMaxBlockSize(){
        return getInt(ConfigKeys.BR_MAX_BLOCK_SIZE, ConfigKeys
            .BR_MAX_BLOCK_SIZE_DEFAULT);
    }

    public int getBlockReportingNumOfFilesPerDir(){
        return getInt(ConfigKeys.BR_NUM_FILES_PER_DIR, ConfigKeys
            .BR_NUM_FILES_PER_DIR_DEFAULT);
    }

    public boolean isBlockReportingSkipCreations(){
        return getBoolean(ConfigKeys.BR_SKIP_CREATIONS, ConfigKeys
            .BR_SKIP_CREATIONS_DEFAULT);
    }

    public int getBlockReportingMaxTimeBeforeNextReport(){
        return getInt(ConfigKeys.BR_MAX_TIME_BEFORE_NEXT_REPORT, ConfigKeys
            .BR_MAX_TIME_BEFORE_NEXT_REPORT_DEFAULT);
    }

    public int getBlockReportingMinTimeBeforeNextReport(){
        return getInt(ConfigKeys.BR_MIN_TIME_BEFORE_NEXT_REPORT, ConfigKeys
            .BR_MIN_TIME_BEFORE_NEXT_REPORT_DEFAULT);
    }

    public String getBlockReportingPersistDatabase(){
        return getString(ConfigKeys.BR_PERSIST_DATABASE, ConfigKeys
            .BR_PERSIST_DATABASE_DEFAULT);
    }

    public short getReplicationFactor() {
        return getShort(ConfigKeys.REPLICATION_FACTOR_KEY, ConfigKeys.REPLICATION_FACTOR_DEFAULT);
    }

    public long getFileSize() {
        return getLong(ConfigKeys.FILE_SIZE_IN_Bytes_KEY, ConfigKeys.FILE_SIZE_IN_Bytes_DEFAULT);
    }

    public int getSlaveNumThreads() {
        return getInt(ConfigKeys.NUM_SLAVE_THREADS_KEY, ConfigKeys.NUM_SLAVE_THREADS_DEFAULT);
    }

    public String getBaseDir() {
        return getString(ConfigKeys.BASE_DIR_KEY, ConfigKeys.BASE_DIR_DEFAULT);
    }

    public boolean isSkipAllPrompt() {
        return getBoolean(ConfigKeys.SKIP_ALL_PROMPT_KEY, ConfigKeys.SKIP_ALL_PROMPT_DEFAULT);
    }

    public boolean isEnableRemoteLogging() {
        return getBoolean(ConfigKeys.ENABLE_REMOTE_LOGGING_KEY, ConfigKeys.ENABLE_REMOTE_LOGGING_DEFAULT);
    }

    public int getRemoteLogginPort() {
        return getInt(ConfigKeys.REMOTE_LOGGING_PORT_KEY, ConfigKeys.REMOTE_LOGGING_PORT_DEFAULT);
    }

    public String getResultFile() {
        return getString(ConfigKeys.RESULTS_FIlE_KEY, ConfigKeys.RESULTS_FIlE_DEFAULT);
    }

    public int getFilesToCreateInWarmUpPhase() {
        return getInt(ConfigKeys.FILES_TO_CRAETE_IN_WARM_UP_PHASE_KEY, ConfigKeys.FILES_TO_CRAETE_IN_WARM_UP_PHASE_DEFAULT);
    }

    public long getMaxInterleavedOperations() {
        return getLong(ConfigKeys.MAX_INTERLEAVED_OPERATIONS_KEY, ConfigKeys.MAX_INTERLEAVED_OPERATIONS_DEFAULT);
    }
    
    public int getWarmUpPhaseWaitTime(){
        return getInt(ConfigKeys.WARM_UP_PHASE_WAIT_TIME_KEY, ConfigKeys.WARM_UP_PHASE_WAIT_TIME_DEFAULT);
    }
    
    public String getNameNodeRpcAddress(){
        return getString(ConfigKeys.FS_DEFAULTFS_KEY, ConfigKeys.FS_DEFAULTFS_DEFAULT);
    }
    
    public String getNameNodeSelectorPolicy(){
        return getString(ConfigKeys.DFS_NAMENODE_SELECTOR_POLICY_KEY, ConfigKeys.DFS_NAMENODE_SELECTOR_POLICY_DEFAULT);
    }
    
    public long getNameNodeRefreshRate(){
        return getLong(ConfigKeys.DFS_CLIENT_REFRESH_NAMENODE_LIST_KEY, ConfigKeys.DFS_CLIENT_REFRESH_NAMENODE_LIST_DEFAULT);
    }

    private int getInt(String key, int defaultVal) {
        String val = props.getProperty(key, Integer.toString(defaultVal));
        return Integer.parseInt(val);
    }

    private long getLong(String key, long defaultVal) {
        String val = props.getProperty(key, Long.toString(defaultVal));
        return Long.parseLong(val);
    }

    private short getShort(String key, short defaultVal) {
        String val = props.getProperty(key, Short.toString(defaultVal));
        return Short.parseShort(val);
    }

    private boolean getBoolean(String key, boolean defaultVal) {
        String val = props.getProperty(key, Boolean.toString(defaultVal));
        return Boolean.parseBoolean(val);
    }

    private String getString(String key, String defaultVal) {
        return props.getProperty(key, defaultVal);
    }
}
