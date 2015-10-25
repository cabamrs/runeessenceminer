package org.powerbot.cabam.runeessenceminer.tasks;

import java.util.concurrent.Callable;
import java.util.logging.Logger;

import org.powerbot.script.Condition;
import org.powerbot.script.rt6.ClientContext;

public class CloseBank extends Task<ClientContext> {
	
	private Logger logger = Logger.getGlobal();

	public CloseBank(ClientContext ctx) {
		super(ctx);
	}

	@Override
	public void execute() {
		logger.info("Executing task - CloseBank");
		
		ctx.bank.close();
		
		Condition.wait(new Callable<Boolean>() {
			@Override
			public Boolean call() throws Exception {
				return !ctx.bank.opened();
			}
		}, 125, 16);
	}

}
