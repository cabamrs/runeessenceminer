package org.powerbot.cabam.runeessenceminer.tasks;

import org.powerbot.cabam.runeessenceminer.constants.Constants;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.GameObject;

public class Exit extends Task<ClientContext> {
	
	public Exit(ClientContext ctx) {
		super(ctx);
	}

	@Override
	public void execute() {
		final GameObject exitPortal = ctx.objects.id(Constants.OBJECT_EXIT_PORTAL).nearest().peek();
		exitPortal.interact("Enter");
	}

}
