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
package in.co.s13.SIPS.Scanner;

import in.co.s13.SIPS.settings.GlobalValues;
import static in.co.s13.SIPS.settings.GlobalValues.*;
import in.co.s13.SIPS.settings.Settings;
import static in.co.s13.SIPS.tools.Util.outPrintln;
import in.co.s13.SIPS.virtualdb.LiveDBRow;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 *
 * @author Nika
 */
public class CheckLiveNodes implements Runnable {
    
    public static boolean livenodechecker = true;
    
    public CheckLiveNodes() {
        
        outPrintln("thread started");
    }
    
 
    
    @Override
    public void run() {
        
        Thread.currentThread().setName("CheckLiveNodeThread");
     //   Hashtable<String, LiveDBRow> livehosts = (GlobalValues.LIVE_NODE_DB);
        {
            if (!GlobalValues.IS_WRITING) {
                liveDBExecutor.execute(() -> {
                    /* String sql = "SELECT * FROM LIVE";
                    SQLiteJDBC livedb = new SQLiteJDBC();
                    try (ResultSet rs = livedb.select("appdb/live.db", sql)) {
                    while (rs.next()) {
                    livehosts.add(rs.getString("IP"));
                    }
                    } catch (SQLException ex) {
                    Logger.getLogger(CheckLiveNodes.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    livedb.closeConnection();
                     */
//                    LIVE_NODE_DB.stream().forEach((liveget) -> {
//                        livehosts.add(liveget.getName());
//                    });

//                    for (int i = 0; i <= livehosts.keys().size() - 1; i++) {
//                        String ip = livehosts.get(i).trim();
//                        Thread p1 = new Thread(new Ping(ip));
//                 //       p1.setPriority(Thread.NORM_PRIORITY - 1);
//                        pingExecutor.execute(p1);
//
//                    }
                    Enumeration<String> keys = GlobalValues.LIVE_NODE_DB.keys();
                    while (keys.hasMoreElements()) {
                        String key = keys.nextElement();
                        LiveDBRow liveNode = GlobalValues.LIVE_NODE_DB.get(key);
                        ArrayList<String> ips = liveNode.getIpAddresses();
                        for (int i = 0; i < ips.size(); i++) {
                            String get = ips.get(i);
                            Thread p1 = new Thread(new Ping(get, liveNode.getUuid()));
                            pingExecutor.submit(p1);
                        }
                        
                    }
                });
                
            }
        }
    }
}
