import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.ServletContextHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class GalleryServer {
	public static final int PORT = 8080;
	public static Logger log = LogManager.getLogger();

	public static void main(String[] args) throws Exception {
		Server server = new Server(PORT);

		ContextHandler resourceContext = new ContextHandler("/images");
		ServletContextHandler servletContext = new ServletContextHandler();

		// TODO

		HandlerList handlers = new HandlerList();
		handlers.addHandler(resourceContext);
		handlers.addHandler(servletContext);

		server.setHandler(handlers);
		server.start();
		server.join();
	}

	public static class GalleryServlet extends HttpServlet {
		private static final long serialVersionUID = 202040;
		private static final String TITLE = "Gallery";
		private static final Path ROOT = Path.of("foto");

		@Override
		protected void doGet(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
			log.info("{} handling request: {}", Thread.currentThread().getName(), request.getRequestURI());
			response.setContentType("text/html");

			PrintWriter out = response.getWriter();
			out.printf("<html>%n");
			out.printf("<head><title>%s</title></head>%n", TITLE);
			out.printf("<body>%n");

			// TODO
			out.println(ROOT);

			out.printf("<p>This request was handled by thread %s.</p>%n", Thread.currentThread().getName());
			out.printf("</body>%n");
			out.printf("</html>%n");

			response.setStatus(HttpServletResponse.SC_OK);
		}
	}
}
