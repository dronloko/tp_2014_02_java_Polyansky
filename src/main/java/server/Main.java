package server;

import org.eclipse.jetty.rewrite.handler.RedirectRegexRule;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.rewrite.handler.RewriteHandler;

/**
 * Created by dronloko 15.02.14.
 */

public class Main {

    public static void main (String[] args) throws Exception {
        Frontend frontend = new Frontend();

        Server server = new Server (8080);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        server.setHandler(context);
        context.addServlet(new ServletHolder(frontend), "/*");

        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setDirectoriesListed(true);
        resourceHandler.setResourceBase("static");


        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{resourceHandler, context});

        server.setHandler(handlers);

        server.start();
        server.join();
    }
}
