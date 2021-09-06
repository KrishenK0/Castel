package fr.krishenk.castel.client.fx;

import net.minecraft.client.particle.EntityPortalFX;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityCircleFX extends EntityPortalFX {
	double x;
	double y;
	double z;
	int d;
	
	EntityPlayer player;
	
	public EntityCircleFX(EntityPlayer player, double x, double y, double z, double motionX, double motionY, double motionZ, int d, int color) {
		super(player.worldObj, player.posY, player.posY + player.eyeHeight, player.posZ, motionX, motionY, motionZ);
		
		this.x = x;
		this.y = y;
		this.z = z;
		this.d = d;
		this.player = player;
		float[] colors;
		if(color <=15) {
			colors = EntitySheep.fleeceColorTable[color];
		} else {
			colors = new float[] { (float) (color >> 16 & 255) / 255.0F, (float) (color >> 8 & 255) / 255.0F, (float) (color & 255) / 255.0F};
		}
		
		super.particleRed = colors[0];
		super.particleBlue = colors[1];
		super.particleGreen = colors[2];
		super.particleMaxAge = (int)(Math.random() * 10.0D) + 42;
		super.noClip = false;
	}

	public void onUpdate() {
		if(this.player.isDead) {
			this.setDead();
		} else {
			super.prevPosX = super.posX;
			super.prevPosY = super.posY;
			super.prevPosZ = super.posZ;
			
			float f = (float)this.particleAge / (float)this.particleMaxAge;
			f = f*2*3.141597F-2.0F;
			
			double dx = this.d*MathHelper.cos(f*2)*1.5D;
			double dz = this.d*MathHelper.sin(f*2)*1.5D;
			
			
			if(!this.player.capabilities.isFlying) {
				super.posX = this.player.posX + dx;
				super.posY = this.player.posY + this.player.eyeHeight*2.D  - this.player.height*0.5D;
				super.posZ = this.player.posZ + dz;
			} else {
				super.posX = this.player.posX + dx;
				super.posY = this.player.posY + dz/1.5D*this.d + this.player.eyeHeight*2.D  - this.player.height*0.5D;
				super.posZ = this.player.posZ + dz;
			}
			
			if(this.particleAge++ >= this.particleMaxAge) {
				this.setDead();
			}
		}
	}
}
