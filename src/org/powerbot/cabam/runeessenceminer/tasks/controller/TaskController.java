package org.powerbot.cabam.runeessenceminer.tasks.controller;

import java.util.logging.Logger;

import org.powerbot.cabam.runeessenceminer.tasks.Task;
import org.powerbot.cabam.runeessenceminer.tasks.factory.TaskFactory;
import org.powerbot.script.rt6.ClientContext;

@SuppressWarnings("rawtypes")
public class TaskController {
	
	private TaskFactory taskFactory;
	private Logger logger;
	
	public TaskController(ClientContext ctx) {
		this.taskFactory = new TaskFactory(ctx);
		this.logger = Logger.getGlobal();
	}
	
	public void execute() {
		final Task task = taskFactory.getTask();
		
		logger.info("Executing task - " + task.getClass().getName());
		task.execute();
	}

}
