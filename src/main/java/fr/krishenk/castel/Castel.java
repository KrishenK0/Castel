package fr.krishenk.castel;

import java.io.UnsupportedEncodingException;

import org.apache.logging.log4j.Logger;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import fr.krishenk.castel.client.entity.EntityOrb;
import fr.krishenk.castel.client.gui.GuiHandler;
import fr.krishenk.castel.client.utils.IProxy;
import fr.krishenk.castel.server.customItems;
import fr.krishenk.castel.server.events.CastelEventHandler;
import fr.krishenk.castel.server.packet.PacketDispatcher;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraftforge.common.MinecraftForge;

@Mod(modid = Castel.MODID, name = "Castel", version = "1.2")
public class Castel {

	public final static String MODID = "castel";

	public static boolean UseUniqueEntities = true;
	public static int EntityStartId = 120;

	private static int modGuiIndex = 10;
	public static final int CASTEL_MENU_GUI_ID = modGuiIndex++;
	
	public static Logger logger;

	// public static FMLEventChannel channel =
	// NetworkRegistry.INSTANCE.newEventDrivenChannel("castel");

	@SidedProxy(serverSide = "fr.krishenk.castel.server.ServerProxy", clientSide = "fr.krishenk.castel.client.ClientProxy")
	public static IProxy proxy;

	@Instance(Castel.MODID)
	public static Castel INSTANCE;

	public Castel() {
		INSTANCE = this;
	}
	@EventHandler
	public void preInit(FMLPreInitializationEvent e) {
		logger = e.getModLog();
		logger.info("preInit");

		// network.registerMessage(GenericMessage.Handler.class, GenericMessage.class,
		// 0, Side.CLIENT);


		proxy.onPreInit();

		customItems.load();
		proxy.load();
		proxy.logPhysicalSide(logger);

		int thowid = getEntityId();
		EntityRegistry.registerGlobalEntityID(EntityOrb.class, "throwableitem", thowid);
		EntityRegistry.registerModEntity(EntityOrb.class, "throwableitem", thowid, this, 64, 3, true);

		PacketDispatcher.registerPackets();
	}

	@EventHandler
	public void init(FMLInitializationEvent e) {
		CastelEventHandler events = new CastelEventHandler();
		MinecraftForge.EVENT_BUS.register(events);

		// channel.register(this);
		NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());

	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent e) {

	}

	public static int getEntityId() {
		return UseUniqueEntities ? EntityRegistry.findGlobalUniqueEntityId() : EntityStartId++;
	}

	@SubscribeEvent
	public void onServerPacket(FMLNetworkEvent.ServerCustomPacketEvent event) throws UnsupportedEncodingException {
		Packet packet = event.packet.toC17Packet();

		if (packet instanceof C17PacketCustomPayload) {
			C17PacketCustomPayload pluginMessage = (C17PacketCustomPayload) packet;

			System.out.println("Message re�ue : " + pluginMessage.func_149559_c() + ". Il contient : "
					+ new String(pluginMessage.func_149558_e(), "UTF-8"));
		}
	}

	@SubscribeEvent
	public void onClientPacket(FMLNetworkEvent.ClientCustomPacketEvent event) throws UnsupportedEncodingException {
		Packet packet = event.packet.toC17Packet();

		if (packet instanceof C17PacketCustomPayload) {
			C17PacketCustomPayload pluginMessage = (C17PacketCustomPayload) packet;

			System.out.println("Channel : " + pluginMessage.func_149559_c());
			System.out.println("Data content : " + new String(pluginMessage.func_149558_e(), "UTF-8"));
			

//			if (!Minecraft.getMinecraft().theWorld.isRemote)
//				Minecraft.getMinecraft().displayGuiScreen(new GUIFactionMain(Minecraft.getMinecraft()));
		}
	}
}
