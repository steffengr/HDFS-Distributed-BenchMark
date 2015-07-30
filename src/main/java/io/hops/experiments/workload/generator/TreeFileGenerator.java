/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package io.hops.experiments.workload.generator;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 *
 * @author salman
 */
public class TreeFileGenerator implements FilePool {

    private Random rand;
    private UUID uuid = null;
    protected List<String> allThreadFiles;
    protected List<String> allThreadDirs;
    protected String threadDir;
    private NameSpaceGenerator nameSpaceGenerator;

    public TreeFileGenerator(String baseDir, int filesPerDir, int dirPerDir, int addedDepth) {
        this.allThreadFiles = new LinkedList<String>();
        this.allThreadDirs = new LinkedList<String>();
        this.rand = new Random(System.currentTimeMillis());
        uuid = UUID.randomUUID();
        
        
        String machineName = "";
        try {
            machineName = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            machineName = "Client_Machine+" + rand.nextInt();
        }

        if (!baseDir.endsWith("/")) {
            threadDir = baseDir + "/";
        }
        threadDir = threadDir + "_" + machineName + "_" + uuid;

        String[] comp = PathUtils.getPathNames(threadDir);

        if (comp.length < addedDepth) {
            for (int i = comp.length; i < (addedDepth); i++) {
                threadDir += "/depth_" + i;
            }
        }
        
        nameSpaceGenerator = new NameSpaceGenerator(threadDir, filesPerDir, dirPerDir);
    }

    @Override
    public String getDirToCreate() {
        String path = nameSpaceGenerator.generateNewDirPath();
        allThreadDirs.add(path);
        //System.out.println("Create Dir "+path);
        return path;
    }

    @Override
    public String getFileToCreate() {
        String path = nameSpaceGenerator.getFileToCreate();
        allThreadFiles.add(path);
        
        //System.out.println("Create Path "+path);
        return path;
    }

    @Override
    public void fileCreationFailed(String file) {
        allThreadFiles.remove(file);
    }

    @Override
    public String getFileToRead() {
        if (allThreadFiles.isEmpty()) {
            return null;
        }
        int readIndex = rand.nextInt(allThreadFiles.size());
        String path = allThreadFiles.get(readIndex);
        //System.out.println("Read Path "+path);
        return path;
    }

    @Override
    public String getFileToRename() {
        if (allThreadFiles.isEmpty()) {
            return null;
        }
        int renameIndex = rand.nextInt(allThreadFiles.size());
        String path = allThreadFiles.get(renameIndex);
        //System.out.println("Rename path "+path);
        return path;
    }

    @Override
    public void fileRenamed(String from, String to) {
        int index = allThreadFiles.indexOf(from);
        allThreadFiles.remove(index);
        allThreadFiles.add(index, to);
    }

    @Override
    public String getFileToDelete() {
        if (allThreadFiles.isEmpty()) {
            return null;
        }
        int deleteIndex = rand.nextInt(allThreadFiles.size());
        String file = allThreadFiles.remove(deleteIndex);
        //System.out.println("Delete Path "+file);
        return file;
    }

    @Override
    public String getDirToStat() {
        if (allThreadFiles.isEmpty()) {
            return null;
        }
        int index = rand.nextInt(allThreadFiles.size());
        String path = allThreadFiles.get(index);
        int dirIndex = path.lastIndexOf("/");
        path = path.substring(0, dirIndex);
        //System.out.println("List Path "+path);
        return path;
    }
    
    
    @Override
    public String getFileToStat() {
        if (allThreadFiles.isEmpty()) {
            return null;
        }
        int index = rand.nextInt(allThreadFiles.size());
        String path = allThreadFiles.get(index);
        //System.out.println("List Path "+path);
        return path;
    }

    @Override
    public String getPathToChangePermissions() {
        if (allThreadFiles.isEmpty()) {
            return null;
        }
        int index = rand.nextInt(allThreadFiles.size());
        String path = allThreadFiles.get(index);
        //System.out.println("Chmod Path "+path);
        return path;
    }
}
