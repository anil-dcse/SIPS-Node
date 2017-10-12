/*
 * Copyright (C) 2017 Navdeep Singh Sidhu
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package in.co.s13.SIPS.tools;

import in.co.s13.SIPS.Scanner.ScheduledLiveNodeScanner;
import in.co.s13.SIPS.Scanner.ScheduledNodeScanner;
import in.co.s13.SIPS.executor.sockets.PingServer;
import in.co.s13.SIPS.executor.sockets.TaskFinishListenerServer;
import in.co.s13.SIPS.settings.GlobalValues;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nika
 */
public class ServiceOperations {

    public static synchronized void initPingServerAtStartUp() {
        if (GlobalValues.PING_SERVER_ENABLED_AT_START) {
            startPingServer();
        }
    }

    public static synchronized void startPingServer() {
        GlobalValues.PING_SERVER_IS_RUNNING = true;
        if ((GlobalValues.PING_SERVER_SOCKET == null || GlobalValues.PING_SERVER_SOCKET.isClosed()) && (GlobalValues.PING_SERVER_THREAD == null || !GlobalValues.PING_SERVER_THREAD.isAlive())) {
            GlobalValues.PING_SERVER_THREAD = new Thread(new PingServer());
            GlobalValues.PING_SERVER_THREAD.start();
        }
    }

    public static synchronized void stopPingServer() {
        GlobalValues.PING_SERVER_IS_RUNNING = false;
//        if (GlobalValues.PING_SERVER_SOCKET != null && !GlobalValues.PING_SERVER_SOCKET.isClosed()) {
//            try {
//                GlobalValues.PING_SERVER_SOCKET.close();
//            } catch (IOException ex) {
//                Logger.getLogger(ServiceOperations.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
    }

    public static synchronized void restartPingServer() {
        stopPingServer();
        startPingServer();
    }

    public static synchronized void initApiServerAtStartUp() {
        if (GlobalValues.API_SERVER_ENABLED_AT_START) {
            startApiServer();
        }
    }

    public static synchronized void startApiServer() {
        GlobalValues.API_SERVER_IS_RUNNING = true;
        if ((GlobalValues.API_SERVER_SOCKET == null || GlobalValues.API_SERVER_SOCKET.isClosed()) && (GlobalValues.API_SERVER_THREAD == null || !GlobalValues.API_SERVER_THREAD.isAlive())) {
            GlobalValues.API_SERVER_THREAD = new Thread(new PingServer());
            GlobalValues.API_SERVER_THREAD.start();
        }
    }

    public static synchronized void stopApiServer() {
        GlobalValues.API_SERVER_IS_RUNNING = false;
//        if (GlobalValues.API_SERVER_SOCKET != null && !GlobalValues.API_SERVER_SOCKET.isClosed()) {
//            try {
//                GlobalValues.API_SERVER_SOCKET.close();
//            } catch (IOException ex) {
//                Logger.getLogger(ServiceOperations.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
    }

    public static synchronized void restartApiServer() {
        stopApiServer();
        startApiServer();
    }

    public static synchronized void initTaskServerAtStartUp() {
        if (GlobalValues.TASK_SERVER_ENABLED_AT_START) {
            startTaskServer();
        }
    }

    public static synchronized void startTaskServer() {
        GlobalValues.TASK_SERVER_IS_RUNNING = true;
        if ((GlobalValues.TASK_SERVER_SOCKET == null || GlobalValues.TASK_SERVER_SOCKET.isClosed()) && (GlobalValues.TASK_SERVER_THREAD == null || !GlobalValues.TASK_SERVER_THREAD.isAlive())) {
            GlobalValues.TASK_SERVER_THREAD = new Thread(new PingServer());
            GlobalValues.TASK_SERVER_THREAD.start();
        }
    }

    public static synchronized void stopTaskServer() {
        GlobalValues.TASK_SERVER_IS_RUNNING = false;
//        if (GlobalValues.TASK_SERVER_SOCKET != null && !GlobalValues.TASK_SERVER_SOCKET.isClosed()) {
//            try {
//                GlobalValues.TASK_SERVER_SOCKET.close();
//            } catch (IOException ex) {
//                Logger.getLogger(ServiceOperations.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
    }

    public static synchronized void restartTaskServer() {
        stopTaskServer();
        startTaskServer();
    }

    public static synchronized void initFileServerAtStartUp() {
        if (GlobalValues.FILE_SERVER_ENABLED_AT_START) {
            startFileServer();
        }
    }

    public static synchronized void startFileServer() {
        GlobalValues.FILE_SERVER_IS_RUNNING = true;
        if ((GlobalValues.FILE_SERVER_SOCKET == null || GlobalValues.FILE_SERVER_SOCKET.isClosed()) && (GlobalValues.FILE_SERVER_THREAD == null || !GlobalValues.FILE_SERVER_THREAD.isAlive())) {
            GlobalValues.FILE_SERVER_THREAD = new Thread(new PingServer());
            GlobalValues.FILE_SERVER_THREAD.start();
        }
    }

    public static synchronized void stopFileServer() {
        GlobalValues.FILE_SERVER_IS_RUNNING = false;
//        if (GlobalValues.FILE_SERVER_SOCKET != null && !GlobalValues.FILE_SERVER_SOCKET.isClosed()) {
//            try {
//                GlobalValues.FILE_SERVER_SOCKET.close();
//            } catch (IOException ex) {
//                Logger.getLogger(ServiceOperations.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
    }

    public static synchronized void restartFileServer() {
        stopFileServer();
        startFileServer();
    }

    public static synchronized void initLiveNodeScannerAtStartUp() {
        if (GlobalValues.LIVE_NODE_SCANNER_ENABLED_AT_START) {
            startLiveNodeScanner();
        }
    }

    public static synchronized void startLiveNodeScanner() {
        if ((GlobalValues.KEEP_LIVE_NODE_SCANNER_ALIVE == false) && (GlobalValues.CHECK_LIVE_NODE_THREAD == null || !GlobalValues.CHECK_LIVE_NODE_THREAD.isAlive())) {
            GlobalValues.KEEP_LIVE_NODE_SCANNER_ALIVE = true;
            GlobalValues.CHECK_LIVE_NODE_THREAD = new Thread(new ScheduledLiveNodeScanner());
            GlobalValues.CHECK_LIVE_NODE_THREAD.setName("Scan Live Nodes Scheduled Thread");
            GlobalValues.CHECK_LIVE_NODE_THREAD.start();
        }
    }

    public static synchronized void stopLiveNodeScanner() {
        if (GlobalValues.KEEP_LIVE_NODE_SCANNER_ALIVE == true || GlobalValues.CHECK_LIVE_NODE_THREAD.isAlive()) {
            GlobalValues.KEEP_LIVE_NODE_SCANNER_ALIVE = false;
            while (GlobalValues.CHECK_LIVE_NODE_THREAD.isAlive()) {
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ServiceOperations.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public static synchronized void restartLiveNodeScanner() {
        stopLiveNodeScanner();
        startLiveNodeScanner();
    }

    public static synchronized void initNodeScannerAtStartUp() {
        if (GlobalValues.NODE_SCANNER_ENABLED_AT_START) {
            startNodeScanner();
        }
    }

    public static synchronized void startNodeScanner() {
        if ((GlobalValues.KEEP_NODE_SCANNER_ALIVE == false) && (GlobalValues.NODE_SCANNING_THREAD == null || !GlobalValues.NODE_SCANNING_THREAD.isAlive())) {
            GlobalValues.KEEP_NODE_SCANNER_ALIVE = true;
            GlobalValues.NODE_SCANNING_THREAD = new Thread(new ScheduledNodeScanner());
            GlobalValues.NODE_SCANNING_THREAD.setName("Add Live Nodes Scheduled Thread");
            GlobalValues.NODE_SCANNING_THREAD.start();
        }
    }

    public static synchronized void stopNodeScanner() {
        if (GlobalValues.KEEP_NODE_SCANNER_ALIVE == true || GlobalValues.NODE_SCANNING_THREAD.isAlive()) {
            GlobalValues.KEEP_NODE_SCANNER_ALIVE = false;
            while (GlobalValues.NODE_SCANNING_THREAD.isAlive()) {
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ServiceOperations.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public static synchronized void restartNodeScanner() {
        stopLiveNodeScanner();
        startLiveNodeScanner();
    }

    public static synchronized void initTaskFinishListenerServerAtStartUp() {
        if (GlobalValues.TASK_FINISH_LISTENER_SERVER_ENABLED_AT_START) {
            startTaskFinishListenerServer();
        }
    }

    public static synchronized void startTaskFinishListenerServer() {
        GlobalValues.TASK_FINISH_SERVER_IS_RUNNING = true;
        if ((GlobalValues.TASK_FINISH_LISTENER_SERVER_SOCKET == null || GlobalValues.TASK_FINISH_LISTENER_SERVER_SOCKET.isClosed()) && (GlobalValues.TASK_FINISH_LISTENER_SERVER_THREAD == null || !GlobalValues.TASK_FINISH_LISTENER_SERVER_THREAD.isAlive())) {
            GlobalValues.TASK_FINISH_LISTENER_SERVER_THREAD = new Thread(new TaskFinishListenerServer());
            GlobalValues.TASK_FINISH_LISTENER_SERVER_THREAD.start();
        }
    }

    public static synchronized void stopTaskFinishListenerServer() {
        GlobalValues.TASK_FINISH_SERVER_IS_RUNNING = false;
//        if (GlobalValues.TASK_FINISH_LISTENER_SERVER_SOCKET != null && !GlobalValues.TASK_FINISH_LISTENER_SERVER_SOCKET.isClosed()) {
//            try {
//                GlobalValues.TASK_FINISH_LISTENER_SERVER_SOCKET.close();
//            } catch (IOException ex) {
//                Logger.getLogger(ServiceOperations.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
    }

    public static synchronized void restartTaskFinishListenerServer() {
        stopTaskFinishListenerServer();
        startTaskFinishListenerServer();
    }

}
