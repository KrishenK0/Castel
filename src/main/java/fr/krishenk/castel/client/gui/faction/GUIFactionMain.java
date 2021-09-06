package fr.krishenk.castel.client.gui.faction;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.krishenk.castel.Castel;
import fr.krishenk.castel.client.gui.GUICastel;
import fr.krishenk.castel.client.gui.GUICustomButton;
import fr.krishenk.castel.client.gui.TabGui;
import fr.krishenk.castel.client.gui.override.GUICustomContainer;
import fr.krishenk.castel.client.gui.override.inventory.GUICustomInventory;
import fr.krishenk.castel.server.packet.ExtendedPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class GUIFactionMain extends GuiScreen {

	private final static List<TabGui> TABS = new ArrayList<TabGui>();

	private final ResourceLocation bg = new ResourceLocation(Castel.MODID, "textures/gui/factionMain.png");

	private final int xSize = 240;
	private final int ySize = 233;

	private int guiX;
	private int guiY;

	private GUICustomButton factionBank;

	private static Minecraft mc;

	public GUIFactionMain(Minecraft mc) {
		initTabs();
		this.mc = mc;
	}

	@Override
	public void initGui() {
		guiX = (this.width - this.xSize) / 2;
		guiY = (this.height - this.ySize) / 2;

		buttonList.clear();
		for (int i = 0; i < TABS.size(); i++) {
			buttonList.add(new GUICustomButton(i, (guiX + xSize), guiY + 20 + 50 * i, "").bindTexture(bg, 512, 512)
					.setButtonDim(0, 233, 26, 32));
		}
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float ticks) {
		mc.renderEngine.bindTexture(bg);
		GUICastel.drawGuiBackground(guiX, guiY, xSize, ySize);

		GUICastel.drawCenteredString(GUICastel.factionName, this.width / 2, this.height / 2 - 109, 0x636363);
		GUICastel.drawScaledString(GUICastel.factionStats, (int) this.width / 2, (int) this.height / 2 - 8, 0xFFFFFF,
				0.7F, true, false);

		super.drawScreen(mouseX, mouseY, ticks);
		// this.func_152125_a((guiX + xSize) + 3, guiY + 24, 0, 265, 18, 18, 18, 18,
		// 512, 512);

		for (int i = 0; i < TABS.size(); i++) {
			this.func_152125_a((guiX + xSize) + 2, guiY + 26 + 50 * i, 18 * i, 265, 18, 18, 18, 18, 512, 512);
		}
	}

	@Override
	public void actionPerformed(GuiButton button) {
		for (int i = 0; i < TABS.size(); i++) {
			if (button.id == i) {
				try {
					TABS.get(i).call();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		super.actionPerformed(button);
	}

	public static void initTabs() {
		TABS.clear();
		TABS.add(new TabGui() {
			public Class<? extends GuiScreen> getClassReferent() {
				return (Class) GUIFactionBank.class;
			}

			public void call() {
				Minecraft.getMinecraft().displayGuiScreen(new GUIFactionBank(Minecraft.getMinecraft()));
			}
		});
		
		TABS.add(new TabGui() {
			public Class<? extends GuiScreen> getClassReferent() {
				return (Class) GUICustomInventory.class;
			}

			public void call() {
				Minecraft.getMinecraft().displayGuiScreen(new GUICustomInventory((EntityPlayer) mc.thePlayer,
						mc.thePlayer.inventory, ExtendedPlayer.get((EntityPlayer) mc.thePlayer).inventory));
			}
		});
	}
}