package org.powerbot.cabam.runeessenceminer.tasks;

import java.util.logging.Logger;

import org.powerbot.cabam.runeessenceminer.util.Constants;
import org.powerbot.cabam.runeessenceminer.util.Task;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.Npc;

public class Teleport extends Task<ClientContext> {

	private Logger logger = Logger.getGlobal();
	
	public Teleport(ClientContext ctx) {
		super(ctx);
	}

	@Override
	public void execute() {
		logger.info("Executing task - Teleport");
		final Npc aubury = ctx.npcs.id(Constants.NPC_AUBURY).nearest().peek();
		aubury.interact("Teleport");
	}
	
}
