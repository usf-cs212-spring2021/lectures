import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Locale;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Demonstrates the danger of using user-input in a web application, especially
 * regarding cross-site scripting (XSS) attacks.
 */
public class HelloServer {
	/** The hard-coded port to run this server. */
	public static final int PORT = 8080;
	
	/** The logger object to use (Jetty is configured via the pom.xml to use Log4j2 as well) */
	public static Logger log = LogManager.getLogger();
	
	/**
	 * Sets up a Jetty server with two servlet mappings.
	 *
	 * @param args unused
	 * @throws Exception if unable to start web server
	 */
	public static void main(String[] args) throws Exception {
		Server server = new Server(PORT);

		ServletHandler handler = new ServletHandler();
		handler.addServletWithMapping(HelloServlet.class, "/hello");
		handler.addServletWithMapping(TodayServer.TodayServlet.class, "/today");

		server.setHandler(handler);
		server.start();
		server.join();
	}

	/**
	 * Returns the day of the week for today's date.
	 *
	 * @return the day of the week for today's date
	 */
	public static String dayOfWeek() {
		return Calendar.getInstance().getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.ENGLISH);
	}

	/**
	 * Reads user name passed via a GET query string and uses that input to say
	 * hello to the user. Demonstrates XSS attacks caused by this approach. THIS
	 * IS AN EXAMPLE OF WHAT **NOT** TO DO!
	 */
	public static class HelloServlet extends HttpServlet {
		/** Class version for serialization, in [YEAR][TERM] format (unused). */
		private static final long serialVersionUID = 202040;

		/** The title to use for this webpage. */
		private static final String TITLE = "Hello";

		@Override
		protected void doGet(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
			log.info("{} handling request: {}", Thread.currentThread().getName(), request.getRequestURI());

			response.setContentType("text/html");
			response.setStatus(HttpServletResponse.SC_OK);

			PrintWriter out = response.getWriter();
			out.printf("<html>%n");
			out.printf("<head><title>%s</title></head>%n", TITLE);
			out.printf("<body>%n");

			String name = request.getParameter("name");
			name = name == null ? "anonymous" : name;

			/*
			 * This can lead to a cross-site scripting attack! NEVER directly use
			 * input from the user in your webpage.
			 *
			 * JavaScript:
			 * ?name=<script>window.open("http://www.usfca.edu/");</script>
			 * ?name=<script>alert("Mwahaha");</script>
			 *
			 * Some browsers are smart enough to not load Javascript found within the
			 * URL request... so these examples may no longer work.
			 */

			out.printf("<h1>Hello, %s!</h1>%n", name);
			out.printf("<p>Thank you for visiting on this fine %s.</p>%n", dayOfWeek());

			out.printf("</body>%n");
			out.printf("</html>%n");
		}
	}
}
