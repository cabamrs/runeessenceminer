package org.powerbot.cabam.runeessenceminer.tasks;

import org.powerbot.cabam.runeessenceminer.constants.Constants;
import org.powerbot.script.rt6.ClientContext;

public class RunToBank extends Task<ClientContext> {
	
	public RunToBank(ClientContext ctx) {
		super(ctx);
	}

	@Override
	public void execute() {
		ctx.movement.step(Constants.TILE_BANK);
	}

}
