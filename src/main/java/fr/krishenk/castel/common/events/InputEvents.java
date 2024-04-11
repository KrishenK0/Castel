package fr.krishenk.castel.common.events;

import fr.krishenk.castel.Castel;
import fr.krishenk.castel.client.gui.faction.FactionTab;
import fr.krishenk.castel.common.Keybinds;
import fr.krishenk.castel.server.network.PacketHandler;
import fr.krishenk.castel.server.network.packet.FactionGuiCSPacket;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Castel.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class InputEvents {

    @SubscribeEvent
    public static void onKeyPress(InputEvent.KeyInputEvent event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.world == null) return;
        onInput(mc, event.getKey(), event.getAction());
    }

    @SubscribeEvent
    public static void onMouseClick(InputEvent.MouseInputEvent event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.world == null) return;
        onInput(mc, event.getButton(), event.getAction());
    }

    private static void onInput(Minecraft mc, int key, int action) {
        if (mc.currentScreen != null) return;
        if (Keybinds.factionGui.isPressed()) {
            PacketHandler.CHANNEL.sendToServer(new FactionGuiCSPacket(FactionTab.Tab.main));
            //mc.displayGuiScreen(new GuiFaction());
        } else if(Keybinds.debugKey.isPressed()) {
            PacketHandler.CHANNEL.sendToServer(new FactionGuiCSPacket(FactionTab.Tab.bank));
        }
    }
}
