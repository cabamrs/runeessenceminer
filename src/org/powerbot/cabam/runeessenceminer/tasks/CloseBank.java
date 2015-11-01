package org.powerbot.cabam.runeessenceminer.tasks;

import java.util.concurrent.Callable;

import org.powerbot.script.Condition;
import org.powerbot.script.rt6.ClientContext;

public class CloseBank extends Task<ClientContext> {
	
	public CloseBank(ClientContext ctx) {
		super(ctx);
	}

	@Override
	public void execute() {
		ctx.bank.close();
		
		Condition.wait(new Callable<Boolean>() {
			@Override
			public Boolean call() throws Exception {
				return !ctx.bank.opened();
			}
		}, 125, 16);
	}

}
