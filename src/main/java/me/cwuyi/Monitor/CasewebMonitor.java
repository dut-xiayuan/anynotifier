package me.cwuyi.Monitor;

import me.cwuyi.notifier.SlackNotifier;
import me.cwuyi.watcher.CasewebWatcher;
import me.cwuyi.watcher.Watcher;

public class CasewebMonitor implements Runnable {

    private static final String MESSAGE = "Caseweb超时";
    private static long MIN_SEND_INTERVAL = 5000l;

    @Override
    public void run() {
        SlackNotifier notifier = new SlackNotifier();
        Watcher watcher = new CasewebWatcher();

        while (true) {
            if ((boolean)watcher.getEvent()) {
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
