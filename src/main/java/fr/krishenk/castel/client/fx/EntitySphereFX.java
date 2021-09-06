package fr.krishenk.castel.client.fx;

import net.minecraft.client.particle.EntityPortalFX;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntitySphereFX extends EntityPortalFX {
	double x;
	double y;
	double z;
	double r;
	EntityPlayer player;

	public EntitySphereFX(EntityPlayer player, double x, double y, double z, double r, double motionX, double motionY,
			double motionZ, int color) {
		super(player.worldObj, player.posX, player.posY + player.height, player.posZ, motionX, motionY, motionZ);
		
		this.player = player;
		this.x = x;
		this.y = y;
		this.z = y;
		this.r = r;
		
		float[] colors;
		
		if(color <= 15) {
			colors = EntitySheep.fleeceColorTable[color];
		} else {
			colors = new float[] { (float) (color >> 16 & 255) / 255.0F, (float) (color >> 8 & 255) / 255.0F, (float) (color & 255) / 255.0F };
		}
		
		super.particleRed = colors[0];
		super.particleGreen = colors[1];
		super.particleBlue = colors[2];
		super.particleMaxAge = (int)(Math.random() * 10.0D) + 40;
		super.noClip = false;
	}
	
	public void onUpdate() {
		if(player.isDead) {
			this.setDead();
		} else {
			super.prevPosX = super.posX;
			super.prevPosY = super.posY;
			super.prevPosZ = super.posZ;	
			
			
//			float f = (float)this.particleAge / (float)this.particleMaxAge;
//			f = f*2*3.141597F-2.0F;
			
//			double dx = this.r*MathHelper.cos(f*2)*1.5D;
//			double dz = this.r*MathHelper.sin(f*2)*1.5D;
	        
			super.posX = this.player.posX + this.x;
			super.posY = this.player.posY + this.y;
			super.posZ = this.player.posZ + this.z;
			
			if (this.particleAge++ >= this.particleMaxAge) {
	            this.setDead();
	        }
		}
	}

}
