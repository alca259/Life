package com.alca259.life.events;


import com.alca259.life.Life;
import com.alca259.life.blocks.BlockCherrySapling;
import com.alca259.life.util.LogHelper;

import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.entity.player.BonemealEvent;

public class CherryBoneMealEvent {

	@SubscribeEvent
	public void usedBonemeal(BonemealEvent event) {
		if (event.block == Life.cherrySapling) {
			LogHelper.debug("[Alca] usedBonemeal:CherryBoneMealEvent.java");
			if (!event.world.isRemote) {
				// Grow tree
				((BlockCherrySapling)Life.cherrySapling).markOrGrowMarked(event.world, event.x, event.y, event.z, event.world.rand);
				event.setResult(Result.ALLOW);
			}
		}
	}

}
