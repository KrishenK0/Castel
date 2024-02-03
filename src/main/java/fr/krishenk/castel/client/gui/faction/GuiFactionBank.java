package fr.krishenk.castel.client.gui.faction;

import com.mojang.blaze3d.matrix.MatrixStack;
import fr.krishenk.castel.Castel;
import fr.krishenk.castel.FactionInfo;
import fr.krishenk.castel.client.gui.GuiCastel;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class GuiFactionBank extends GuiCastel {
    protected static ITextComponent title = new TranslationTextComponent("gui.faction.bank.title");

    private boolean isChestOpen = false;

    public GuiFactionBank() {
        super(title, new ResourceLocation(Castel.MODID, "textures/gui/faction-bank.png"), 256, 286, 206, 233);
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        super.render(matrixStack, mouseX, mouseY, partialTicks);

        this.drawScaledCenteredString(matrixStack, String.valueOf(getGuild().getBank()), this.guiX+this.guiLeft/2, this.guiY+82, 1F, 0xFFFFFF);

        this.minecraft.getTextureManager().bindTexture(this.GUI_TEXTURE);
        if (isChestOpen) {
            blit(matrixStack, this.guiX + 77, this.guiY + 37, 54, 233,54, 43, xSize, ySize);
            this.renderTooltip(matrixStack, new StringTextComponent("Open faction chest"), mouseX, mouseY);
        } else blit(matrixStack, this.guiX + 77, this.guiY + 40, 0, 236,54, 40, xSize, ySize);

    }

    @Override
    public void mouseMoved(double mouseX, double mouseY) {
        super.mouseMoved(mouseX, mouseY);
        isChestOpen = (mouseX >= this.guiX + 77 && mouseX <= this.guiX + 129 && mouseY >= this.guiY + 40 && mouseY <= this.guiY + 79);
    }
}
