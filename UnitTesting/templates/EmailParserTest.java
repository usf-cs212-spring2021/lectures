import java.net.URISyntaxException;

public class EmailParserTest {
	
	public void testSimple() throws URISyntaxException {
		// TODO 
	}
	

	@SuppressWarnings("unused")
	public class SimpleTest {
		private EmailParser parser;
		private String email;
		private String local;
		private String domain;
		private String tld;
		
		public void setupTest(String email, String local, String domain, String tld) throws URISyntaxException {
			this.email = email;
			this.local = local;
			this.domain = domain;
			this.tld = tld;
			
			this.parser = new EmailParser(this.email);
		}

		// TODO Fill in tests 
	}
	
	// TODO ComplexTest
	
	public class ParameterTests {
		// TODO  
//				"email@example.com",
//				"email@subdomain.example.com",
//				"first.last@example.org",
//				"first-last@example.net",
//				"first+last@example.net",
//				"1234567890@example.com",
//				"a@example.com",
//				"email@localhost"

		// TODO
//				"",
//				"hello",
//				"example.com",
//				"@example.com",
//				"example@",
//				"a@b@c",
//				"@"
	}
}
