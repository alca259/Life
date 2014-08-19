package com.alca259.life.blocks;

import java.util.List;
import java.util.Random;

import com.alca259.life.Life;
import com.alca259.life.util.LogHelper;


import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockCherryLog extends BlockRotatedPillar {
	/********************************************** VARIABLES DE LA CLASE **************************************************/
	@SideOnly(Side.CLIENT)
	private IIcon textureSide;

	/********************************************** CONSTRUCTORES **************************************************/
	public BlockCherryLog() {
		super(Material.wood);

		// Propiedades
		this.setHardness(1.5F);
		this.setHarvestLevel("axe", 0);
		this.setStepSound(Block.soundTypeWood);
		this.setCreativeTab(CreativeTabs.tabBlock);
		this.setBlockName("alca259cherryLog");
	}

	/********************************************** METODOS PROPIEDADES **************************************************/
	@Override
	public boolean isFlammable(IBlockAccess world, int x, int y, int z, net.minecraftforge.common.util.ForgeDirection face) {
		return true;
	}

    /**
     * Chance that fire will spread and consume this block.
     * 300 being a 100% chance, 0, being a 0% chance.
     *
     * @param world The current world
     * @param x The blocks X position
     * @param y The blocks Y position
     * @param z The blocks Z position
     * @param face The face that the fire is coming from
     * @return A number ranging from 0 to 300 relating used to determine if the block will be consumed by fire
     */
    public int getFlammability(IBlockAccess world, int x, int y, int z, ForgeDirection face)
    {
        return 20;
    }

    /**
     * Called when fire is updating on a neighbor block.
     * The higher the number returned, the faster fire will spread around this block.
     *
     * @param world The current world
     * @param x The blocks X position
     * @param y The blocks Y position
     * @param z The blocks Z position
     * @param face The face that the fire is coming from
     * @return A number that is used to determine the speed of fire growth around the block
     */
    public int getFireSpreadSpeed(IBlockAccess world, int x, int y, int z, ForgeDirection face)
    {
        return 5;
    }

	@Override
	public int damageDropped(int damage) {
		return 20;
	}

	/**
	 * Limit to valid metadata
	 * @param par1
	 * @return a number between 0 and 3
	 */
	public static int func_150165_c(int par1)
    {
        return par1 & 3;
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    @Override
    public int quantityDropped(Random par1Random)
    {
        return 1;
    }

    @Override
    public Item getItemDropped(int x, Random yRandom, int z)
    {
        return Item.getItemFromBlock(this);
    }

	/**
	 * Asigna las texturas al bloque
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconReg)
	{
		this.textureSide = iconReg.registerIcon(Life.MODID + ":BlockCherryLog");
		this.blockIcon = iconReg.registerIcon(Life.MODID + ":BlockCherryLogTop");
	}

    @SideOnly(Side.CLIENT)
    @Override
    protected IIcon getSideIcon(int meta)
    {
        return this.textureSide;
    }

    @SideOnly(Side.CLIENT)
    @Override
    protected IIcon getTopIcon(int meta)
    {
        return this.blockIcon;
    }

    @Override
    public boolean canSustainLeaves(IBlockAccess block, int x, int y, int z)
    {
        return true;
    }

    @Override
    public boolean isWood(IBlockAccess block, int x, int y, int z)
    {
        return true;
    }

    /**
     * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
     */
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs tab, List blockList)
    {
        blockList.add(new ItemStack(item, 1, 0));
    }
    /********************************************** METODOS EVENTOS **************************************************/
    @Override
    public void breakBlock(World world, int x, int y, int z, Block bloque, int meta)
    {
    	LogHelper.debug("[Alca] breakBlock:BlockCherryLog.java");
        byte b0 = 4;
        int i1 = b0 + 1;

        if (world.checkChunksExist(x - i1, y - i1, z - i1, x + i1, y + i1, z + i1))
        {
            for (int j1 = -b0; j1 <= b0; ++j1)
            {
                for (int k1 = -b0; k1 <= b0; ++k1)
                {
                    for (int l1 = -b0; l1 <= b0; ++l1)
                    {
                        Block block = world.getBlock(x + j1, y + k1, z + l1);
                        if (block.isLeaves(world, x + j1, y + k1, z + l1))
                        {
                            block.beginLeavesDecay(world, x + j1, y + k1, z + l1);
                        }
                    }
                }
            }
        }
    }
}
