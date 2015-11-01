package org.powerbot.cabam.runeessenceminer.tasks;

import org.powerbot.script.rt6.ClientContext;

public class CloseInventoryFullMessage extends Task<ClientContext> {
	
	public CloseInventoryFullMessage(ClientContext ctx) {
		super(ctx);
	}

	@Override
	public void execute() {
		ctx.widgets.component(1186, 4).click();
	}

}
