package org.powerbot.cabam.runeessenceminer;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.EnumMap;
import java.util.Map;
import java.util.logging.Logger;

import org.powerbot.cabam.runeessenceminer.tasks.ClimbDownStaircase;
import org.powerbot.cabam.runeessenceminer.tasks.CloseBank;
import org.powerbot.cabam.runeessenceminer.tasks.CloseInventoryFullMessage;
import org.powerbot.cabam.runeessenceminer.tasks.Deposit;
import org.powerbot.cabam.runeessenceminer.tasks.Exit;
import org.powerbot.cabam.runeessenceminer.tasks.Idle;
import org.powerbot.cabam.runeessenceminer.tasks.Mine;
import org.powerbot.cabam.runeessenceminer.tasks.OpenBank;
import org.powerbot.cabam.runeessenceminer.tasks.OpenDoor;
import org.powerbot.cabam.runeessenceminer.tasks.RunToBank;
import org.powerbot.cabam.runeessenceminer.tasks.RunToEntrance;
import org.powerbot.cabam.runeessenceminer.tasks.RunToExit;
import org.powerbot.cabam.runeessenceminer.tasks.RunToMine;
import org.powerbot.cabam.runeessenceminer.tasks.Teleport;
import org.powerbot.cabam.runeessenceminer.util.Constants;
import org.powerbot.cabam.runeessenceminer.util.SState;
import org.powerbot.cabam.runeessenceminer.util.Task;
import org.powerbot.script.PaintListener;
import org.powerbot.script.PollingScript;
import org.powerbot.script.Script;
import org.powerbot.script.Tile;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.GameObject;
import org.powerbot.script.rt6.Npc;
import org.powerbot.script.rt6.Player;

@Script.Manifest(
	name = "Essene Miner",
	description = "Mines Rune Essence in Varrock.",
	properties = "client=6"
)
public class RuneEssenceMiner extends PollingScript<ClientContext> implements PaintListener {
	
	public static final Font FONT_TAHOMA = new Font("Tahoma", Font.PLAIN, 12);
	
	private Map<SState, Task> tasks = new EnumMap<SState, Task>(SState.class);
	
	// Statistic variables
	private int miningExperienceAtStart;
	private int miningLevelAtStart;
	
	// Logging variables
	private long startTime;
	private Logger logger = Logger.getGlobal();

	@Override
	public void start() {
		tasks.put(SState.RUN_TO_ENTRANCE, new RunToEntrance(ctx));
		tasks.put(SState.OPEN_DOOR, new OpenDoor(ctx));
		tasks.put(SState.TELEPORT, new Teleport(ctx));
		tasks.put(SState.RUN_TO_MINE, new RunToMine(ctx));
		tasks.put(SState.MINE, new Mine(ctx));
		tasks.put(SState.RUN_TO_EXIT, new RunToExit(ctx));
		tasks.put(SState.EXIT, new Exit(ctx));
		tasks.put(SState.RUN_TO_BANK, new RunToBank(ctx));
		tasks.put(SState.DEPOSIT, new Deposit(ctx));
		tasks.put(SState.OPEN_BANK, new OpenBank(ctx));
		tasks.put(SState.CLOSE_BANK, new CloseBank(ctx));
		tasks.put(SState.CLIMB_DOWN_STAIRCASE, new ClimbDownStaircase(ctx));
		tasks.put(SState.CLOSE_INVENTORY_FULL_MESSAGE, new CloseInventoryFullMessage(ctx));
		tasks.put(SState.IDLE, new Idle(ctx));
		
		miningExperienceAtStart = ctx.skills.experience(org.powerbot.script.rt6.Constants.SKILLS_MINING);
		miningLevelAtStart = ctx.skills.level(org.powerbot.script.rt6.Constants.SKILLS_MINING);
	}
	
	@Override
	public void poll() {
		logger.info("POLL START - ");
		startTime = System.currentTimeMillis();
		tasks.get(state()).execute();
		logger.info("POLL FINISH - " + (System.currentTimeMillis()-startTime));
	}
	
	private SState state() {
		try {
			final Player player = ctx.players.local();
			final Tile playerTile = player.tile();
			ctx.backpack.select();
			
			if (playerTile.distanceTo(Constants.TILE_ENTRANCE) < 8) {
				final Npc aubury = ctx.npcs.select().id(Constants.NPC_AUBURY).peek();
				
				if (aubury.tile().matrix(ctx).reachable()) {
					if (ctx.backpack.isEmpty()) {
						return SState.TELEPORT;
					} else if (!Constants.TILE_BANK.matrix(ctx).reachable()){
						return SState.OPEN_DOOR;
					}
				} else if (Constants.TILE_BANK.matrix(ctx).reachable()) {
					return SState.OPEN_DOOR;
				}
				
				return player.inMotion() ? SState.IDLE : SState.RUN_TO_BANK;
			}
			
			if (playerTile.distanceTo(Constants.TILE_BANK) < 100) {
				if (ctx.backpack.isEmpty()) {
					return player.inMotion() ? SState.IDLE : SState.RUN_TO_ENTRANCE;
				}

				if (playerTile.distanceTo(Constants.TILE_BANK) < 8) {
					if (!ctx.bank.opened()) {
						return SState.OPEN_BANK;
					}
					
					return SState.DEPOSIT;
				}
				
				return player.inMotion() ? SState.IDLE : SState.RUN_TO_BANK;
			}
			
			if (playerTile.floor() == 1) {
				return SState.CLIMB_DOWN_STAIRCASE;
			}
			
			final GameObject runeEssence = ctx.objects.select().id(Constants.OBJECT_RUNE_ESSENCE).nearest().peek();
			if (runeEssence.valid()) {
				if (ctx.backpack.count() < 28) {
					if (runeEssence.inViewport()) {
						if (playerTile.distanceTo(runeEssence.tile()) > 7) {
							return SState.RUN_TO_MINE;
						}
						
						if (ctx.players.local().animation() == -1) {
							return SState.MINE;
						}
						
						return SState.IDLE;
					}
					
					return player.inMotion() ? SState.IDLE : SState.RUN_TO_MINE;
				}
				
				final GameObject exitPortal = ctx.objects.id(Constants.OBJECT_EXIT_PORTAL).nearest().peek();
				if (exitPortal.inViewport()) {
					if (ctx.widgets.component(Constants.WIDGET_INVENTORY_FULL, Constants.COMPONENT_INVENTORY_FULL_CLOSE).valid()) {
						return SState.CLOSE_INVENTORY_FULL_MESSAGE;
					}
					return SState.EXIT;
				}
				
				return player.inMotion() ? SState.IDLE : SState.RUN_TO_EXIT;
			}
			
			return SState.IDLE;
		} finally {
			logTimeUntilHere("RETURN STATE - ");
		}
	}
	
	private void logTimeUntilHere(String message) {
		logger.info(message + (System.currentTimeMillis()-startTime));
	}
	
	@Override
    public void repaint(Graphics graphics) {
        final Graphics2D g = (Graphics2D) graphics;
        
        final long runtime = getRuntime();
        final int currentMiningExperience = ctx.skills.experience(org.powerbot.script.rt6.Constants.SKILLS_MINING);
        final int miningExperienceGained = currentMiningExperience - miningExperienceAtStart;
        final int essenceMined = miningExperienceGained / 5;
        final int essenceMinedPerHour = (int) ((essenceMined * 3600000D) / runtime);
        final int miningExperiencePerHour = (int) ((miningExperienceGained * 3600000D) / runtime);
        final int miningLevelsGained = ctx.skills.level(org.powerbot.script.rt6.Constants.SKILLS_MINING) - miningLevelAtStart;
        
        final int hours = (int) (runtime / 3600000);
        int minutes = (int) (runtime / 60000 - hours * 60);
        int seconds = (int) (runtime / 1000 - hours * 3600 - minutes * 60);
        final String time = String.format("Time: %02d", hours) + ":" + String.format("%02d", minutes) + ":" + String.format("%02d", seconds);

        g.setFont(FONT_TAHOMA);
        g.setColor(Color.BLACK);
        g.fillRect(5, 5, 220, 85);

        g.setColor(Color.WHITE);
        g.drawString(time, 10, 20);
        g.drawString(String.format("Mining: %,d +%,d", miningLevelAtStart, miningLevelsGained), 10, 40);
        g.drawString(String.format("Ess: %,d (%,d/h)", essenceMined, essenceMinedPerHour), 10, 60);
        g.drawString(String.format("Exp: %,d (%,d/h)", miningExperienceGained, miningExperiencePerHour), 10, 80);
    }
	
	@Override
	public void stop() {
		final long runtime = getRuntime();
		final int currentMiningExperience = ctx.skills.experience(org.powerbot.script.rt6.Constants.SKILLS_MINING);
		final int miningExperienceGained = currentMiningExperience - miningExperienceAtStart;
		final int essenceMined = miningExperienceGained / 5;
		final int essenceMinedPerHour = (int) ((essenceMined * 3600000D) / runtime);
		final int miningExperiencePerHour = (int) ((miningExperienceGained * 3600000D) / runtime);
		final int miningLevelsGained = ctx.skills.level(org.powerbot.script.rt6.Constants.SKILLS_MINING) - miningLevelAtStart;
		
		final int hours = (int) (runtime / 3600000);
		int minutes = (int) (runtime / 60000 - hours * 60);
		int seconds = (int) (runtime / 1000 - hours * 3600 - minutes * 60);
		final String time = String.format("Time: %02d", hours) + ":" + String.format("%02d", minutes) + ":" + String.format("%02d", seconds);
		
		logger.info(time);
		logger.info(String.format("Mining: %,d +%,d", miningLevelAtStart, miningLevelsGained));
		logger.info(String.format("Ess: %,d (%,d/h)", essenceMined, essenceMinedPerHour));
		logger.info(String.format("Exp: %,d (%,d/h)", miningExperienceGained, miningExperiencePerHour));
	}

}
