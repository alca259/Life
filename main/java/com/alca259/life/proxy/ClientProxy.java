package com.alca259.life.proxy;

import com.alca259.life.util.LogHelper;
import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraftforge.client.MinecraftForgeClient;

public class ClientProxy extends CommonProxy {
	public void registerRenders() {
		LogHelper.debug("[Alca] registerRenders:ClientProxy.java");
	}
}
