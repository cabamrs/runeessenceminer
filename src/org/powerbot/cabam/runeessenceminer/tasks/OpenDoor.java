package org.powerbot.cabam.runeessenceminer.tasks;

import java.util.logging.Logger;

import org.powerbot.cabam.runeessenceminer.constants.Constants;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.GameObject;

public class OpenDoor extends Task<ClientContext> {
	
	private Logger logger = Logger.getGlobal();

	public OpenDoor(ClientContext ctx) {
		super(ctx);
	}

	@Override
	public void execute() {
		logger.info("Executing task - OpenDoor");
		final GameObject closedDoor = ctx.objects.select().id(Constants.OBJECT_CLOSED_DOOR).nearest().peek();
		closedDoor.interact("Open");
	}

}
