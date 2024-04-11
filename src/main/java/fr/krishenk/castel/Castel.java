package fr.krishenk.castel;

import fr.krishenk.castel.client.gui.faction.FactionChest;
import fr.krishenk.castel.common.Keybinds;
import fr.krishenk.castel.common.container.CastelContainers;
import fr.krishenk.castel.server.network.PacketHandler;
import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Castel.MODID)
public class Castel {

    public static final String MODID = "castel";
    public static final Logger LOGGER = LogManager.getLogger(MODID);

    public Castel() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        CastelContainers.register(eventBus);
        eventBus.addListener(this::commonSetup);
        eventBus.addListener(this::clientSetup);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void clientSetup(FMLClientSetupEvent event) {
        DeferredWorkQueue.runLater(() -> {
//            new FactionInfo();
            Keybinds.register(event);
            ScreenManager.registerFactory(CastelContainers.FACTION_CHEST_CONTAINER.get(),
                    FactionChest::new);
        });
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        DeferredWorkQueue.runLater(() -> {
            PacketHandler.init();
        });
    }
}
