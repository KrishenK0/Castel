package fr.krishenk.castel.common.constants.group.models;

public class BankLog implements Comparable<BankLog> {
    private String player;
    private long time;
    private double amount;

    public BankLog(String player, long time, double amount) {
        this.player = player;
        this.time = time;
        this.amount = amount;
    }

    public String getPlayer() {
        return player;
    }

    public long getTime() {
        return time;
    }

    public double getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "BankLog{" +
                "playerName='" + player + '\'' +
                ", time=" + time +
                ", amount=" + amount +
                '}';
    }

    @Override
    public int compareTo(BankLog o) {
        return (int) (this.getTime()-o.getTime());
    }
}
