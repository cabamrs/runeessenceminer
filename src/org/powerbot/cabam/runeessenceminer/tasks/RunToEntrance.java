package org.powerbot.cabam.runeessenceminer.tasks;

import org.powerbot.cabam.runeessenceminer.constants.Constants;
import org.powerbot.script.rt6.ClientContext;

public class RunToEntrance extends Task<ClientContext> {
	
	public RunToEntrance(ClientContext ctx) {
		super(ctx);
	}

	@Override
	public void execute() {
		ctx.movement.step(Constants.TILE_ENTRANCE);
	}

}
