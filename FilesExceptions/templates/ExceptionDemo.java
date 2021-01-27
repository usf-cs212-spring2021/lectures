import java.util.InputMismatchException;
import java.util.Scanner;

@SuppressWarnings({ "resource", "unused" })
public class ExceptionDemo {

	private static int calcPercentage(int earned, int possible) {
		return 100 * earned / possible;
	}

	private static void printResult(int earned, int possible, int percentage) {
		System.out.printf("%d/%d = %d%% %n", earned, possible, percentage);
	}

	public static void uncaughtDemo() {
		int earned = 0;
		int possible = 0;
		int percentage = 0;

		Scanner scanner = new Scanner(System.in);

		System.out.print("Enter total points earned: ");
		earned = scanner.nextInt();

		System.out.print("Enter total points possible: ");
		possible = scanner.nextInt();

		percentage = calcPercentage(earned, possible);
		printResult(earned, possible, percentage);

		scanner.close();
		System.out.println("[done]");
		
		// TODO What can go wrong?
	}

	public static void validationDemo() {
		int earned = 0;
		int possible = 0;
		int percentage = 0;

		Scanner scanner = new Scanner(System.in);

		System.out.print("Enter total points earned: ");

		if (scanner.hasNextInt()) {
			earned = scanner.nextInt();
		}
		else {
			System.err.println("Please enter integer values.");
			System.exit(1); // TODO Fix this
		}

		System.out.print("Enter total points possible: ");

		if (scanner.hasNextInt()) {
			possible = scanner.nextInt();
		}
		else {
			System.err.println("Please enter integer values.");
			return;
		}

		if (earned < 0 || possible <= 0) {
			System.err.println("Please enter non-negative values.");
			return;
		}

		percentage = calcPercentage(earned, possible);
		printResult(earned, possible, percentage);

		// TODO Finish example
		System.out.println("[done]");
	}

	public static void catchAllDemo() {
		int earned = 0;
		int possible = 0;
		int percentage = 0;

		Scanner scanner = null;

		try {
			scanner = new Scanner(System.in);

			System.out.print("Enter total points earned: ");
			earned = scanner.nextInt();

			System.out.print("Enter total points possible: ");
			possible = scanner.nextInt();

			percentage = calcPercentage(earned, possible);
			printResult(earned, possible, percentage);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		// TODO Finish example
	}

	public static void resourcefulDemo() {
		int earned = 0;
		int possible = 0;
		int percentage = 0;

		try (Scanner scanner = new Scanner(System.in);) {
			System.out.print("Enter total points earned: ");
			earned = scanner.nextInt();

			System.out.print("Enter total points possible: ");
			possible = scanner.nextInt();

			// TODO Fix validation
			percentage = calcPercentage(earned, possible);
			printResult(earned, possible, percentage);
		}
		catch (InputMismatchException e) {
			System.err.println("Please enter integer values.");
		}
		catch (ArithmeticException e) { // TODO Fix
			System.err.println("Please enter non-negative values.");
		}
		catch (Exception e) {
			// TODO Discuss
			System.err.println(e.toString());
		}

		// TODO Close resource?
		System.out.println("[done]");
	}

	public static void main(String[] args) {
		uncaughtDemo();
		// validationDemo();
		// catchAllDemo();
		// resourcefulDemo();
	}
}
