package fr.krishenk.castel.client.items;

import org.lwjgl.opengl.GL11;

import fr.krishenk.castel.Castel;
import fr.krishenk.castel.client.constants.EnumCastelMaterials;
import fr.krishenk.castel.client.entity.EntityOrb;
import fr.krishenk.castel.client.utils.IProjectileCallback;
import fr.krishenk.castel.server.customItems;
import fr.krishenk.castel.server.packet.PacketDispatcher;
import fr.krishenk.castel.server.packet.impl.OpenGuiPacket;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class ItemStaff extends ItemCastelInterface implements IProjectileCallback {

	// public static final String[] blowPullIconNameArray = new String[] { "1", "2",
	// "3" };
	private EnumCastelMaterials material;

	public ItemStaff(int par1, EnumCastelMaterials material) {
		super(par1);
		this.setCreativeTab(CreativeTabs.tabCombat);
		this.material = material;
	}

	public void renderSpecial() {
		GL11.glScalef(1.0F, 1.14F, 1.0F);
		GL11.glTranslatef(0.14F, -0.3F, 0.08F);
	}

	public void onPlayerStoppedUsing(ItemStack stack, World worldObj, EntityPlayer player, int par4) {
		if (!worldObj.isRemote) {
			if (stack.stackTagCompound != null) {
				Entity entity = ((WorldServer) player.worldObj)
						.getEntityByID(stack.stackTagCompound.getInteger("MagicOrb"));
				if (entity != null && entity instanceof EntityOrb) {
					EntityOrb item = (EntityOrb) entity;
					item.callback = this;
					item.callbackItem = stack;
					item.explosive = true;
					item.explosiveDamage = false;
					item.explosiveRadius = 1;
					item.prevRotationYaw = item.rotationYaw = player.rotationYaw;
					item.prevRotationPitch = item.rotationPitch = player.rotationPitch;
					item.shoot(2.0F);
					player.worldObj.playSoundAtEntity(player, "random.bow", 1.0F, 1.0F);
				}
			}
		}
	}

	public void onUsingTick(ItemStack stack, EntityPlayer player, int count) {
		int tick = this.getMaxItemUseDuration(stack) - count;
		if (player.worldObj.isRemote) {
			this.spawnParticle(stack, player, tick);
		} else {
			/*
			 * int chargeTime = 20 + this.material.getHarvestLevel() * 8; double dx; double
			 * dz; if(tick == chargeTime) { if(!player.capabilities.isCreativeMode) {
			 * if(!this.hasItem(player, customItems.orb)) { return; }
			 * 
			 * this.consumeItem(player, customItems.orb); }
			 * 
			 * player.worldObj.playSoundAtEntity(player, "note.harp", 1.0F, 1.0F);
			 * if(stack.stackTagCompound == null) { stack.stackTagCompound = new
			 * NBTTagCompound(); }
			 * 
			 * int entity = 6 + this.material.getDamageVsEntity() +
			 * player.worldObj.rand.nextInt(4); entity = (int)((float)entity +
			 * (float)(entity * 1.0F * 0.5F)); EntityMagicOrb item = new
			 * EntityMagicOrb(player.worldObj, player, this.getProjectile(stack));
			 * item.damage = (float)entity; item.setSpeed(25); dx =
			 * (double)(-MathHelper.sin((float)((double)(player.rotationYaw / 180.0F) *
			 * 3.141592653589793D)) * MathHelper.cos((float)((double)(player.rotationPitch /
			 * 180.0F) * 3.141592653589793D))); dz =
			 * (double)(MathHelper.cos((float)((double)(player.rotationYaw / 180.0F) *
			 * 3.141592653589793D)) * MathHelper.cos((float)((double)(player.rotationPitch /
			 * 180.0F) * 3.141592653589793D))); item.setPosition(player.posX + dx * 1.2D,
			 * player.posY + 1.5D - (double)(player.rotationPitch / 80.0F), player.posZ + dz
			 * * 1.2D); player.worldObj.spawnEntityInWorld(item);
			 * stack.stackTagCompound.setInteger("MagicOrb", item.getEntityId()); }
			 * 
			 * if(tick > chargeTime && stack.stackTagCompound != null) { Entity entity1 =
			 * ((WorldServer)player.worldObj).getEntityByID(stack.stackTagCompound.
			 * getInteger("MagicOrb")); if(entity1 == null || !(entity1 instanceof
			 * EntityOrb)) { return; }
			 * 
			 * EntityOrb item1 = (EntityOrb)entity1; item1.field_70195_i = 0; dx =
			 * (double)(-MathHelper.sin((float)((double)(player.rotationYaw / 180.0F) *
			 * 3.141592653589793D)) * MathHelper.cos((float)((double)(player.rotationPitch /
			 * 180.0F) * 3.141592653589793D))); dz =
			 * (double)(MathHelper.cos((float)((double)(player.rotationYaw / 180.0F) *
			 * 3.141592653589793D)) * MathHelper.cos((float)((double)(player.rotationPitch /
			 * 180.0F) * 3.141592653589793D))); item1.setPosition(player.posX + dx * 1.2D,
			 * player.posY + 1.5D - (double)(player.rotationPitch / 80.0F), player.posZ + dz
			 * * 1.2D); }
			 */

		}
	}

	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
		par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
		return par1ItemStack;
	}

	public int getMaxItemUseDuration(ItemStack par1ItemStack) {
		return 72000;
	}

	public ItemStack getProjectile(ItemStack stack) {
		return new ItemStack(customItems.orb);
	}

	public EnumAction getItemUseAction(ItemStack par1ItemStack) {
		return EnumAction.bow;
	}

	public int getItemEnchantability() {
		return this.material.getEnchantability();
	}

	public void spawnParticle(ItemStack stack, EntityPlayer player, int tick) {
		if (stack.getItem() == customItems.wizardWand) {
			// Castel.proxy.spawnParticle(player, "Spiral", new Object[]
			// {Integer.valueOf(15), Integer.valueOf(15), Integer.valueOf(-1)});
			// Castel.proxy.spawnParticle(player, "Spiral", new Object[]
			// {Integer.valueOf(14), Integer.valueOf(15), Integer.valueOf(1)});
			// Castel.proxy.spawnParticle(player, "Circle1", new Object[]
			// {Integer.valueOf(15), Integer.valueOf(15)});
			// Castel.proxy.spawnParticle(player, "Circle2", new Object[]
			// {Integer.valueOf(15), Integer.valueOf(15)});

			// Castel.proxy.spawnParticle(player, "MagicBall", new Object[]
			// {Integer.valueOf(15), Integer.valueOf(tick), Integer.valueOf(15)});
			int r = 2;
			double x = 0;
			double y = 0;
			double z = 0;
			for (int i = -180; i <= 180; i += 20) {
				for (int j = -90; j <= 90; j += 10) {
					x = r * Math.cos(j * Math.PI / 180) * Math.cos(i * Math.PI / 180);
					y = r * Math.cos(j * Math.PI / 180) * Math.sin(i * Math.PI / 180);
					z = r * Math.sin(j * Math.PI / 180);

					Castel.proxy.spawnParticle(player, "Sphere", new Object[] { Double.valueOf(x), Double.valueOf(y), Double.valueOf(z), Integer.valueOf(15), Integer.valueOf(1) });
				}
			}

			// System.out.println(x + " "+ y +" "+ z);
		}
	}

	public boolean isItemTool(ItemStack par1ItemStack) {
		return true;
	}

	public boolean onImpact(EntityOrb orb, EntityLivingBase entitylivingbase, ItemStack item) {
		return false;
	}

	/*
	 * public void onPlayerStoppedUsing(ItemStack stack, World world, EntityPlayer
	 * player, int par4) { int j = this.getMaxItemUseDuration(stack) - par4;
	 * 
	 * ArrowLooseEvent event = new ArrowLooseEvent(player, stack, j);
	 * MinecraftForge.EVENT_BUS.post(event); if (event.isCanceled()) { return; } j =
	 * event.charge;
	 * 
	 * boolean flag = player.capabilities.isCreativeMode ||
	 * EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, stack) >
	 * 0;
	 * 
	 * if (flag || player.inventory.hasItem(Items.arrow)) { float f = (float)j /
	 * 20.0F; f = (f * f + f* 2.0F) / 3.0F;
	 * 
	 * if ((double)f < 0.1D) { return; }
	 * 
	 * if (f > 1.0F) { f = 1.0F; }
	 * 
	 * EntityArrow entityArrow = new EntityArrow(world, player, f * 2.0F);
	 * 
	 * if (f == 1.0F) { entityArrow.setIsCritical(true); }
	 * 
	 * stack.damageItem(1, player); world.playSoundAtEntity(player, "random.bow",
	 * 1.0F, 1.0F / (Item.itemRand.nextFloat() * 0.4F + 1.2F) + f * 0.5F);
	 * 
	 * if (flag) { entityArrow.canBePickedUp = 2; } else {
	 * player.inventory.consumeInventoryItem(Items.arrow); }
	 * 
	 * if (!world.isRemote) { world.spawnEntityInWorld(entityArrow); }
	 * 
	 * } }
	 * 
	 * public int getMaxItemUseDuration(ItemStack stack) { return 4000; }
	 * 
	 * public EnumAction getItemUseAction(ItemStack stack) { return EnumAction.bow;
	 * }
	 * 
	 * public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer
	 * player) { ArrowNockEvent event = new ArrowNockEvent(player, stack);
	 * MinecraftForge.EVENT_BUS.post(event); if (event.isCanceled()) { return
	 * event.result; }
	 * 
	 * if (player.capabilities.isCreativeMode ||
	 * player.inventory.hasItem(Items.arrow)) { player.setItemInUse(stack,
	 * this.getMaxItemUseDuration(stack)); }
	 * 
	 * return stack; }
	 * 
	 * public int getItemEnchantability() { return 1; }
	 * 
	 * 
	 * @SideOnly(Side.CLIENT) private IIcon[] iconArray; public void
	 * registerIcons(IIconRegister IconRegister) { this.itemIcon =
	 * IconRegister.registerIcon(this.getIconString()); this.iconArray = new
	 * IIcon[blowPullIconNameArray.length];
	 * 
	 * for (int i = 0; i < iconArray.length; i++) { this.iconArray[i] =
	 * IconRegister.registerIcon(this.getIconString() + "_" +
	 * blowPullIconNameArray[i]); } }
	 * 
	 * @Override public IIcon getIcon(ItemStack stack, int renderPass, EntityPlayer
	 * player, ItemStack usingItem, int useRemaining) { if (usingItem != null &&
	 * usingItem.getItem().equals(this)) { int k = usingItem.getMaxItemUseDuration()
	 * - useRemaining; if(k >= 18) return iconArray[2]; if(k >= 13) return
	 * iconArray[1]; if(k > 0) return iconArray[0]; } return getIconIndex(stack); }
	 */

}
