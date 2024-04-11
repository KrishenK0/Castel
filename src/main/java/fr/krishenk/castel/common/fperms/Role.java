package fr.krishenk.castel.common.fperms;
public enum Role implements Permissable {
    LEADER(8, 1,"leader"),
    COLEADER(7, 1,"coleader"),
    MODERATOR(6,1, "moderator"),
    NORMAL(5,1, "normal member"),
    RECRUIT(4,1, "recruit"),
    ALLY(3,0, "ally"),
    TRUCE(2,0, "truce"),
    NEUTRAL(1,0, "neutral"),
    ENEMY(0,0, "enemy");

    public final int value;
    public final String nicename;
    public final int relation;

    private Role(int value, int relation, String name) {
        this.value = value;
        this.relation = relation;
        this.nicename = name;
    }

    public static Role getRelative(Role role, int relative) {
        return getByValue(role.value + relative);
    }

    public static Role getByValue(int value) {
        switch (value) {
            case 0:
                return RECRUIT;
            case 1:
                return NORMAL;
            case 2:
                return MODERATOR;
            case 3:
                return COLEADER;
            case 4:
                return LEADER;
            default:
                return null;
        }
    }

    public static Role fromString(String check) {
        switch (check.toLowerCase()) {
            case "leader":
            case "admin":
                return LEADER;
            case "coleader":
                return COLEADER;
            case "mod":
            case "moderator":
                return MODERATOR;
            case "normal":
            case "member":
                return NORMAL;
            case "recruit":
            case "rec":
                return RECRUIT;
            default:
                return null;
        }
    }

    public boolean isAtLeast(Role role) {
        return this.value >= role.value;
    }

    public boolean isAtMost(Role role) {
        return this.value <= role.value;
    }

    public String toString() {
        return this.nicename;
    }

    public String getRoleCapitalized() {
        return this.nicename.replace(Character.toString(this.nicename.charAt(0)), Character.toString(this.nicename.charAt(0)).toUpperCase());
    }
}
