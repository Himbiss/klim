package com.company.foo;

import de.himbiss.klim.rest.AuthenticationFilter;
import de.himbiss.klim.rest.EntryPoint;
import org.eclipse.jetty.security.JDBCLoginService;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.component.AbstractLifeCycle;
import org.eclipse.jetty.util.log.JavaUtilLog;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.webapp.WebAppContext;

import java.util.logging.Logger;

public class EmbedMe {
	public static void main(String[] args) throws Exception {
		int port = 1234;
		Server server = new Server(port);
		
		String wardir = "target/klim-1-SNAPSHOT";

		Log.getRootLogger().setDebugEnabled(false);
		WebAppContext context = new WebAppContext();
		context.setResourceBase(wardir);
		context.setDescriptor(wardir + "WEB-INF/web.xml");
		//context.setConfigurations(new Configuration[] {
		//	new AnnotationConfiguration(), new WebXmlConfiguration(),
		//		new WebInfConfiguration(), new TagLibConfiguration(),
		//		new PlusConfiguration(), new MetaInfConfiguration(),
		//		new FragmentConfiguration(), new EnvConfiguration() });

        ServletHolder jerseyServlet = context.addServlet(
                org.glassfish.jersey.servlet.ServletContainer.class, "/rest/*");
		jerseyServlet.setInitOrder(0);
		jerseyServlet.setInitParameter("javax.ws.rs.container.ContainerRequestFilter", AuthenticationFilter.class.getCanonicalName());

        // Tells the Jersey Servlet which REST service/class to load.
        jerseyServlet.setInitParameter(
                "jersey.config.server.provider.classnames",
                EntryPoint.class.getCanonicalName());

        jerseyServlet.setInitParameter("jersey.config.server.provider.packages", "de.himbiss.klim.rest");
		context.setContextPath("/");
		context.setParentLoaderPriority(true);
		server.setHandler(context);
		server.start();
		server.join();

	}
}
