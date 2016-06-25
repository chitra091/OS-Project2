package edu.utdallas.blockingFIFO;

import edu.utdallas.taskExecutor.Task;

public class BlockingFifoQueue {

	private Task[] queue;
	private int limit;
	private int nextin, nextout;
	private int length;

	private static final Object monitor = new Object();

	public BlockingFifoQueue(int limit) {
		this.limit = limit;
		queue = new Task[limit];
	}

	public void put(Task item) throws InterruptedException {

		// Let success show if a task was put into queue
		boolean success = false;

		// Continue to try and put until successful
		while (success == false) {

			// Start critical Section
			synchronized (monitor) {
				if (length == this.limit) {
					// Queue is full, release monitor
					monitor.wait();
				} else {
					queue[nextin] = item;
					nextin = (nextin + 1) % this.limit;
					length++;
					success = true;
					monitor.notifyAll();
				}
			}
		}

	}

	public Task take() throws InterruptedException {

		// Let success show if a task was put into queue
		boolean success = false;

		Task task = null;

		while (success == false) {

			// Start critical section
			synchronized (monitor) {
				if (length == 0) {
					// Queue is empty, release monitor
					monitor.wait();
				} else {
					task = queue[nextout];
					nextout = (nextout + 1) % this.limit;
					length--;
					success = true;
					monitor.notifyAll();
				}
			}

		}
		return task;
	}
}
