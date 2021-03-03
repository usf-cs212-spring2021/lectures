import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CharacterFinder {
	private static final Logger log = LogManager.getLogger();

	public static int findCharacter(Path file, char character, boolean ignoreCase) throws IOException {
		int count = 0;

		// TODO 

		try (BufferedReader reader = Files.newBufferedReader(file, StandardCharsets.UTF_8);) {
			String line;

			while ((line = reader.readLine()) != null) {

				if (ignoreCase) {
					line = line.toLowerCase();
				}

				for (char current : line.toCharArray()) {
					if (current == character) {
						count++;
					}
				}
			}
		}

		// TODO
		return count;
	}

	public static void main(String[] args) {
		Path sherlock = Path.of("src", "main", "resources", "pg1661.txt").normalize();

		char lowerC = 'c';
		char upperC = 'C';

		try {
			int countLowers = findCharacter(sherlock, lowerC, false);
			int countUppers = findCharacter(sherlock, upperC, false);
			int countIgnore = findCharacter(sherlock, lowerC, true);

			// TODO

			log.info("Found {} instances of \"{}\" characters.", countLowers, lowerC);
			log.info("Found {} instances of \"{}\" characters.", countUppers, upperC);
			log.info("Found {} instances of \"{}\" characters (ignore case).", countIgnore, lowerC);
		}
		catch (IOException e) {
			// TODO
		}
	}

}
