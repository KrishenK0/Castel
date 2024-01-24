package fr.krishenk.castel.client.gui.faction;

import fr.krishenk.castel.Castel;
import fr.krishenk.castel.FactionInfo;
import fr.krishenk.castel.client.gui.AbastractGuiTab;
import fr.krishenk.castel.client.gui.ITab;
import fr.krishenk.castel.server.network.PacketHandler;
import fr.krishenk.castel.server.network.packet.FactionGuiCSPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

import java.util.ArrayList;
import java.util.List;

public class FactionTab extends AbastractGuiTab {

    public enum Tab {
        main,
        flag,
        perm,
        bank
    }

    protected ResourceLocation ICONS_TAB_LOCATION = new ResourceLocation(Castel.MODID, "textures/gui/faction-tab.png");
    private final List<ITab> TABS = new ArrayList<ITab>();

    public FactionTab() {
        initTabs();
    }

    @Override
    protected ResourceLocation getIconsTabLocation() {
        return ICONS_TAB_LOCATION;
    }

    public void initTabs() {
        TABS.add(new ITab() {
            @Override
            public Class<? extends Screen> getClassReferent() {
                return GuiFaction.class;
            }
            @Override
            public ITextComponent getTitle() { return GuiFaction.title; }
            @Override
            public Button.IPressable getPressedAction() {
                return button -> PacketHandler.CHANNEL.sendToServer(new FactionGuiCSPacket(Tab.main));
            }
        });

        TABS.add(new ITab() {
            @Override
            public Class<? extends Screen> getClassReferent() {
                return GuiFactionBank.class;
            }
            @Override
            public ITextComponent getTitle() { return GuiFactionBank.title; }
            @Override
            public Button.IPressable getPressedAction() {
                return button -> PacketHandler.CHANNEL.sendToServer(new FactionGuiCSPacket(Tab.bank));
            }
        });

        TABS.add(new ITab() {
            @Override
            public Class<? extends Screen> getClassReferent() {
                return GuiFactionFlag.class;
            }
            @Override
            public ITextComponent getTitle() { return GuiFactionFlag.title; }
            @Override
            public Button.IPressable getPressedAction() {
                return button -> PacketHandler.CHANNEL.sendToServer(new FactionGuiCSPacket(Tab.flag));
            }
        });

        if (FactionInfo.getInstance().getLeaderId().equals(Minecraft.getInstance().player.getUniqueID().toString()))
            TABS.add(new ITab() {
                @Override
                public Class<? extends Screen> getClassReferent() {
                    return GuiFationPerm.class;
                }
                @Override
                public ITextComponent getTitle() { return GuiFationPerm.title; }
                @Override
                public Button.IPressable getPressedAction() {
                    return button -> PacketHandler.CHANNEL.sendToServer(new FactionGuiCSPacket(Tab.perm));
                }
            });
    }

    @Override
    public List<ITab> getTabs() {
        return TABS;
    }

}