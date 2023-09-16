package fr.krishenk.castel.client.gui;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.button.Button.IPressable;
import net.minecraft.util.text.ITextComponent;

public interface ITab {
    Class<? extends Screen> getClassReferent();
    ITextComponent getTitle();
    IPressable getPressedAction();
}
