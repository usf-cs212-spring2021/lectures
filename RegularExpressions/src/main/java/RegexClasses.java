/**
 * Demonstrates basic character classes in regular expressions.
 *
 * @see RegexHelper
 */
public class RegexClasses {
	/**
	 * Demonstrates basic character classes in regular expressions.
	 *
	 * @param args unused
	 */
	public static void main(String[] args) {
		// Sally Sue sells 76 sea-shells, by   the sea_shore.
		// __________0___1____2___3____4___________5___6_____
		System.out.println("Lowercase s");
		RegexHelper.showMatches(RegexHelper.sample, "s");

		System.out.println();

		// Sally Sue sells 76 sea-shells, by   the sea_shore.
		// 0_____1___2___3____4___5____6___________7___8_____
		System.out.println("Lowercase or Uppercase [sS]");
		RegexHelper.showMatches(RegexHelper.sample, "[sS]");

		System.out.println();

		// Sally Sue sells 76 sea-shells, by   the sea_shore.
		// 0_____1___2___3____4___5____6___________7___8_____
		System.out.println("Lowercase or Uppercase (?i)s");
		RegexHelper.showMatches(RegexHelper.sample, "(?i)s");

		System.out.println();

		/*
		 * The above example shows how to use the (?i) flag in a regex.
		 */

		// Sally Sue sells 76 sea-shells, by   the sea_shore.
		// _0123__45_6789A____BCD_EFGHIJ__KL___MNO_PQR_STUVW_
		System.out.println("Lowercase Letters [a-z]");
		RegexHelper.showMatches(RegexHelper.sample, "[a-z]");

		System.out.println();

		// Sally Sue sells 76 sea-shells, by   the sea_shore.
		// 0_____1___________________________________________
		System.out.println("Uppercase  Letters \\p{Upper}");
		RegexHelper.showMatches(RegexHelper.sample, "\\p{Upper}");

		System.out.println();

		/*
		 * As demonstrated above, there are several ways to specify equivalent
		 * character classes.
		 */

		// Sally Sue sells 76 sea-shells, by   the sea_shore.
		// ________________01________________________________
		System.out.println("Digit Characters \\d");
		RegexHelper.showMatches(RegexHelper.sample, "\\d");

		System.out.println();

		/*
		 * Notice in digit output above that each digit 7 and 6 are individual
		 * matches.
		 */

		// Sally Sue sells 76 sea-shells, by   the sea_shore.
		// 01234_567_89ABC_DE_FGH_IJKLMN__OP___QRS_TUVWXYZ01_
		System.out.println("Word Characters \\w");
		RegexHelper.showMatches(RegexHelper.sample, "\\w");

		System.out.println();

		// Sally Sue sells 76 sea-shells, by   the sea_shore.
		// 01234_567_89ABC_DE_FGHIJKLMNOP_QR___STU_VWXYZ01234
		System.out.println("Non-Whitespace Characters \\S");
		RegexHelper.showMatches(RegexHelper.sample, "\\S");

		System.out.println();

		/*
		 * Notice difference between word and non-whitespace characters above is
		 * whether the symbols match.
		 */

		// Sally Sue sells 76 sea-shells, by   the sea_shore.
		// _____0___1_____2__3___________4__567___8__________
		System.out.println("Whitespaces \\s");
		RegexHelper.showMatches(RegexHelper.sample, "\\s");

		System.out.println();

		// Sally Sue sells 76 sea-shells, by   the sea_shore.
		// _____0___1_____2__3___4______56__789___A_________B
		System.out.println("Non-Word Characters \\W");
		RegexHelper.showMatches(RegexHelper.sample, "\\W");

		System.out.println();

		/*
		 * Notice difference between whitespace and non-word characters above is
		 * whether the symbols match.
		 */

		// Sally Sue sells 76 sea-shells, by   the sea_shore.
		// 0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789ABCD
		System.out.println("Any Character .");
		RegexHelper.showMatches(RegexHelper.sample, ".");
	}
}
