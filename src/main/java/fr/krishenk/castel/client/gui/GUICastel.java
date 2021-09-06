package fr.krishenk.castel.client.gui;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.Tessellator;

@SideOnly(Side.CLIENT)
public class GUICastel extends Gui {

	public static String factionName = "TEXXXXXXXXXXXXXXXXXXXXT";
	public static String factionStats = "Cr�ateur : KrishenK  Puissance : 715/800  Level : 77";
	public static int factionBank = 1234567890;

	public Minecraft mc;

	public static void drawGuiBackground(int guiX, int guiY, int xSize, int ySize) {
		GL11.glPushMatrix();
		GL11.glColor4f(1f, 1f, 1f, 1f);
		drawScaledCustomSizeModalRect(guiX, guiY, 0, 0, xSize, ySize, xSize, ySize, 512, 512);
		GL11.glPopMatrix();
	}

	/**
	 * Draw a scaled rect in <code>width</code> and <code>height</code>
	 * 
	 * @param x      Position X of the modal
	 * @param y      Position Y of the modal
	 * @param u      Position X of the modal in the texture
	 * @param v      Position Y of the modal in the texture
	 * @param du     Modal width in the texture
	 * @param dv     Modal height in the texture
	 * @param width  Width value in the screen
	 * @param height Height value in the screen
	 * @param tileX  Width value of the texture
	 * @param tileY  Height value of the texture
	 */
	public static void drawScaledCustomSizeModalRect(int x, int y, int u, int v, int du, int dv, int width, int height,
			float tileX, float tileY) {
		float f4 = 1.0F / tileX;
		float f5 = 1.0F / tileY;
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV((double) x, (double) (y + height), 0.0D, (double) (u * f4),
				(double) ((v + (float) dv) * f5));
		tessellator.addVertexWithUV((double) (x + width), (double) (y + height), 0.0D, (double) ((u + (float) du) * f4),
				(double) ((v + (float) dv) * f5));
		tessellator.addVertexWithUV((double) (x + width), (double) y, 0.0D, (double) ((u + (float) du) * f4),
				(double) (v * f5));
		tessellator.addVertexWithUV((double) x, (double) y, 0.0D, (double) (u * f4), (double) (v * f5));
		tessellator.draw();
	}

	public static void drawCenteredString(String text, int x, int y, int c) {
		Minecraft.getMinecraft().fontRenderer.drawString(text,
				x - Minecraft.getMinecraft().fontRenderer.getStringWidth(text) / 2, y, c);
		GL11.glColor4f(1F, 1F, 1F, 1F);
	}

	public static void drawScaledString(String text, int x, int y, int color, float scale, boolean centered,
			boolean shadow) {
		GL11.glPushMatrix();
		GL11.glScalef(scale, scale, scale);
		float newX = x;
		if (centered)
			newX = x - Minecraft.getMinecraft().fontRenderer.getStringWidth(text) * scale / 2.0F;
		if (shadow)
			Minecraft.getMinecraft().fontRenderer.drawString(text, (int) (newX / scale), (int) ((y + 1) / scale),
					(color & 0xFCFCFC) >> 2 | color & 0xFF000000, false);
		Minecraft.getMinecraft().fontRenderer.drawString(text, (int) (newX / scale), (int) (y / scale), color, false);
		GL11.glPopMatrix();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}

	public static String numeroFormat(int var1) {
		String var2 = String.valueOf(var1);
		String var3 = "";
		for (int i = var2.length(); i > 0; i--) {
			if (i % 3 == 0)
				var3 += " ";
			var3 += var2.charAt(var2.length() - i);
		}
		return var3;
	}
}