package org.powerbot.cabam.runeessenceminer.tasks.controller;

import java.util.logging.Logger;

import org.powerbot.cabam.runeessenceminer.state.GameState;
import org.powerbot.cabam.runeessenceminer.state.factory.GameStateFactory;
import org.powerbot.cabam.runeessenceminer.tasks.Task;
import org.powerbot.cabam.runeessenceminer.tasks.factory.TaskFactory;
import org.powerbot.script.rt6.ClientContext;

@SuppressWarnings("rawtypes")
public class TaskController {
	
	private GameStateFactory stateFactory;
	private TaskFactory taskFactory;
	private Logger logger;
	
	public TaskController(ClientContext ctx) {
		this.stateFactory = new GameStateFactory(ctx);
		this.taskFactory = new TaskFactory(ctx);
		this.logger = Logger.getGlobal();
	}
	
	public void execute() {
		final GameState state = stateFactory.getState();
		final Task task = taskFactory.getTask(state);
		
		logger.info("Executing task - " + task.getClass().getName());
		task.execute();
	}

}
