package fr.krishenk.castel.client.gui.faction;

import com.mojang.blaze3d.matrix.MatrixStack;
import fr.krishenk.castel.Castel;
import fr.krishenk.castel.client.gui.GuiCastel;
import fr.krishenk.castel.client.gui.widget.ScrollBar;
import fr.krishenk.castel.server.network.PacketHandler;
import fr.krishenk.castel.server.network.packet.FactionRiCSPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GuiFactionInvite extends GuiCastel {

    protected static ITextComponent title = new TranslationTextComponent("gui.faction.title");
    private ScrollBar playersScrollBar;
    private TextFieldWidget filter;
    private List<String> playerList;
    private Map<String, String> invites;
    private boolean seeInvite = false;

    public GuiFactionInvite(List<String> playerList, Map<String, String> invites) {
        super(title, new ResourceLocation(Castel.MODID, "textures/gui/faction-invite.png"), 256, 256, 240,233, new FactionTab());
        this.playerList = playerList;
        this.invites = invites;

        System.out.println(invites);
        System.out.println(seeInvite);
    }

    @Override
    protected void init() {
        super.init();
        this.playersScrollBar = new ScrollBar(this, this.guiX + 218,this.guiY + 95,8,110);
        this.filter = new TextFieldWidget(this.font, this.guiX+40, this.guiY+46, 160, 20, new StringTextComponent("test"));
        this.filter.setVisible(true);
        this.filter.setEnabled(true);
        this.children.add(this.filter);
        this.setFocusedDefault(this.filter);
        this.addListener(this.filter);
    }

    @Override
    public void onClose() {
        super.onClose();
        this.minecraft.keyboardListener.enableRepeatEvents(false);
    }

    @Override
    public boolean keyPressed(int p_231046_1_, int p_231046_2_, int p_231046_3_) {
        return super.keyPressed(p_231046_1_, p_231046_2_, p_231046_3_);
    }

    @Override
    public void tick() {
        super.tick();
        this.filter.tick();
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0 && mouseX >= this.guiX+17 && mouseX <= this.guiX+34 && mouseY >= this.guiY+47 && mouseY <= this.guiY+65) {
            Minecraft.getInstance().getSoundHandler().play(SimpleSound.master(
                    !getGuild().isRequiresInvite() ? SoundEvents.BLOCK_WOODEN_PRESSURE_PLATE_CLICK_OFF : SoundEvents.BLOCK_WOODEN_PRESSURE_PLATE_CLICK_ON,
                    !getGuild().isRequiresInvite() ? 0.5F : 0.8F, 0.3F));
            PacketHandler.CHANNEL.sendToServer(new FactionRiCSPacket(!getGuild().isRequiresInvite()));
        }

        if (button == 0 && mouseX >= this.guiX+205 && mouseX <= this.guiX+224 && mouseY >= this.guiY+47 && mouseY <= this.guiY+65) {
            this.seeInvite = !this.seeInvite;
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        super.render(matrixStack, mouseX, mouseY, partialTicks);

        // Filter
        this.filter.render(matrixStack, mouseX, mouseY, partialTicks);

        // Lock
        this.minecraft.getTextureManager().bindTexture(this.GUI_TEXTURE);
        blit(matrixStack, this.guiX+17, this.guiY+47, !this.getGuild().isRequiresInvite() ? 1 : 19, 233, 16, 16);
        if (mouseX >= this.guiX+17 && mouseX <= this.guiX+34 && mouseY >= this.guiY+47 && mouseY <= this.guiY+65)
            this.renderTooltip(matrixStack, new StringTextComponent(!this.getGuild().isRequiresInvite() ? "Open" : "Require invite"), mouseX, mouseY);

        // Invite
        this.minecraft.getTextureManager().bindTexture(this.GUI_TEXTURE);
        blit(matrixStack, this.guiX+206, this.guiY+47, 36, 233, 16, 16);
        if (mouseX >= this.guiX+205 && mouseX <= this.guiX+224 && mouseY >= this.guiY+47 && mouseY <= this.guiY+65)
            this.renderTooltip(matrixStack, new StringTextComponent("See invites"), mouseX, mouseY);

        // Playerlist
        this.playersScrollBar.drawScroller(matrixStack);
        if (this.playerList != null && !this.playerList.isEmpty())
            renderPlayerList(matrixStack, this.guiX + 18, this.guiY + 90, 201, 123, TextFormatting.WHITE.getColor(), mouseX, mouseY, playersScrollBar);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        if (playerList.size() > 4 && mouseX >= (this.guiX + 15) && mouseX <= (this.guiX + 224) && mouseY >= (this.guiY + 84) && mouseY <= (this.guiY + 216))
            this.playersScrollBar.scrollHandler(delta);

        return super.mouseScrolled(mouseX, mouseY, delta);
    }

    private float getSlideOnline(List<?> playerList, ScrollBar scollerbar) {
        return (playerList.size() > 4)
                ? (-((playerList.size() - 4) * 15) * scollerbar.getSliderValue())
                : 0.0F;
    }

    private void renderPlayerList(MatrixStack matrixStack, int x, int y, int width, int height, int color, int mouseX, int mouseY, ScrollBar scrollbar) {
        startGlScissor(x, y, width, height);
        String toolTip = "";
        List<String> filtered = (seeInvite ? invites.keySet() : playerList).stream().filter(name -> name.toLowerCase().contains(this.filter.getText().toLowerCase())).collect(Collectors.toList());
        for (int i = 0; i < filtered.size(); i++) {
            String playerName = filtered.get(i);
            int offsetX = x;
            Float offsetY = Float.valueOf((y + i * 18) + getSlideOnline(filtered, scrollbar));
            this.minecraft.getTextureManager().bindTexture(this.GUI_TEXTURE);
            blit(matrixStack, offsetX+5, offsetY.intValue(), 54, 233, 183, 18);
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
