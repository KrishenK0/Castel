package fr.krishenk.castel.server.network.packet;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import fr.krishenk.castel.client.gui.faction.GuiFaction;
import fr.krishenk.castel.common.constants.group.Guild;
import fr.krishenk.castel.common.fperms.Rank;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Supplier;

public class FactionMaSCPacket {
    Guild guild;
    public FactionMaSCPacket(Guild guild) {
        this.guild = guild;
    }

    public static void encode(FactionMaSCPacket pkt, PacketBuffer buf) { }

    public static FactionMaSCPacket decode(PacketBuffer buf) {
        UUID id = UUID.fromString(buf.readString());
        UUID leader = UUID.fromString(buf.readString());
        String leaderName = buf.readString();
        String name = buf.readString();
        String tag = buf.readString();
        long since = buf.readLong();

        List<String> membersOnline = new Gson().fromJson(buf.readString(), new TypeToken<List<String>>(){}.getType());
        List<String> membersOffline = new Gson().fromJson(buf.readString(), new TypeToken<List<String>>(){}.getType());
        List<Rank> ranks = new Gson().fromJson(buf.readString(), new TypeToken<List<Rank>>(){}.getType());
//        Location home;
        double publicHomeCost = buf.readDouble();
        boolean publicHome = buf.readBoolean();
        Color color = Color.decode(buf.readString());
        double bank = buf.readDouble();
        String tax = buf.readString();
        String flag = buf.readString();
//        Map<UUID, GuildRelationshipRequest> relationshipRequests
//        Map<UUID, GuildRelation> relations;
//        Map<GuildRelation, Set<RelationAttribute>> attributes;
        long resourcePoints = buf.readLong();
        boolean requiresInvite = buf.readBoolean();
        boolean permanent = buf.readBoolean();
        boolean hidden = buf.readBoolean();
        String mailsStr = buf.readString();
        Set<UUID> mails = !mailsStr.equals("{}") ? new Gson().fromJson(mailsStr, new TypeToken<Set<UUID>>(){}.getType()) : null;
//        Set<SimpleChunkLocation> lands;
//        Map<Guild.Powerup, Integer> powerups;
//        Map<Guild.MiscUpgrade, Integer> miscUpgrades;
//        Map<String, Guild.InviteCode> inviteCodes;
        String challengeStr = buf.readString();
        Map<UUID, Long> challenges = !challengeStr.equals("{}") ? new Gson().fromJson(challengeStr, new TypeToken<Map<UUID, Long>>(){}.getType()) : null;
        String inventoryStr = buf.readString();
        Inventory chest = !inventoryStr.contains("null") ? new Inventory(new Gson().fromJson(inventoryStr, new TypeToken<List<ItemStack>>(){}.getType())) : null;
        String lore = buf.readString();
        boolean pacifist = buf.readBoolean();
        int maxLandsModifier = buf.readInt();

        Guild guild = new Guild(id, leader, leaderName, name, tag, since, membersOnline, membersOffline, ranks, publicHomeCost, publicHome, color, bank, tax, flag, resourcePoints, requiresInvite, permanent, hidden, mails, challenges, chest, lore, pacifist, maxLandsModifier);



//        factionInfo1.setTitle(buf.readString());
//        factionInfo1.setPower(buf.readInt());
//        factionInfo1.setPowerMax(buf.readInt());
//        factionInfo1.setLeaderName(buf.readString());
//        factionInfo1.setLeaderId(buf.readString());
//        factionInfo1.setRanks(new Gson().fromJson(buf.readString(), new TypeToken<List<Rank>>(){}.getType()));
//        factionInfo1.setPlayerOnline(new Gson().fromJson(buf.readString(), List.class));
//        factionInfo1.setPlayerOffline(new Gson().fromJson(buf.readString(), List.class));


        return new FactionMaSCPacket(guild);
    }

    public static class Handler {
        public static void handle(FactionMaSCPacket msg, Supplier<NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> FactionMaSCPacket.Handler.handlePacket(msg, ctx)));
            ctx.get().setPacketHandled(true);
        }

        public static void handlePacket(FactionMaSCPacket msg, Supplier<NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() -> Minecraft.getInstance().displayGuiScreen(new GuiFaction()));
            ctx.get().setPacketHandled(true);
        }
    }
}
