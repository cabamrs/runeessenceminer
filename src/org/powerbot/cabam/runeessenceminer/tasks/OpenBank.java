package org.powerbot.cabam.runeessenceminer.tasks;

import java.util.concurrent.Callable;

import org.powerbot.script.Condition;
import org.powerbot.script.rt6.ClientContext;

public class OpenBank extends Task<ClientContext> {
	
	public OpenBank(ClientContext ctx) {
		super(ctx);
	}

	@Override
	public void execute() {
		ctx.bank.open();
		
		Condition.wait(new Callable<Boolean>() {
			@Override
			public Boolean call() throws Exception {
				return !ctx.players.local().inMotion();
			}
		}, 125, 24);
	}

}
