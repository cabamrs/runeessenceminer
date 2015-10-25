package org.powerbot.cabam.runeessenceminer.tasks;

import java.util.logging.Logger;

import org.powerbot.script.rt6.ClientContext;

public class Idle extends Task<ClientContext> {
	
	private Logger logger = Logger.getGlobal();
	
	public Idle(ClientContext ctx) {
		super(ctx);
	}

	@Override
	public void execute() {
		logger.info("Executing task - Idle");
	}

}
