import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.text.StringSubstitutor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CookieVisitServlet extends HttpServlet {
	private static final long serialVersionUID = 202101;
	private static final Logger log = LogManager.getLogger();
	private static final String TITLE = "Cookies!";
	
	public static final String COOKIES_OK = "Cookies";
	public static final String VISIT_DATE = "Visited";
	public static final String VISIT_COUNT = "Count";
	
	public static final String DATE_FORMAT = "hh:mm a 'on' EEEE, MMMM dd yyyy";
	public static final Path TEMPLATE_PATH = Path.of("src", "main", "resources", "cookie_visits.html");

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.info("GET " + request.getRequestURL().toString());

		if (request.getRequestURI().endsWith("favicon.ico")) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		Map<String, String> values = new HashMap<>();

		values.put("title", TITLE);
		values.put("url", request.getRequestURL().toString());
		values.put("path", request.getRequestURI());
		values.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
		values.put("thread", Thread.currentThread().getName());

		// TODO
		
		String template = Files.readString(TEMPLATE_PATH, StandardCharsets.UTF_8);
		StringSubstitutor replacer = new StringSubstitutor(values);

		PrintWriter out = response.getWriter();
		out.write(replacer.replace(template));

		response.setContentType("text/html");
		response.setStatus(HttpServletResponse.SC_OK);
		response.flushBuffer();
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.info("POST " + request.getRequestURL().toString());

		// TODO
	}

	public static Map<String, Cookie> getCookieMap(HttpServletRequest request) {
		HashMap<String, Cookie> map = new HashMap<>();
		
		// TODO

		return map;
	}

	public static void clearCookies(HttpServletRequest request, HttpServletResponse response) {
		// TODO
	}
}
