package fr.krishenk.castel.client.entity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class EntityMagicOrb extends EntityOrb {
	private EntityPlayer player;
	private ItemStack equiped;
	
	public EntityMagicOrb(World world, EntityPlayer player, ItemStack item) {
		super(world, player, item);
		this.player = player;
		this.equiped = player.inventory.getCurrentItem();
	}
	
	public void onUpdate() {
		if(this.player.inventory.getCurrentItem() != this.equiped) {
			this.setDead();
		}
		
		super.onUpdate();
	}
	
	public String getCommandSenderName() {
		return StatCollector.translateToLocal("entity.throwableitem.name");
	}
}
