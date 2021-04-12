import java.io.IOException;
import java.io.PrintWriter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class VisitServer {
	public static final int PORT = 8080;
	public static Logger log = LogManager.getLogger();

	// TODO Add members as necessary.

	public static void main(String[] args) throws Exception {
		Server server = new Server(PORT);

		ServletHandler handler = new ServletHandler();
		// TODO Configure VisitServer handler.

		server.setHandler(handler);
		server.start();
		server.join();
	}

	public static class VisitServlet extends HttpServlet {
		private static final long serialVersionUID = 202040;
		private static final String TITLE = "Visits";

		@Override
		protected void doGet(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
			log.info("{} handling request: {}", Thread.currentThread().getName(), request.getRequestURI());

			// TODO Handle extra requests...

			response.setContentType("text/html");

			PrintWriter out = response.getWriter();
			out.printf("<html>%n");
			out.printf("<head><title>%s</title></head>%n", TITLE);
			out.printf("<body>%n");

			// TODO Fill in VisitServlet.doGet(...) method.

			out.printf("<p>This request was handled by thread %s.</p>%n", Thread.currentThread().getName());
			out.printf("</body>%n");
			out.printf("</html>%n");

			response.setStatus(HttpServletResponse.SC_OK);
		}
	}
}
