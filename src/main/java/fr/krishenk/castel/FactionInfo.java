package fr.krishenk.castel;

import fr.krishenk.castel.common.fperms.Rank;

import java.util.*;

public class FactionInfo {

    private static FactionInfo INSTANCE;
    private String title = "";
    private List<String> playerOnline = new ArrayList<>();
    private List<String> playerOffline = new ArrayList<>();
    private int power = 0;
    private int powerMax = 99;
    private String leaderName = "System";
    private String leaderId;
    private List<Rank> ranks = new ArrayList<>();


    public FactionInfo() {
        INSTANCE = this;
    }

    public void setPlayerOffline(List<String> playerOffline) {
        this.playerOffline = playerOffline;
    }

    public void setPlayerOnline(List<String> playerOnline) {
        this.playerOnline = playerOnline;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getPlayerOffline() {
        return playerOffline;
    }

    public List<String> getPlayerOnline() {
        return playerOnline;
    }

    public String getTitle() {
        return title;
    }

    public static FactionInfo getInstance() { return INSTANCE; }

    public int getPower() {
        return power;
    }

    public int getPowerMax() {
        return powerMax;
    }

    public String getLeaderName() {
        return leaderName;
    }

    public String getLeaderId() { return leaderId; }

    public void setPower(int power) { this.power = power; }

    public void setPowerMax(int powerMax) { this.powerMax = powerMax; }

    public void setLeaderName(String leaderName) { this.leaderName = leaderName; }

    public void setLeaderId(String leaderId) { this.leaderId = leaderId; }

    public List<Rank> getRanks() { return ranks; }

    public FactionInfo setRanks(List<Rank> ranks) {
        Comparator<Rank> rankComparator = Comparator.comparingInt(Rank::getPriority);
        Collections.sort(ranks, rankComparator);
        this.ranks = ranks;
        return this;
    }
}
