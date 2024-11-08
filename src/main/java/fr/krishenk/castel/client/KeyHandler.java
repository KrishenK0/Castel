package fr.krishenk.castel.client;

import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.ArrayUtils;
import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.ObfuscationReflectionHelper;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent.KeyInputEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.krishenk.castel.Castel;
import fr.krishenk.castel.server.packet.PacketDispatcher;
import fr.krishenk.castel.server.packet.impl.OpenGuiPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiKeyBindingList;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.IntHashMap;
import net.minecraft.util.StatCollector;

public class KeyHandler {
	private static Minecraft mc;
	/** Key index for easy handling */
	public static final int CUSTOM_INV = 0;

	/** Key descriptions; use a language file to localize the description later */
	private static final String[] desc = { "key.castel.desc" };

	/** Default key values */
	private static final int[] keyValues = { Keyboard.KEY_P };

	/**
	 * Make this public or provide a getter if you'll need access to the key
	 * bindings from elsewhere
	 */
	public static final KeyBinding[] keys = new KeyBinding[desc.length];

	public KeyHandler(Minecraft mc) {
		this.mc = mc;
		for (int i = 0; i < desc.length; ++i) {
			keys[i] = new KeyBinding(desc[i], keyValues[i], StatCollector.translateToLocal("key.castel.label"));
			ClientRegistry.registerKeyBinding(keys[i]);
			KeyBinding keyInventory = Minecraft.getMinecraft().gameSettings.keyBindInventory;
		}
	}

	/**
	 * KeyInputEvent is in the FML package, so we must register to the FML event bus
	 */
	@SubscribeEvent
	public void onEvent(KeyInputEvent event) {
		// checking inGameHasFocus prevents your keys from firing when the player is
		// typing a chat message
		// NOTE that the KeyInputEvent will NOT be posted when a gui screen such as the
		// inventory is open
		// so we cannot close an inventory screen from here; that should be done in the
		// GUI itself
		if (mc.inGameHasFocus) {
			if (!(Minecraft.getMinecraft().thePlayer.capabilities.isCreativeMode)) {
				if (Minecraft.getMinecraft().gameSettings.keyBindInventory.isPressed()) {
					PacketDispatcher.sendToServer(new OpenGuiPacket(Castel.CASTEL_MENU_GUI_ID));
				}
			}
		}
	}
}
