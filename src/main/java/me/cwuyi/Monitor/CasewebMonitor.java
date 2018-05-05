package me.cwuyi.Monitor;

import me.cwuyi.constant.CasewebProperties;
import me.cwuyi.notifier.SlackNotifier;
import me.cwuyi.watcher.CasewebWatcher;
import me.cwuyi.watcher.Watcher;

import java.util.Properties;

public class CasewebMonitor implements Runnable {

    private static final String MESSAGE = "Caseweb超时";
    private static long MIN_SEND_INTERVAL;

    static {
        try {
            Properties properties = new Properties();
            properties.load(CasewebWatcher.class.getResourceAsStream("/watcher.properties"));
            MIN_SEND_INTERVAL = Long.parseLong(properties.getProperty(CasewebProperties.CASEWEB_ISALIVE_SLEEPTIME));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        SlackNotifier notifier = new SlackNotifier();
        Watcher watcher = new CasewebWatcher();

        while (true) {
            if (new Boolean(watcher.getEvent().toString())) {
                try {
                    System.out.println("caseweb is alive");
                    Thread.sleep(MIN_SEND_INTERVAL);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                notifier.send(MESSAGE);
            }
        }
    }
}
