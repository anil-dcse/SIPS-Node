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
package in.co.s13.SIPS.executor;

import in.co.s13.SIPS.datastructure.TaskDBRow;
import in.co.s13.SIPS.settings.GlobalValues;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;

/**
 *
 * @author Nika
 */
public class SendFinishMessage implements Runnable {

    String ipadd = "", pid = "", outPut = "", filename = "", value = "", cmd, chunkno, exitCode;
    double avgLoad = Double.MAX_VALUE;
    String uuid;

    public SendFinishMessage(String overheadName, String ip, String PID, String chunknumber, String Filename, String value, String ExitCode, double avgLoad, String uuid) {
        ipadd = ip;
        pid = PID;
        filename = Filename;
        this.value = value;
        cmd = overheadName;
        chunkno = chunknumber;
        exitCode = ExitCode;
        this.avgLoad = avgLoad;
        this.uuid = uuid;
    }

    @Override
    public void run() {
        int i = 0;
        boolean sent = false;
        while (!sent) {
            try (Socket s = new Socket(ipadd, GlobalValues.TASK_FINISH_LISTENER_SERVER_PORT); DataInputStream dIn = new DataInputStream(s.getInputStream());
                    OutputStream os = s.getOutputStream();
                    DataOutputStream outToServer = new DataOutputStream(os)) {
                JSONObject sendmsgJsonObj = new JSONObject();
                sendmsgJsonObj.put("Command", cmd);
                JSONObject sendmsgBodyJsonObj = new JSONObject();
                sendmsgBodyJsonObj.put("PID", pid);
                sendmsgBodyJsonObj.put("UUID", in.co.s13.sips.lib.node.settings.GlobalValues.NODE_UUID);
                sendmsgBodyJsonObj.put("CNO", chunkno);
                sendmsgBodyJsonObj.put("FILENAME", filename);
                sendmsgBodyJsonObj.put("OUTPUT", value);
                sendmsgBodyJsonObj.put("EXTCODE", exitCode);
                sendmsgBodyJsonObj.put("AVGLOAD", avgLoad);
                TaskDBRow task = GlobalValues.TASK_DB.get("" + uuid + "-ID-" + pid + "-CN-" + chunkno);
                if (task != null) {
                    sendmsgBodyJsonObj.put("TASK", task.toJSON());
                }
                sendmsgJsonObj.put("Body", sendmsgBodyJsonObj);

                String sendmsg = sendmsgJsonObj.toString();
                System.out.println("Send Overhead Message :" + sendmsgJsonObj.toString(4) + " to " + ipadd);
                byte[] bytes = sendmsg.getBytes("UTF-8");
                outToServer.writeInt(bytes.length);
                outToServer.write(bytes);

                int length = dIn.readInt();                    // read length of incoming message
                byte[] message = new byte[length];
                if (length > 0) {
                    dIn.readFully(message, 0, message.length); // read the message
                }
                String reply = new String(message);
                if (reply.contains("OK")) {
                    System.out.println("Received Reply For Finish Message: " + reply);
                    sent = true;
                } else {

                }
            } catch (IOException ex) {
                Logger.getLogger(SendOutput.class.getName()).log(Level.SEVERE, null, ex);
            }
            i++;
            if (i == 5) {
                // failed in 5 tries
                sent = true;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(SendFinishMessage.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
