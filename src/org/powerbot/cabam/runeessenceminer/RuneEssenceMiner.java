package org.powerbot.cabam.runeessenceminer;

import java.util.EnumMap;
import java.util.Map;
import java.util.logging.Logger;

import org.powerbot.cabam.runeessenceminer.tasks.Deposit;
import org.powerbot.cabam.runeessenceminer.tasks.Exit;
import org.powerbot.cabam.runeessenceminer.tasks.Idle;
import org.powerbot.cabam.runeessenceminer.tasks.Mine;
import org.powerbot.cabam.runeessenceminer.tasks.OpenDoor;
import org.powerbot.cabam.runeessenceminer.tasks.RunToBank;
import org.powerbot.cabam.runeessenceminer.tasks.RunToEntrance;
import org.powerbot.cabam.runeessenceminer.tasks.RunToExit;
import org.powerbot.cabam.runeessenceminer.tasks.RunToMine;
import org.powerbot.cabam.runeessenceminer.tasks.Teleport;
import org.powerbot.cabam.runeessenceminer.util.Constants;
import org.powerbot.cabam.runeessenceminer.util.SState;
import org.powerbot.cabam.runeessenceminer.util.Task;
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
public class RuneEssenceMiner extends PollingScript<ClientContext> {
	
	private Map<SState, Task> tasks = new EnumMap<SState, Task>(SState.class);
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
		tasks.put(SState.IDLE, new Idle(ctx));
		
		ctx.camera.angle('n');
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
				
				if (ctx.bank.inViewport()) {
					return SState.DEPOSIT;
				}
				
				return player.inMotion() ? SState.IDLE : SState.RUN_TO_BANK;
			}
			
			final GameObject runeEssence = ctx.objects.select().id(Constants.OBJECT_RUNE_ESSENCE).nearest().peek();
			if (runeEssence.valid()) {
				if (ctx.backpack.count() < 28) {
					if (runeEssence.inViewport()) {
						if (ctx.players.local().animation() == -1) {
							return SState.MINE;
						}
						
						return SState.IDLE;
					}
					
					return player.inMotion() ? SState.IDLE : SState.RUN_TO_MINE;
				}
				
				final GameObject exitPortal = ctx.objects.id(Constants.OBJECT_EXIT_PORTAL).nearest().peek();
				if (exitPortal.inViewport()) {
					return SState.EXIT;
				}
				
				return player.inMotion() ? SState.IDLE : SState.RUN_TO_EXIT;
			}
			
			return SState.IDLE;
		} finally {
			logTimeUntilHere();
		}
	}
	
	private void logTimeUntilHere() {
		logger.info("RETURN STATE - " + (System.currentTimeMillis()-startTime));
	}

}
