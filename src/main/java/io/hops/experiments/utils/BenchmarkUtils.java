package io.hops.experiments.utils;

import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hdfs.DistributedFileSystem;


import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.permission.FsPermission;
import io.hops.experiments.workload.generator.FilePool;
import io.hops.experiments.workload.generator.TreeFileGenerator;

public class BenchmarkUtils {

    private static ThreadLocal<DistributedFileSystem> dfsClients = new ThreadLocal<DistributedFileSystem>();
    private static ThreadLocal<FilePool> filePools = new ThreadLocal<FilePool>();
    
    private static int filePoolCount = 0;
    private static int dfsClientsCount = 0;

    public static DistributedFileSystem getDFSClient(Configuration conf) throws IOException {
        System.out.println(Thread.currentThread().getName()  +
            " get dfs ");
        DistributedFileSystem client = dfsClients.get();
        System.out.println(Thread.currentThread().getName()  +
            " dfs " + client);
        if (client == null) {
            System.out.println(Thread.currentThread().getName()  +
                " create new" );
            client = (DistributedFileSystem) FileSystem.newInstance(conf);
            System.out.println("New DSFClient created. Total :"+ ++dfsClientsCount);
            dfsClients.set(client);
        }else{
            //System.out.println("Reusing existing client");
        }
        return client;
    }

    public static FilePool getFilePool(Configuration conf, String baseDir) {
        FilePool filePool = filePools.get();
        if (filePool == null) {
            filePool = new TreeFileGenerator(baseDir,16,16,0);
            filePools.set(filePool);
            System.out.println("New FilePool created. Total :"+ ++filePoolCount);
        }else{
            //System.out.println("Reusing file pool obj");
        }
        
        return filePool;
    }
    
    public static void createFile(DistributedFileSystem dfs, Path path, short replication, final long size /*in bytes*/) throws IOException {
        FSDataOutputStream out = dfs.create(path, replication);
        if (size != 0) {
            for (long bytesWritten = 0; bytesWritten < size; bytesWritten += 4) {
                out.writeInt(1);
            }
        }
        out.close();
    }

    public static void readFile(DistributedFileSystem dfs, Path path, final long size /*in bytes*/) throws IOException {
        FSDataInputStream in = dfs.open(path);
        if (size != 0) {
            for (long bytesRead = 0; bytesRead < size; bytesRead += 4) {
                in.readInt();
            }
        }
        in.close();
    }

    public static boolean renameFile(DistributedFileSystem dfs, Path from, Path to) throws IOException {
        return dfs.rename(from, to);    
    }

    public static boolean deleteFile(DistributedFileSystem dfs, Path file) throws IOException {
        return dfs.delete(file, true);
    }
    
    public static void stat(DistributedFileSystem dfs, Path path) throws IOException {
       dfs.listStatus(path);
    }
    
    public static void chmodPath(DistributedFileSystem dfs, Path path) throws IOException {
        dfs.setPermission(path, new FsPermission((short)0777));
    }
    
    public static void mkdirs(DistributedFileSystem dfs, Path path) throws IOException {
        dfs.mkdirs(path);
    }

    public static double speedPSec(AtomicInteger ops, long startTime) {
        long timePassed = (System.currentTimeMillis() - startTime);
        double opsPerMSec = (double) (ops.get()) / (double) timePassed;
        return opsPerMSec * 1000;
    }
}
