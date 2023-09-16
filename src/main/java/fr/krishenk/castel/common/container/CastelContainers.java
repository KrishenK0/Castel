package fr.krishenk.castel.common.container;

import fr.krishenk.castel.Castel;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class CastelContainers {
    public static DeferredRegister<ContainerType<?>> CONTAINERS =
            DeferredRegister.create(ForgeRegistries.CONTAINERS, Castel.MODID);

    public static final RegistryObject<ContainerType<FactionChestContainers>> FACTION_CHEST_CONTAINER =
            CONTAINERS.register("faction_chest_container",
                    () -> IForgeContainerType.create((windowId, inv, data) -> {
                        return new FactionChestContainers(windowId, inv, inv.player);
                    }));

    public static void register(IEventBus bus) {
        CONTAINERS.register(bus);
    }
}
