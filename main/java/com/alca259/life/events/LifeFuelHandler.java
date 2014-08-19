package com.alca259.life.events;

import com.alca259.life.Life;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.IFuelHandler;

public class LifeFuelHandler implements IFuelHandler {

	@Override
	public int getBurnTime(ItemStack fuel) {
		int fuelId = Item.getIdFromItem(fuel.getItem());
		
		if(fuelId == Block.getIdFromBlock(Life.cherrySapling)) {
			return 100;
		} else {
			return 0;
		}
	}
}
