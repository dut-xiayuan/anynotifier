package me.cwuyi.Monitor;

import me.cwuyi.constant.WebProperties;
import me.cwuyi.notifier.SlackNotifier;
import me.cwuyi.watcher.Watcher;

import java.util.Properties;

public class WebMonitor implements Runnable {

    private String timeoutMess;
    private String recoverMess;
    private static long MIN_SEND_INTERVAL;
    private Watcher watcher;
    private String webName;

    public WebMonitor(Watcher watcher, String webName) {
        this.watcher = watcher;
        this.webName = webName;
        this.timeoutMess = webName + "服务超时";
        this.recoverMess = webName + "服务正常";
    }

    static {
        try {
            Properties properties = new Properties();
            properties.load(WebMonitor.class.getResourceAsStream("/watcher.properties"));
            MIN_SEND_INTERVAL = Long.parseLong(properties.getProperty(WebProperties.WEB_ISALIVE_SLEEPTIME));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        SlackNotifier notifier = new SlackNotifier();

        boolean hasSendFailMess = false;
        boolean hasSendRecoverMess = false;

        while (true) {

            if (new Boolean(watcher.getEvent().toString())) {
                System.out.println(webName + " is alive");
                if (!hasSendRecoverMess) {
                    notifier.send(recoverMess);
                    hasSendRecoverMess = true;
                    hasSendFailMess = false;
                }
            } else {
                if (!hasSendFailMess) {
                    notifier.send(timeoutMess);
                    hasSendFailMess = true;
                    hasSendRecoverMess = false;
                }
            }

            try {
                Thread.sleep(MIN_SEND_INTERVAL);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
