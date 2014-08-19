package com.alca259.life.blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.alca259.life.Life;
import com.alca259.life.util.LogHelper;
import com.alca259.life.world.ColorizerLeaves;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLeavesBase;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockCherryLeaf extends BlockLeavesBase implements IShearable {

	/********************************************** VARIABLES DE LA CLASE **************************************************/
	public static final String[][] tipoHojas = new String[][] {	{ Life.MODID + ":BlockCherryLeaf" }, { Life.MODID + ":BlockCherryLeafOpaque" } };
	public static final String[] tipoMadera = new String[] { "cherry" };
	int[] arrayEnteros;
	@SideOnly(Side.CLIENT)
	protected int graficosPotentes;
	protected IIcon[][] textures = new IIcon[2][];

	/********************************************** CONSTRUCTORES **************************************************/
	public BlockCherryLeaf() {
		super(Material.leaves, true);

		// Propiedades
		this.setHardness(0.1F);
		this.setTickRandomly(true);
		this.setLightOpacity(1);
		this.setStepSound(Block.soundTypeGrass);
		this.setCreativeTab(CreativeTabs.tabDecorations);
		this.setBlockName("alca259cherryLeaf");
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
        return 40;
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
        return 10;
    }

	/**
	 * Determines the damage on the item the block drops. Used in cloth and
	 * wood.
	 */
	@Override
	public int damageDropped(int meta) {
		return meta & 3;
	}

	@Override
	public boolean isShearable(ItemStack item, IBlockAccess world, int x, int y, int z) {
		return true;
	}

	@Override
	public ArrayList<ItemStack> onSheared(ItemStack item, IBlockAccess world, int x, int y, int z, int fortune) {
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
		ret.add(new ItemStack(this, 1, world.getBlockMetadata(x, y, z) & 3));
		return ret;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public int getBlockColor() {
		double d0 = 0.5D;
		double d1 = 1.0D;
		return ColorizerLeaves.getFoliageColorCherry(d0, d1);
	}

	/**
	 * Returns the color this block should be rendered. Used by leaves.
	 */
	@SideOnly(Side.CLIENT)
	@Override
	public int getRenderColor(int meta) {
		return ColorizerLeaves.getFoliageColorCherry();
	}

	/**
	 * Returns a integer with hex for 0xrrggbb with this color multiplied
	 * against the blocks color. Note only called when first determining what to
	 * render.
	 */
	@SideOnly(Side.CLIENT)
	@Override
	public int colorMultiplier(IBlockAccess bloque, int x, int y, int z) {
		int l = 0;
		int i1 = 0;
		int j1 = 0;

		for (int k1 = -1; k1 <= 1; ++k1) {
			for (int l1 = -1; l1 <= 1; ++l1) {
				//int i2 = bloque.getBiomeGenForCoords(x + l1, z + k1).getBiomeFoliageColor(x + l1, y, z + k1);
				int i2 = ColorizerLeaves.getFoliageColorCherry();
				l += (i2 & 16711680) >> 16;
				i1 += (i2 & 65280) >> 8;
				j1 += i2 & 255;
			}
		}

		return (l / 9 & 255) << 16 | (i1 / 9 & 255) << 8 | j1 / 9 & 255;
	}

	@Override
	public boolean isLeaves(IBlockAccess world, int x, int y, int z) {
		return true;
	}

	/**
	 * Is this block (a) opaque and (b) a full 1m cube? This determines whether
	 * or not to render the shared face of two adjacent blocks and also whether
	 * the player can attach torches, redstone wire, etc to this block.
	 */
	public boolean isOpaqueCube() {
		return !this.field_150121_P;
	}

	/**
	 * Pass true to draw this block using fancy graphics, or false for fast
	 * graphics.
	 */
	@SideOnly(Side.CLIENT)
	public void setGraphicsLevel(boolean par1) {
		this.field_150121_P = par1;
		this.graficosPotentes = par1 ? 0 : 1;
	}

	/**
	 * Returns an item stack containing a single instance of the current block
	 * type. 'i' is the block's subtype/damage and is ignored for blocks which
	 * do not support subtypes. Blocks which cannot be harvested should return
	 * null.
	 */
	protected ItemStack createStackedBlock(int par1) {
		return new ItemStack(Item.getItemFromBlock(this), 1, par1 & 3);
	}

	private void removeLeaves(World world, int x, int y, int z) {
		this.dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
		world.setBlockToAir(x, y, z);
	}

	/**
	 * Returns the quantity of items to drop on block destruction.
	 */
	public int quantityDropped(Random parRandom1) {
		// Esto es el porcentaje de posibilidades de que dropee arbol
		return parRandom1.nextInt(40) == 0 ? 1 : 0;
	}

	public Item getItemDropped(int x, Random yRandom, int z) {
		return Item.getItemFromBlock(Life.cherrySapling);
	}

	protected void func_150124_c(World world, int x, int y, int z, int meta, int par1) {
		LogHelper.debug("[Alca] func_150124_c:BlockCherryLeaf.java");
		if ((meta & 3) == 0 && world.rand.nextInt(par1) == 0) {
			this.dropBlockAsItem(world, x, y, z, new ItemStack(Life.cherry,	1, 0));
		}
	}

	protected int func_150123_b(int par1) {
		LogHelper.debug("[Alca] (Dropeo de sapling) func_150123_b:BlockCherryLeaf.java");
		int j = par1;

		if ((par1 & 8) == 8) {
			j = 20;
		}

		return j;
	}

	/**
	 * Gets the block's texture. Args: side, meta
	 */
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return (meta & 3) == 1 ? this.textures[this.graficosPotentes][1]
				: ((meta & 3) == 3 ? this.textures[this.graficosPotentes][3]
						: ((meta & 3) == 2 ? this.textures[this.graficosPotentes][2]
								: this.textures[this.graficosPotentes][0]));
	}

	/**
	 * returns a list of blocks with the same ID, but different meta (eg: wood
	 * returns 4 blocks)
	 */
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs tab, List listLeaves) {
		listLeaves.add(new ItemStack(item, 1, 0));
	}

	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconReg) {
		for (int i = 0; i < tipoHojas.length; ++i) {
			this.textures[i] = new IIcon[tipoHojas[i].length];

			for (int j = 0; j < tipoHojas[i].length; ++j) {
				this.textures[i][j] = iconReg.registerIcon(tipoHojas[i][j]);
			}
		}
	}

	public String[] func_150125_e() {
		return tipoMadera;
	}

	/********************************************** METODOS EVENTUALES **************************************************/
	@Override
	public void beginLeavesDecay(World world, int x, int y, int z) {
		LogHelper.debug("[Alca] beginLeavesDecay:BlockCherryLeaf.java");
		int i2 = world.getBlockMetadata(x, y, z);

		if ((i2 & 8) == 0) {
			world.setBlockMetadataWithNotify(x, y, z, i2 | 8, 4);
		}
		world.setBlockMetadataWithNotify(x, y, z,
				world.getBlockMetadata(x, y, z) | 8, 4);
	}

	/**
	 * Drops the block items with a specified chance of dropping the specified
	 * items
	 */
	public void dropBlockAsItemWithChance(World world, int x, int y, int z,	int meta, float par1, int par2) {
		LogHelper.debug("[Alca] dropBlockAsItemWithChance:BlockCherryLeaf.java");
		if (!world.isRemote) {
			int j1 = this.func_150123_b(meta);

			if (par2 > 0) {
				j1 -= 2 << par2;

				if (j1 < 10) {
					j1 = 10;
				}
			}

			// Obligamos al sistema a que sea un numero entero
			// Para prevenir errores de numeros negativos al obtener un numero aleatorio
			if (j1 <= 0) j1 = 20;

			if (world.rand.nextInt(j1) == 0) {
				Item item = this.getItemDropped(meta, world.rand, par2);
				this.dropBlockAsItem(world, x, y, z, new ItemStack(item, 1,
						this.damageDropped(meta)));
			}

			// Este es el porcentaje de caida de cerezas 1 de cada 20 hojas
			j1 = 20;

			if (par2 > 0) {
				j1 -= 10 << par2;

				if (j1 < 10) {
					j1 = 10;
				}
			}

			this.func_150124_c(world, x, y, z, meta, j1);
		}
	}

	/**
	 * Called when the player destroys a block with an item that can harvest it.
	 * (i, j, k) are the coordinates of the block and l is the block's
	 * subtype/damage.
	 */
	public void harvestBlock(World world, EntityPlayer player, int x, int y, int z, int meta) {
		LogHelper.debug("[Alca] harvestBlock:BlockCherryLeaf.java");
		super.harvestBlock(world, player, x, y, z, meta);
	}

	public void breakBlock(World world, int x, int y, int z, Block bloque, int meta) {
		LogHelper.debug("[Alca] breakBlock:BlockCherryLeaf.java");
		byte b0 = 1;
		int i1 = b0 + 1;

		if (world.checkChunksExist(x - i1, y - i1, z - i1, x + i1, y + i1, z
				+ i1)) {
			for (int j1 = -b0; j1 <= b0; ++j1) {
				for (int k1 = -b0; k1 <= b0; ++k1) {
					for (int l1 = -b0; l1 <= b0; ++l1) {
						Block block = world.getBlock(x + j1, y + k1, z + l1);
						if (block.isLeaves(world, x + j1, y + k1, z + l1)) {
							block.beginLeavesDecay(world, x + j1, y + k1, z
									+ l1);
						}
					}
				}
			}
		}
	}

	/**
	 * Ticks the block if it's been scheduled
	 */
	public void updateTick(World world, int x, int y, int z, Random parRandom1) {
		LogHelper.debug("[Alca] updateTick:BlockCherryLeaf.java");
		if (!world.isRemote) {
			int l = world.getBlockMetadata(x, y, z);

			if ((l & 8) != 0 && (l & 4) == 0) {
				byte b0 = 4;
				int i1 = b0 + 1;
				byte b1 = 32;
				int j1 = b1 * b1;
				int k1 = b1 / 2;

				if (this.arrayEnteros == null) {
					this.arrayEnteros = new int[b1 * b1 * b1];
				}

				int l1;

				if (world.checkChunksExist(x - i1, y - i1, z - i1, x + i1, y
						+ i1, z + i1)) {
					int i2;
					int j2;

					for (l1 = -b0; l1 <= b0; ++l1) {
						for (i2 = -b0; i2 <= b0; ++i2) {
							for (j2 = -b0; j2 <= b0; ++j2) {
								Block block = world.getBlock(x + l1, y + i2, z
										+ j2);

								if (!block.canSustainLeaves(world, x + l1, y
										+ i2, z + j2)) {
									if (block.isLeaves(world, x + l1, y + i2, z
											+ j2)) {
										this.arrayEnteros[(l1 + k1) * j1
												+ (i2 + k1) * b1 + j2 + k1] = -2;
									} else {
										this.arrayEnteros[(l1 + k1) * j1
												+ (i2 + k1) * b1 + j2 + k1] = -1;
									}
								} else {
									this.arrayEnteros[(l1 + k1) * j1
											+ (i2 + k1) * b1 + j2 + k1] = 0;
								}
							}
						}
					}

					for (l1 = 1; l1 <= 4; ++l1) {
						for (i2 = -b0; i2 <= b0; ++i2) {
							for (j2 = -b0; j2 <= b0; ++j2) {
								for (int k2 = -b0; k2 <= b0; ++k2) {
									if (this.arrayEnteros[(i2 + k1) * j1
											+ (j2 + k1) * b1 + k2 + k1] == l1 - 1) {
										if (this.arrayEnteros[(i2 + k1 - 1)
												* j1 + (j2 + k1) * b1 + k2 + k1] == -2) {
											this.arrayEnteros[(i2 + k1 - 1)
													* j1 + (j2 + k1) * b1 + k2
													+ k1] = l1;
										}

										if (this.arrayEnteros[(i2 + k1 + 1)
												* j1 + (j2 + k1) * b1 + k2 + k1] == -2) {
											this.arrayEnteros[(i2 + k1 + 1)
													* j1 + (j2 + k1) * b1 + k2
													+ k1] = l1;
										}

										if (this.arrayEnteros[(i2 + k1) * j1
												+ (j2 + k1 - 1) * b1 + k2 + k1] == -2) {
											this.arrayEnteros[(i2 + k1) * j1
													+ (j2 + k1 - 1) * b1 + k2
													+ k1] = l1;
										}

										if (this.arrayEnteros[(i2 + k1) * j1
												+ (j2 + k1 + 1) * b1 + k2 + k1] == -2) {
											this.arrayEnteros[(i2 + k1) * j1
													+ (j2 + k1 + 1) * b1 + k2
													+ k1] = l1;
										}

										if (this.arrayEnteros[(i2 + k1) * j1
												+ (j2 + k1) * b1
												+ (k2 + k1 - 1)] == -2) {
											this.arrayEnteros[(i2 + k1) * j1
													+ (j2 + k1) * b1
													+ (k2 + k1 - 1)] = l1;
										}

										if (this.arrayEnteros[(i2 + k1) * j1
												+ (j2 + k1) * b1 + k2 + k1 + 1] == -2) {
											this.arrayEnteros[(i2 + k1) * j1
													+ (j2 + k1) * b1 + k2 + k1
													+ 1] = l1;
										}
									}
								}
							}
						}
					}
				}

				l1 = this.arrayEnteros[k1 * j1 + k1 * b1 + k1];

				if (l1 >= 0) {
					world.setBlockMetadataWithNotify(x, y, z, l & -9, 4);
				} else {
					this.removeLeaves(world, x, y, z);
				}
			}
		}
	}

	/**
	 * A randomly called display update to be able to add particles or other
	 * items for display
	 */
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z,	Random parRandom1) {
		LogHelper.debug("[Alca] randomDisplayTick:BlockCherryLeaf.java");
		if (world.canLightningStrikeAt(x, y + 1, z)
				&& !World.doesBlockHaveSolidTopSurface(world, x, y - 1, z)
				&& parRandom1.nextInt(15) == 1) {
			double d0 = (double) ((float) x + parRandom1.nextFloat());
			double d1 = (double) y - 0.05D;
			double d2 = (double) ((float) z + parRandom1.nextFloat());
			world.spawnParticle("dripWater", d0, d1, d2, 0.0D, 0.0D, 0.0D);
		}
	}

}
