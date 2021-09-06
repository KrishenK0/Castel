package fr.krishenk.castel.server;

import fr.krishenk.castel.Castel;
import fr.krishenk.castel.client.constants.EnumCastelMaterials;
import fr.krishenk.castel.client.items.ItemCastelInterface;
import fr.krishenk.castel.client.items.ItemElementStaff;
import fr.krishenk.castel.client.items.ItemStaff;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;;

public class customItems {
	public static Item wizardWand;
	public static Item staffElemental;
	public static Item orb;
	
	public static void load() {
		wizardWand = (new ItemStaff(25581, EnumCastelMaterials.IRON)).setUnlocalizedName("wizardWand").setFull3D().setMaxStackSize(1).setTextureName(Castel.MODID+":wizardWand");
		staffElemental = (new ItemElementStaff(25582, EnumCastelMaterials.WOOD).setUnlocalizedName("staffElemental").setFull3D().setMaxStackSize(1).setTextureName(Castel.MODID + ":staffElemental"));
		orb = (new ItemCastelInterface(25583)).setUnlocalizedName("orb").setCreativeTab(CreativeTabs.tabMisc).setTextureName(Castel.MODID + ":orb");
	}
	
}
