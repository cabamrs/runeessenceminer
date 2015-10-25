package org.powerbot.cabam.runeessenceminer.stats;


public class Stats {

	private final int startingMiningExperience;
	private final int startingMiningLevel;
	private final int currentMiningExperience;
	private final int gainedMiningExperience;
	private final int gainedMiningExperiencePerHour;
	private final int gainedMiningLevels;
	private final int minedEssence;
	private final int minedEssencePerHour;
	private final String elapsedTime;
	
	public Stats(
			final int startingMiningExperience,
			final int startingMiningLevel,
			final int currentMiningExperience,
			final int gainedMiningExperience,
			final int gainedMiningExperiencePerHour,
			final int gainedMiningLevels,
			final int minedEssence,
			final int minedEssencePerHour,
			final String elapsedTime) {
		this.startingMiningExperience = startingMiningExperience;
		this.startingMiningLevel = startingMiningLevel;
		this.currentMiningExperience = currentMiningExperience;
		this.gainedMiningExperience = gainedMiningExperience;
		this.gainedMiningExperiencePerHour = gainedMiningExperiencePerHour;
		this.gainedMiningLevels = gainedMiningLevels;
		this.minedEssence = minedEssence;
		this.minedEssencePerHour = minedEssencePerHour;
		this.elapsedTime = elapsedTime;
	}

	public int getStartingMiningExperience() {
		return startingMiningExperience;
	}

	public int getStartingMiningLevel() {
		return startingMiningLevel;
	}

	public int getCurrentMiningExperience() {
		return currentMiningExperience;
	}

	public int getGainedMiningExperience() {
		return gainedMiningExperience;
	}

	public int getGainedMiningExperiencePerHour() {
		return gainedMiningExperiencePerHour;
	}

	public int getGainedMiningLevels() {
		return gainedMiningLevels;
	}

	public int getMinedEssence() {
		return minedEssence;
	}

	public int getMinedEssencePerHour() {
		return minedEssencePerHour;
	}

	public String getElapsedTime() {
		return elapsedTime;
	}
	
}
