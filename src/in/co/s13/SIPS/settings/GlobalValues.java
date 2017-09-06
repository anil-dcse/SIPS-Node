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
package in.co.s13.SIPS.settings;

import in.co.s13.SIPS.datastructure.Resource;
import in.co.s13.SIPS.datastructure.UniqueElementList;
import in.co.s13.SIPS.datastructure.threadpools.FixedThreadPool;
import in.co.s13.SIPS.db.OLDSQLiteJDBC;
import in.co.s13.SIPS.db.SQLiteJDBC;
import in.co.s13.SIPS.virtualdb.LiveDBRow;
import in.co.s13.SIPS.virtualdb.NodeDBRow;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author nika
 */
public class GlobalValues {

    /**
     * Meta Info Operating System
     */
    public static String OS = System.getProperty("os.name").toLowerCase();
    public static int OS_Name = 0;

    /**
     * **
     * Directories
     */
    public static String PWD = "";
    public static String dir_workspace = "";
    public static String dir_appdb = "appdb";
    public static String dir_temp = "var";

    /**
     * *
     * Resource Stats
     */
    public static int TASK_ID = 0;
    public static SQLiteJDBC TASK_DB = new SQLiteJDBC();
    public static String HOST_NAME = "DummySlave";
    public static String NODE_UUID = "";
    public static long MEM_SIZE = 0L, MEM_FREE = 0L, HDD_SIZE = 0L, HDD_FREE = 0L;
    public static String CPU_NAME = "";
    public static double CPU_LOAD_AVG = 0.0;
    public static JSONObject BENCHMARKING;
    public static Hashtable<String, Resource> resources = new Hashtable<>(5);
    public static JSONArray ipAddresses = new JSONArray();
    public static boolean SHARED_STORAGE = false;
    /**
     * Log Files and variables
     */
    public static boolean VERBOSE, DUMP_LOG;
    public static String OUT_FILE = dir_appdb + "/out.log", ERR_FILE = dir_appdb + "/err.log", LOG_FILE = dir_appdb + "/app.log";
    public static PrintStream out, err, log;

    /**
     * all Node DB
     */
    public static OLDSQLiteJDBC alldb;

    /**
     * Executor Limits
     */
    public static int TOTAL_THREADS = 3;
    public static int FILES_RESOLVER_LIMIT = 10;
    public static int PING_HANDLER_LIMIT = 10;
    public static int PING_REQUEST_LIMIT = 3;
    public static int API_HANDLER_LIMIT = 10;
    public static int TASK_HANDLER_LIMIT = 10;
    public static int TASK_LIMIT = Runtime.getRuntime().availableProcessors() - 2;
    public static int TASK_WAITING = 0;
    /**
     * *
     * Executors
     */
    //    public static ExecutorService NETWORK_EXECUTOR;
    //    public static ExecutorService TASK_EXECUTOR = Executors.newFixedThreadPool(TASK_LIMIT);
    public static ExecutorService NODE_SCANNER_EXECUTOR = Executors.newFixedThreadPool(2);
    public static FixedThreadPool NETWORK_EXECUTOR;
    public static FixedThreadPool TASK_EXECUTOR;
    public static FixedThreadPool PING_REQUEST_EXECUTOR;
    public static ExecutorService NODE_DB_EXECUTOR = Executors.newFixedThreadPool(1);
    public static ExecutorService TASK_DB_EXECUTOR = Executors.newFixedThreadPool(1);
    public static ExecutorService LIVE_DB_EXECUTOR = Executors.newFixedThreadPool(1);

    /**
     * Server ThreadPools
     */
    public static FixedThreadPool API_HANDLER_EXECUTOR_SERVICE, PING_HANDLER_EXECUTOR_SERVICE, FILE_HANDLER_EXECUTOR_SERVICE, TASK_HANDLER_EXECUTOR_SERVICE;

    /**
     * *
     * Storage Data Structure
     */
    /**
     * **
     * Networking Vars
     */
    //  public static ArrayList<String> livehosts = new ArrayList();
    //  public static ObservableList<LiveNode> liveNodes = FXCollections.observableArrayList();
    public static Hashtable<String, LiveDBRow> LIVE_NODE_DB = new Hashtable<>();
    public static Hashtable<String, LiveDBRow> NON_ADJ_LIVE_NODE_DB = new Hashtable<>();
    public static Hashtable<String, NodeDBRow> ALL_NODE_DB = new Hashtable<>();
    public static ArrayList<String> HOSTS = new ArrayList<>();
    public static Hashtable<String, String> CURRENTLY_SCANNING = new Hashtable<>();
    public static Hashtable<String, String> BLACKLIST = new Hashtable<>();
    public static Hashtable<String, JSONObject> API_LIST = new Hashtable<>();
    public static boolean IS_WRITING = false;
    public static JSONObject blacklistJSON, networksToScanJSON, ipToScanJSON, API_JSON;
    public static int THREAD_NUMBER = TOTAL_THREADS;
    public static Hashtable<String, String> routingTable = new Hashtable<>();
    public static Hashtable<String, Long> ADJACENT_NODES_TABLE = new Hashtable<>();
    public static Hashtable<String, UniqueElementList> NON_ADJACENT_NODES_TABLE = new Hashtable<>();

    public static int PING_SERVER_PORT = 13131, FILE_QUEUE_SERVER_PORT = 13132, TASK_SERVER_PORT = 13133, API_SERVER_PORT = 13139;

}
