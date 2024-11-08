package fr.krishenk.castel.server;

import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.krishenk.castel.client.utils.IProxy;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;

public class ServerProxy implements IProxy {
	public void onPreInit() {
	}

	public void load() {
	}

	public void registerItem(Item item) {
	}

	public EntityPlayer getPlayerEntity(MessageContext ctx) {
		return (ctx.getServerHandler().playerEntity);
	}

	public void spawnParticle(EntityPlayer player, String string, Object... ob) {
	}

	public void spawnParticle(String particle, double x, double y, double z, double motionX, double motionY,
			double motionZ, float scale) {
	}

	@SideOnly(Side.SERVER)
	public void registerRender() {
		System.out.println("[SERVER]");
	}

	@Override
	public String localize(String unlocalized) {
		return I18n.format(unlocalized);
	}

	@Override
	public String localize(String unlocalized, Object... args) {
		return I18n.format(unlocalized, args);
	}

	public Side getPhysicalSide() {
		return Side.SERVER;
	}

}
