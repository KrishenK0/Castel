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
	private ResourceLocation texture;
	private int textureWidth;
	private int textureHeight;
	private int u;
	private int v;
	private int du;
	private int dv;

	public GUICustomButton(int id, int x, int y, String text) {
		super(id, x, y, text);
	}

	public GUICustomButton bindTexture(ResourceLocation texture, int textureWidth, int textureHeight) {
		this.texture = texture;
		this.textureWidth = textureWidth;
		this.textureHeight = textureHeight;
		return this;
	}

	public GUICustomButton setButtonDim(int u, int v, int uWidth, int vHeight) {
		this.u = u;
		this.v = v;
		this.du = super.width = uWidth;
		this.dv = super.height = vHeight;

		return this;
	}

	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY) {
		if (this.visible) {
			boolean mouseHover = mouseX >= this.xPosition && mouseY >= yPosition && mouseX < this.xPosition + this.width
					&& mouseY < this.yPosition + this.height;
			if (mouseHover) {
				mc.getTextureManager().bindTexture(this.texture);
			} else {
				mc.getTextureManager().bindTexture(this.texture);
			}

			GL11.glEnable(GL11.GL_BLEND);
			OpenGlHelper.glBlendFunc(770, 771, 1, 0);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			// x, y, u, v, uWidth, vHeight, width, height, textureWidth, textureHeight
			// System.out.println(String.valueOf(this.du));
			GUICastel.drawScaledCustomSizeModalRect(this.xPosition, this.yPosition, this.u, this.v, this.du, this.dv,
					this.du, this.dv, this.textureWidth, this.textureHeight);
			this.mouseDragged(mc, mouseX, mouseY);

		}
	}

}
