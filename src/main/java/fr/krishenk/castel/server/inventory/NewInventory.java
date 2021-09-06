package fr.krishenk.castel.server.inventory;

import fr.krishenk.castel.client.gui.AbstractInventory;
import fr.krishenk.castel.client.items.ItemElementStaff;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class NewInventory extends AbstractInventory {
	/**
	 * The name your custom inventory will display in the GUI, possibly just
	 * "Inventory"
	 */
	private final String name = "New Inventory";

	/** The key used to store and retrieve the inventory from NBT */
	private static final String SAVE_KEY = "NewInvTag";

	/** Define the inventory size here for easy reference */
	// This is also the place to define which slot is which if you have different
	// types,
	// for example SLOT_SHIELD = 0, SLOT_AMULET = 1;
	public static final int INV_SIZE = 2;

	public NewInventory() {
		// Make sure to initialize the inventory slots:
		this.inventory = new ItemStack[INV_SIZE];
	}

	/**
	 * Show our custom inventory name
	 */
	@Override
	public String getInventoryName() {
		return name;
	}

	/**
	 * Custom name is already translated, so we return true here
	 */
	@Override
	public boolean hasCustomInventoryName() {
		return true;
	}

	/**
	 * Our custom slots are similar to armor - only one item per slot
	 */
	@Override
	public int getInventoryStackLimit() {
		return 1;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return true;
	}

	/**
	 * This method doesn't seem to do what it claims to do, as items can still be
	 * left-clicked and placed in the inventory even when this returns false
	 */
	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		// If you have different kinds of slots, then check them here:
		// if (slot == SLOT_SHIELD && stack.getItem() instanceof ItemShield) return
		// true;

		// For now, only ItemUseMana items can be stored in these slots
		return stack.getItem() instanceof ItemElementStaff;
	}

	@Override
	protected String getNbtKey() {
		return SAVE_KEY;
	}

	/**
	 * Makes this inventory an exact replica of the inventory provided (useful, for
	 * example, when persisting IExtendedEntityProperties)
	 */
	public void copy(AbstractInventory inv) {
		for (int i = 0; i < inv.getSizeInventory(); ++i) {
			ItemStack stack = inv.getStackInSlot(i);
			inventory[i] = (stack == null ? null : stack.copy());
		}
		markDirty();
	}

}
