package me.cwuyi.watcher;

import me.cwuyi.constant.CasewebProperties;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Properties;

public class CasewebWatcher implements Watcher {

    private static String casewebUrl;
    private static int casewebPort;
    private static int SOCKET_TIMEOUT;

    static {
        try {
            Properties properties = new Properties();
            properties.load(CasewebWatcher.class.getResourceAsStream("/watcher.properties"));
            casewebUrl = properties.getProperty(CasewebProperties.CASEWEB_URL_PRO);
            casewebPort = Integer.parseInt(properties.getProperty(CasewebProperties.CASEWEB_PORT_PRO));
            SOCKET_TIMEOUT = Integer.parseInt(properties.getProperty(CasewebProperties.CASEWEB_SOCKET_TIMEOUT));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @return true if caseweb is alive, false if caseweb down
     */
    @Override
    public Object getEvent() {
        Socket socket = new Socket();
        SocketAddress address = new InetSocketAddress(casewebUrl, casewebPort);
        try {
            socket.connect(address, SOCKET_TIMEOUT);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return true;
    }
}
