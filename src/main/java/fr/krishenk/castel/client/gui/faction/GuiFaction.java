package fr.krishenk.castel.client.gui.faction;

import com.mojang.blaze3d.matrix.MatrixStack;
import fr.krishenk.castel.Castel;
import fr.krishenk.castel.FactionInfo;
import fr.krishenk.castel.client.gui.GuiCastel;
import fr.krishenk.castel.client.gui.widget.ScrollBar;
import fr.krishenk.castel.common.fperms.Rank;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.List;
import java.util.Map;

public class GuiFaction extends GuiCastel {

    protected static ITextComponent title = new TranslationTextComponent("gui.faction.title");

    private final List<String> playerOnlineList;
    private final List<String> playerOfflineList;

    private ScrollBar onlineScrollBar;
    private ScrollBar offlineScrollBar;

    public GuiFaction() {
        super(title, new ResourceLocation(Castel.MODID, "textures/gui/faction-main.png"), 256, 256, 240, 233);
    }

    @Override
    protected void init() {
        super.init();
        this.onlineScrollBar = new ScrollBar(this, this.guiX + 108,this.guiY + 141,8,74 );
        this.offlineScrollBar = new ScrollBar(this, this.guiX + 204,this.guiY + 141,8,74 );
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        super.render(matrixStack, mouseX, mouseY, partialTicks);

        // DRAW FLAG
        byte[] imageData = DatatypeConverter.parseBase64Binary(this.getGuild().getFlag());
        NativeImage image;
        try {
            image = NativeImage.read(new ByteArrayInputStream(imageData));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        DynamicTexture texture = new DynamicTexture(image);
        Minecraft.getInstance().getTextureManager().loadTexture(new ResourceLocation(Castel.MODID, "custom_image"), texture);
        blit(matrixStack, this.guiX + 65, this.guiY + 32, 0, 0, 110, 62, 110, 62);

        // Draw members list
        renderPlayerList(matrixStack, this.guiX + 27, this.guiY + 135, 81, 86, TextFormatting.WHITE.getColor(), mouseX, mouseY, getGuild().getMembersOnline(), onlineScrollBar);
        renderPlayerList(matrixStack, this.guiX + 123, this.guiY + 135, 81, 86, TextFormatting.GRAY.getColor(), mouseX, mouseY, getGuild().getMembersOffline(), offlineScrollBar);
        this.onlineScrollBar.drawScroller(matrixStack);
        this.offlineScrollBar.drawScroller(matrixStack);
        super.drawScaledString(matrixStack, "Leader: " + super.getGuild().getLeaderName(), this.guiX + 25, this.guiY + 109, .75F, 0xFFFFFFFF);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        if (getGuild().getMembersOnline().size() > 4 &&
                mouseX >= (this.guiX + 27) && mouseX <= (this.guiX + 116) && mouseY >= (this.guiY + 134) && mouseY <= (this.guiY + 220))
            this.onlineScrollBar.scrollHandler(delta);

        if (getGuild().getMembersOffline().size() > 4 &&
                mouseX >= (this.guiX + 123) && mouseX <= (this.guiX + 212) && mouseY >= (this.guiY + 134) && mouseY <= (this.guiY + 220))
            this.offlineScrollBar.scrollHandler(delta);

        return super.mouseScrolled(mouseX, mouseY, delta);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button)
    {
        if (this.onlineScrollBar.isMouseHover(mouseX, mouseY) && button == 0)
            this.onlineScrollBar.setScrollValuePosY(mouseY);

        if (this.offlineScrollBar.isMouseHover(mouseX, mouseY) && button == 0)
            this.offlineScrollBar.setScrollValuePosY(mouseY);

        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        if (this.onlineScrollBar.isMouseHover(mouseX, mouseY) && button == 0)
            this.onlineScrollBar.setScrollValuePosY(mouseY);

        if (this.offlineScrollBar.isMouseHover(mouseX, mouseY) && button == 0)
            this.offlineScrollBar.setScrollValuePosY(mouseY);

        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }

    private float getSlideOnline(List<?> playerList, ScrollBar scollerbar) {
        return (playerList.size() > 4)
                ? (-((playerList.size() - 4) * 15) * scollerbar.getSliderValue())
                : 0.0F;
    }

    private void renderPlayerList(MatrixStack matrixStack, int x, int y, int width, int height, int color, int mouseX, int mouseY, List<? extends String> playerList, ScrollBar scrollbar) {
        startGlScissor(x, y, width, height);
        String toolTip = "";
        for (int i = 0; i < playerList.size(); i++) {
            String playerName = playerList.get(i);
            int offsetX = x;
            Float offsetY = Float.valueOf((y + i * 18) + getSlideOnline(playerList, scrollbar));
            this.minecraft.getTextureManager().bindTexture(fr.krishenk.castel.client.gui.widget.ScrollBar.WIDGET_LOCATION);
            blit(matrixStack, offsetX, offsetY.intValue(), 0, 206, 76, 18, 256, 256);
            ResourceLocation resourceLocation = AbstractClientPlayerEntity.getLocationSkin("_KrishenK_");
            AbstractClientPlayerEntity.getDownloadImageSkin(resourceLocation, "_KrishenK_");
            this.minecraft.getTextureManager().bindTexture(resourceLocation);
            blit(matrixStack, offsetX + 6, offsetY.intValue() + 4, 8, 8, 8, 8, 64, 64);
            this.minecraft.fontRenderer.drawString(matrixStack, playerName, offsetX + 20, offsetY.intValue() + 4, color);
            if (mouseX >= offsetX + 6 && mouseX <= offsetX + 14 && mouseY >= offsetY + 4F && mouseY <= offsetY + 12F )
                toolTip = playerName;
        }
        endGlScissor();
        if(!toolTip.isEmpty())
            this.renderTooltip(matrixStack, new StringTextComponent(toolTip), mouseX, mouseY);
    }
}
