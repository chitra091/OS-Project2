package edu.utdallas.taskExecutorImpl;

import edu.utdallas.blockingFIFO.BlockingFifoQueue;
import edu.utdallas.taskExecutor.Task;
import edu.utdallas.taskExecutor.TaskExecutor;
import edu.utdallas.taskExecutor.TaskRunner;

public class TaskExecutorImpl implements TaskExecutor {

	private TaskRunner taskrunner;
	private BlockingFifoQueue queue = null;

	public TaskExecutorImpl(int i) {
		// TODO Auto-generated constructor stub
		queue = new BlockingFifoQueue(100);
		for (int idx = 0; idx < i; idx++) {
			taskrunner = new TaskRunner("thread" + idx, queue);
			taskrunner.start();
		}
	}

	@Override
	public void addTask(Task task) {
		if (task != null) {
			try {
				queue.put(task);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
