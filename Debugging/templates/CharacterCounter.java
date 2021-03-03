import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// TODO FOLD ALL METHODS

public class CharacterCounter {

	private static int SIZE = 1000;

	@SuppressWarnings("unused")
	public static int countNoDebug(Path file) {
		int count = 0;
		int total = 0;

		char[] buffer = new char[SIZE];

		try (BufferedReader reader = Files.newBufferedReader(file, StandardCharsets.UTF_8);) {
			// TODO
		}
		catch (IOException e) {

		}

		return total;
	}

	public static int countAssertions(Path file) {
		int count = 0;
		int total = 0;

		char[] buffer = new char[SIZE];

		try (Reader reader = Files.newBufferedReader(file, StandardCharsets.UTF_8);) {
			while (count >= 0) {
				count = reader.read(buffer);
				total += count;
			}

			assert count < 0;
		}
		catch (IOException e) {

		}

		assert total >= 0;
		return total;
	}

	public static int countWithPrintln(Path file) {
		int count = 0;
		int total = 0;

		char[] buffer = new char[SIZE];

		try (Reader reader = Files.newBufferedReader(file, StandardCharsets.UTF_8);) {
			while (count >= 0) {
				count = reader.read(buffer);
				System.out.println("Read " + count + " characters into buffer.");

				total += count;
				System.out.println("Total number of characters is now " + total + ".");
			}

			System.out.println("Finished reading file.");
			assert count < 0;
		}
		catch (IOException e) {
			System.out.println(e.toString());
		}

		assert total >= 0;
		return total;
	}

	public static int countWithDebug(Path file) {
		int count = 0;
		int total = 0;

		char[] buffer = new char[SIZE];

		try (Reader reader = Files.newBufferedReader(file, StandardCharsets.UTF_8);) {
			while (count >= 0) {
				count = reader.read(buffer);
				Debug.println("Read " + count + " characters into buffer.");

				total += count;
				Debug.println("Total number of characters is now " + total + ".");
			}

			Debug.println("Finished reading file.");
			assert count < 0;
		}
		catch (IOException e) {
			Debug.println(e.toString());
		}

		assert total >= 0;
		return total;
	}

	private static Logger logger = LogManager.getLogger();
	
	public static int countWithLogging(Path file) {
		logger.debug("Counting characters in file \"{}\".", file.getFileName());

		int count = 0;
		int total = 0;

		char[] buffer = new char[SIZE];

		try (Reader reader = Files.newBufferedReader(file, StandardCharsets.UTF_8);) {
			logger.trace("Opened file successfully.");

			while (count >= 0) {
				count = reader.read(buffer);
				logger.debug("Read {} characters into buffer.", count);

				total += count;
				logger.debug("Total number of characters is now {}.", total);
			}

			logger.trace("Finished reading file.");
			assert count < 0;
		}
		catch (IOException e) {
			logger.warn("Unable to count characters in \"{}\".", file.getFileName().toString());
			logger.debug("Unable to count characters.", e);
		}

		logger.debug("Found {} characters total.", total);
		assert total >= 0;
		return total;
	}

	public static int countCharacters(Path file) {
		logger.debug("Counting characters in file \"{}\".", file.getFileName());

		int count = 0;
		int total = 0;

		char[] buffer = new char[SIZE];

		try (Reader reader = Files.newBufferedReader(file, StandardCharsets.UTF_8);) {
			logger.trace("Opened file successfully.");

			while (count >= 0) {
				total += count;
				logger.debug("Total number of characters is now {}.", total);

				count = reader.read(buffer);
				logger.debug("Read {} characters into buffer.", count);
			}

			logger.trace("Finished reading file.");
			assert count < 0;
		}
		catch (IOException e) {
			logger.warn("Unable to count characters in \"{}\".", file.getFileName().toString());
			logger.catching(Level.DEBUG, e);

			return -1;
		}

		logger.debug("Found {} characters total.", total);
		assert total >= 0;
		return total;
	}

	public static void main(String[] args) {
		Path path1 = Path.of("src");
		Path path2 = Path.of("README.md");

		System.out.println("No Output Messages:");
		System.out.println(countNoDebug(path1));
		System.out.println(countNoDebug(path2));
		System.out.println();

		// System.out.println("With Assertions:");
		// System.out.println(countAssertions(path1));
		// System.out.println(countAssertions(path2));
		// System.out.println();

		// System.out.println("With Println Statements:");
		// System.out.println(countWithPrintln(path1));
		// System.out.println(countWithPrintln(path2));
		// System.out.println();

		// Debug.on = true;
		// System.out.println("With Debug Statements:");
		// System.out.println(countWithDebug(path1));
		// System.out.println(countWithDebug(path2));
		// System.out.println();

		// System.out.println("With Logging:");
		// System.out.println(countWithLogging(path1));
		// System.out.println(countWithLogging(path2));
		// System.out.println();

		// System.out.println("Final Version:");
		// System.out.println(countCharacters(path1));
		// System.out.println(countCharacters(path2));
		// System.out.println();
	}
}
