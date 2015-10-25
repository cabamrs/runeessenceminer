package org.powerbot.cabam.runeessenceminer.tasks;

import java.util.logging.Logger;

import org.powerbot.script.rt6.ClientContext;

public class CloseInventoryFullMessage extends Task<ClientContext> {
	
	private Logger logger = Logger.getGlobal();

	public CloseInventoryFullMessage(ClientContext ctx) {
		super(ctx);
	}

	@Override
	public void execute() {
		logger.info("Executing task - CloseInventoryFullMessage");
		ctx.widgets.component(1186, 4).click();
	}

}
