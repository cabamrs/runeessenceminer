package org.powerbot.cabam.runeessenceminer.tasks;

import java.util.logging.Logger;

import org.powerbot.cabam.runeessenceminer.util.Task;
import org.powerbot.script.Random;
import org.powerbot.script.rt6.ClientContext;

public class Idle extends Task<ClientContext> {
	
	private Logger logger = Logger.getGlobal();
	private long lastAntiban = 0;
	
	public Idle(ClientContext ctx) {
		super(ctx);
	}

	@Override
	public void execute() {
		logger.info("Executing task - Idle");
//		final long currentTime = System.currentTimeMillis();
//		if (Math.abs(currentTime-lastAntiban) > 60000) {
//			logger.info("Antiban");
//			ctx.camera.angle(ctx.camera.yaw()+Random.nextInt(-60, 60));
//			lastAntiban = currentTime;
//		}
	}

}
