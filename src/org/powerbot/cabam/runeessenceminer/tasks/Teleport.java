package org.powerbot.cabam.runeessenceminer.tasks;

import org.powerbot.cabam.runeessenceminer.constants.Constants;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.Npc;

public class Teleport extends Task<ClientContext> {

	public Teleport(ClientContext ctx) {
		super(ctx);
	}

	@Override
	public void execute() {
		final Npc aubury = ctx.npcs.id(Constants.NPC_AUBURY).nearest().peek();
		aubury.interact("Teleport");
	}
	
}
