package fr.krishenk.castel.server.inventory;

import fr.krishenk.castel.client.items.ItemElementStaff;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.event.ClickEvent;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;

public class ContainerCustom extends Container {

	/**
	 * Avoid magic numbers! This will greatly reduce the chance of you making errors
	 * in 'transferStackInSlot' method
	 */
	private static final int ARMOR_START = NewInventory.INV_SIZE, // INV_SIZE = 2, so slots 0 and 1 are the custom
																	// inventory, armor starts at the next slot (i.e. 2)
			ARMOR_END = ARMOR_START + 3, // 4 slots total, e.g. 2-5 (2, 3, 4, 5)
			INV_START = ARMOR_END + 1, // start at next slot after armor, e.g. 6
			INV_END = INV_START + 26, // 27 vanilla inventory slots total (i.e. the first one plus 26 more)
			HOTBAR_START = INV_END + 1, // start at next slot after inventory
			HOTBAR_END = HOTBAR_START + 8; // 9 slots total (i.e. the first one plus 8 more)

	public InventoryCrafting craftMatrix = new InventoryCrafting(this, 2, 2);
	public IInventory craftResult = new InventoryCraftResult();
	private final EntityPlayer thePlayer;

	public ContainerCustom(EntityPlayer player, InventoryPlayer inventoryPlayer, NewInventory inventoryCustom) {
		this.thePlayer = player;
		int i;
		int j;

		// Add CUSTOM slots - we'll just add two for now, both of the same type.
		// Make a new Slot class for each different item type you want to add
		addSlotToContainer(new SlotCustom(inventoryCustom, 0, 80, 8));
		addSlotToContainer(new SlotCustom(inventoryCustom, 1, 80, 26));

		// CRAFT

		this.addSlotToContainer(
				new SlotCrafting(inventoryPlayer.player, this.craftMatrix, this.craftResult, 0, 144, 36));
		for (i = 0; i < 2; ++i) {
			for (j = 0; j < 2; ++j) {
				this.addSlotToContainer(new Slot(this.craftMatrix, j + i * 2, 88 + j * 18, 26 + i * 18));
			}
		}

		// Add ARMOR slots; note you need to make a public version of SlotArmor
		// just copy and paste the vanilla code into a new class and change what you
		// need

		for (i = 0; i < 4; ++i) {
			addSlotToContainer(new SlotArmor(player, inventoryPlayer, inventoryPlayer.getSizeInventory() - 1 - i, 8,
					8 + i * 18, i));
		}

		// Add vanilla PLAYER INVENTORY - just copied/pasted from vanilla classes

		for (i = 0; i < 3; ++i) {
			for (j = 0; j < 9; ++j) {
				addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		// Add ACTION BAR - just copied/pasted from vanilla classes

		for (i = 0; i < 9; ++i) {
			addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 142));
		}

		this.onCraftMatrixChanged(this.craftMatrix);
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return true;
	}

	public void onCraftMatrixChanged(IInventory p_75130_1_) {
		this.craftResult.setInventorySlotContents(0,
				CraftingManager.getInstance().findMatchingRecipe(this.craftMatrix, this.thePlayer.worldObj));
	}

	public void onContainerClosed(EntityPlayer player) {
		super.onContainerClosed(player);

		for (int i = 0; i < 4; ++i) {
			ItemStack itemstack = this.craftMatrix.getStackInSlotOnClosing(i);

			if (itemstack != null) {
				player.dropPlayerItemWithRandomChoice(itemstack, false);
			}
		}

		this.craftResult.setInventorySlotContents(0, (ItemStack) null);
	}

	public ItemStack transferStackInSlot(EntityPlayer player, int par2) {
		ItemStack itemstack = null;
		Slot slot = (Slot) this.inventorySlots.get(par2);

		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			// Either armor slot or custom item slot was clicked
			if (par2 < INV_START) {
				// try to place in player inventory / action bar
				if (!this.mergeItemStack(itemstack1, INV_START, HOTBAR_END + 1, true)) {
					return null;
				}

				slot.onSlotChange(itemstack1, itemstack);
			}
			// Item is in inventory / hotbar, try to place either in custom or armor slots
			else {
				// if item is our custom item
				if (itemstack1.getItem() instanceof ItemElementStaff) {
					if (!this.mergeItemStack(itemstack1, 0, NewInventory.INV_SIZE, false)) {
						return null;
					}
				}
				// if item is armor
				else if (itemstack1.getItem() instanceof ItemArmor) {
					int type = 5 + ((ItemArmor) itemstack1.getItem()).armorType;
					if (!this.mergeItemStack(itemstack1, ARMOR_START + type, ARMOR_START + type + 1, false)) {
						return null;
					}
				}
				// item in player's inventory, but not in action bar
				else if (par2 >= INV_START && par2 < HOTBAR_START) {
					// place in action bar
					if (!this.mergeItemStack(itemstack1, HOTBAR_START, HOTBAR_START + 1, false)) {
						return null;
					}
				}
				// item in action bar - place in player inventory
				else if (par2 >= HOTBAR_START && par2 < HOTBAR_END + 1) {
					if (!this.mergeItemStack(itemstack1, INV_START, INV_END + 1, false)) {
						return null;
					}
				}
			}

			if (itemstack1.stackSize == 0) {
				slot.putStack((ItemStack) null);
			} else {
				slot.onSlotChanged();
			}

			if (itemstack1.stackSize == itemstack.stackSize) {
				return null;
			}

			slot.onPickupFromSlot(player, itemstack1);
		}

		return itemstack;
	}

	public boolean func_94530_a(ItemStack itemstack, Slot slotIndex) {
		return slotIndex.inventory != this.craftResult && super.func_94530_a(itemstack, slotIndex);
	}

}
