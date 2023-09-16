package fr.krishenk.castel.common;

import fr.krishenk.castel.Castel;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.awt.event.KeyEvent;
import java.security.Key;

@OnlyIn(Dist.CLIENT)
public class Keybinds {

    public static KeyBinding factionGui;

    public static KeyBinding debugKey;

    @OnlyIn(Dist.CLIENT)
    public static void register(FMLClientSetupEvent eventBus) {
        factionGui = create("faction_gui", KeyEvent.VK_F);
        debugKey = create("debug_key", KeyEvent.VK_O);
        ClientRegistry.registerKeyBinding(factionGui);
        ClientRegistry.registerKeyBinding(debugKey);
    }

    private static KeyBinding create(String name, int key) {
        return new KeyBinding("key."+ Castel.MODID+"."+name, key, "key.category."+Castel.MODID);
    }
}
