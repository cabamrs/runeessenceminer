package org.powerbot.cabam.runeessenceminer.tasks;

import java.util.concurrent.Callable;

import org.powerbot.script.Condition;
import org.powerbot.script.rt6.ClientContext;

public class Deposit extends Task<ClientContext> {
	
	public Deposit(ClientContext ctx) {
		super(ctx);
	}

	@Override
	public void execute() {
		ctx.bank.depositInventory();
		
		Condition.wait(new Callable<Boolean>() {
			@Override
			public Boolean call() throws Exception {
				return ctx.backpack.select().isEmpty();
			}
		}, 125, 16);
	}

}
