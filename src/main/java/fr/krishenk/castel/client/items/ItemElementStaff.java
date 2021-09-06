package fr.krishenk.castel.client.items;

import java.awt.Color;
import java.util.List;

import fr.krishenk.castel.Castel;
import fr.krishenk.castel.client.constants.EnumCastelMaterials;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemElementStaff extends ItemStaff {
	
	public ItemElementStaff(int par1, EnumCastelMaterials material) {
		super(par1, material);
		this.setHasSubtypes(true);
	}
	
	public int getColorFromItemStack(ItemStack stack, int par2) {
		float[] color = EntitySheep.fleeceColorTable[stack.getItemDamage()];
		return (new Color(color[0], color[1], color[2])).getRGB();
	}
	
	public boolean requiresMultipleRenderPasses() {
		return true;
	}
	
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		for (int i = 0; i < 16; i++) {
			list.add(new ItemStack(item, 1, i));
		}
	}
	
	public ItemStack getProjectile(ItemStack stack) {
		return new ItemStack(Items.fire_charge, 1, stack.getItemDamage());
	}
	
	public void spawnParticle(ItemStack stack, EntityPlayer player) {
		Castel.proxy.spawnParticle(player, "Spell", new Object[] {Integer.valueOf(stack.getItemDamage()), Integer.valueOf(4)});
	}
}
