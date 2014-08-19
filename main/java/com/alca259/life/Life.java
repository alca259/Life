package com.alca259.life;

//Importaciones
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import com.alca259.life.blocks.BlockCherryLeaf;
import com.alca259.life.blocks.BlockCherryLog;
import com.alca259.life.blocks.BlockCherrySapling;
import com.alca259.life.events.CherryBoneMealEvent;
import com.alca259.life.events.LifeFuelHandler;
import com.alca259.life.items.ItemCherry;
import com.alca259.life.items.ItemJuiceCherry;
import com.alca259.life.items.ItemSweetCherry;
import com.alca259.life.proxy.CommonProxy;
import com.alca259.life.util.LogHelper;
import com.alca259.life.world.gen.CherryWorldGenerator;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;

/**
 * Inicio del mod, propiedades del mod en forge
 */
// Requerido para identificar al mod
@Mod(modid = Life.MODID, name = Life.MODNAME, version = Life.MODVERSION)

/**
 * Clase principal
 * @author alca259
 */
public class Life {

	// El nombre de la instancia del mod que Forge utiliza
	// Es MUY recomendable que tenga el mismo nombre que el ModId
	// Para evitar errores con otros mods
	@Instance(value = Life.MODID)
	public static Life instance;

	// Indicacion de las clases controladoras de los proxys
	// para que el mod sea compatible cliente - servidor
	@SidedProxy(clientSide = "com.alca259.life.proxy.ClientProxy", serverSide = "com.alca259.life.proxy.CommonProxy")
	public static CommonProxy proxy = new CommonProxy();

	// Variables de solo lectura
	public static final String MODID = "alca259_life";
	public static final String MODNAME = "Life";
	public static final String MODVERSION = "1.0.4";

	// Declaracion de bloques
	public static Block cherryLog;
	public static Block cherryLeaf;
	public static Block cherrySapling;

	// Declaracion de items
	public static Item cherry;
	public static Item juiceCherry;
	public static Item sweetCherry;
	
	// Declaracion de generaciones de mundo
	public static CherryWorldGenerator cherryWorldGen;

	/**
	 * Metodos de evento
	 */
	@EventHandler
	public void preLoad(FMLPreInitializationEvent event) {
		// Codigo a ser pre-inicializado
		LogHelper.init(event.getModLog());
	}

	@EventHandler
	public void load(FMLInitializationEvent event) {
		// Creamos bloques e items
		loadItemsAndBlocks();

		registrarBloques();
		registrarItems();
		registrarTileEntities();
		registrarEntities();
		registrarRecetas();
		registrarFundiciones();
		registrarGeneracionesMundo();
		registrarEventos();

		GameRegistry.registerFuelHandler(new LifeFuelHandler());
		
		// Inicializar el proxy
		proxy.registerRenders();
	}

	@EventHandler
	public void postLoad(FMLPostInitializationEvent event) {
		// Codigo a ser post-inicializado
		LogHelper.info("Life mod loaded");
	}

	/**
	 * Inicializamos bloques y objetos
	 */
	public void loadItemsAndBlocks() {
		LogHelper.debug("[Alca] loadItemsAndBlocks:Life.java");
		cherryLog = new BlockCherryLog();
		cherryLeaf = new BlockCherryLeaf();
		cherrySapling = new BlockCherrySapling();
		cherry = new ItemCherry();
		juiceCherry = new ItemJuiceCherry();
		sweetCherry = new ItemSweetCherry();
		cherryWorldGen = new CherryWorldGenerator();
	}

	/**
	 * Registrar bloque en el juego
	 */
	public void registrarBloques() {
		LogHelper.debug("[Alca] registrarBloques:Life.java");
		GameRegistry.registerBlock(cherryLog, "alca259_cherryLog");
		GameRegistry.registerBlock(cherryLeaf, "alca259_cherryLeaf");
		GameRegistry.registerBlock(cherrySapling, "alca259_cherrySapling");
	}
	
	/**
	 * Registrar item en el juego
	 */
	public void registrarItems() {
		LogHelper.debug("[Alca] registrarItems:Life.java");
		GameRegistry.registerItem(cherry, "alca259_cherry");
		GameRegistry.registerItem(juiceCherry, "alca259_juiceCherry");
		GameRegistry.registerItem(sweetCherry, "alca259_sweetCherry");
	}

	/**
	 * Registrar entidades de bloques e items
	 */
	public void registrarTileEntities() {
		LogHelper.debug("[Alca] registrarTileEntities:Life.java");
	}

	/**
	 * Registrar entidades
	 */
	public void registrarEntities() {
		LogHelper.debug("[Alca] registrarEntities:Life.java");
	}

	/**
	 * Registramos la recetas para poder craftearlos
	 */
	public void registrarRecetas() {
		LogHelper.debug("[Alca] registrarRecetas:Life.java");
		
		GameRegistry.addRecipe(new ItemStack(juiceCherry, 1), new Object[] {
			"S", "C", "B",
			Character.valueOf('S'), Items.sugar,
			Character.valueOf('C'), cherry,
			Character.valueOf('B'), new ItemStack(Items.potionitem, 1, 0)
		});

		GameRegistry.addShapelessRecipe(new ItemStack(sweetCherry, 1), new Object[] {
			cherry, Items.sugar
		});

		GameRegistry.addRecipe(new ItemStack(Blocks.planks, 4, 2), new Object[] {
			"B", Character.valueOf('B'), cherryLog
		});

	}

	/**
	 * Registramos las recetas que se preparan en el horno
	 */
	public void registrarFundiciones() {
		LogHelper.debug("[Alca] registrarFundiciones:Life.java");
		GameRegistry.addSmelting(cherryLog, new ItemStack(Items.coal, 1, 1), 0.1F);
	}
	
	public void registrarGeneracionesMundo() {
		LogHelper.debug("[Alca] registrarGeneracionesMundo:Life.java");
		GameRegistry.registerWorldGenerator(cherryWorldGen, 20);
	}
	
	public void registrarEventos() {
		LogHelper.debug("[Alca] registrarEventos:Life.java");
		MinecraftForge.EVENT_BUS.register(new CherryBoneMealEvent());
	}

}
