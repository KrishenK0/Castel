package fr.krishenk.castel.client;

import java.util.Random;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import fr.krishenk.castel.client.entity.EntityOrb;
import fr.krishenk.castel.client.fx.EntityCircleFX;
import fr.krishenk.castel.client.fx.EntityElementalStaffFX;
import fr.krishenk.castel.client.fx.EntityMagicBallFX;
import fr.krishenk.castel.client.fx.EntitySphereFX;
import fr.krishenk.castel.client.fx.EntitySpiralFX;
import fr.krishenk.castel.client.renderer.CustomItemRenderer;
import fr.krishenk.castel.client.renderer.RenderOrb;
import fr.krishenk.castel.client.utils.IProxy;
import fr.krishenk.castel.server.ServerProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;

public class ClientProxy extends ServerProxy implements IProxy {

	private final Minecraft mc = Minecraft.getMinecraft();

	@Override
	public void registerRender() {
		System.out.println("[CLIENT]");
	}

	
	public void onPreInit() {
		FMLCommonHandler.instance().bus().register(new KeyHandler(mc));
	}

	public void load() {
		RenderingRegistry.registerEntityRenderingHandler(EntityOrb.class, new RenderOrb());
	}

	public void registerItem(Item item) {
		MinecraftForgeClient.registerItemRenderer(item, new CustomItemRenderer());
	}

	public void spawnParticle(EntityPlayer player, String string, Object... ob) {
		double height;
		double x;
		double motionX;
		double motionY;
		double motionZ;

		if (string.equals("Spell")) {
			int data = ((Integer) ob[0]).intValue();
			int particles = ((Integer) ob[1]).intValue();

			for (int i = 0; i < particles; i++) {
				Random minecraft = player.worldObj.rand;
				height = (minecraft.nextDouble() - 0.5D) * (double) player.width;
				double rand = (double) player.getEyeHeight();
				x = (minecraft.nextDouble() - 0.5D) * (double) player.width;
				motionX = (minecraft.nextDouble() - 0.5D) * 2.0D;
				motionY = -minecraft.nextDouble();
				motionZ = (minecraft.nextDouble() - 0.5D) * 2.0D;
				Minecraft.getMinecraft().effectRenderer.addEffect(
						new EntityElementalStaffFX(player, height, rand, x, motionX, motionY, motionZ, data));
			}
		} else if (string.equals("Spiral")) {
			int data = ((Integer) ob[0]).intValue();
			int particles = ((Integer) ob[1]).intValue();
			int j = ((Integer) ob[2]).intValue();

			for (int i = 0; i < particles; i++) {
				Random minecraft = player.worldObj.rand;
				height = (minecraft.nextDouble() - 0.5D) * (double) player.width;
				double rand = (double) player.getEyeHeight();
				x = (minecraft.nextDouble() - 0.5D) * (double) player.width;
				motionX = (minecraft.nextDouble() - 0.5D) * 2.0D;
				motionY = -minecraft.nextDouble();
				motionZ = (minecraft.nextDouble() - 0.5D) * 2.0D;
				Minecraft.getMinecraft().effectRenderer
						.addEffect(new EntitySpiralFX(player, j, height, rand, x, motionX, motionY, motionZ, data));
			}
		} else if (string.equals("Circle1") || string.equals("Circle2")) {
			int data = ((Integer) ob[0]).intValue();
			int particles = ((Integer) ob[1]).intValue();
			int d;
			if (string.equals("Circle1")) {
				d = -1;
			} else {
				d = 1;
			}

			for (int i = 0; i < particles; i++) {
				Random minecraft = player.worldObj.rand;
				height = (minecraft.nextDouble() - 0.5D) * (double) player.width;
				double rand = (double) player.getEyeHeight();
				x = (minecraft.nextDouble() - 0.5D) * (double) player.width;
				motionX = (minecraft.nextDouble() - 0.5D) * 2.0D;
				motionY = -minecraft.nextDouble();
				motionZ = (minecraft.nextDouble() - 0.5D) * 2.0D;
				Minecraft.getMinecraft().effectRenderer
						.addEffect(new EntityCircleFX(player, height, rand, x, motionX, motionY, motionZ, d, data));
			}
		} else if (string.equals("MagicBall")) {
			int data = ((Integer) ob[0]).intValue();
			int tick = ((Integer) ob[1]).intValue();
			int particles = ((Integer) ob[2]).intValue();

			for (int i = 0; i < particles; i++) {
				Random minecraft = player.worldObj.rand;
				height = (minecraft.nextDouble() - 0.5D) * (double) player.width;
				double rand = (double) player.getEyeHeight();
				x = (minecraft.nextDouble() - 0.5D) * (double) player.width;
				motionX = (minecraft.nextDouble() - 0.5D) * 2.0D;
				motionY = -minecraft.nextDouble();
				motionZ = (minecraft.nextDouble() - 0.5D) * 2.0D;
				Minecraft.getMinecraft().effectRenderer.addEffect(
						new EntityMagicBallFX(player, height, rand, x, motionX, motionY, motionZ, tick, data));
			}
		} else if (string.equals("Sphere")) {
			double xP = ((Double) ob[0]).doubleValue();
			double yP = ((Double) ob[1]).doubleValue();
			double zP = ((Double) ob[2]).doubleValue();

			int data = ((Integer) ob[3]).intValue();
			int particles = ((Integer) ob[4]).intValue();

			for (int i = 0; i < particles; i++) {
				Random minecraft = player.worldObj.rand;

				height = xP;
				double rand = yP;
				x = zP;

				motionX = (minecraft.nextDouble() - 0.5D) * 2.0D;
				motionY = -minecraft.nextDouble();
				motionZ = (minecraft.nextDouble() - 0.5D) * 2.0D;
				Minecraft.getMinecraft().effectRenderer
						.addEffect(new EntitySphereFX(player, height, rand, x, motionX, 0, motionY, motionZ, data));
			}
		}
	}


	@Override
	public String localize(String unlocalized) {
		return this.localize(unlocalized);
	}


	@Override
	public String localize(String unlocalized, Object... args) {
		return I18n.format(unlocalized, args);
	}


	@Override
	public Side getPhysicalSide() {
		return Side.CLIENT;
	}
}
