package fr.krishenk.castel.client.gui;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public abstract class AbstractInventory implements IInventory {
	protected ItemStack[] inventory;

	@Override
	public int getSizeInventory() {
		return inventory.length;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return inventory[slot];
	}
	
	@Override
	public ItemStack getStackInSlotOnClosing(int slot)
	{
		ItemStack stack = getStackInSlot(slot);
		setInventorySlotContents(slot, null);
		return stack;
	}

	@Override
	public ItemStack decrStackSize(int slot, int amount) {
		ItemStack stack = getStackInSlot(slot);
		if (stack != null) {
			if (stack.stackSize > amount) {
				stack = stack.splitStack(amount);
				markDirty();
			} else {
				setInventorySlotContents(slot, null);
			}
		}
		return stack;
	}

	public ItemStack removeStackFromSlot(int slot) {
		ItemStack stack = getStackInSlot(slot);
		setInventorySlotContents(slot, null);
		return stack;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack itemstack) {
		inventory[slot] = itemstack;
		if (itemstack != null && itemstack.stackSize > getInventoryStackLimit()) {
			itemstack.stackSize = getInventoryStackLimit();
		}
		markDirty();
	}

	@Override
	public void markDirty() {
	}

	@Override
	public void openInventory() {
	}

	@Override
	public void closeInventory() {
	}

	@Override
	public String getInventoryName() {
		return "";
	}

	@Override
	public boolean hasCustomInventoryName() {
		return false;
	}

	protected abstract String getNbtKey();

	public void writeToNBT(NBTTagCompound compound) {
		String key = getNbtKey();
		if (key == null || key.equals("")) {
			return;
		}
		NBTTagList items = new NBTTagList();
		for (int i = 0; i < getSizeInventory(); i++) {
			if (getStackInSlot(i) != null) {
				NBTTagCompound item = new NBTTagCompound();
				item.setByte("Slot", (byte) i);
				getStackInSlot(i).writeToNBT(item);
				items.appendTag(item);
			}
		}
		compound.setTag(key, items);
	}

	public void readFromNBT(NBTTagCompound compound) {
		String key = getNbtKey();
		if (key == null || key.equals("")) {
			return;
		}
		NBTTagList items = compound.getTagList(key, compound.getId());
		for (int i = 0; i < items.tagCount(); i++) {
			NBTTagCompound item = items.getCompoundTagAt(i);
			byte slot = item.getByte("Slot");
			if(slot >= 0 && slot < getSizeInventory()) {
				inventory[slot] = ItemStack.loadItemStackFromNBT(item);
			}
		}
	}

}
