package fr.krishenk.castel.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import fr.krishenk.castel.Castel;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.util.ResourceLocation;

public class GuiTab {

    private final int guiX;
    private final int guiY;
    private final int xSize;
    private final int ySize;

    private final ResourceLocation WIDGET_LOCATION = new ResourceLocation(Castel.MODID, "textures/gui/widget.png");

    private final GuiCastel gui;
    private final AbastractGuiTab tabs;

    public GuiTab(GuiCastel gui, AbastractGuiTab tabs, int guiX, int guiY, int xSize, int ySize) {
        this.gui = gui;
        this.tabs = tabs;
        this.guiX = guiX;
        this.guiY = guiY;
        this.xSize = xSize;
        this.ySize = ySize;
    }

    public void addButtons() {
        int di = 1;
        for (int i = 0; i < tabs.getTabs().size(); i++) {
            //di = (tabs.getTabs().get(i).getClassReferent().equals(this.gui.getClass())) ? 26 : 0;
            ITab tabButton =  tabs.getTabs().get(i);
            if (i % 2 == 0)
                this.gui.addNewButton(new GuiTabButton(this.WIDGET_LOCATION, guiX-23, guiY+35*di, 26, 32, (i % 2), tabButton, this.gui));
            else {
                this.gui.addNewButton(new GuiTabButton(this.WIDGET_LOCATION, guiX+xSize-3, guiY+35*di, 26, 32, (i % 2), tabButton, this.gui));
                di++;
            }
        }
    }

    public void drawTabs(MatrixStack matrixStack) {
        int di = 1;
        this.gui.getMinecraft().getTextureManager().bindTexture(this.tabs.getIconsTabLocation());
        for (int i = 0; i < tabs.getTabs().size(); i++) {

            if (i % 2 == 0)
                AbstractGui.blit(matrixStack, guiX-16, guiY+8+35*di, i*16, i * 16, 16, 16, 102, 16);
            //this.gui.blit(matrixStack, guiX - 20, guiY + 26 + 50 * i, 16 * i, 265, 16, 16, 16, 16, 102, 16);
            else {
                AbstractGui.blit(matrixStack, guiX+xSize, guiY+8+35*di, i*16, i * 16, 16, 16, 102, 16);
                di++;
            }
        }
    }
}
