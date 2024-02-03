package fr.krishenk.castel.server.network.packet;

import fr.krishenk.castel.common.container.FactionChestContainers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class FactionPacket {

    public final String factionId;

    public FactionPacket(String factionId) { this.factionId = factionId; }

    public static void encode(FactionPacket pkt, PacketBuffer buf) {
        buf.writeString(pkt.factionId);
    }

    public static FactionPacket decode(PacketBuffer buf) {
        return new FactionPacket(buf.readString());
    }

    public static class Handler {
        public static void handle(FactionPacket msg, Supplier<NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() -> {
                ServerPlayerEntity player = ctx.get().getSender();
                //player.addItemStackToInventory(new ItemStack(Items.DIAMOND_AXE));
                System.out.println("MOD: " + msg.factionId);

                INamedContainerProvider containerProvider = new INamedContainerProvider() {
                    @Override
                    public ITextComponent getDisplayName() {
                        return new StringTextComponent("Screen containers");
                    }

                    @Nullable
                    @Override
                    public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
                        return new FactionChestContainers(i, playerInventory, playerEntity);
                    }
                };
                NetworkHooks.openGui(player, containerProvider);
            });
            ctx.get().setPacketHandled(true);
        }
    }
}
