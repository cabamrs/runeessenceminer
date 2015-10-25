package org.powerbot.cabam.runeessenceminer.tasks;

import java.util.concurrent.Callable;
import java.util.logging.Logger;

import org.powerbot.cabam.runeessenceminer.util.Task;
import org.powerbot.script.Condition;
import org.powerbot.script.rt6.ClientContext;

public class OpenBank extends Task<ClientContext> {
	
	private Logger logger = Logger.getGlobal();

	public OpenBank(ClientContext ctx) {
		super(ctx);
	}

	@Override
	public void execute() {
		logger.info("Executing task - OpenBank");
		
		ctx.bank.open();
		
		Condition.wait(new Callable<Boolean>() {
			@Override
			public Boolean call() throws Exception {
				return !ctx.players.local().inMotion();
			}
		}, 125, 24);
	}

}
