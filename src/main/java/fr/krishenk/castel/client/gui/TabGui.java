package fr.krishenk.castel.client.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;

@SideOnly(Side.CLIENT)
public interface TabGui {
	Class<? extends GuiScreen> getClassReferent();
	
	void call();
	
}
