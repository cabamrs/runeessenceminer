package org.powerbot.cabam.runeessenceminer.tasks;

import java.util.concurrent.Callable;
import java.util.logging.Logger;

import org.powerbot.cabam.runeessenceminer.util.Task;
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
		
		if (!ctx.bank.opened()) {
			logger.info("Bank is NOT open. Trying to open bank.");
			ctx.bank.open();
			
			Condition.wait(new Callable<Boolean>() {
				@Override
				public Boolean call() throws Exception {
					return ctx.bank.opened();
				}
			}, 250, 8);
		}
		
		logger.info("Depositing inventory.");
		ctx.bank.depositInventory();
		Condition.wait(new Callable<Boolean>() {
			@Override
			public Boolean call() throws Exception {
				return ctx.backpack.select().isEmpty();
			}
		}, 250, 8);
		
		logger.info("Bank is open. Trying to close bank.");
		ctx.bank.close();
		Condition.wait(new Callable<Boolean>() {
			@Override
			public Boolean call() throws Exception {
				return !ctx.bank.opened();
			}
		}, 250, 8);
	}

}
