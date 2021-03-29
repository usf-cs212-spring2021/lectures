import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Illustrates how to use a generic type, basic synchronization, and the use of
 * {@link Thread#wait()} and {@link Thread#notifyAll()} to create a thread-safe
 * data structure. Uses the produce-consumer model to demo this data structure
 * with multiple threads.
 *
 * @param <E> type of element to store in buffer
 *
 * @see MirrorDriver
 * @see BoundedBuffer
 */
public class BoundedBuffer<E> {

	/*
	 * Think of a bounded buffer as a circular buffer. For example:
	 *
	 *    0 1 2 3 4 5 6 7 8 9
	 *
	 * ...versus...
	 *
	 *    0 1 2 3
	 *    9     4
	 *    8 7 6 5
	 *
	 * We add elements at the end of the buffer, increment the "end" index. We remove elements from
	 * the "beginning" of the buffer, and increment the "beg" index. These indices wrap around the
	 * circle as necessary. If the "beg" and "end" indices are the same, then we have run out of room
	 * in the buffer.
	 */

	// https://docs.oracle.com/javase/tutorial/java/generics/restrictions.html#createArrays

	/** A circular buffer (or bounded buffer) of elements. */
	private Object[] buffer;

	/** Beginning index of circular buffer. */
	private int beg;

	/** Ending index of circular buffer. */
	private int end;

	/** Number of elements stored in buffer. */
	private int num;

	/** Maximum number of elements buffer may store. */
	private int max;

	/** Used to generate log messages. */
	private static Logger log = LogManager.getLogger();

	/**
	 * Initializes a bounded buffer capable of storing {@code bufferSize} elements
	 * at once.
	 *
	 * @param bufferSize size of buffer (should be positive)
	 */
	public BoundedBuffer(int bufferSize) {
		buffer = new Object[bufferSize];

		beg = 0;
		end = 0;
		num = 0;
		max = buffer.length;
	}

	/**
	 * Places an element into the buffer, or if full, waits until space is
	 * available.
	 *
	 * @param item to store in buffer
	 * @throws InterruptedException if unable to wait
	 */
	public synchronized void put(E item) throws InterruptedException {
		// Wait until we have space for the item.
		while (num >= max) {
			log.debug("put(): waiting until buffer not full.");
			// wait() will release the lock on "this" until notified
			this.wait();
			log.debug("put(): woke up, checking buffer.");
		}

		log.debug("put(): adding {} in buffer.", item);

		buffer[end] = item; // Place item at the end of the buffer.
		num++; // Increase the number of items stored.
		end = (end + 1) % max; // Move over 1, loop to start if necessary.

		log.debug("put(): buffer now has {} elements.", num);
		log.debug("put(): range is now ({}, {}).", beg, end);

		// Wake up any sleeping threads to re-check buffer status
		this.notifyAll();
	}

	/**
	 * Convenience method for adding multiple items at once. Not the most
	 * efficient implementation, but demonstrates that synchronized methods can
	 * call other synchronized methods since the thread already holds the
	 * appropriate lock.
	 *
	 * @param items the items to add to the bounded buffer
	 * @throws InterruptedException from {@link #put(Object)}
	 */
	public synchronized void putAll(E[] items) throws InterruptedException {
		for (E item : items) {
			this.put(item);
		}
	}

	/**
	 * Removes and returns an element from the buffer. If the buffer is empty,
	 * waits until there is an element to retrieve.
	 *
	 * @return element of type {@code E}
	 * @throws InterruptedException if unable to wait
	 */
	@SuppressWarnings("unchecked") // one time this is appropriate!
	public synchronized E get() throws InterruptedException {
		// Wait until we have an item to get
		while (num <= 0) {
			log.debug("get(): waiting until buffer not empty.");
			this.wait();
			log.debug("get(): woke up, checking buffer.");
		}

		log.debug("get(): getting {} from buffer.", buffer[beg]);

		Object item = buffer[beg]; // Get item from start of the buffer.
		num--; // Increase the number of items stored.
		beg = (beg + 1) % max; // Move over 1, loop to start if necessary.

		log.debug("put(): buffer now has {} elements.", num);
		log.debug("put(): range is now ({}, {}).", beg, end);

		// Wake up any sleeping threads to re-check buffer status
		this.notifyAll();

		// Return the item, cast as an element of type E
		// See: https://docs.oracle.com/javase/tutorial/java/generics/restrictions.html#createArrays
		return (E) item;
	}
}
