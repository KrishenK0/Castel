package fr.krishenk.castel.client.gui.widget;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;

public class SimpleButton extends Button {
    public SimpleButton(int x, int y, int width, int height, ITextComponent message, IPressable onPress) {
        super(x+1, y+1, width, height, message, onPress);
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float tick) {
        super.render(matrixStack, mouseX, mouseY, tick);
        Minecraft minecraft = Minecraft.getInstance();
        FontRenderer fontrenderer = minecraft.fontRenderer;
        minecraft.getTextureManager().bindTexture(WIDGETS_LOCATION);
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, this.alpha);

        int i = this.isHovered() ? 0x00979796 : 0x00686868;
        int j = this.isHovered() ? 0x00202020 : 0x00C0C0C0;
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
        fill(matrixStack, this.x - 1, this.y - 1, this.x + this.width + 1, this.y + this.height + 1, -i);
        fill(matrixStack, this.x, this.y, this.x + this.width, this.y + this.height, -j);

        int k = this.getFGColor();
        drawCenteredString(matrixStack, fontrenderer, this.getMessage(), this.x + this.width / 2, this.y + (this.height - 8) / 2, k | MathHelper.ceil(this.alpha * 255.0F) << 24);

    }
}
