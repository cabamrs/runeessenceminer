package org.powerbot.cabam.runeessenceminer.state.factory;

import org.powerbot.cabam.runeessenceminer.constants.Constants;
import org.powerbot.cabam.runeessenceminer.state.GameState;
import org.powerbot.script.Tile;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.GameObject;
import org.powerbot.script.rt6.Npc;
import org.powerbot.script.rt6.Player;

public class GameStateFactory {
	
	private ClientContext ctx;
	
	public GameStateFactory(ClientContext ctx) {
		this.ctx = ctx;
	}

	public GameState getState() {
		final Player player = ctx.players.local();
		final Tile playerTile = player.tile();
		ctx.backpack.select();
		
		if (playerTile.distanceTo(Constants.TILE_ENTRANCE) < 8) {
			final Npc aubury = ctx.npcs.select().id(Constants.NPC_AUBURY).peek();
			
			if (aubury.tile().matrix(ctx).reachable()) {
				if (ctx.backpack.isEmpty()) {
					return GameState.TELEPORT;
				} else if (!Constants.TILE_BANK.matrix(ctx).reachable()){
					return GameState.OPEN_DOOR;
				}
			} else if (Constants.TILE_BANK.matrix(ctx).reachable()) {
				return GameState.OPEN_DOOR;
			}
			
			return player.inMotion() ? GameState.IDLE : GameState.RUN_TO_BANK;
		}
		
		if (playerTile.distanceTo(Constants.TILE_BANK) < 100) {
			if (ctx.backpack.isEmpty()) {
				return player.inMotion() ? GameState.IDLE : GameState.RUN_TO_ENTRANCE;
			}

			if (playerTile.distanceTo(Constants.TILE_BANK) < 8) {
				if (!ctx.bank.opened()) {
					return GameState.OPEN_BANK;
				}
				
				return GameState.DEPOSIT;
			}
			
			return player.inMotion() ? GameState.IDLE : GameState.RUN_TO_BANK;
		}
		
		if (playerTile.floor() == 1) {
			return GameState.CLIMB_DOWN_STAIRCASE;
		}
		
		final GameObject runeEssence = ctx.objects.select().id(Constants.OBJECT_RUNE_ESSENCE).nearest().peek();
		if (runeEssence.valid()) {
			if (ctx.backpack.count() < 28) {
				if (runeEssence.inViewport()) {
					if (playerTile.distanceTo(runeEssence.tile()) > 7) {
						return GameState.RUN_TO_MINE;
					}
					
					if (ctx.players.local().animation() == -1) {
						return GameState.MINE;
					}
					
					return GameState.IDLE;
				}
				
				return player.inMotion() ? GameState.IDLE : GameState.RUN_TO_MINE;
			}
			
			final GameObject exitPortal = ctx.objects.id(Constants.OBJECT_EXIT_PORTAL).nearest().peek();
			if (exitPortal.inViewport()) {
				if (ctx.widgets.component(Constants.WIDGET_INVENTORY_FULL, Constants.COMPONENT_INVENTORY_FULL_CLOSE).valid()) {
					return GameState.CLOSE_INVENTORY_FULL_MESSAGE;
				}
				return GameState.EXIT;
			}
			
			return player.inMotion() ? GameState.IDLE : GameState.RUN_TO_EXIT;
		}
		
		return GameState.IDLE;
	}
	
}
