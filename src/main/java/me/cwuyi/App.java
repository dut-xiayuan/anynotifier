package me.cwuyi;


import me.cwuyi.Monitor.BTCMonitor;
import me.cwuyi.Monitor.CasewebMonitor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class App {

    private static final int THREAD_NUMS = 2;

    public static void main(String[] args) throws Exception {

        Thread btcMonitorThread = new Thread(new BTCMonitor());
        Thread casewebMonitorThread = new Thread(new CasewebMonitor());

        ExecutorService threadPoll = Executors.newFixedThreadPool(THREAD_NUMS);

        Future future = threadPoll.submit(btcMonitorThread);
        Future future2 = threadPoll.submit(casewebMonitorThread);

        future.get();
        future2.get();

        threadPoll.shutdown();
    }
}
