package org.powerbot.cabam.runeessenceminer.tasks;

import java.util.concurrent.Callable;
import java.util.logging.Logger;

import org.powerbot.script.Condition;
import org.powerbot.script.rt6.ClientContext;

public class Deposit extends Task<ClientContext> {
	
	private Logger logger = Logger.getGlobal();

	public Deposit(ClientContext ctx) {
		super(ctx);
	}

	@Override
	public void execute() {
		logger.info("Executing task - Deposit");
		
		ctx.bank.depositInventory();
		
		Condition.wait(new Callable<Boolean>() {
			@Override
			public Boolean call() throws Exception {
				return ctx.backpack.select().isEmpty();
			}
		}, 125, 16);
	}

}
