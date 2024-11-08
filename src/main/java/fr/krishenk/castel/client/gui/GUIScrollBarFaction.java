package fr.krishenk.castel.client.gui;

import org.lwjgl.input.Mouse;

import fr.krishenk.castel.Castel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;

public class GUIScrollBarFaction extends Gui {
	protected float x;
	protected float y;
	protected int width;
	protected int height;
	protected boolean dragging;
	protected float sliderValue = 0.0F;
	protected boolean selected;
	protected boolean isPressed = false;
	protected float increment = 0.05F;

	public GUIScrollBarFaction(float x, float y, int height) {
		this.x = x;
		this.y = y;
		this.width = 4;
		this.height = height;
	}

	public void setScrollIncrement(float i) {
		this.increment = i;
	}

	protected void drawScroller() {
		Minecraft.getMinecraft().renderEngine
				.bindTexture(new ResourceLocation(Castel.MODID, "textures/gui/factionMain.png"));
		func_146110_a((int) this.x, (int) (this.y + (this.height - 16) * this.sliderValue), 104, 233, 3, 20, 512, 512);
	}

	public void draw(int mouseX, int mouseY) {
		drawScroller();
		if (!Mouse.isButtonDown(0)) {
			if (this.isPressed) {
				mouseReleased(mouseX, mouseY);
				this.isPressed = false;
			}
			while (!(Minecraft.getMinecraft()).gameSettings.touchscreen && Mouse.next()) {
				int l2 = Mouse.getEventDWheel();
				if (l2 != 0) {
					if (l2 > 0) {
						l2 = -1;
					} else if (l2 < 0) {
						l2 = 1;
					}
					this.sliderValue += l2 * this.increment;
					if (this.sliderValue < 0.0F)
						this.sliderValue = 0.0F;
					if (this.sliderValue > 1.0F)
						this.sliderValue = 1.0F;
				}
			}
		} else if (!this.isPressed) {
			this.isPressed = true;
			mousePressed(Minecraft.getMinecraft(), mouseX, mouseY);
		}
		mouseDragged(Minecraft.getMinecraft(), mouseX, mouseY);
	}

	public void mouseReleased(int mouseX, int mouseY) {
		if (this.selected)
			this.dragging = this.selected = false;
	}

	public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
		if (mouseX >= this.x - 2.0F && mouseX <= this.x + this.width + 2.0F && mouseY >= this.y
				&& mouseY <= this.y + this.height) {
			this.selected = true;
			this.sliderValue = (mouseY - this.y) / this.height;
			if (this.sliderValue < 0.0F)
				this.sliderValue = 0.0F;
			if (this.sliderValue > 1.0F)
				this.sliderValue = 1.0F;
			this.dragging = true;
			return true;
		}
		return false;
	}

	protected void mouseDragged(Minecraft mc, int mouseX, int mouseY) {
		if (this.dragging) {
			this.sliderValue = (mouseY - this.y) / this.height;
			if (this.sliderValue < 0.0F)
				this.sliderValue = 0.0F;
			if (this.sliderValue > 1.0F)
				this.sliderValue = 1.0F;
		}
	}

	public float getSliderValue() {
		return sliderValue;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void reset() {
		this.sliderValue = 0.0F;
	}

	public void setSliderValue(float sliderValue) {
		this.sliderValue = sliderValue;
	}
}
