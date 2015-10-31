package org.powerbot.cabam.runeessenceminer.stats.factory;

import org.powerbot.cabam.runeessenceminer.stats.Stats;
import org.powerbot.script.rt6.ClientContext;

public class StatsFactory {
	
	private ClientContext ctx;
	private final Stats initial;
	
	public StatsFactory(ClientContext ctx) {
		this.ctx = ctx;
		this.initial = getInitialStats();
	}
	
	private Stats getInitialStats() {
		return new Stats(
			ctx.skills.experience(org.powerbot.script.rt6.Constants.SKILLS_MINING),
			ctx.skills.level(org.powerbot.script.rt6.Constants.SKILLS_MINING),
			0, 0, 0, 0, 0, 0, 0, "00:00:00"
		);
	}

	public Stats getStats(final long runtime) {
		final int currentMiningLevel = ctx.skills.level(org.powerbot.script.rt6.Constants.SKILLS_MINING);
		final int miningLevelsGained = currentMiningLevel - initial.getStartingMiningLevel();
		final int currentMiningExperience = ctx.skills.experience(org.powerbot.script.rt6.Constants.SKILLS_MINING);
		final int miningExperienceGained = currentMiningExperience - initial.getStartingMiningExperience();
		final int essenceMined = miningExperienceGained / 5;
		final int essenceMinedPerHour = calculateMetricPerHour(essenceMined, runtime);
		final int miningExperiencePerHour = calculateMetricPerHour(miningExperienceGained, runtime);
		
		final int hoursElapsed = (int) (runtime / 3600000);
		int minutesElapsed = (int) (runtime / 60000 - hoursElapsed * 60);
		int secondsElapsed = (int) (runtime / 1000 - hoursElapsed * 3600 - minutesElapsed * 60);
		final String timeElapsed = String.format("%02d", hoursElapsed) + ":" + String.format("%02d", minutesElapsed) + ":" + String.format("%02d", secondsElapsed);
		
		return new Stats(
			initial.getStartingMiningExperience(),
			initial.getStartingMiningLevel(),
			currentMiningExperience,
			currentMiningLevel,
			miningExperienceGained,
			miningExperiencePerHour,
			miningLevelsGained,
			essenceMined,
			essenceMinedPerHour,
			timeElapsed
		);
	}
	
	private int calculateMetricPerHour(final int metric, final long runtime) {
		return (int) ((metric * 3600000D) / runtime);
	}
}
