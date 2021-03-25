import java.net.URISyntaxException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailParser {
	private final String email;
	private final String local;
	private final String domain;
	private final String tld;

	public EmailParser(String email) throws URISyntaxException {
		String regex = ".+"; // TODO 

		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(email);

		if (!matcher.matches()) {
			throw new URISyntaxException(email, "Unable to parse email.");
		}

		assert matcher.groupCount() == 3;

		this.email = matcher.group(0);
		this.local = matcher.group(1);
		this.domain = matcher.group(2);
		this.tld = matcher.group(3);
	}

	public String getLocal() {
		return local;
	}

	public String getDomain() {
		return domain;
	}

	public String getTLD() {
		return tld;
	}

	public String getEmail() {
		return email;
	}

	@Override
	public String toString() {
		String format = "Email: %s, Local: %s, Domain: %s, TLD: %s";
		return format.formatted(getEmail(), getLocal(), getDomain(), getTLD());
	}
}
