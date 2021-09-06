package fr.krishenk.castel.client.gui;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class GUICustomButton extends GuiButton {

	private static GUICustomButton button;
	private static ResourceLocation texture;
	private static int textureWidth;
	private static int textureHeight;
	private static int u;
	private static int v;
	private static int du;
	private static int dv;

	public GUICustomButton(int id, int x, int y, String text) {
		super(id, x, y, text);

		this.button = this;

	}

	public GUICustomButton bindTexture(ResourceLocation texture, int textureWidth, int textureHeight) {
		this.texture = texture;
		this.textureWidth = textureWidth;
		this.textureHeight = textureHeight;
		return button;
	}

	public GUICustomButton setButtonDim(int u, int v, int uWidth, int vHeight) {
		this.u = u;
		this.v = v;
		this.du = uWidth;
		this.dv = vHeight;		
		return button;
	}

	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY) {
		if (this.visible) {
			boolean mouseHover = mouseX >= this.xPosition && mouseY >= yPosition && mouseX < this.xPosition + this.width
					&& mouseY < this.yPosition + this.height;
			if (mouseHover) {
				mc.getTextureManager().bindTexture(texture);
			} else {
				mc.getTextureManager().bindTexture(texture);
			}

			GL11.glEnable(GL11.GL_BLEND);
			OpenGlHelper.glBlendFunc(770, 771, 1, 0);
			this.width = 32;
			this.height = 26;
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			// x, y, u, v, uWidth, vHeight, width, height, textureWidth, textureHeight
			this.func_152125_a(this.xPosition, this.yPosition, u, v, du, dv, du, dv, textureWidth, textureHeight);
			this.mouseDragged(mc, mouseX, mouseY);

		}
	}

}