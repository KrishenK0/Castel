package fr.krishenk.castel.client.fx;

import net.minecraft.client.particle.EntityPortalFX;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.util.MathHelper;

public class EntityElementalStaffFX extends EntityPortalFX {
	double x;
	double y;
	double z;
	EntityLivingBase player;

	public EntityElementalStaffFX(EntityLivingBase player, double x, double y, double z, double motionX, double motionY, double motionZ, int color) {
		super(player.worldObj, player.posX + x, player.posY + y, player.posZ + z, motionX, motionY, motionZ);
		this.player = player;
		this.x = x;
		this.y = y;
		this.z = z;
		float[] colors;
		if (color <= 15) {
			colors = EntitySheep.fleeceColorTable[color];
		} else {
			colors = new float[] { (float) (color >> 16 & 255) / 255.0F, (float) (color >> 8 & 255) / 255.0F,
					(float) (color & 255) / 255.0F };
		}

		super.particleRed = colors[0];
		super.particleGreen = colors[1];
		super.particleBlue = colors[2];
		super.particleMaxAge = (int) (16.0D / (Math.random() * 0.8D + 0.2D));
		super.noClip = false;
	}

	public void onUpdate() {
		if (this.player.isDead) {
			this.setDead();
		} else {
			super.prevPosX = super.posX;
			super.prevPosY = super.posY;
			super.prevPosZ = super.posZ;
			float var1 = (float) super.particleAge / (float) super.particleMaxAge;
			float var2 = var1;
			var1 = -var1 + var1 * var1 * 2.0F;
			var1 = 1.0F - var1;
			double dx = (double) (-MathHelper.sin((float) ((double) (this.player.rotationYaw / 180.0F) * 3.141592653589793D)) * MathHelper.cos((float) ((double) (this.player.rotationPitch / 180.0F) * 3.141592653589793D)));
			double dz = (double) (MathHelper.cos((float) ((double) (this.player.rotationYaw / 180.0F) * 3.141592653589793D)) * MathHelper.cos((float) ((double) (this.player.rotationPitch / 180.0F) * 3.141592653589793D)));
			super.posX = this.player.posX + this.x + dx + super.motionX * (double) var1;
			super.posY = this.player.posY + this.y + super.motionY * (double) var1 + (double) (1.0F - var2) - (double) (this.player.rotationPitch / 40.0F);
			super.posZ = this.player.posZ + this.z + dz + super.motionZ * (double) var1;
			if (super.particleAge++ >= super.particleMaxAge) {
				this.setDead();
			}
		}
	}

	public void setDead() {
		super.setDead();
	}
}
