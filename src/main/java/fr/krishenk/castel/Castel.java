package fr.krishenk.castel;

import fr.krishenk.castel.common.blocks.CastelBlocks;
import fr.krishenk.castel.common.items.CastelItems;
import fr.krishenk.castel.network.TransferPacketHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.common.Mod;
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

        eventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        DeferredWorkQueue.runLater(() -> {
        });
    }
}
