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
		g.fillRect(5, 5, 450, 20);

		g.setColor(Color.BLACK);
		g.drawString(Constants.STAT_TIME + stats.getElapsedTime()
				+ Constants.STAT_SEPARATOR + getFormattedLevel(stats.getStartingMiningLevel(), stats.getGainedMiningLevels())
				+ Constants.STAT_SEPARATOR + getFormattedEssence(stats.getMinedEssence(), stats.getMinedEssencePerHour())
				+ Constants.STAT_SEPARATOR + getFormattedExperience(stats.getGainedMiningExperience(), stats.getGainedMiningExperiencePerHour())
				, 10, 20);
	}

	public static void log(Logger logger, final Stats stats) {
		logger.info(Constants.STAT_TIME + stats.getElapsedTime());
		logger.info(String.format(Constants.STAT_LEVEL, stats.getStartingMiningLevel(), stats.getGainedMiningLevels()));
		logger.info(String.format(Constants.STAT_ESSENCE, stats.getMinedEssence(), stats.getMinedEssencePerHour()));
		logger.info(String.format(Constants.STAT_EXPERIENCE, stats.getGainedMiningExperience(), stats.getGainedMiningExperiencePerHour()));
	}

	private static String getFormattedLevel(int startingMiningLevel, int gainedMiningLevels) {
		return String.format(Constants.STAT_LEVEL, startingMiningLevel, gainedMiningLevels);
	}

	private static String getFormattedEssence(int minedEssence, int minedEssencePerHour) {
		return String.format(Constants.STAT_ESSENCE, minedEssence, minedEssencePerHour);
	}

	private static String getFormattedExperience(int gainedMiningExperience, int gainedMiningExperiencePerHour) {
		return String.format(Constants.STAT_EXPERIENCE, gainedMiningExperience, gainedMiningExperiencePerHour);
	}
}
