package org.powerbot.cabam.runeessenceminer.report;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.logging.Logger;

import org.powerbot.cabam.runeessenceminer.constants.Constants;
import org.powerbot.cabam.runeessenceminer.stats.Stats;

public class ProgressReport {

	public static void paint(Graphics graphics, final Stats stats) {
		final Graphics2D g = (Graphics2D) graphics;

		g.setFont(Constants.FONT_TAHOMA);
		g.setColor(Color.YELLOW);
		g.fillRect(5, 5, 490, 20);

		g.setColor(Color.BLACK);
		g.drawString(getFormattedStats(stats), 10, 20);
	}

	public static void log(Logger logger, final Stats stats) {
		logger.info(getFormattedStats(stats));
	}
	
	private static String getFormattedStats(Stats stats) {
		return Constants.VERSION
				+ Constants.STAT_SEPARATOR + Constants.STAT_TIME + stats.getElapsedTime()
				+ Constants.STAT_SEPARATOR + getFormattedLevel(stats.getCurrentMiningLevel(), stats.getGainedMiningLevels())
				+ Constants.STAT_SEPARATOR + getFormattedEssence(stats.getMinedEssence(), stats.getMinedEssencePerHour())
				+ Constants.STAT_SEPARATOR + getFormattedExperience(stats.getGainedMiningExperience(), stats.getGainedMiningExperiencePerHour());
	}

	private static String getFormattedLevel(int currentMiningLevel, int gainedMiningLevels) {
		return String.format(Constants.STAT_LEVEL, currentMiningLevel, gainedMiningLevels);
	}

	private static String getFormattedEssence(int minedEssence, int minedEssencePerHour) {
		return String.format(Constants.STAT_ESSENCE, minedEssence, minedEssencePerHour);
	}

	private static String getFormattedExperience(int gainedMiningExperience, int gainedMiningExperiencePerHour) {
		return String.format(Constants.STAT_EXPERIENCE, gainedMiningExperience, gainedMiningExperiencePerHour);
	}
}
