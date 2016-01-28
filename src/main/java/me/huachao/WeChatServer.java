package me.huachao;

import me.huachao.util.http.IdleConnectionMonitorThread;
import org.eclipse.jetty.rewrite.handler.RewriteHandler;
import org.eclipse.jetty.rewrite.handler.RewritePatternRule;
import org.eclipse.jetty.rewrite.handler.RewriteRegexRule;
import org.eclipse.jetty.server.*;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.RequestLogHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * Created by huachao on 1/22/16.
 */
public class WeChatServer {

    private static Logger logger = LoggerFactory.getLogger(WeChatServer.class);

    public static void main(String[] args) {
        //server
        Server server = new Server();

        //http connector
        ServerConnector http = new ServerConnector(server);
        http.setPort(8080);

        //add connector
        server.addConnector(http);

        //urlrewrite handler
        /*
        RewriteHandler rewrite = new RewriteHandler();
        RewritePatternRule oldToNew = new RewritePatternRule();
        oldToNew.setPattern("/some/old/spingMvcHandler");
        oldToNew.setReplacement("/someAction?val1=old&val2=spingMvcHandler");
        rewrite.addRule(oldToNew);

        RewriteRegexRule reverse = new RewriteRegexRule();
        reverse.setRegex("/reverse/([^/]*)/(.*)");
        reverse.setReplacement("/reverse/$2/$1");
        rewrite.addRule(reverse);
        */


        //springmvc handler
        ServletContextHandler spingMvcHandler = new ServletContextHandler();
        spingMvcHandler.setContextPath("/");
        XmlWebApplicationContext context = new XmlWebApplicationContext();
        context.setConfigLocations(new String[]{"classpath:config/spring/appcontext-server.xml"});
        spingMvcHandler.addEventListener(new ContextLoaderListener(context));
        spingMvcHandler.addServlet(new ServletHolder(new DispatcherServlet(context)), "/*");


        //add handlerList
        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{
                //rewrite,
                spingMvcHandler,
                new DefaultHandler()
        });

        server.setHandler(handlers);
        try {
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        server.dumpStdErr();

        logger.info("Jetty Started!");

        //monitor the http idle connection monitor
        IdleConnectionMonitorThread idleConnectionMonitorThread = context.getBean(IdleConnectionMonitorThread.class);
        idleConnectionMonitorThread.start();

        try {
            server.join();
        } catch (InterruptedException e) {
            logger.warn("main thread Interrupted! Stopping Jetty Server", e);
            try {
                server.stop();
                idleConnectionMonitorThread.interrupt();
            } catch (Exception e1) {
                logger.warn("Failed to Stop Jetty Server", e);
            }
        }
    }

}
