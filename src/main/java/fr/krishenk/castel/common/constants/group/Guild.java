package fr.krishenk.castel.common.constants.group;

import fr.krishenk.castel.FactionInfo;
import fr.krishenk.castel.common.constants.player.RankMap;
import fr.krishenk.castel.common.fperms.Rank;
import fr.krishenk.castel.common.utils.SimpleChunkLocation;
import fr.krishenk.castel.common.utils.TimeUtils;
import net.minecraft.inventory.Inventory;

import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class Guild extends Group {
    static Guild INSTANCE;
//    private Set<SimpleChunkLocation> lands;
//    private Map<Powerup, Integer> powerups;
//    private Map<MiscUpgrade, Integer> miscUpgrades;
//    private Map<String, InviteCode> inviteCodes;
    private Map<UUID, Long> challenges;
    private Inventory chest;
    private String lore;
    private boolean pacifist;
    private int maxLandsModifier;

    public Guild(UUID id, UUID leader, String leaderName, String name, String tag, long since, List<String> membersOnline, List<String> membersOffline, List<Rank> ranks, double publicHomeCost, boolean publicHome, Color color, double bank, String tax, String flag, long resourcePoints, boolean requiresInvite, boolean permanent, boolean hidden, Set<UUID> mails, Map<UUID, Long> challenges, Inventory chest, String lore, boolean pacifist, int maxLandsModifier) {
        super(id, leader, leaderName, name, tag, since, membersOnline, membersOffline, ranks, publicHomeCost, publicHome, color, bank, tax, flag, resourcePoints, requiresInvite, permanent, hidden, mails);
        INSTANCE = this;
        this.challenges = challenges;
        this.chest = chest;
        this.lore = lore;
        this.pacifist = pacifist;
        this.maxLandsModifier = maxLandsModifier;
    }

    public static Guild getInstance() { return INSTANCE; }

//    public String toString() {
//        return "Guild[ID:" + this.id + ", Name: "+ this.name +']';
//    }


    @Override
    public String toString() {
        return "Guild[" +
                "id=" + id +
                ", leader=" + leader +
                ", name='" + name + '\'' +
                ", tag='" + tag + '\'' +
                ", since=" + since +
                ", membersOnline=" + membersOnline +
                ", membersOffline=" + membersOffline +
                ", ranks=" + ranks +
                ", publicHomeCost=" + publicHomeCost +
                ", publicHome=" + publicHome +
                ", bank=" + bank +
                ", tax='" + tax + '\'' +
                ", resourcePoints=" + resourcePoints +
                ", requiresInvite=" + requiresInvite +
                ", permanent=" + permanent +
                ", hidden=" + hidden +
                ']';
    }

    public Group getGroup() {
        return this;
    }

    public Map<UUID, Long> getChallenges() {
        return challenges;
    }

    public void setChallenges(Map<UUID, Long> challenges) {
        this.challenges = challenges;
    }

    public Inventory getChest() {
        return chest;
    }

    public void setChest(Inventory chest) {
        this.chest = chest;
    }

    public String getLore() {
        return lore;
    }

    public void setLore(String lore) {
        this.lore = lore;
    }

    public boolean isPacifist() {
        return pacifist;
    }

    public void setPacifist(boolean pacifist) {
        this.pacifist = pacifist;
    }

    public int getMaxLandsModifier() {
        return maxLandsModifier;
    }

    public void setMaxLandsModifier(int maxLandsModifier) {
        this.maxLandsModifier = maxLandsModifier;
    }

    private class Powerup {
        String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    private class MiscUpgrade {
        String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    private class InviteCode {
        private final String code;
        private final long createdAt;
        private long expiration;
        private final UUID createdBy;
        private final Set<UUID> usedBy;
        private int uses;

        public InviteCode(String code, long createdAt, long expiration, UUID createdBy, Set<UUID> usedBy, int uses) {
            this.code = code;
            this.createdAt = createdAt;
            this.redeemFor(expiration);
            this.createdBy = createdBy;
            this.usedBy = usedBy;
            this.setUses(uses);
        }

        public int getUses() {
            return this.uses;
        }

        public boolean isAllUsed() {
            return this.uses != 0 && this.usedBy.size() >= this.uses;
        }

        public UUID getCreatedBy() {
            return this.createdBy;
        }

        private void redeemFor(long expiresIn) {
            if (expiresIn < 0L)
                throw new IllegalArgumentException("Expiration of invite code must be greater than or equal to 0");
            if (expiresIn != 0L)
                TimeUtils.validateUnixTime(expiresIn);
            this.expiration = expiresIn;
        }

        public Set<UUID> getUsedBy() {
            return this.usedBy;
        }

        public void setUses(int uses) {
            if (uses < 0)
                throw new IllegalArgumentException("Invite code uses must be greater than or equal to 0");
            this.uses = uses;
        }

        public boolean hasExpired() {
            if (this.expiration == 0L) return false;
            return System.currentTimeMillis() >= this.expiration;
        }

        public long getExpiration() {
            return this.expiration;
        }

        public long getCreatedAt() {
            return createdAt;
        }

        public String getCode() {
            return code;
        }
    }

}
