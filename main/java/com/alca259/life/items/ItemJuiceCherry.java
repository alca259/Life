package com.alca259.life.items;

import java.util.Iterator;
import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import com.alca259.life.Life;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemJuiceCherry extends ItemFood {
	private static int hungerRestore = 4; // Dos muslamenes
	private static float saturationRestore = 1.0F;
	private static boolean wolvesFood = false;

	public ItemJuiceCherry() {
		//First parameter is how much hunger it restores.
		//2nd parameter is how much saturation it restores.
		//3rd parameter defines whether wolves like this food or not.
		super(hungerRestore, saturationRestore, wolvesFood);
		this.setAlwaysEdible(); // Para permitir comer incluso sin hambre
		this.setUnlocalizedName("alca259JuiceCherry");
	}

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconReg)
    {
    	this.itemIcon = iconReg.registerIcon(Life.MODID + ":ItemJuiceCherry");
    }

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int meta) {
		return this.itemIcon;
	}

    /**
     * returns the action that specifies what animation to play when the items is being used
     */
	@Override
    public EnumAction getItemUseAction(ItemStack par1ItemStack)
    {
        return EnumAction.drink;
    }

    @Override
    protected void onFoodEaten(ItemStack items, World world, EntityPlayer player)
    {
        if (!world.isRemote)
        {
            //player.addPotionEffect(new PotionEffect(Potion.field_76444_x.id, 240, 0)); // Absorcion
            player.addPotionEffect(new PotionEffect(Potion.regeneration.id, 240, 4));
            super.onFoodEaten(items, world, player);
        }
    }

    @Override
    public ItemStack onEaten(ItemStack items, World world, EntityPlayer player)
    {
        if (!player.capabilities.isCreativeMode)
        {
            --items.stackSize;
            
            player.getFoodStats().func_151686_a(this, items);
            
            world.playSoundAtEntity(player, "random.burp", 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
            
            this.onFoodEaten(items, world, player);

            if (items.stackSize <= 0)
            {
                return new ItemStack(Items.glass_bottle);
            }

            player.inventory.addItemStackToInventory(new ItemStack(Items.glass_bottle));
        }

        return items;
    }
}
