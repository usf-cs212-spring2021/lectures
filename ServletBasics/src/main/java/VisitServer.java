import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * A simple example of using Jetty and servlets to track the number of visitors
 * to a web page, demonstrating that this code is run in a multi-threaded
 * setting.
 */
public class VisitServer {
	/** The hard-coded port to run this server. */
	public static final int PORT = 8080;

	/** The logger object to use (Jetty is configured via the pom.xml to use Log4j2 as well) */
	public static Logger log = LogManager.getLogger();
	
	// Keep in mind our server is multithreaded, so we need to use
	// storage safe for access by multiple threads simultaneously

	/**
	 * A thread-safe counter to track number of visits to this website since the
	 * server was started.
	 */
	private static AtomicInteger visits = new AtomicInteger();

	/**
	 * Sets up a Jetty server configured to use the servlets defined in this
	 * class.
	 *
	 * @param args unused
	 * @throws Exception if unable to start web server
	 */
	public static void main(String[] args) throws Exception {
		Server server = new Server(PORT);

		ServletHandler handler = new ServletHandler();
		handler.addServletWithMapping(VisitServlet.class, "/");

		server.setHandler(handler);
		server.start();
		server.join();
	}

	/**
	 * Increments the number of visits to this website and outputs the current
	 * value.
	 */
	public static class VisitServlet extends HttpServlet {
		/** Class version for serialization, in [YEAR][TERM] format (unused). */
		private static final long serialVersionUID = 202040;

		/** The title to use for this webpage. */
		private static final String TITLE = "Visits";

		@Override
		protected void doGet(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
			log.info("{} handling request: {}", Thread.currentThread().getName(), request.getRequestURI());

			// Check to make sure the browser is not requesting favicon.ico
			if (request.getRequestURI().endsWith("favicon.ico")) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
				return;
			}

			response.setContentType("text/html");

			PrintWriter out = response.getWriter();
			out.printf("<html>%n");
			out.printf("<head><title>%s</title></head>%n", TITLE);
			out.printf("<body>%n");

			// Safely increment the number of visits to this website
			int current = visits.incrementAndGet();
			out.printf("<p>There have been %d visits to this page.%n", current);

			// Demonstrate that this servlet is called by different threads
			out.printf("<p>This request was handled by thread %s.</p>%n", Thread.currentThread().getName());

			out.printf("</body>%n");
			out.printf("</html>%n");

			response.setStatus(HttpServletResponse.SC_OK);
		}
	}
}
