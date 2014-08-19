package com.alca259.life.items;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemFood;
import net.minecraft.util.IIcon;
import com.alca259.life.Life;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemCherry extends ItemFood {
	private static int hungerRestore = 1; // Medio muslo
	private static float saturationRestore = 0.1F;
	private static boolean wolvesFood = false;

	public ItemCherry() {
		//First parameter is how much hunger it restores.
		//2nd parameter is how much saturation it restores.
		//3rd parameter defines whether wolves like this food or not.
		super(hungerRestore, saturationRestore, wolvesFood);
		this.setUnlocalizedName("alca259Cherry");
	}

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconReg)
    {
    	this.itemIcon = iconReg.registerIcon(Life.MODID + ":ItemCherry");
    }

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int meta) {
		return this.itemIcon;
	}
}
