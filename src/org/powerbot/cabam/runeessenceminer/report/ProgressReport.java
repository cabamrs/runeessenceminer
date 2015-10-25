package org.powerbot.cabam.runeessenceminer.report;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.logging.Logger;

import org.powerbot.cabam.runeessenceminer.stats.Stats;

public class ProgressReport {
	
	public static final Font FONT_TAHOMA = new Font("Tahoma", Font.PLAIN, 12);

	public static void paint(Graphics graphics, final Stats stats) {
		final Graphics2D g = (Graphics2D) graphics;

        g.setFont(FONT_TAHOMA);
        g.setColor(Color.YELLOW);
        g.fillRect(5, 5, 450, 20);

        g.setColor(Color.BLACK);
        g.drawString("TIME: " + stats.getElapsedTime()
        		+ " | " + String.format("LVL: %,d +%,d", stats.getStartingMiningLevel(), stats.getGainedMiningLevels())
        		+ " | " + String.format("ESS: %,d (%,d/h)", stats.getMinedEssence(), stats.getMinedEssencePerHour())
        		+ " | " + String.format("EXP: %,d (%,d/h)", stats.getGainedMiningExperience(), stats.getGainedMiningExperiencePerHour())
        		, 10, 20);
	}
	
	public static void log(Logger logger, final Stats stats) {
		logger.info("Time: " + stats.getElapsedTime());
		logger.info(String.format("Lvl: %,d +%,d", stats.getStartingMiningLevel(), stats.getGainedMiningLevels()));
		logger.info(String.format("Ess: %,d (%,d/h)", stats.getMinedEssence(), stats.getMinedEssencePerHour()));
		logger.info(String.format("Exp: %,d (%,d/h)", stats.getGainedMiningExperience(), stats.getGainedMiningExperiencePerHour()));
	}
}
