package org.powerbot.cabam.runeessenceminer;

import java.awt.Graphics;
import java.util.EnumMap;
import java.util.Map;
import java.util.logging.Logger;

import org.powerbot.cabam.runeessenceminer.report.ProgressReport;
import org.powerbot.cabam.runeessenceminer.stats.Stats;
import org.powerbot.cabam.runeessenceminer.stats.StatsFactory;
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
	name = "cabAm's Rune Essene Miner",
	description = "Mines Rune Essence in Varrock.",
	properties = "client=6"
)
@SuppressWarnings("rawtypes")
public class RuneEssenceMiner extends PollingScript<ClientContext> implements PaintListener {
	
	private Map<SState, Task> tasks = new EnumMap<SState, Task>(SState.class);
	private StatsFactory statsFactory;
	
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
		
		statsFactory = new StatsFactory(ctx);
	}
	
	@Override
	public void poll() {
		tasks.get(state()).execute();
	}
	
	private SState state() {
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
	}
	
	@Override
    public void repaint(Graphics graphics) {
		final Stats stats = statsFactory.getStats(getRuntime());
        ProgressReport.update(graphics, stats);
    }
	
	@Override
	public void stop() {
		final Stats stats = statsFactory.getStats(getRuntime());
		ProgressReport.log(logger, stats);
	}

}
