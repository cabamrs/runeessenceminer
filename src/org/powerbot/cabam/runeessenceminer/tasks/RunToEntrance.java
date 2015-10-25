package org.powerbot.cabam.runeessenceminer.tasks;

import java.util.logging.Logger;

import org.powerbot.cabam.runeessenceminer.util.Constants;
import org.powerbot.script.rt6.ClientContext;

public class RunToEntrance extends Task<ClientContext> {
	
	private Logger logger = Logger.getGlobal();

	public RunToEntrance(ClientContext ctx) {
		super(ctx);
	}

	public boolean activate() {
		return ctx.backpack.select().isEmpty()
				&& ctx.players.local().tile().distanceTo(ctx.bank.nearest()) < 100
				&& !ctx.npcs.select().id(Constants.NPC_AUBURY).nearest().poll().inViewport();
	}

	@Override
	public void execute() {
		logger.info("Executing task - RunToEntrance");
		ctx.movement.step(Constants.TILE_ENTRANCE);
	}

}
