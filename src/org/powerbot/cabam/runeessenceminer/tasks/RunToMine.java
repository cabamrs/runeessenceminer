package org.powerbot.cabam.runeessenceminer.tasks;

import org.powerbot.cabam.runeessenceminer.constants.Constants;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.GameObject;

public class RunToMine extends Task<ClientContext> {
	
	public RunToMine(ClientContext ctx) {
		super(ctx);
	}

	@Override
	public void execute() {
		final GameObject runeEssence = ctx.objects.id(Constants.OBJECT_RUNE_ESSENCE).nearest().peek();
		ctx.movement.step(runeEssence);
	}

}
