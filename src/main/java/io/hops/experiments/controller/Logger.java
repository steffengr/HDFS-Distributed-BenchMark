/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package io.hops.experiments.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.concurrent.atomic.AtomicLong;

/**
 *
 * @author salman
 */
public class Logger {

    static AtomicLong lastmsg = new AtomicLong(System.currentTimeMillis());
    private static InetAddress loggerIp = null;
    private static int loggerPort = 0;
    private static boolean enableRemoteLogging = false;
    private static DatagramSocket socket = null;

    public static void error(Exception e) throws IOException{
        StringWriter writer  = new StringWriter();
        e.printStackTrace(new PrintWriter(writer));
        printMsg("Exception -> " + writer.toString());
        writer.close();
    }
    
    public static void printMsg(String msg) throws IOException {
        if (enableRemoteLogging) {
            
            if(socket == null){
                socket = new DatagramSocket();
            }
            
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ObjectOutputStream os = new ObjectOutputStream(outputStream);
            os.writeObject(msg);
            byte[] data = outputStream.toByteArray();

            DatagramPacket packet = new DatagramPacket(data, data.length,
                    loggerIp, loggerPort);
            socket.send(packet);
        }
        System.out.println(msg);
    }

    public static boolean canILog() {
        if ((System.currentTimeMillis() - lastmsg.get()) > 5000) {
            lastmsg.set(System.currentTimeMillis());
            return true;
        } else {
            return false;
        }
    }

    public static void setLoggerIp(InetAddress loggerIp) {
        Logger.loggerIp = loggerIp;
    }

    public static void setLoggerPort(int loggerPort) {
        Logger.loggerPort = loggerPort;
    }

    public static void setEnableRemoteLogging(boolean enableRemoteLogging) {
        Logger.enableRemoteLogging = enableRemoteLogging;
    }

    public static class LogListener implements Runnable {

        private int port;
        private boolean running = true;

        public LogListener(int port) {
            this.port = port;
        }

        @Override
        public void run() {
            try {
                DatagramSocket socket = new DatagramSocket(port, InetAddress.getByName("0.0.0.0"));

                while (running) {
                    byte[] recvData = new byte[ConfigKeys.BUFFER_SIZE];
                    DatagramPacket recvPacket = new DatagramPacket(recvData, recvData.length);
                    socket.receive(recvPacket);

                    byte[] data = recvPacket.getData();
                    ByteArrayInputStream in = new ByteArrayInputStream(data);
                    ObjectInputStream is = new ObjectInputStream(in);
                    String msg = (String) is.readObject();
                    
                    System.out.println(recvPacket.getAddress().getHostName()+" -> "+msg);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void stop() {
            this.running = false;
        }
    }
}
