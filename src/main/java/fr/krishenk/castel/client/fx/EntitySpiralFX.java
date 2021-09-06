package fr.krishenk.castel.client.fx;

import java.util.Random;

import net.minecraft.client.particle.EntityPortalFX;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;

public class EntitySpiralFX extends EntityPortalFX {
	double x;
	double y;
	double z;
	int d;
	EntityPlayer player;
	//MathHelper.cos((float)(x*8.0D))*Math.PI
	public EntitySpiralFX(EntityPlayer player, int i, double x, double y, double z, double motionX, double motionY, double motionZ, int color) {
		super(player.worldObj, player.posX, player.posY + player.height, player.posZ, motionX, motionY, motionZ);
		
		this.player = player;
		this.x = x;
		this.y = y;
		this.z = z;
		this.d = i;
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
			
			
			float f = (float)super.particleAge / (float)super.particleMaxAge;
			float f2 = f;
	        float f1 = f;
	        f = -f + f * f * 2.0F;
	        f = 1.0F - f;
	        
	        double dx = this.d*MathHelper.cos((f2* 3.141592653589793F))*1.5D;
			double dz = this.d*MathHelper.sin((f2* 3.141592653589793F))*1.5D;
	        
	        super.posX = (double)(this.player.posX - dx);
			super.posY = (double)(this.player.posY + this.y * 12.0D - (double)f2* 3.141592653589793F);
			super.posZ = (double)(this.player.posZ - dz);
			
			if (this.particleAge++ >= this.particleMaxAge) {
	            this.setDead();
	        }
		}
	}
}