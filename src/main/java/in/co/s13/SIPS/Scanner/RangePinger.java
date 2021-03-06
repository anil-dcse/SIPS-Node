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

import java.util.ArrayList;
import static in.co.s13.SIPS.settings.GlobalValues.*;
import in.co.s13.SIPS.tools.Util;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nika
 */
public class RangePinger implements Runnable {

    int low, up;
    ArrayList<String> temp;

    public RangePinger(int min, int max, ArrayList<String> al) {
        low = min;
        up = max;
        temp = new ArrayList<>(al);
        if (up == temp.size()) {
            up--;
//            System.OUT.println("UP equals to array , decremented");
        }
        if (up > temp.size()) {
            up = temp.size() - 1;

//            System.OUT.println("Size equals to array , decremented");
        }
//        System.OUT.println("Executing Ping on AL" + al + " from " + low + " to " + up);
    }

    @Override
    public void run() {
        Thread.currentThread().setName("Range Pinger Thread From " + low + " to " + up + " on List With Size " + temp.size());
        for (int i = low; i <= up; i++) {
            String node = temp.get(i);
            try {
                Thread.currentThread().sleep(2000);
            } catch (InterruptedException ex) {
                Logger.getLogger(RangePinger.class.getName()).log(Level.SEVERE, null, ex);
            }
            Thread p1 = new Thread(new Ping(node, ""));
            PING_REQUEST_EXECUTOR.submit(p1);
            //    p1.setPriority(Thread.NORM_PRIORITY - 1);

//            ArrayList<String> ips = node.getIpAddresses();
//            for (int j = 0; j < ips.size(); j++) {
//                String get = ips.get(j);
//
//            }
        }
        Util.appendToPingLog(LOG_LEVEL.OUTPUT, ("Executed Range Pinger Thread From " + low + " to " + up + " on List With Size " + temp.size()));
    }

}
