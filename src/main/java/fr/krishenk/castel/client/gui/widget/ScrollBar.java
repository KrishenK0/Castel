package fr.krishenk.castel.client.gui.widget;

import com.mojang.blaze3d.matrix.MatrixStack;
import fr.krishenk.castel.Castel;
import fr.krishenk.castel.client.gui.GuiCastel;
import net.minecraft.util.ResourceLocation;

public class ScrollBar {
    public static ResourceLocation WIDGET_LOCATION = new ResourceLocation(Castel.MODID, "textures/gui/widget.png");
    private final GuiCastel parent;
    private final int x;
    private final int y;
    private final int height;
    private final int width;
    private float sliderValue = 0F;

    public ScrollBar(GuiCastel parent, int x, int y, int width, int height) {
        this.parent = parent;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void setScrollValuePosY(double posY) {
        this.sliderValue = (float) (posY-y)/height;
        checkExtremum();
    }

    public void scrollHandler(double delta) {
        this.sliderValue += (-delta) * 0.05F;
        checkExtremum();
    }

    private void checkExtremum() {
        if (this.sliderValue < 0.0F)
            this.sliderValue = 0.0F;
        if (this.sliderValue > 1.0F)
            this.sliderValue = 1.0F;
    }

    public void drawScroller(MatrixStack matrixStack) {
        this.parent.drawVLine(matrixStack, x+1, y, y+height, 3,0xFF303030);
        this.parent.getMinecraft().getTextureManager().bindTexture(WIDGET_LOCATION);
        this.parent.blit(matrixStack, x, (int) (y - 7 + height * this.sliderValue), 76, 206, width, 15);
    }

    public float getSliderValue() {
        return sliderValue;
    }

    public boolean isMouseHover(double mouseX, double mouseY) {
        return (mouseX >= x && mouseX <= (x+width) && mouseY >= y && mouseY <= (y+height));
    }
}