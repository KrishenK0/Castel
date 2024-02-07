package fr.krishenk.castel.client.gui.faction;

import com.mojang.blaze3d.matrix.MatrixStack;
import fr.krishenk.castel.Castel;
import fr.krishenk.castel.client.gui.GuiCastel;
import fr.krishenk.castel.client.gui.widget.ScrollBar;
import fr.krishenk.castel.common.constants.group.models.BankLog;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class GuiFactionBank extends GuiCastel {
    protected static ITextComponent title = new TranslationTextComponent("gui.faction.bank.title");

    private boolean isChestOpen = false;
    private List<BankLog> benefits;
    private List<BankLog> deficits;
    private ScrollBar benefitsScrollbar;
    private ScrollBar deficitsScrollbar;

    public GuiFactionBank(List<BankLog> logs) {
        super(title, new ResourceLocation(Castel.MODID, "textures/gui/faction-bank.png"), 256, 286, 206, 233);
        this.benefits = logs.stream().filter(x -> x.getAmount() >= 0).collect(Collectors.toList());
        this.benefits.sort(Collections.reverseOrder());
        this.deficits = logs.stream().filter(x -> x.getAmount() < 0).sorted(Collections.reverseOrder()).collect(Collectors.toList());
        this.deficits.sort(Collections.reverseOrder());
    }

    @Override
    protected void init() {
        super.init();
        this.benefitsScrollbar = new ScrollBar(this, this.guiX + 92, this.guiY + 149, 8, 65);
        this.deficitsScrollbar = new ScrollBar(this, this.guiX + 188, this.guiY + 149, 8, 65);
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

        this.deficitsScrollbar.drawScroller(matrixStack);
        this.benefitsScrollbar.drawScroller(matrixStack);

        renderPlayerList(matrixStack, this.guiX + 15, this.guiY + 141, 75, 80, 0x00AD2B, mouseX, mouseY, this.benefits, benefitsScrollbar);
        renderPlayerList(matrixStack, this.guiX + 110, this.guiY + 141, 75, 80, 0xFF0000, mouseX, mouseY, this.deficits, deficitsScrollbar);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (this.deficits.size() > 4 && this.deficitsScrollbar.isMouseHover(mouseX, mouseY) && button == 0)
            this.deficitsScrollbar.setScrollValuePosY(mouseY);

        if (this.benefits.size() > 4 && this.benefitsScrollbar.isMouseHover(mouseX, mouseY) && button == 0)
            this.benefitsScrollbar.setScrollValuePosY(mouseY);

        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        if (this.deficits.size() > 4 && this.deficitsScrollbar.isMouseHover(mouseX, mouseY) && button == 0)
            this.deficitsScrollbar.setScrollValuePosY(mouseY);

        if (this.benefits.size() > 4 && this.benefitsScrollbar.isMouseHover(mouseX, mouseY) && button == 0)
            this.benefitsScrollbar.setScrollValuePosY(mouseY);

        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        if (this.benefits.size() > 4 &&
                mouseX >= (this.guiX+10) && mouseX <= (this.guiX+99) && mouseY >= (this.guiY+138) && mouseY <= (this.guiY+224))
            this.benefitsScrollbar.scrollHandler(delta);

        if (this.deficits.size() > 4 &&
                mouseX >= (this.guiX+106) && mouseX <= (this.guiX+195) && mouseY >= (this.guiY+138) && mouseY <= (this.guiY+224))
            this.deficitsScrollbar.scrollHandler(delta);

        return super.mouseScrolled(mouseX, mouseY, delta);
    }

    private float getSlideOnline(List<?> playerList, ScrollBar scollerbar) {
        return (playerList.size() > 4)
                ? (-((playerList.size() - 4) * 17) * scollerbar.getSliderValue())
                : 0.0F;
    }

    private void renderPlayerList(MatrixStack matrixStack, int x, int y, int width, int height, int color, int mouseX, int mouseY, List<? extends BankLog> logs, ScrollBar scrollbar) {
        startGlScissor(x, y, width, height);
        String toolTip = "";
        for (int i = 0; i < logs.size(); i++) {
            BankLog log = logs.get(i);
            int offsetX = x;
            Float offsetY = Float.valueOf((y + i * 18) + getSlideOnline(logs, scrollbar));
            this.minecraft.getTextureManager().bindTexture(fr.krishenk.castel.client.gui.widget.ScrollBar.WIDGET_LOCATION);
            blit(matrixStack, offsetX, offsetY.intValue(), 0, 206, 76, 18, 256, 256);
            ResourceLocation resourceLocation = AbstractClientPlayerEntity.getLocationSkin("_KrishenK_");
            AbstractClientPlayerEntity.getDownloadImageSkin(resourceLocation, "_KrishenK_");
            this.minecraft.getTextureManager().bindTexture(resourceLocation);
            blit(matrixStack, offsetX + 6, offsetY.intValue() + 4, 8, 8, 8, 8, 64, 64);
            this.minecraft.fontRenderer.drawString(matrixStack, String.valueOf(log.getAmount()), offsetX + 20, offsetY.intValue() + 4, color);
            if (mouseX >= offsetX + 6 && mouseX <= offsetX + 14 && mouseY >= offsetY + 4F && mouseY <= offsetY + 12F )
                toolTip = log.getPlayer();
        }
        endGlScissor();
        if(!toolTip.isEmpty())
            this.renderTooltip(matrixStack, new StringTextComponent(toolTip), mouseX, mouseY);
    }


    @Override
    public void mouseMoved(double mouseX, double mouseY) {
        super.mouseMoved(mouseX, mouseY);
        isChestOpen = (mouseX >= this.guiX + 77 && mouseX <= this.guiX + 129 && mouseY >= this.guiY + 40 && mouseY <= this.guiY + 79);
    }
}
