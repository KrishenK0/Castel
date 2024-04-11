package fr.krishenk.castel.common.constants.group;

import fr.krishenk.castel.common.fperms.Rank;

import java.awt.*;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public abstract class Group {
    protected transient UUID id;
    protected UUID leader;
    protected String leaderName;
    protected String name;
    protected String tag;
    protected long since;
    protected List<String> membersOnline;
    protected List<String> membersOffline;
    protected List<Rank> ranks;
//    protected Location home;
    protected double publicHomeCost;
    protected boolean publicHome;
    private Color color;
    protected double bank;
    protected String tax;
    private String flag;
//    protected Map<UUID, GuildRelationshipRequest> relationshipRequests;
//    protected Map<UUID, GuildRelation> relations;
//    protected Map<GuildRelation, Set<RelationAttribute>> attributes;
    protected long resourcePoints;
    protected boolean requiresInvite;
    protected boolean permanent;
    protected boolean hidden;
    private Set<UUID> mails;
    //private final LinkedList<AuditLog> logs;


    public Group(UUID id, UUID leader, String leaderName, String name, String tag, long since, List<String> membersOnline, List<String> membersOffline, List<Rank> ranks, double publicHomeCost, boolean publicHome, Color color, double bank, String tax, String flag, long resourcePoints, boolean requiresInvite, boolean permanent, boolean hidden, Set<UUID> mails) {
        this.id = id;
        this.leader = leader;
        this.leaderName = leaderName;
        this.name = name;
        this.tag = tag;
        this.since = since;
        this.membersOnline = membersOnline;
        this.membersOffline = membersOffline;
        this.ranks = ranks;
        this.publicHomeCost = publicHomeCost;
        this.publicHome = publicHome;
        this.color = color;
        this.bank = bank;
        this.tax = tax;
        this.flag = flag;
        this.resourcePoints = resourcePoints;
        this.requiresInvite = requiresInvite;
        this.permanent = permanent;
        this.hidden = hidden;
        this.mails = mails;
    }

    public UUID getId() {
        return id;
    }

    public UUID getLeader() {
        return leader;
    }

    public String getLeaderName() {
        return leaderName;
    }

    public void setLeaderName(String leaderName) {
        this.leaderName = leaderName;
    }

    public String getName() {
        return name;
    }

    public String getTag() {
        return tag;
    }

    public long getSince() {
        return since;
    }

    public List<String> getMembersOnline() {
        return membersOnline;
    }

    public List<String> getMembersOffline() {
        return membersOffline;
    }

    public List<Rank> getRanks() {
        return ranks;
    }

    public double getPublicHomeCost() {
        return publicHomeCost;
    }

    public boolean isPublicHome() {
        return publicHome;
    }

    public Color getColor() {
        return color;
    }

    public double getBank() {
        return bank;
    }

    public String getTax() {
        return tax;
    }

    public String getFlag() {
        return flag;
    }

    public long getResourcePoints() {
        return resourcePoints;
    }

    public boolean isRequiresInvite() {
        return requiresInvite;
    }

    public boolean isPermanent() {
        return permanent;
    }

    public boolean isHidden() {
        return hidden;
    }

    public Set<UUID> getMails() {
        return mails;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setLeader(UUID leader) {
        this.leader = leader;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setSince(long since) {
        this.since = since;
    }

    public void setMembersOnline(List<String> membersOnline) {
        this.membersOnline = membersOnline;
    }

    public void setMembersOffline(List<String> membersOffline) {
        this.membersOffline = membersOffline;
    }

    public void setRanks(List<Rank> ranks) {
        this.ranks = ranks;
    }

    public void setPublicHomeCost(double publicHomeCost) {
        this.publicHomeCost = publicHomeCost;
    }

    public void setPublicHome(boolean publicHome) {
        this.publicHome = publicHome;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setBank(double bank) {
        this.bank = bank;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public void setResourcePoints(long resourcePoints) {
        this.resourcePoints = resourcePoints;
    }

    public void setRequiresInvite(boolean requiresInvite) {
        this.requiresInvite = requiresInvite;
    }

    public void setPermanent(boolean permanent) {
        this.permanent = permanent;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public void setMails(Set<UUID> mails) {
        this.mails = mails;
    }
}