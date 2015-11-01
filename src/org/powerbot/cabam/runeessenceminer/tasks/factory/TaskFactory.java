package org.powerbot.cabam.runeessenceminer.tasks.factory;

import java.util.EnumMap;
import java.util.Map;

import org.powerbot.cabam.runeessenceminer.state.GameState;
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
import org.powerbot.cabam.runeessenceminer.tasks.Task;
import org.powerbot.cabam.runeessenceminer.tasks.Teleport;
import org.powerbot.script.rt6.ClientContext;

@SuppressWarnings("rawtypes")
public class TaskFactory {
	
	private ClientContext ctx;
	private final Map<GameState, Task> stateToTasksMap;
	
	public TaskFactory(ClientContext ctx) {
		this.ctx = ctx;
		this.stateToTasksMap = getStateToTasksMap();
	}
	
	public Task getTask(final GameState state) {
		return stateToTasksMap.get(state);
	}
	
	private Map<GameState, Task> getStateToTasksMap() {
		Map<GameState, Task> tasks = new EnumMap<GameState, Task>(GameState.class);
		
		tasks.put(GameState.RUN_TO_ENTRANCE, new RunToEntrance(ctx));
		tasks.put(GameState.OPEN_DOOR, new OpenDoor(ctx));
		tasks.put(GameState.TELEPORT, new Teleport(ctx));
		tasks.put(GameState.RUN_TO_MINE, new RunToMine(ctx));
		tasks.put(GameState.MINE, new Mine(ctx));
		tasks.put(GameState.RUN_TO_EXIT, new RunToExit(ctx));
		tasks.put(GameState.EXIT, new Exit(ctx));
		tasks.put(GameState.RUN_TO_BANK, new RunToBank(ctx));
		tasks.put(GameState.DEPOSIT, new Deposit(ctx));
		tasks.put(GameState.OPEN_BANK, new OpenBank(ctx));
		tasks.put(GameState.CLOSE_BANK, new CloseBank(ctx));
		tasks.put(GameState.CLIMB_DOWN_STAIRCASE, new ClimbDownStaircase(ctx));
		tasks.put(GameState.CLOSE_INVENTORY_FULL_MESSAGE, new CloseInventoryFullMessage(ctx));
		tasks.put(GameState.IDLE, new Idle(ctx));
		
		return tasks;
	}

}
