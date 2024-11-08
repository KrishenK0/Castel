package fr.krishenk.castel.client.gui;

import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.krishenk.castel.Castel;
import fr.krishenk.castel.client.gui.override.inventory.GUICustomInventory;
import fr.krishenk.castel.server.inventory.ContainerCustom;
import fr.krishenk.castel.server.packet.ExtendedPlayer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if(ID == Castel.CASTEL_MENU_GUI_ID) {
			return new ContainerCustom(player, player.inventory, ExtendedPlayer.get(player).inventory);
		} else {
			return null;
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if(world.isRemote) {
			if(ID == Castel.CASTEL_MENU_GUI_ID) {
				return new GUICustomInventory(player, player.inventory, ExtendedPlayer.get(player).inventory);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}
	
	
}
