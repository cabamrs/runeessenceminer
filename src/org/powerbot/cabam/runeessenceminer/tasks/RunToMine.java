package org.powerbot.cabam.runeessenceminer.tasks;

import java.util.logging.Logger;

import org.powerbot.cabam.runeessenceminer.util.Constants;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.GameObject;

public class RunToMine extends Task<ClientContext> {
	
	private Logger logger = Logger.getGlobal();

	public RunToMine(ClientContext ctx) {
		super(ctx);
	}

	@Override
	public void execute() {
		logger.info("Executing task - RunToMine");
		final GameObject runeEssence = ctx.objects.id(Constants.OBJECT_RUNE_ESSENCE).nearest().peek();
		ctx.movement.step(runeEssence);
	}

}
