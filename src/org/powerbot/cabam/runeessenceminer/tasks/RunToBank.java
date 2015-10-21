package org.powerbot.cabam.runeessenceminer.tasks;

import java.util.logging.Logger;

import org.powerbot.cabam.runeessenceminer.util.Constants;
import org.powerbot.cabam.runeessenceminer.util.Task;
import org.powerbot.script.rt6.ClientContext;

public class RunToBank extends Task<ClientContext> {
	
	private Logger logger = Logger.getGlobal();

	public RunToBank(ClientContext ctx) {
		super(ctx);
	}

	public boolean activate() {
		return !ctx.backpack.select().isEmpty()
				&& ctx.players.local().tile().distanceTo(ctx.bank.nearest()) < 100
				&& !ctx.bank.inViewport();
	}

	@Override
	public void execute() {
		logger.info("Executing task - RunToBank");
		ctx.movement.step(Constants.TILE_BANK);
	}

}
