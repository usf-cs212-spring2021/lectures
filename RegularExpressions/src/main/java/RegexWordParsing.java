/**
 * Demonstrates basic word matching in regular expressions.
 *
 * @see RegexHelper
 */
public class RegexWordParsing {
	/**
	 * Demonstrates basic word matching in regular expressions.
	 *
	 * @param args unused
	 */
	public static void main(String[] args) {
		String text = RegexHelper.sample;
		String regex = "";

		// Sally Sue sells 76 sea-shells, by   the sea_shore.
		// 0_____1___2_____3__4___5_______6____7___8_________
		// Note: The underscore does not start a new word.
		regex = "\\b\\w";
		System.out.println("Start of Word: " + regex);
		RegexHelper.showMatches(text, regex);

		System.out.println();

		// Sally Sue sells 76 sea-shells, by   the sea_shore.
		// ____0___1_____2__3___4______5___6_____7_________8_
		// Note: The dash and punctuation (except underscore) ends words. 
		regex = "\\w\\b";
		System.out.println("End of Word: " + regex);
		RegexHelper.showMatches(text, regex);

		System.out.println();

		// Sally Sue sells 76 sea-shells, by   the sea_shore.
		// 00000_111_22222_33_444_555555__66___777_888888888_
		// Note: Matches do not include spaces or punctuation.
		regex = "\\b\\w+\\b";
		System.out.println("Words (Sans Hyphen): " + regex);
		RegexHelper.showMatches(text, regex);

		System.out.println();

		// Sally Sue sells 76 sea-shells, by   the sea_shore.
		// 00000_111_22222_33_444_555555__66___777_888888888_
		// Note: Same affect as before, simplier regex.
		regex = "\\w+";
		System.out.println("Words (Sans Hyphen): " + regex);
		RegexHelper.showMatches(text, regex);

		System.out.println();

		// Sally Sue sells 76 sea-shells, by   the sea_shore.
		// 00000_111_22222_33_4444444444__55___666_777777777_
		// Note: The dash and underscore are included in the matches.
		regex = "\\b\\S+\\b";
		System.out.println("Words (With Hyphen): " + regex);
		RegexHelper.showMatches(text, regex);

		System.out.println();

		// Sally Sue sells 76 sea-shells, by   the sea_shore.
		// 00000_111_22222_33_44444444444_55___666_7777777777
		// Note: Unlike before, the simplier case (without \b) is different. It includes punctuation.
		regex = "\\S+";
		System.out.println("Words (With Symbol): " + regex);
		RegexHelper.showMatches(text, regex);

		System.out.println();

		// Sally Sue sells 76 sea-shells, by   the sea_shore.
		// 00000_111_22222____3333333333___________444444444_
		// Note: Matches any "word" that starts with "s" 
		regex = "\\b[sS]\\S+\\b";
		System.out.println("Words that start with S: " + regex);
		RegexHelper.showMatches(text, regex);

		System.out.println();

		// Sally Sue sells 76 sea-shells, by   the sea_shore.
		// ______000_11111____2222222222_______333_444444444_
		// Note: Matches any "word" that contains an "e" within.
		regex = "\\b\\S*e\\S*\\b";
		System.out.println("Words that contain an e: " + regex);
		RegexHelper.showMatches(text, regex);
	}
}
