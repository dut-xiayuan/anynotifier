package me.cwuyi;


import me.cwuyi.Monitor.BTCMonitor;
import me.cwuyi.Monitor.CasewebMonitor;

public class App {

    public static void main(String[] args) throws Exception {

        Thread btcMonitorThread = new Thread(new BTCMonitor());
        Thread casewebMonitorThread = new Thread(new CasewebMonitor());

        btcMonitorThread.start();
        btcMonitorThread.join();
        casewebMonitorThread.start();
        casewebMonitorThread.join();
    }
}
