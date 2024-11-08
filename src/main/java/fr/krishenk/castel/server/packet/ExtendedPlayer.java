package fr.krishenk.castel.server.packet;


import fr.krishenk.castel.server.inventory.NewInventory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class ExtendedPlayer implements IExtendedEntityProperties
{
	public final static String EXT_PROP_NAME = "ExtendedPlayer";

	private final EntityPlayer player;

	/** Custom inventory slots will be stored here - be sure to save to NBT! */
	public final NewInventory inventory = new NewInventory();

	public ExtendedPlayer(EntityPlayer player) {
		this.player = player;
	}

	/**
	 * Used to register these extended properties for the player during EntityConstructing event
	 */
	public static final void register(EntityPlayer player) {
		player.registerExtendedProperties(ExtendedPlayer.EXT_PROP_NAME, new ExtendedPlayer(player));
	}

	/**
	 * Returns ExtendedPlayer properties for player
	 */
	public static final ExtendedPlayer get(EntityPlayer player) {
		return (ExtendedPlayer) player.getExtendedProperties(EXT_PROP_NAME);
	}

	/**
	 * Copies additional player data from the given ExtendedPlayer instance
	 * Avoids NBT disk I/O overhead when cloning a player after respawn
	 */
	public void copy(ExtendedPlayer props) {
		inventory.copy(props.inventory);
	}

	@Override
	public final void saveNBTData(NBTTagCompound compound) {
		// We store all of our data nested in a single tag;
		// this way, we never have to worry about conflicting with other
		// mods that may also be writing to the player's tag compound
		NBTTagCompound properties = new NBTTagCompound();
		
		// Write everything to our new tag:
		inventory.writeToNBT(properties);
		
		// Finally, set the tag with our unique identifier:
		compound.setTag(EXT_PROP_NAME, properties);
	}

	@Override
	public final void loadNBTData(NBTTagCompound compound) {
		// Pretty much the reverse of saveNBTData - get our
		// unique tag and then load everything from it:
		NBTTagCompound properties = (NBTTagCompound) compound.getTag(EXT_PROP_NAME);
		inventory.readFromNBT(properties);
	}

	@Override
	public void init(Entity entity, World world) {}
	

	
	
}
