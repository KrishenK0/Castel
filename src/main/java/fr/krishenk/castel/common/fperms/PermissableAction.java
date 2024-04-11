package fr.krishenk.castel.common.fperms;

public enum PermissableAction {
//    BUILD("build", "Grants users access to build."),
//    DESTROY("destroy", "Grant users access to destroy."),
//    CONTAINER("container", "Grant users access to use containers."),
//    LEVER("lever", "Grant users access to use levers."),
//    BUTTON("button", "Grant users access to use buttons."),
//    DOOR("door", "Grant users access to use doors."),
//    ITEM("items", "Grant users access to use most generic items."),
//    FROST_WALK("frostwalk", "Grant users to use FrostWalker enchantment."),
//    PAIN_BUILD("painbuild", "Users denied building damage themselves."),
//    INVITE("invite", "Grant users access to use /f invite."),
//    KICK("kick", "Grant users access to use /f kick."),
//    BAN("ban", "Grant users access to use /f ban."),
//    PROMOTE("promote", "Grant users access to use /f <promote/demote>."),
//    CHEST("chest", "Grant users access to use /f chest."),
//    VAULT("vault","Grant users access to use /f vault."),
//    SETHOME("set_home", "Grant users access to use /f sethome."),
//    HOME("home", "Grant users access to use /f home."),
//    WITHDRAW("withdraw", "Grant users access to use /f withdraw."),
//    SETWARP("setwarp","Grant users access to use /f setwarp."),
//    WARP("warp", "Grant users access to use /f warp."),
//    TERRITORY("territory", "Grant users access to manage claims."),
//    DISBAND("disband", "Grant users access to use /f disband."),
//    FLY("fly","Grant users access to use /f fly."),
//    SPAWNER("spawner", "Grant users access to manage spawners."),
//    CHECK("check", "Grant users access to manage /f check system.");


    /**
     * NEW PERMISSIONS
     */
//     BROADCAST("BROADCAST", ""),
     BUILD("BUILD", "Grants users access to build."),
     DESTROY("DESTROY", ""),

//     BUILD_OWNED("BUILD_OWNED", "Grants users access to build on member lands."),
     INTERACT("INTERACT", "Grant users access to interact on claimed lands."),
     USE("USE", "Grant users access to use on claimed lands."),
     INVITE("INVITE", "Grant users access to use /c invite"),
     KICK("KICK", "Grant users access to use /c kick."),
     MANAGE_RANKS("MANAGE_RANKS", "Grant users access to manage members rank."),
     EDIT_RANKS("EDIT_RANKS", ""),
//     UNCLAIM_OWNED("UNCLAIM_OWNED", "Grant users access to use /c unclaim on member lands."),
     VAULT("VAULT", ""), // not in plugin
     WITHDRAW("WITHDRAW", "Grant users access to use /c withdraw."),
     EXCLUDE_TAX("EXCLUDE_TAX", "Grant users access to avoid guild taxes."),
     ENEMY("ENEMY", "Grant users access to use /c enemy"),
     TRUCE("TRUCE", "Grant users access to use /c truce"),
     ALLIANCE("ALLIANCE", "Grant users access to use /c ally"),
     SET_HOME("SET_HOME", "Grant users access to use /c sethome."),
     HOME("HOME", "Grant users access to use /c home."),
     CLAIM("CLAIM", "Grant users access to use /c claim."),
     UNCLAIM("UNCLAIM", "Grant users access to use /c unclaim."),
//     LORE("LORE", "Grant users access to use /c lore."),
     INSTANT_TELEPORT("INSTANT_TELEPORT", "Grant users access to instant teleport to a player"),
     FLY("FLY", "Grant users access to use /c fly."),
     MANAGE_MAILS("MANAGE_MAILS", "Grant users access to manage mails"),
     PROTECTION_SIGNS("PROTECTION_SIGNS", ""),
     SETTINGS("SETTINGS", "");
//     RELATION_ATTRIBUTES("RELATION_ATTRIBUTES", ""),
//     UPGRADE("UPGRADE", ""),
//     INVSEE("INVSEE", ""),
//     READ_MAILS("READ_MAILS", ""),
//     VIEW_LOGS("VIEW_LOGS", "");

    private final String name;
    private final String description;

    PermissableAction(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public static PermissableAction fromString(String check) {
        PermissableAction[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            PermissableAction permissableAction = var1[var3];
            if (permissableAction.name().equalsIgnoreCase(check)) {
                return permissableAction;
            }
        }

        return null;
    }

    public String getName() {
        return this.name;
    }

    public String toString() {
        return this.name;
    }

    public String getDescription() {
        return description;
    }
}