package fr.krishenk.castel.client.gui;

import net.minecraft.util.ResourceLocation;

import java.util.List;

public abstract class AbastractGuiTab {
    protected ResourceLocation ICONS_TAB_LOCATION;

    protected ResourceLocation getIconsTabLocation() { return this.ICONS_TAB_LOCATION; }

    protected abstract void initTabs();

    public abstract List<ITab> getTabs();

}
