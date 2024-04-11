package fr.krishenk.castel.client.gui.faction;

import com.mojang.blaze3d.matrix.MatrixStack;
import fr.krishenk.castel.common.container.FactionChestContainers;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

public class FactionChest extends ContainerScreen<FactionChestContainers> {
    public FactionChest(FactionChestContainers screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {
        this.minecraft.fontRenderer.drawString(matrixStack, "CONTAINER", 0, 0, 0xFFFF0000);
    }
}
