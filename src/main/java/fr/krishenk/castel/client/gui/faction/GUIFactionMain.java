package fr.krishenk.castel.client.gui.faction;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.krishenk.castel.Castel;
import fr.krishenk.castel.client.gui.GUICastel;
import fr.krishenk.castel.client.gui.GUICustomButton;
import fr.krishenk.castel.client.gui.GUIScrollBarFaction;
import fr.krishenk.castel.client.gui.TabGui;
import fr.krishenk.castel.client.gui.override.GUICustomContainer;
import fr.krishenk.castel.client.gui.override.inventory.GUICustomInventory;
import fr.krishenk.castel.server.packet.ExtendedPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class GUIFactionMain extends GuiScreen {

	private final static List<TabGui> TABS = new ArrayList<TabGui>();

	private final ResourceLocation bg = new ResourceLocation(Castel.MODID, "textures/gui/factionMain.png");

	private final int xSize = 240;
	private final int ySize = 233;

	private int guiX;
	private int guiY;

	protected GUIScrollBarFaction scrollBarOnline;
	protected String hoveredPlayer = "";

	private static Minecraft mc;

	public GUIFactionMain(Minecraft mc) {
		initTabs();
		this.mc = mc;
	}

	@Override
	public void initGui() {
		guiX = (this.width - this.xSize) / 2;
		guiY = (this.height - this.ySize) / 2;

		this.scrollBarOnline = new GUIScrollBarFaction((this.guiX + 109), (this.guiY + 140), 74);

		buttonList.clear();
		int i;
		int di;
		for (i = 0; i < Math.floor((float) (TABS.size()) / 2); i++) {
			di = (TABS.get(i).getClassReferent().equals(this.getClass())) ? 26 : 0;
			
			buttonList.add(new GUICustomButton(i, (guiX - 26), guiY + 20 + 50 * i, "").bindTexture(bg, 512, 512)
					.setButtonDim(26 + di*2, 233, 26, 32));
		}

		for (int j = 0; j < Math.ceil((float) (TABS.size()) / 2); j++) {
			di = (TABS.get(j+i).getClassReferent().equals(this.getClass())) ? 26 : 0;
			buttonList.add(new GUICustomButton(j + i, (guiX + xSize), guiY + 20 + 50 * j, "").bindTexture(bg, 512, 512)
					.setButtonDim(0 + di*2, 233, 26, 32));
		}
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float ticks) {
		mc.renderEngine.bindTexture(bg);
		GUICastel.drawGuiBackground(guiX, guiY, xSize, ySize, 512, 512);

		GUICastel.drawCenteredString(GUICastel.factionName, this.width / 2, this.height / 2 - 109, 0x636363);
		GUICastel.drawScaledString(GUICastel.factionStats, (int) this.width / 2, (int) this.height / 2 - 8, 0xFFFFFF,
				0.7F, true, false);

		super.drawScreen(mouseX, mouseY, ticks);
		// this.func_152125_a((guiX + xSize) + 3, guiY + 24, 0, 265, 18, 18, 18, 18,
		// 512, 512);

		String tooltipToDraw = "";

		GUICastel.startGlScissor(this.guiX + 27, this.guiY + 135, 81, 86);
		for (int i = 0; i < GUICastel.playerOnline.size(); i++) {
			String playerName = GUICastel.playerOnline.get(i);
			int offsetX = this.guiX + 27;
			Float offsetY = Float.valueOf((this.guiY + 135 + i * 15) + getSlideOnline());
			if (mouseX > offsetX && mouseX < offsetX + 116 && mouseY > offsetY.floatValue()
					&& mouseY < offsetY.floatValue() + 15.0F)
				this.hoveredPlayer = playerName;
			mc.renderEngine.bindTexture(bg);
			func_146110_a(offsetX, offsetY.intValue(), 27, 135, 81, 15, 512, 512);
			try {
				ResourceLocation resourcelocation = AbstractClientPlayer.locationStevePng;
				resourcelocation = AbstractClientPlayer.getLocationSkin(playerName);
				AbstractClientPlayer.getDownloadImageSkin(resourcelocation, playerName);
				(Minecraft.getMinecraft()).renderEngine.bindTexture(resourcelocation);
				this.mc.getTextureManager().bindTexture(resourcelocation);
				GUICastel.func_152125_a(offsetX + 6 + 10, offsetY.intValue() + 4 + 8, 8.0F, 16.0F, 8, -8, -10, -10,
						64.0F, 64.0F);
				GUICastel.func_152125_a(offsetX + 6 + 10, offsetY.intValue() + 4 + 8, 40.0F, 16.0F, 8, -8, -10, -10,
						64.0F, 64.0F);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			GUICastel.drawScaledString(playerName, offsetX + 20, offsetY.intValue() + 4, 11842740, 0.8F, false, true);
			this.mc.renderEngine.bindTexture(bg);
			if (mouseX >= offsetX + 6 && mouseX <= offsetX + 6 + 10 && mouseY >= offsetY.floatValue() + 5.0F
					&& mouseY <= offsetY.floatValue() + 5.0F + 11.0F)
				tooltipToDraw = playerName;
		}
		GUICastel.endGLScissor();
		if (mouseX > this.guiX + 27 && mouseX < this.guiX + 116 && mouseY > this.guiY + 138 && mouseY < this.guiY + 220)
			this.scrollBarOnline.draw(mouseX, mouseY);

		int i;
		for (i = 0; i < Math.floor((float) (TABS.size()) / 2); i++) {
			GUICastel.drawScaledCustomSizeModalRect(guiX - 20, guiY + 26 + 50 * i, 18 * i, 265, 18, 18, 18, 18, 512,
					512);
		}

		for (int j = 0; j < Math.ceil((float) (TABS.size()) / 2); j++) {
			GUICastel.drawScaledCustomSizeModalRect(guiX + xSize + 2, guiY + 26 + 50 * j, 18 * (j + i), 265, 18, 18, 18,
					18, 512, 512);
		}

		if (!tooltipToDraw.isEmpty()) {

			final String tooltipToDrawFinal = tooltipToDraw;
			drawTooltip((new ArrayList() {
				{
					add(tooltipToDrawFinal);
				}
			}), mouseX, mouseY);
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
				return (Class) GUICustomInventory.class;
			}

			public void call() {
				Minecraft.getMinecraft().displayGuiScreen(new GUICustomInventory((EntityPlayer) mc.thePlayer,
						mc.thePlayer.inventory, ExtendedPlayer.get((EntityPlayer) mc.thePlayer).inventory));
			}
		});

		TABS.add(new TabGui() {
			public Class<? extends GuiScreen> getClassReferent() {
				return (Class) GUIFactionMain.class;
			}

			public void call() {
				Minecraft.getMinecraft().displayGuiScreen(new GUIFactionMain(mc));
			}
		});

		TABS.add(new TabGui() {
			public Class<? extends GuiScreen> getClassReferent() {
				return (Class) GUIFactionBank.class;
			}

			public void call() {
				Minecraft.getMinecraft().displayGuiScreen(new GUIFactionBank(mc));
			}
		});

		TABS.add(new TabGui() {
			public Class<? extends GuiScreen> getClassReferent() {
				return (Class) GUIFactionPerm.class;
			}

			public void call() {
				Minecraft.getMinecraft().displayGuiScreen(new GUIFactionPerm(mc));
			}
		});

		TABS.add(new TabGui() {
			public Class<? extends GuiScreen> getClassReferent() {
				return (Class) GUIFactionFlag.class;
			}

			public void call() {
				Minecraft.getMinecraft().displayGuiScreen(new GUIFactionFlag(mc));
			}
		});
	}

	private float getSlideOnline() {
		return (((ArrayList) GUICastel.playerOnline).size() > 4)
				? ((-(((ArrayList) GUICastel.playerOnline).size() - 4) * 15) * this.scrollBarOnline.getSliderValue())
				: 0.0F;
	}

	public void drawTooltip(List<String> text, int mouseX, int mouseY) {
		int mouseXGui = mouseX - guiX;
		int mouseYGui = mouseY - guiY;
		super.drawHoveringText(text, mouseX, mouseY, this.fontRendererObj);

	}

}
