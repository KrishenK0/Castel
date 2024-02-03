package fr.krishenk.castel.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import fr.krishenk.castel.client.gui.faction.FactionTab;
import fr.krishenk.castel.common.constants.group.Guild;
import net.minecraft.client.MainWindow;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nonnull;

public abstract class GuiCastel extends Screen {
    protected ResourceLocation GUI_TEXTURE;
    protected int xSize, ySize, guiLeft, guiTop;

    protected int guiX = 0, guiY = 0;

    protected GuiTab tab;

    private final AbastractGuiTab abastractGuiTab;
    private final Guild guild;

    protected GuiCastel(ITextComponent titleIn, @Nonnull ResourceLocation resourceLocation, @Nonnull int xSize, @Nonnull int ySize, @Nonnull int guiLeft, @Nonnull int guiTop, AbastractGuiTab tab) {
        super(titleIn);
        this.GUI_TEXTURE = resourceLocation;
        this.xSize = xSize;
        this.ySize = ySize;
        this.guiLeft = guiLeft;
        this.guiTop = guiTop;
        this.abastractGuiTab = tab;
        this.guild = Guild.getInstance();
    }

    protected GuiCastel(ITextComponent titleIn, @Nonnull ResourceLocation resourceLocation, @Nonnull int xSize, @Nonnull int ySize, @Nonnull int guiLeft, @Nonnull int guiTop) {
        super(titleIn);
        this.GUI_TEXTURE = resourceLocation;
        this.xSize = xSize;
        this.ySize = ySize;
        this.guiLeft = guiLeft;
        this.guiTop = guiTop;
        this.abastractGuiTab = new FactionTab();
        this.guild = Guild.getInstance();
    }


    @Override
    protected void init() {
        this.guiX = (this.width - this.guiLeft) / 2;
        this.guiY = (this.height - this.guiTop) / 2;
        tab = new GuiTab(this, this.abastractGuiTab, this.guiX, this.guiY, this.guiLeft, this.guiTop);
        tab.addButtons();
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        drawBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        tab.drawTabs(matrixStack);
        this.drawScaledCenteredString(matrixStack, guild.getName(), this.guiX+guiLeft/2, this.guiY+6, 1.5F, TextFormatting.WHITE.getColor());
    }

    public void drawVLine(MatrixStack matrixStack, int x, int minY, int maxY, int width, int color) {
        fill(matrixStack, x, minY, x+width, maxY, color);
    }

    public void drawScaledString(MatrixStack matrixStack, String text, int x, int y, float scale, int color) {
        GL11.glPushMatrix();
        GL11.glScaled(scale, scale, scale);
        this.minecraft.fontRenderer.drawString(matrixStack, text, x/scale, y/scale, color);
        GL11.glPopMatrix();
    }

    public void drawScaledCenteredString(MatrixStack matrixStack, String text, int x, int y, float scale, int color) {
        GL11.glPushMatrix();
        GL11.glScaled(scale, scale, scale);
        this.minecraft.fontRenderer.drawStringWithTransparency(matrixStack, text, (int)(x - this.minecraft.fontRenderer.getStringWidth(text) * scale / 2)/scale, y/scale, color, true);
        GL11.glPopMatrix();
        //GL11.glColor4f(1F, 1F, 1F, 1F);
    }

    protected void drawBackground(MatrixStack matrixStack) {
        this.minecraft.getTextureManager().bindTexture(this.GUI_TEXTURE);
        blit(matrixStack, guiX, guiY, guiLeft, guiTop, 0F, 0F, guiLeft, guiTop, xSize, ySize);
    }

    protected void drawBackground(MatrixStack matrixStack, int x, int y, int width, int height, int textureWidth, int textureHeight, ResourceLocation texture) {
        this.minecraft.getTextureManager().bindTexture(texture);
        blit(matrixStack, x, y, width, height, 0F, 0F, width, height, textureWidth, textureHeight);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    public <T extends Widget> void addNewButton(Widget button) {
        this.addButton(button);
    }

    protected void startGlScissor(int x, int y, int width, int height) {
        MainWindow reso = this.minecraft.getMainWindow();
        double scaleW = (double) reso.getWidth() / reso.getScaledWidth();
        double scaleH = (double) reso.getHeight() / reso.getScaledHeight();

        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        GL11.glScissor((int) (x * scaleW),
                (int) (reso.getHeight() - (y+height)*scaleH),
                (int) ((x + width) * scaleW - x*scaleW),
                (int) (Math.floor((double) reso.getHeight() - ((double) y * scaleH))
                        - (int) Math.floor((double) reso.getHeight() - ((double) (y + height) * scaleH))));
    }

    protected void endGlScissor() {
        GL11.glDisable(GL11.GL_SCISSOR_TEST);
    }

    public Guild getGuild() {
        return guild;
    }
}
