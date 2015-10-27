package org.powerbot.cabam.runeessenceminer;

import java.awt.Graphics;
import java.util.logging.Logger;

import org.powerbot.cabam.runeessenceminer.report.ProgressReport;
import org.powerbot.cabam.runeessenceminer.stats.Stats;
import org.powerbot.cabam.runeessenceminer.stats.factory.StatsFactory;
import org.powerbot.cabam.runeessenceminer.tasks.controller.TaskController;
import org.powerbot.script.PaintListener;
import org.powerbot.script.PollingScript;
import org.powerbot.script.Script;
import org.powerbot.script.rt6.ClientContext;

@Script.Manifest(
	name = "cabAm's Rune Essene Miner",
	description = "Mines Rune Essence in Varrock.",
	properties = "client=6; topic=1288454"
)
public class RuneEssenceMiner extends PollingScript<ClientContext> implements PaintListener {
	
	private TaskController taskController;
	private StatsFactory statsFactory;
	private Logger logger = Logger.getGlobal();

	@Override
	public void start() {
		taskController = new TaskController(ctx);
		statsFactory = new StatsFactory(ctx);
		
		ctx.camera.angle(334);
		ctx.camera.pitch(26);
	}

	@Override
	public void poll() {
		taskController.getTask().execute();
	}

	@Override
	public void repaint(Graphics graphics) {
		final Stats stats = statsFactory.getStats(getRuntime());
		ProgressReport.paint(graphics, stats);
	}

	@Override
	public void stop() {
		final Stats stats = statsFactory.getStats(getRuntime());
		ProgressReport.log(logger, stats);
	}

}
