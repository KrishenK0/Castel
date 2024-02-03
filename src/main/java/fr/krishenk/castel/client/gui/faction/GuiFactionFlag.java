package fr.krishenk.castel.client.gui.faction;

import com.mojang.blaze3d.matrix.MatrixStack;
import fr.krishenk.castel.Castel;
import fr.krishenk.castel.client.gui.GuiCastel;
import fr.krishenk.castel.server.network.PacketHandler;
import fr.krishenk.castel.server.network.packet.FactionFlCSPacket;
import joptsimple.internal.Strings;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;

public class GuiFactionFlag extends GuiCastel {

    protected static ITextComponent title = new TranslationTextComponent("gui.faction.flag.title");

    private final ArrayList<Integer> colorList = new ArrayList<>();
    private final HashMap<Integer, Integer> pixels = new HashMap<>();
    private final boolean mouseDrawing = true;
    private int colorSelected = -1;
    private int colorHovered = 0;


    public GuiFactionFlag() {
        super(title, new ResourceLocation(Castel.MODID, "textures/gui/faction-flag.png"), 256, 256, 240, 233
        );
        initPixels();
        addColors();
    }

    // TODO : handle coloring + add button


    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        super.render(matrixStack, mouseX, mouseY, partialTicks);

        int x = 0, y = 0;
        int i;
        for (i = 0; i < this.colorList.size(); i++) {
            int colorId = this.colorList.get(i).intValue();
            if (mouseX > this.guiX + 89 + x * 16 && mouseX < this.guiX + 218 + (x+1) * 16 &&
                mouseY > this.guiY + 156 + y * 16 && mouseY < this.guiY + 224 + (y+1) * 16)
                this.colorHovered = colorId;
            if (colorId == this.colorSelected) {
                this.minecraft.getTextureManager().bindTexture(this.GUI_TEXTURE);
                this.blit(matrixStack, this.guiX + 89 + x * 16, this.guiY + 159 + y * 16, 0, 233, 18, 18);
            }
            x = (x < 7) ? (x+1) : 0;
            y = (x == 0) ? (y+1) : y;
        }

        if (this.pixels.size() > 0) {
            Iterator<Map.Entry<Integer, Integer>> it = this.pixels.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = it.next();
                int colorId = ((Integer) pair.getValue());
                this.fillGradient(matrixStack, this.guiX + 24 + x * 6, this.guiY + 25 + y * 6, this.guiX + 24 + x * 6 + 6,
                        this.guiY + 25 + y * 6 + 6, colorId, colorId);

                x = (x < 25) ? (x+1) : 0;
                y = (x == 0) ? (y + 1) : y;
                i++;
            }
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0) {
            // Choose color
            if (mouseX >= this.guiX + 89 && mouseX <= this.guiX + 218 && mouseY >= this.guiY + 159
                    && mouseY <= this.guiY + 224) {
                this.colorSelected = this.colorHovered;
                this.colorHovered = 0;
                this.minecraft.getSoundHandler().play(SimpleSound.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
            }
            // Clear canva
            if (mouseX >= this.guiX + 75 && mouseX <= this.guiX + 88 && mouseY >= this.guiY + 207
                    && mouseY <= this.guiY + 222) {
                for (int i = 0; i < 338; i++) {
                    this.pixels.put(Integer.valueOf(i), -1);
                }
                this.minecraft.getSoundHandler().play(SimpleSound.master(SoundEvents.ITEM_BOOK_PAGE_TURN, 1.0F));
            }
            // Draw
            if (mouseX > (guiX + 25) && mouseX < (guiX + 176) && mouseY > (guiY + 50) && mouseY < (guiY + 126))
                drawColor(mouseX, mouseY);
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        if (button == 0) {
            if (mouseX > (guiX + 25) && mouseX < (guiX + 176) && mouseY > (guiY + 50) && mouseY < (guiY + 126))
                drawColor(mouseX, mouseY);
        }
        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }

/*
    @Override
    public void actionPerformed(GuiButton button) {
        int i;
        for (i = 0; i < TABS.size(); i++) {
            if (button.id == i) {
                try {
                    TABS.get(i).call();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        if (button.id == (i + 1)) {

            ArrayList<Integer> imagePixels = new ArrayList<>();
            BufferedImage image = new BufferedImage(152, 80, 2);
            Graphics2D graphics2D = image.createGraphics();
            int x = 0;
            int y = 0;
            Iterator<Map.Entry<Integer, Integer>> it = this.pixels.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = it.next();
                imagePixels.add(Integer.valueOf((Integer) pair.getValue()).intValue());
                graphics2D.setPaint(new Color(((Integer) pair.getValue()).intValue()));
                graphics2D.fillRect(x * 6, y * 6, 6, 6);
                x = (x < 25) ? (x + 1) : 0;
                y = (x == 0) ? (y + 1) : y;
            }
            graphics2D.dispose();
            System.out.println(encodeToString(image, "png"));

        }

        super.actionPerformed(button);
    }*/

    private void initPixels() {
        for (int i = 0; i < 338; i++) {
            this.pixels.put(Integer.valueOf(i), Integer.valueOf(-1));
        }
    }

    private void addColors() {
        this.colorList.addAll(
                Arrays.asList(
                        hexToRGB(0x0000ff), hexToRGB(0xff0000),
                        hexToRGB(0x0cff00), hexToRGB(0xff00f6),
                        hexToRGB(0xfcff00), hexToRGB(0xff6c00),
                        hexToRGB(0x9800df), hexToRGB(0xffffff),
                        hexToRGB(0x000098), hexToRGB(0x960d0d),
                        hexToRGB(0x08a800), hexToRGB(0xa900a3),
                        hexToRGB(0xc9a100), hexToRGB(0xb64d00),
                        hexToRGB(0x7600ad), hexToRGB(0x939393),
                        hexToRGB(0x000063), hexToRGB(0x701010),
                        hexToRGB(0x057100), hexToRGB(0x7b0077),
                        hexToRGB(0x9f7000), hexToRGB(0x8b3b00),
                        hexToRGB(0x520079), hexToRGB(0x464646),
                        hexToRGB(0x030330), hexToRGB(0x370d0d),
                        hexToRGB(0x034a00), hexToRGB(0x460044),
                        hexToRGB(0x674900), hexToRGB(0x622900),
                        hexToRGB(0x34004d), hexToRGB(0x000000)
                )
        );
    }

    private void drawColor(double mouseX, double mouseY) {
        int x = 0;
        int y = 0;
        int pI = 0;
        int pixelHoveredId = -1;
        if (this.pixels.size() > 0) {
            Iterator<Map.Entry<Integer, Integer>> it = this.pixels.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = it.next();
                int colorId = ((Integer) pair.getValue()).intValue();
                if (mouseX >= this.guiX + 25 + x * 6 && mouseX <= this.guiX + 25 + x * 6 + 6
                        && mouseY >= this.guiY + 48 + y * 6 && mouseY <= this.guiY + 48 + y * 6 + 6)
                    pixelHoveredId = pI;
                if (this.mouseDrawing && colorId != this.colorSelected && pixelHoveredId == pI) {
                    this.pixels.put(Integer.valueOf(pI), Integer.valueOf(this.colorSelected));
                }

                x = (x < 25) ? (x + 1) : 0;
                y = (x == 0) ? (y + 1) : y;
                pI++;
            }
        }
    }

    private Integer hexToRGB(int hex) {
        return new Color(hex).getRGB();
    }
}
