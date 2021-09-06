package fr.krishenk.castel.client.utils;

import fr.krishenk.castel.client.entity.EntityOrb;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public interface IProjectileCallback {
	boolean onImpact(EntityOrb orb, EntityLivingBase entitylivingbase, ItemStack item);
}
