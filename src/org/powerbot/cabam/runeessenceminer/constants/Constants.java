package org.powerbot.cabam.runeessenceminer.constants;

import java.awt.Font;

import org.powerbot.script.Tile;

public class Constants {
	
	public static final int NPC_AUBURY = 5913;
	
	public static final int OBJECT_RUNE_ESSENCE = 2491;
	public static final int OBJECT_EXIT_PORTAL = 39831;
	public static final int OBJECT_CLOSED_DOOR = 24381;
	public static final int OBJECT_STAIRCASE = 24353;
	
	public static final Tile TILE_BANK = new Tile(3254, 3420, 0);
	public static final Tile TILE_ENTRANCE = new Tile(3253, 3398, 0);
	public static final Tile TILE_CLOSED_DOOR = new Tile(3253, 3399, 0);
	
	public static final int WIDGET_INVENTORY_FULL = 1186;
	
	public static final int COMPONENT_INVENTORY_FULL_CLOSE = 4;
	
	public static final Font FONT_TAHOMA = new Font("Tahoma", Font.PLAIN, 12);
	
	public static final String STAT_TIME = "TIME: ";
	public static final String STAT_LEVEL = "LVL: %,d +%,d";
	public static final String STAT_ESSENCE = "ESS: %,d (%,d/h)";
	public static final String STAT_EXPERIENCE = "EXP: %,d (%,d/h)";
	public static final String STAT_SEPARATOR = " | ";
	
}
