package org.powerbot.cabam.runeessenceminer.tasks;

import org.powerbot.cabam.runeessenceminer.constants.Constants;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.GameObject;

public class ClimbDownStaircase extends Task<ClientContext> {
	
	public ClimbDownStaircase(ClientContext ctx) {
		super(ctx);
	}

	@Override
	public void execute() {
		final GameObject staircase = ctx.objects.select().id(Constants.OBJECT_STAIRCASE).nearest().peek();
		staircase.interact("Climb-down");
	}

}
