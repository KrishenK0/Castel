package fr.krishenk.castel.client.gui.faction;

import com.mojang.blaze3d.matrix.MatrixStack;
import fr.krishenk.castel.Castel;
import fr.krishenk.castel.FactionInfo;
import fr.krishenk.castel.client.gui.GuiCastel;
import fr.krishenk.castel.client.gui.widget.ScrollBar;
import fr.krishenk.castel.common.fperms.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import org.antlr.v4.runtime.misc.Pair;

import java.util.List;

public class GuiFationPerm extends GuiCastel {
    protected static ITextComponent title = new TranslationTextComponent("gui.faction.title");
    private ScrollBar permScrollBar;
    private String toolTip = "";

    private Pair<Rank, Boolean> permSelected = null;

    private static GuiFationPerm INSTANCE;

    public GuiFationPerm() {
        super(
                title,
                new ResourceLocation(Castel.MODID, "textures/gui/faction-perm.png"),
                302,
                256,
                280,
                167,
                new FactionTab()
        );
        INSTANCE = this;

        System.out.println(FactionInfo.getInstance().getTitle());
    }

    @Override
    protected void init() {
        super.init();
        this.permScrollBar = new ScrollBar(this, this.guiX + 255, this.guiY + 50, 9, 100);
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.toolTip = "";
        renderHeader(matrixStack, mouseX, mouseY);
        renderRows(matrixStack, mouseX, mouseY);
        this.permScrollBar.drawScroller(matrixStack);
        if (!this.toolTip.isEmpty())
            this.renderTooltip(matrixStack, new StringTextComponent(this.toolTip), mouseX, mouseY);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        if (mouseX >= (this.guiX + 18) && mouseX <= (this.guiX + 264) && mouseY >= (this.guiY + 50) && mouseY <= (this.guiY + 150))
            this.permScrollBar.scrollHandler(delta);

        return super.mouseScrolled(mouseX, mouseY, delta);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button)
    {
        if (this.permScrollBar.isMouseHover(mouseX, mouseY) && button == 0)
            this.permScrollBar.setScrollValuePosY(mouseY);

        if (this.permSelected != null && button == 0) {
            System.out.println(permSelected);
            Minecraft.getInstance().getSoundHandler().play(SimpleSound.master(
                    permSelected.b ? SoundEvents.BLOCK_WOODEN_PRESSURE_PLATE_CLICK_OFF : SoundEvents.BLOCK_WOODEN_PRESSURE_PLATE_CLICK_ON,
                    permSelected.b ? 0.5F : 0.8F, 0.3F));
//            PacketHandler.CHANNEL.sendToServer(new FactionChangePermCSPacket(permSelected.a, permSelected.a.relation, permSelected.b, permSelected.c));
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        if (this.permScrollBar.isMouseHover(mouseX, mouseY) && button == 0)
            this.permScrollBar.setScrollValuePosY(mouseY);

        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }

    private void renderHeader(MatrixStack matrixStack, int mouseX, int mouseY) {
        this.minecraft.getTextureManager().bindTexture(this.GUI_TEXTURE);
        int i = 0;
        for (Rank rank : getFactionInfo().getRanks()) {
            if (rank.getPriority() == 0) continue;
            drawScaledString(matrixStack, rank.getName().substring(0, 2), this.guiX + 56 + 25*i, this.guiY + 32, 1.5F, 0xFFFFFFFF);
//            blit(matrixStack, this.guiX + 56 + 25*i, this.guiY + 28, 1+18*i, 210, 16, 16, xSize, ySize);
            if (mouseX >= this.guiX + 56 + 25*i && mouseX <= this.guiX + 56+16 + 25*i && mouseY >= this.guiY + 28 && mouseY <= this.guiY + 28+16)
                setToolTip(rank.getName());
            i++;
        }
    }

    // TODO: CREATE COPY THE INSTANCE SYSTEM TO ALL OF FACTION GUI \
    //  USING THE PARENT

    private void renderRows(MatrixStack matrixStack, int mouseX, int mouseY) {
        this.minecraft.getTextureManager().bindTexture(this.GUI_TEXTURE);
        super.startGlScissor(this.guiX+19, this.guiY+50, 245, 100);
        int dv = 0;
        int j = 0;
        this.permSelected = null;
        for (Rank rank : getFactionInfo().getRanks()) {
            int i = 0;
            if (rank.getPriority() == 0) continue;
            List<Rank.Permission> perms = rank.getPermissions();
            for (PermissableAction permAction : PermissableAction.values()) {

                Boolean access = perms.stream().anyMatch(p ->  p.getNamespace().getKey().equals(permAction.getName().toUpperCase()));
//                if (access == null) continue;

                int offsetX = this.guiX+28 + j*25;
                Float offsetY = Float.valueOf((this.guiY+52 + i*20) + getSlideOnline());
                if (j == 0) {
                    if ((i+1) % 16 == 0) dv++;
                    blit(matrixStack, offsetX, offsetY.intValue(), 1+18*(i%15), 168+18*dv, 16, 16, xSize, ySize);
                    if (i != perms.size()-1)
                        this.hLine(matrixStack, offsetX, offsetX+225, offsetY.intValue()+18, 0xFF000000);
                    if (mouseY >= this.guiY+50 && mouseY <= this.guiY+150) {
                        if (mouseX >= offsetX && mouseX <= offsetX + 16 && mouseY >= offsetY && mouseY <= offsetY + 16)
                            setToolTip(permAction.getDescription());
                    }
                }
                blit(matrixStack, offsetX+28, offsetY.intValue(), (access ? 1 : 19) , 235, 16, 16, xSize, ySize);

                if (mouseY >= this.guiY+50 && mouseY <= this.guiY+150) {
                    if (mouseX >= offsetX+28 && mouseX <= offsetX+46 && mouseY >= offsetY && mouseY <= offsetY+18) {
                        setToolTip(access ? "ALLOW" : "DENY");
                        this.permSelected = new Pair<>(rank, access);
                    }
                }
                i++;
            }
            j++;
        }
        super.endGlScissor();
    }

    private float getSlideOnline() {
        return (PermissableAction.values().length > 4)
                ? (-((PermissableAction.values().length - 4) * 20) * permScrollBar.getSliderValue())
                : 0.0F;
    }

    private void setToolTip(String toolTip) {
        if (this.toolTip.isEmpty())
            this.toolTip = toolTip;
    }

    public static GuiFationPerm getInstance() { return INSTANCE; }

    @Override
    public void onClose() {
        INSTANCE = null;
        super.onClose();
    }
}
