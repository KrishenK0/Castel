package fr.krishenk.castel.client.gui.faction;

import com.mojang.blaze3d.matrix.MatrixStack;
import fr.krishenk.castel.Castel;
import fr.krishenk.castel.client.gui.GuiCastel;
import fr.krishenk.castel.client.gui.widget.ScrollBar;
import fr.krishenk.castel.client.gui.widget.SimpleButton;
import fr.krishenk.castel.common.utils.SimpleChunkLocation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

public class GuiFactionClaims extends GuiCastel {

    protected static ITextComponent title = new TranslationTextComponent("gui.faction.title");
    private ScrollBar claimsScrollBar;
    private TextFieldWidget filter;
    private boolean isSquareMod;

    private static GuiFactionClaims INSTANCE;
    private MARKER markersType;

    public GuiFactionClaims(String markersType) {
        super(title, new ResourceLocation(Castel.MODID, "textures/gui/faction-claims.png"), 256, 256, 240,233, new FactionTab());
        INSTANCE = this;
        this.markersType = MARKER.valueOf(markersType.toUpperCase());
    }

    @Override
    protected void init() {
        super.init();
        this.claimsScrollBar = new ScrollBar(this, this.guiX + 218,this.guiY + 50,8,160);
        this.filter = new TextFieldWidget(this.font, this.guiX+38, this.guiY+47, 34, 15, new StringTextComponent("test"));
        this.filter.setText("1");
        this.filter.setVisible(true);
        this.filter.setEnabled(true);
        this.children.add(this.filter);
        this.addListener(this.filter);
        this.addButton(new SimpleButton(this.guiX+18, this.guiY+65, 35, 10, new StringTextComponent("claim"), (action) -> this.minecraft.player.sendChatMessage("/c claim")));
        this.addButton(new SimpleButton(this.guiX+18, this.guiY+80, 74, 10, new StringTextComponent("auto claim"), (action) -> this.minecraft.player.sendChatMessage("/c claim auto")));
        this.addButton(new SimpleButton(this.guiX+18, this.guiY+132, 74, 10, new StringTextComponent("unclaim"), (action) -> this.minecraft.player.sendChatMessage("/c unclaim")));
        this.addButton(new SimpleButton(this.guiX+18, this.guiY+148, 74, 10, new StringTextComponent("auto unclaim"), (action) -> this.minecraft.player.sendChatMessage("/c unclaim auto")));
        this.addButton(new SimpleButton(this.guiX+35, this.guiY+191, 57, 10, new StringTextComponent("all"), (action) -> this.minecraft.player.sendChatMessage("/c visualize all")));
        this.addButton(new SimpleButton(this.guiX+18, this.guiY+207, 74, 10, new StringTextComponent("toggle"), (action) -> this.minecraft.player.sendChatMessage("/c visualize toggle")));
    }

    @Override
    public void onClose() {
        INSTANCE = null;
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
        if (mouseX >= this.guiX+18 && mouseX <= this.guiX+31 && mouseY >= this.guiY+47 && mouseY <= this.guiY+61) {
            Minecraft.getInstance().getSoundHandler().play(SimpleSound.master(
                    !getGuild().isRequiresInvite() ? SoundEvents.BLOCK_WOODEN_PRESSURE_PLATE_CLICK_OFF : SoundEvents.BLOCK_WOODEN_PRESSURE_PLATE_CLICK_ON,
                    !getGuild().isRequiresInvite() ? 0.5F : 0.8F, 0.3F));
            this.isSquareMod = !this.isSquareMod;
        }

        if (mouseX >= this.guiX+76 && mouseX <= this.guiX+89 && mouseY >= this.guiY+47 && mouseY <= this.guiY+61) {
            Minecraft.getInstance().getSoundHandler().play(SimpleSound.master(
                    !getGuild().isRequiresInvite() ? SoundEvents.BLOCK_WOODEN_PRESSURE_PLATE_CLICK_OFF : SoundEvents.BLOCK_WOODEN_PRESSURE_PLATE_CLICK_ON,
                    !getGuild().isRequiresInvite() ? 0.5F : 0.8F, 0.3F));
            if (this.isSquareMod) {
                Vector3d look = this.minecraft.player.getLookVec();
                Direction direction = Direction.getFacingFromVector(look.x, look.y, look.z);
                if (!direction.equals(Direction.UP) && !direction.equals(Direction.DOWN))
                    this.minecraft.player.sendChatMessage("/c claim line " + this.filter.getText() + ' ' + direction.getString());
            } else
                this.minecraft.player.sendChatMessage("/c claim square " + this.filter.getText());
        }

        if (mouseX >= this.guiX+18 && mouseX <= this.guiX+31 && mouseY >= this.guiY+190 && mouseY <= this.guiY+216) {
            Minecraft.getInstance().getSoundHandler().play(SimpleSound.master(
                    !getGuild().isRequiresInvite() ? SoundEvents.BLOCK_WOODEN_PRESSURE_PLATE_CLICK_OFF : SoundEvents.BLOCK_WOODEN_PRESSURE_PLATE_CLICK_ON,
                    !getGuild().isRequiresInvite() ? 0.5F : 0.8F, 0.3F));
            this.markersType = MARKER.values()[(this.markersType.ordinal()+1)%MARKER.values().length];
            this.minecraft.player.sendChatMessage("/c visualize markers " + this.markersType.name().toLowerCase());
        }

        if (this.claimsScrollBar.isMouseHover(mouseX, mouseY) && button == 0)
            this.claimsScrollBar.setScrollValuePosY(mouseY);

        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        super.render(matrixStack, mouseX, mouseY, partialTicks);

        // Filter
        this.filter.render(matrixStack, mouseX, mouseY, partialTicks);

        //Titles
        drawCenteredString(matrixStack, "Claim", this.guiX+55, this.guiY+31, 0x505050);
        drawCenteredString(matrixStack, "Unclaim", this.guiX+55, this.guiY+118, 0x505050);
        drawCenteredString(matrixStack, "Visualize", this.guiX+55, this.guiY+174, 0x505050);
        drawCenteredString(matrixStack, "Liste claims", this.guiX+172, this.guiY+31, 0x505050);

        // Claim
        this.minecraft.getTextureManager().bindTexture(this.GUI_TEXTURE);
        blit(matrixStack, this.guiX+79, this.guiY+47, 33, 234, 14, 15);
        blit(matrixStack, this.guiX+18, this.guiY+47, !isSquareMod ? 1 : 17, 234, 14, 15);
        if (mouseX >= this.guiX+18 && mouseX <= this.guiX+31 && mouseY >= this.guiY+47 && mouseY <= this.guiY+61)
            this.renderTooltip(matrixStack, new StringTextComponent(!isSquareMod ? "Square" : "Line"), mouseX, mouseY);
        else if (mouseX >= this.guiX+79 && mouseX <= this.guiX+92 && mouseY >= this.guiY+47 && mouseY <= this.guiY+61)
            this.renderTooltip(matrixStack, new StringTextComponent("Valider"), mouseX, mouseY);

        // Visualize
        renderVisualize(matrixStack, mouseX, mouseY);

        // Playerlist
        this.claimsScrollBar.render(getGuild().getLands(), matrixStack);
        if (getGuild().getLands() != null && !getGuild().getLands().isEmpty())
            renderPlayerList(matrixStack, this.guiX + 117, this.guiY + 47, 95, 167, TextFormatting.WHITE.getColor(), mouseX, mouseY, claimsScrollBar);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        if (getGuild().getLands().size() > 4 && mouseX >= (this.guiX + 15) && mouseX <= (this.guiX + 224) && mouseY >= (this.guiY + 84) && mouseY <= (this.guiY + 216))
            this.claimsScrollBar.scrollHandler(delta);

        return super.mouseScrolled(mouseX, mouseY, delta);
    }
    private void renderPlayerList(MatrixStack matrixStack, int x, int y, int width, int height, int color, int mouseX, int mouseY, ScrollBar scrollbar) {
        startGlScissor(x, y, width, height);
        int i = 0;
        for (SimpleChunkLocation location : getGuild().getLands()) {
            float offsetY = (y + i * 18) + scrollbar.getSlideOffset(getGuild().getLands());
            this.minecraft.getTextureManager().bindTexture(this.GUI_TEXTURE);
            blit(matrixStack, x +5, (int) offsetY, 97, 233, 100, 18);
            this.minecraft.fontRenderer.drawString(matrixStack, location.getWorld() + "  " + location.getX() + " | " + location.getZ(), x + 10, (int) offsetY + 4, color);
            i++;
        }
        endGlScissor();
    }

    private void renderVisualize(MatrixStack matrixStack, int mouseX, int mouseY) {
        this.minecraft.getTextureManager().bindTexture(this.GUI_TEXTURE);
        blit(matrixStack, this.guiX+18, this.guiY+190, 49+16*this.markersType.ordinal(), 234, 14, 15);
        if (mouseX >= this.guiX+18 && mouseX <= this.guiX+31 && mouseY >= this.guiY+190 && mouseY <= this.guiY+216)
            this.renderTooltip(matrixStack, new StringTextComponent("Mode " + this.markersType.name().toLowerCase()), mouseX, mouseY);
    }

    public static GuiFactionClaims getINSTANCE() {
        return INSTANCE;
    }

    enum MARKER {
        BLOCKS,
        VERTICAL,
        HORIZONTAL;
    }
}
