package org.powerbot.cabam.runeessenceminer.tasks;

import java.util.concurrent.Callable;
import java.util.logging.Logger;

import org.powerbot.cabam.runeessenceminer.constants.Constants;
import org.powerbot.script.Condition;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.GameObject;

public class Mine extends Task<ClientContext> {
	
	private Logger logger = Logger.getGlobal();

	public Mine(ClientContext ctx) {
		super(ctx);
	}

	@Override
	public void execute() {
		logger.info("Executing task - Mine");
		if (!ctx.backpack.isEmpty()) {
			Condition.wait(new Callable<Boolean>() {
				@Override
				public Boolean call() throws Exception {
					return ctx.players.local().animation() != -1;
				}
			}, 250, 8);
			
			if (ctx.players.local().animation() == -1 && ctx.backpack.select().count() == 28) {
				return;
			}
		}
		
		final GameObject runeEssence = ctx.objects.id(Constants.OBJECT_RUNE_ESSENCE).nearest().peek();
		runeEssence.click();
	}

}
