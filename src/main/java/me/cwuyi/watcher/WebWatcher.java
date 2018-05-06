package me.cwuyi.watcher;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

public class WebWatcher implements Watcher {

    private String webUrl;
    private int webPort;
    private int socketTimeout;

    public WebWatcher(String webUrl, int webPort, int socketTimeout) {
        this.webUrl = webUrl;
        this.webPort = webPort;
        this.socketTimeout = socketTimeout;
    }

    /**
     *
     * @return true if web is alive, false if web down
     */
    @Override
    public Object getEvent() {
        Socket socket = new Socket();
        SocketAddress address = new InetSocketAddress(webUrl, webPort);
        try {
            socket.connect(address, socketTimeout);
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
