package edu.utdallas.taskExecutor;

import edu.utdallas.blockingFIFO.BlockingFifoQueue;

public class TaskRunner implements Runnable {

	private Thread t;
	private String threadname;
	private BlockingFifoQueue queue;
	private Task newTask;

	public TaskRunner(String name, BlockingFifoQueue newQueue) {
		threadname = name;
		queue = newQueue;
	}

	public void start() {
		t = new Thread(this, threadname);
		t.start();
	}

	@Override
	public void run() {

		// Run forever
		while (true) {
			try {
				newTask = queue.take();
				newTask.execute();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
