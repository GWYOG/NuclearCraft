package com.nr.mod.container;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import com.nr.mod.blocks.tileentities.TileEntityOxidiser;
import com.nr.mod.crafting.OxidiserRecipes;

public class ContainerOxidiser extends ContainerMachine {
	public ContainerOxidiser(InventoryPlayer inventory, TileEntityOxidiser tileentity) {
		super(inventory, tileentity, OxidiserRecipes.instance());
		addSlotToContainer(new Slot(tileentity, 0, 41, 38));
		addSlotToContainer(new Slot(tileentity, 1, 41, 58));
		addSlotToContainer(new SlotBlockedInventory(tileentity, 2, 134, 34));
		addSlotToContainer(new SlotBlockedInventory(tileentity, 3, 134, 62));
		addSlotToContainer(new Slot(tileentity, 4, 31, 15));
		addSlotToContainer(new Slot(tileentity, 5, 51, 15));
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				addSlotToContainer(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 92 + i * 18));
			}
		}
		for (int i = 0; i < 9; i++) {
			addSlotToContainer(new Slot(inventory, i, 8 + i * 18, 150));
		}
	}
}