package fr.krishenk.castel.client.fx;

import net.minecraft.client.particle.EntityPortalFX;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityMagicBallFX extends EntityPortalFX {
	double x;
	double y;
	double z;
	int tick;
	
	EntityPlayer player;
	
	public EntityMagicBallFX(EntityPlayer player, double x, double y, double z, double motionX, double motionY, double motionZ, int tick, int color) {
		super(player.worldObj, player.posX, player.posY + player.eyeHeight, player.posZ, motionX, motionY, motionZ);
		this.x = x;
		this.y = y;
		this.z = z;
		this.tick = tick;
		this.player = player;
		float[] colors;
		if (color <= 15) {
			colors = EntitySheep.fleeceColorTable[color];
		} else {
			colors = new float[] { (float) (color >> 16 & 255) / 255.0F, (float) (color >> 8 & 255) / 255.0F, (float) (color & 255) / 255.0F};
		}
		
		super.particleRed = colors[0];
		super.particleBlue = colors[1];
		super.particleGreen = colors[2];
		super.particleMaxAge = (int)(Math.random() * 10.0D) + 10;
		super.noClip = false;
		
	}
	
	public void onUpdate() {
		if(this.player.isDead) {
			super.setDead();
		}
		
		super.prevPosX = super.posX;
		super.prevPosY = super.posY;
		super.prevPosZ = super.posZ;
		
		float f = (float)super.particleAge/(float)super.particleMaxAge;
		float f1 = f;
        f = -f + f * f * 2.0F;
        f = 2.0F - f;
        
        double dy = MathHelper.cos(((float)this.tick/(float)10))/4;
        
        super.posX = this.player.posX + super.motionX*0.05D + 1.0D;
        super.posY = this.player.posY + (double)(f1 - f) * 0.5D + dy;
        super.posZ = this.player.posZ + super.motionZ*0.05D + 1.0D;
        
        
        
        
        /*if (super.onGround) {
        	super.motionX *= 0.699999988079071D;
        	super.motionZ *= 0.699999988079071D;
        }*/
		
		if(super.particleAge++ >= super.particleMaxAge) {
			super.setDead();
		}
	}
}
