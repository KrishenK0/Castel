package fr.krishenk.castel.client.gui.faction;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.krishenk.castel.client.gui.GUICastel;
import fr.krishenk.castel.client.gui.GUICustomButton;
import fr.krishenk.castel.client.gui.TabGui;
import fr.krishenk.castel.client.gui.override.inventory.GUICustomInventory;
import fr.krishenk.castel.server.packet.ExtendedPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
@SideOnly(Side.CLIENT)
public class GUIFactionBank extends GuiScreen {

	private final ResourceLocation bg = new ResourceLocation("castel", "textures/gui/factionBank.png");
	private final static List<TabGui> TABS = new ArrayList<TabGui>();

	private final int xSize = 206;
	private final int ySize = 232;
	
	private static Minecraft mc;

	private int xGui;
	private int yGui;

	public GUIFactionBank(Minecraft mc) {
		initTabs();
		this.mc = mc;
	}

	@Override
	public void initGui() {
		xGui = (this.width - this.xSize) / 2;
		yGui = (this.height - this.ySize) / 2;
		
		buttonList.clear();
		for (int i = 0; i < TABS.size(); i++) {
			if(TABS.get(i).getClassReferent() != this.getClass())
				buttonList.add(new GUICustomButton(i, (xGui + xSize), yGui + 20 + 50 * i, "").bindTexture(bg, 512, 512)
						.setButtonDim(0, 233, 26, 32));
		}
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float ticks) {
		mc.renderEngine.bindTexture(bg);
		GUICastel.drawGuiBackground(xGui, yGui, xSize, ySize);
		
		GUICastel.drawCenteredString(GUICastel.factionName, this.width / 2, this.height / 2 - 109, 0x636363);
		GUICastel.drawCenteredString(GUICastel.numeroFormat(GUICastel.factionBank) + " CM", this.width / 2, this.height / 2 - 34, 0xFFE42E);
		

		super.drawScreen(mouseX, mouseY, ticks);
		
		for (int i = 0; i < TABS.size(); i++) {
			if(TABS.get(i).getClassReferent() != this.getClass())
				this.func_152125_a((xGui + xSize) + 2, yGui + 26 + 50 * i, 18 * i, 265, 18, 18, 18, 18, 512, 512);
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
