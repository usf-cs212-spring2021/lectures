import java.net.URISyntaxException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parses an email into its local, domain, and top-level domain components. Does
 * not do full email validation. The primary purpose of this class is to demo
 * unit testing.
 */
public class EmailParser {
	/** Reconstructed email. */
	private final String email;

	/** Local component of email; appears before the "@" at sign. */
	private final String local;

	/** Domain component of the email; appears after the "@" at sign. */
	private final String domain;

	/** Top-level domain component of the domain; similar to a file extension. */
	private final String tld;

	/**
	 * Initializes each component of the specified email.
	 *
	 * @param email email to parse into components
	 * @throws URISyntaxException when the email cannot be parsed
	 */
	public EmailParser(String email) throws URISyntaxException {
		// There are many possible regular expressions.
		// Note: Validating RFC-compliant emails are pretty hard :(
		
		// String regex = "(.+)@(.+)"; // (fails because no group for tld)
		// String regex = "(.+)@(.+(\\..*))"; // (fails because period in tld)
		// String regex = "(.+)@(.+\\.(.*))"; // (fails no period in domain test)
		// String regex = "(.+?)@(.+?(?:\\.([^.]+?))?)"; // (fails multiple @ symbols test)

		String regex = "([^@]+)@([^@]+?(?:\\.([^@.]+))?)";

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

	/**
	 * Returns the local component of the email.
	 *
	 * @return local component of email
	 */
	public String getLocal() {
		return local;
	}

	/**
	 * Returns the domain component of the email.
	 *
	 * @return domain component of email
	 */
	public String getDomain() {
		return domain;
	}

	/**
	 * Returns the top-level domain of the email.
	 *
	 * @return top-level domain of email
	 */
	public String getTLD() {
		return tld;
	}

	/**
	 * Returns the reconstructed email from the local and domain components.
	 *
	 * @return reconstructed email
	 */
	public String getEmail() {
		return email;
	}

	@Override
	public String toString() {
		String format = "Email: %s, Local: %s, Domain: %s, TLD: %s";
		return format.formatted(getEmail(), getLocal(), getDomain(), getTLD());
	}
}
