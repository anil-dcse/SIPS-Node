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

import in.co.s13.SIPS.settings.GlobalValues;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;

/**
 *
 * @author Nika
 */
public class SendSleeptime implements Runnable {

    String ipadd = "", ID = "", outPut = "", filename = "", value = "", cmd, chunkno;

    public SendSleeptime(String overheadName, String ip, String PID, String chunknumber, String Filename, String value) {
        ipadd = ip;
        ID = PID;
        filename = Filename;
        this.value = value;
        cmd = overheadName;
        chunkno = chunknumber;
        System.out.println("Sending sleep time to " + ipadd);

    }

    @Override
    public void run() {
        System.out.println("Run Sending sleep time to " + ipadd);
    try (Socket s = new Socket(ipadd, GlobalValues.TASK_SERVER_PORT); OutputStream os = s.getOutputStream(); DataOutputStream outToServer = new DataOutputStream(os); DataInputStream dIn = new DataInputStream(s.getInputStream())) {
            JSONObject msg = new JSONObject();
            JSONObject msgBody = new JSONObject();
            msgBody.put("PID", ID);
            msgBody.put("UUID", in.co.s13.sips.lib.node.settings.GlobalValues.NODE_UUID);
            msgBody.put("CNO", chunkno);
            msgBody.put("FILENAME", filename);
            msgBody.put("OUTPUT", value);
            msg.put("Command", cmd);
            msg.put("Body", msgBody);
            String sendmsg = msg.toString();
            byte[] bytes = sendmsg.getBytes("UTF-8");
            outToServer.writeInt(bytes.length);
            outToServer.write(bytes);
            System.out.println("Sending " + msg.toString() + " to " + ipadd);
            /* int length = dIn.readInt();                    // read length of incoming message
                    byte[] message = new byte[length];
                    
                    if (length > 0) {
                        dIn.readFully(message, 0, message.length); // read the message
                    }
                    String reply = new String(message);
                    if (reply.contains("OK")) {
                    } else {
                    }*/
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(SendSleeptime.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SendSleeptime.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
