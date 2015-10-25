package org.powerbot.cabam.runeessenceminer.tasks;

import java.util.logging.Logger;

import org.powerbot.cabam.runeessenceminer.constants.Constants;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.GameObject;

public class ClimbDownStaircase extends Task<ClientContext> {
	
	private Logger logger = Logger.getGlobal();
	
	public ClimbDownStaircase(ClientContext ctx) {
		super(ctx);
	}

	@Override
	public void execute() {
		logger.info("Executing task - ClimbDownStaircase");
		final GameObject staircase = ctx.objects.select().id(Constants.OBJECT_STAIRCASE).nearest().peek();
		staircase.interact("Climb-down");
	}

}
