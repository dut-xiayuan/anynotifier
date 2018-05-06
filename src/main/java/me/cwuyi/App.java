package me.cwuyi;


import me.cwuyi.Monitor.BTCMonitor;
import me.cwuyi.Monitor.WebMonitor;
import me.cwuyi.constant.WebProperties;
import me.cwuyi.watcher.WebWatcher;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class App {

    private static final int THREAD_NUMS = 2;
    private static String watchUriStr;

    public static void main(String[] args) throws Exception {

        try {
            Properties properties = new Properties();
            properties.load(WebWatcher.class.getResourceAsStream("/watcher.properties"));
            watchUriStr = properties.getProperty(WebProperties.WEB_URI_PRO);
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<Future> futures = new ArrayList<>();
        ExecutorService threadPoll = Executors.newCachedThreadPool();

        Thread btcMonitorThread = new Thread(new BTCMonitor());
        futures.add(threadPoll.submit(btcMonitorThread));

        for (String watchUri : watchUriStr.split(",")) {
            String[] uriItem = watchUri.split(":");
            if (uriItem.length != 4) continue;
            String webName = uriItem[0];
            String webUrl = uriItem[1];
            int webPort = Integer.parseInt(uriItem[2]);
            int socketTimeout = Integer.parseInt(uriItem[3]);

            WebWatcher webWatcher = new WebWatcher(webUrl, webPort, socketTimeout);

            WebMonitor webMonitor = new WebMonitor(webWatcher, webName);
            futures.add(threadPoll.submit(new Thread(webMonitor)));
        }

        for (Future future : futures) {
            future.get();
        }

        threadPoll.shutdown();
    }
}
