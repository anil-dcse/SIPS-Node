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
package in.co.s13.SIPS.executor.sockets;

import in.co.s13.SIPS.datastructure.FileDownQueReq;
import in.co.s13.SIPS.datastructure.threadpools.FixedThreadPool;
import in.co.s13.SIPS.executor.sockets.handlers.FileReqQueHandler;
import in.co.s13.SIPS.settings.GlobalValues;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nika
 */
public class FileReqQueServer implements Runnable {

    public ServerSocket ss;
    public boolean serverisRunning = false;
    public static ArrayList<FileDownQueReq> downQue = new ArrayList();

    public FileReqQueServer(boolean serverisrunning) throws IOException {
        serverisRunning = serverisrunning;
        GlobalValues.FILE_HANDLER_EXECUTOR_SERVICE = new FixedThreadPool(GlobalValues.FILES_RESOLVER_LIMIT);
    }

    @Override
    public void run() {
        try {
            if (ss == null || ss.isClosed()) {
                ss = new ServerSocket(GlobalValues.FILE_QUEUE_SERVER_PORT);

            }
        } catch (IOException ex) {
            Logger.getLogger(FileReqQueServer.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        Thread.currentThread().setName("File Server Thread");

        System.out.println("File Download Que Server is running");
        while (serverisRunning) {
            try {
                Socket s = ss.accept();
                Thread t = new Thread(new FileReqQueHandler(s));
                t.setPriority(Thread.NORM_PRIORITY + 1);
                t.setName("FileHandlIngThread");
                GlobalValues.FILE_HANDLER_EXECUTOR_SERVICE.submit(t);

            } catch (IOException ex) {
                Logger.getLogger(FileReqQueServer.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
        try {
            ss.close();

        } catch (IOException ex) {
            Logger.getLogger(FileReqQueServer.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }
}
