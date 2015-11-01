package org.powerbot.cabam.runeessenceminer.tasks;

import org.powerbot.cabam.runeessenceminer.constants.Constants;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.GameObject;

public class RunToExit extends Task<ClientContext> {
	
	public RunToExit(ClientContext ctx) {
		super(ctx);
	}

	@Override
	public void execute() {
		final GameObject exitPortal = ctx.objects.id(Constants.OBJECT_EXIT_PORTAL).nearest().peek();
		ctx.movement.step(exitPortal);
	}

}
