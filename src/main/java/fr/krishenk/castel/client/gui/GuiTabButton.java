package fr.krishenk.castel.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class GuiTabButton extends Button {

    private ResourceLocation WIDGETS_LOCATION;
    private ITab button = null;
    private GuiCastel gui = null;
    private int position;

    public GuiTabButton(ResourceLocation resourceLocation, int x, int y, int width, int height, int position, ITab button, GuiCastel gui) {
        this(resourceLocation, x, y, width, height, button.getTitle(), button.getPressedAction());
        this.WIDGETS_LOCATION = resourceLocation;
        this.position = position;
        this.button = button;
        this.gui = gui;
    }

    private GuiTabButton(ResourceLocation resourceLocation, int x, int y, int width, int height, ITextComponent title, Button.IPressable pressedAction) {
        super(x, y, width, height, title, pressedAction);
        this.WIDGETS_LOCATION = resourceLocation;
    }

    @Override
    public void renderWidget(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        Minecraft minecraft = Minecraft.getInstance();
        FontRenderer fontrenderer = minecraft.fontRenderer;
        minecraft.getTextureManager().bindTexture(this.WIDGETS_LOCATION);
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, this.alpha);

        int i = this.getYImage(this.isHovered());
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();

        this.blit(matrixStack, this.x, this.y, 48 + position * 26, 106 + i * 32, this.width, this.height);

        //this.blit(matrixStack, this.x, this.y, 48, 106 + i * 32, this.width / 2, this.height);
        //this.blit(matrixStack, this.x + this.width / 2, this.y, 26 - this.width / 2, 32 + i * 32, this.width / 2, this.height);
        this.renderBg(matrixStack, minecraft, mouseX, mouseY);

        //int j = getFGColor();
        //drawCenteredString(matrixStack, fontrenderer, this.getMessage(), this.x + this.width / 2, this.y + (this.height - 8) / 2, j | MathHelper.ceil(this.alpha * 255.0F) << 24);

        //super.renderWidget(matrixStack, mouseX, mouseY, partialTicks);
    }

    @Override
    protected int getYImage(boolean isHovered) {
        int i = 0;
        if (!this.active) i = 0;
        else if (this.gui.getClass() == this.button.getClassReferent()) i = 1;
        else if (isHovered) i = 2;
        return i;
    }
}
