package me.huachao.util.http;

import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * Created by huachao on 1/28/16.
 */
public class IdleConnectionMonitorThread extends Thread {

    private static final Logger logger = LoggerFactory.getLogger(IdleConnectionMonitorThread.class);

    @Resource
    private PoolingHttpClientConnectionManager poolingHttpClientConnectionManager;
    private volatile boolean shutdown;

    public IdleConnectionMonitorThread() {
        super("idleConnectionMonitorThread");
    }

    @Override
    public void run() {
        try {
            while (!shutdown) {
                synchronized (this) {
                    wait(5000);
                    // Close expired connections
                    poolingHttpClientConnectionManager.closeExpiredConnections();
                    // Optionally, close connections
                    // that have been idle longer than 30 sec
                    poolingHttpClientConnectionManager.closeIdleConnections(30, TimeUnit.SECONDS);
                }
            }
        } catch (InterruptedException ex) {
            logger.warn("interrupted! shutdown this thread");
            shutdown();
        }
    }

    public void shutdown() {
        shutdown = true;
        synchronized (this) {
            notifyAll();
        }
    }

}
