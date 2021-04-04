import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This class demonstrates a simple but inefficient approach to multithreading
 * the {@link RecursiveDirectoryListing} class. It still has a single public
 * static method, but there are several private instance methods in here to help
 * with the multithreading aspects.
 */
public class SlowMultithreadedDirectoryListing {

	/** Logger to use for this class. */
	private static final Logger log = LogManager.getLogger();

	/**
	 * Returns a directory listing for the given path.
	 *
	 * @param path directory to create listing
	 * @return paths found within directory and its subdirectories
	 * @throws InterruptedException from {@link Thread#join()}
	 */
	public static Set<Path> list(Path path) throws InterruptedException {
		HashSet<Path> paths = new HashSet<>();

		if (Files.exists(path)) {
			paths.add(path);

			if (Files.isDirectory(path)) {
				Thread worker = new Worker(path, paths);
				worker.start();

				/*
				 * Now what? How do we wait for work to be done? Well, the main thread
				 * could call join() on the worker here. But, this becomes non-ideal
				 * when we get into the run() method of our workers...
				 */

				worker.join();
			}
		}

		return paths;
	}

	/**
	 * Will list the directory or add the path creating a new worker per each
	 * subdirectory found.
	 */
	private static class Worker extends Thread {

		/** The path to add or list. */
		private final Path path;

		/** The shared set of all paths found thus far. */
		private final Set<Path> paths;

		/**
		 * Initializes this worker thread.
		 *
		 * @param path the path to add or list
		 * @param paths the shared set of all paths found thus far
		 */
		public Worker(Path path, Set<Path> paths) {
			this.path = path;
			this.paths = paths;
			log.debug("Worker for {} created.", path);
		}

		@Override
		public void run() {
			try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
				for (Path current : stream) {

					/*
					 * We have to protect all access to our shared data now! It is common
					 * to use the object you are trying to protect as the lock itself.
					 *
					 * This is really slow though, it causes tons of locking/unlocking
					 * which itself causes a bunch of blocking.
					 *
					 * We generally try to stay away from putting a synchronized block
					 * INSIDE of a loop!
					 */
					synchronized (paths) {
						paths.add(current);
					}

					if (Files.isDirectory(current)) {
						/*
						 * Instead of a recursive call, we will create a new worker to deal
						 * with the subdirectory. Our workers create new workers!
						 */
						Thread worker = new Worker(current, paths);
						worker.start();

						/*
						 * Uh oh... how to we wait for our new worker to be done? If we call
						 * join here... well, basically only 1 thread is really going to get
						 * a chance to run at a time. Check out the logs to be sure!
						 */
						worker.join();
					}
				}
			}
			catch (IOException ex) {
				/*
				 * With threads we run into an issue with exception handling. The run()
				 * method of Runnable does not allow for any checked exceptions to be
				 * thrown. If you still want to be able to throw exceptions, use the
				 * Callable interface instead. That requires the use of other built-in
				 * multithreading features in Java, so we leave it at that for now. In
				 * the meantime, you can wrap a checked IO exception in an unchecked one
				 * so it doesn't get completely lost.
				 */
				throw new UncheckedIOException(ex);
			}
			catch (InterruptedException ex) {
				/*
				 * This lets us essentially re-throw the interrupted exception. It is a
				 * bad idea to suppress interrupts entirely, but we are not actively
				 * using them yet in class.
				 */
				Thread.currentThread().interrupt();
			}

			log.debug("Worker for {} finished.", path);
		}
	}

	/**
	 * Tests the directory listing for the current directory.
	 *
	 * @param args unused
	 * @throws InterruptedException from {@link #list(Path)}
	 * @throws IOException from {@link RecursiveDirectoryListing#list(Path)}
	 */
	public static void main(String[] args) throws IOException, InterruptedException {
		Path path = Path.of(".");
		Set<Path> actual = list(path);
		Set<Path> expected = RecursiveDirectoryListing.list(path);

		System.out.println(actual.equals(expected));
	}
}
