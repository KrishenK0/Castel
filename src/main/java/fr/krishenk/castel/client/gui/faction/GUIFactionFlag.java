package fr.krishenk.castel.client.gui.faction;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.imageio.ImageIO;

import org.lwjgl.opengl.GL11;

import com.google.common.util.concurrent.UncheckedExecutionException;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.krishenk.castel.client.gui.GUICastel;
import fr.krishenk.castel.client.gui.GUICustomButton;
import fr.krishenk.castel.client.gui.TabGui;
import fr.krishenk.castel.client.gui.override.inventory.GUICustomInventory;
import fr.krishenk.castel.server.packet.ExtendedPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ResourceLocation;
import scala.actors.threadpool.Arrays;

@SideOnly(Side.CLIENT)
public class GUIFactionFlag extends GuiScreen {
	private final static List<TabGui> TABS = new ArrayList<TabGui>();
	private final ResourceLocation bg = new ResourceLocation("castel", "textures/gui/factionFlag.png");
	private static Minecraft mc;

	private HashMap<Integer, Integer> pixels = new HashMap<>();
	private int pixelHoveredId = -1;
	private boolean mouseDrawing = true;
	private int colorSelected = -1;
	private int colorHovered = 0;

	private final int xSize = 240;
	private final int ySize = 233;

	private int guiX;
	private int guiY;
	private ArrayList<Integer> colorList = new ArrayList<>();

	public GUIFactionFlag(Minecraft mc) {
		initTabs();
		this.mc = mc;

		for (int i = 0; i < 338; i++) {
			this.pixels.put(Integer.valueOf(i), Integer.valueOf(-1));
		}

		this.colorList.addAll(
				Arrays.asList(new Integer[] { Integer.valueOf(hexToRGB(0x0000ff)), Integer.valueOf(hexToRGB(0xff0000)),
						Integer.valueOf(hexToRGB(0x0cff00)), Integer.valueOf(hexToRGB(0xff00f6)),
						Integer.valueOf(hexToRGB(0xfcff00)), Integer.valueOf(hexToRGB(0xff6c00)),
						Integer.valueOf(hexToRGB(0x9800df)), Integer.valueOf(hexToRGB(0xffffff)),
						Integer.valueOf(hexToRGB(0x000098)), Integer.valueOf(hexToRGB(0x960d0d)),
						Integer.valueOf(hexToRGB(0x08a800)), Integer.valueOf(hexToRGB(0xa900a3)),
						Integer.valueOf(hexToRGB(0xc9a100)), Integer.valueOf(hexToRGB(0xb64d00)),
						Integer.valueOf(hexToRGB(0x7600ad)), Integer.valueOf(hexToRGB(0x939393)),
						Integer.valueOf(hexToRGB(0x000063)), Integer.valueOf(hexToRGB(0x701010)),
						Integer.valueOf(hexToRGB(0x057100)), Integer.valueOf(hexToRGB(0x7b0077)),
						Integer.valueOf(hexToRGB(0x9f7000)), Integer.valueOf(hexToRGB(0x8b3b00)),
						Integer.valueOf(hexToRGB(0x520079)), Integer.valueOf(hexToRGB(0x464646)),
						Integer.valueOf(hexToRGB(0x030330)), Integer.valueOf(hexToRGB(0x370d0d)),
						Integer.valueOf(hexToRGB(0x034a00)), Integer.valueOf(hexToRGB(0x460044)),
						Integer.valueOf(hexToRGB(0x674900)), Integer.valueOf(hexToRGB(0x622900)),
						Integer.valueOf(hexToRGB(0x34004d)), Integer.valueOf(hexToRGB(0x000000))

				}));

	}

	@Override
	public void initGui() {
		guiX = (this.width - xSize) / 2;
		guiY = (this.height - ySize) / 2;

		buttonList.clear();
		int i;
		int j;
		int di;
		for (i = 0; i < Math.floor((float) (TABS.size()) / 2); i++) {
			di = (TABS.get(i).getClassReferent().equals(this.getClass())) ? 26 : 0;

			buttonList.add(new GUICustomButton(i, (guiX - 26), guiY + 20 + 50 * i, "").bindTexture(bg, 286, 286)
					.setButtonDim(26 + di * 2, 233, 26, 32));
		}
		for (j = 0; j < Math.ceil((float) (TABS.size()) / 2); j++) {
			di = (TABS.get(j+i).getClassReferent().equals(this.getClass())) ? 26 : 0;
			buttonList.add(new GUICustomButton(j + i, (guiX + xSize), guiY + 20 + 50 * j, "").bindTexture(bg, 286, 286)
					.setButtonDim(0 + di*2, 233, 26, 32));
		}
		buttonList.add(new GuiButton(1 + i + j, 0, 0, "Save"));
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float ticks) {
		mc.renderEngine.bindTexture(bg);
		GUICastel.drawGuiBackground(guiX, guiY, xSize, ySize, 286, 286);

		GUICastel.drawCenteredString(GUICastel.factionName, this.width / 2, this.height / 2 - 109, 0x636363);

		super.drawScreen(mouseX, mouseY, ticks);

		int x = 0;
		int y = 0;
		int pI = 0;
		for (pI = 0; pI < this.colorList.size(); pI++) {
			int colorId = ((Integer) this.colorList.get(pI)).intValue();
			// 89,156 218,224
			if (mouseX > this.guiX + 89 + x * 16 && mouseX < this.guiX + 218 + x * 16 + 16
					&& mouseY > this.guiY + 156 + y * 16 && mouseY < this.guiY + 224 + y * 16 + 16)
				this.colorHovered = colorId;
			if (colorId == this.colorSelected) {
				mc.renderEngine.bindTexture(bg);
				GUICastel.drawScaledCustomSizeModalRect(this.guiX + 89 + x * 16, this.guiY + 159 + y * 16, 52, 233, 18,
						18, 18, 18, 286, 286);
			}
			x = (x < 7) ? (x + 1) : 0;
			y = (x == 0) ? (y + 1) : y;

		}

		if (this.pixels.size() > 0) {
			Iterator<Entry<Integer, Integer>> it = this.pixels.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry pair = it.next();
				int colorId = ((Integer) pair.getValue()).intValue();
				Gui.drawRect(this.guiX + 24 + x * 6, this.guiY + 25 + y * 6, this.guiX + 24 + x * 6 + 6,
						this.guiY + 25 + y * 6 + 6, colorId);

				x = (x < 25) ? (x + 1) : 0;
				y = (x == 0) ? (y + 1) : y;
				pI++;
			}
		}

		GL11.glColor4f(1F, 1F, 1F, 1F);
		mc.renderEngine.bindTexture(bg);
		int i;
		for (i = 0; i < Math.floor((float) (TABS.size()) / 2); i++) {
			GUICastel.drawScaledCustomSizeModalRect(guiX - 20, guiY + 26 + 50 * i, 18 * i, 265, 18, 18, 18, 18, 286,
					286);
		}

		for (int j = 0; j < Math.ceil((float) (TABS.size()) / 2); j++) {
			GUICastel.drawScaledCustomSizeModalRect(guiX + xSize + 2, guiY + 26 + 50 * j, 18 * (j + i), 265, 18, 18, 18,
					18, 286, 286);
		}
	}

	@Override
	public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
		super.mouseClicked(mouseX, mouseY, mouseButton);

		if (mouseButton == 0) {
			if (mouseX >= this.guiX + 89 && mouseX <= this.guiX + 218 && mouseY >= this.guiY + 159
					&& mouseY <= this.guiY + 224) {
				this.colorSelected = this.colorHovered;
				this.colorHovered = 0;
			}
			if (mouseX >= this.guiX + 75 && mouseX <= this.guiX + 88 && mouseY >= this.guiY + 207
					&& mouseY <= this.guiY + 222) {
				for (int i = 0; i < 338; i++) {
					this.pixels.put(Integer.valueOf(i), -1);
				}
			}
		}
	}

	@Override
	public void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
		// 25, 50
		if (clickedMouseButton == 0) {
			if (mouseX > (guiX + 25) && mouseX < (guiX + 175) && mouseY > (guiY + 50) && mouseY < (guiY + 126)) {

				int x = 0;
				int y = 0;
				int pI = 0;

				if (this.pixels.size() > 0) {
					Iterator<Entry<Integer, Integer>> it = this.pixels.entrySet().iterator();
					while (it.hasNext()) {
						Map.Entry pair = it.next();
						int colorId = ((Integer) pair.getValue()).intValue();
						if (mouseX >= this.guiX + 25 + x * 6 && mouseX <= this.guiX + 25 + x * 6 + 6
								&& mouseY >= this.guiY + 48 + y * 6 && mouseY <= this.guiY + 48 + y * 6 + 6)
							this.pixelHoveredId = pI;
						if (this.mouseDrawing && colorId != this.colorSelected && this.pixelHoveredId == pI) {
							this.pixels.put(Integer.valueOf(pI), Integer.valueOf(this.colorSelected));
							colorId = this.colorSelected;

						}

						x = (x < 25) ? (x + 1) : 0;
						y = (x == 0) ? (y + 1) : y;
						pI++;
					}
				}
			}

		}

		// mc.thePlayer.addChatMessage(new ChatComponentText(mouseX + " " + mouseY + " "
		// + clickedMouseButton + " " + timeSinceLastClick));
	}

	@Override
	public void actionPerformed(GuiButton button) {
		int i;
		for (i = 0; i < TABS.size(); i++) {
			if (button.id == i) {
				try {
					TABS.get(i).call();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		if (button.id == (i + 1)) {

			ArrayList<Integer> imagePixels = new ArrayList<>();
			BufferedImage image = new BufferedImage(152, 80, 2);
			Graphics2D graphics2D = image.createGraphics();
			int x = 0;
			int y = 0;
			Iterator<Map.Entry<Integer, Integer>> it = this.pixels.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry pair = it.next();
				imagePixels.add(Integer.valueOf((Integer) pair.getValue()).intValue());
				graphics2D.setPaint(new Color(((Integer) pair.getValue()).intValue()));
				graphics2D.fillRect(x * 6, y * 6, 6, 6);
				x = (x < 25) ? (x + 1) : 0;
				y = (x == 0) ? (y + 1) : y;
			}
			graphics2D.dispose();
			System.out.println(encodeToString(image, "png"));

		}

		super.actionPerformed(button);
	}

	public void initTabs() {
		TABS.clear();
		TABS.add(new TabGui() {
			public Class<? extends GuiScreen> getClassReferent() {
				return (Class) GUIFactionMain.class;
			}

			public void call() {
				Minecraft.getMinecraft().displayGuiScreen(new GUIFactionMain(Minecraft.getMinecraft()));
			}
		});
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
				return (Class) GUIFactionPerm.class;
			}

			public void call() {
				Minecraft.getMinecraft().displayGuiScreen(new GUIFactionPerm(Minecraft.getMinecraft()));
			}
		});

		TABS.add(new TabGui() {
			public Class<? extends GuiScreen> getClassReferent() {
				return (Class) GUIFactionFlag.class;
			}

			public void call() {
				Minecraft.getMinecraft().displayGuiScreen(new GUIFactionFlag(Minecraft.getMinecraft()));
			}
		});
	}

	public static String encodeToString(final BufferedImage img, final String format) {
		final ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			ImageIO.write(img, format, os);
			return Base64.getEncoder().encodeToString(os.toByteArray());
		} catch (final IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	public Integer hexToRGB(int par) {
		return new Color(par).getRGB();
	}

}
