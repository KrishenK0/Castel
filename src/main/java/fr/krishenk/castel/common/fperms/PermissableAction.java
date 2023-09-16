package fr.krishenk.castel.common.fperms;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum PermissableAction {
    BUILD("build", "Grants users access to build."),
    DESTROY("destroy", "Grant users access to destroy."),
    CONTAINER("container", "Grant users access to use containers."),
    LEVER("lever", "Grant users access to use levers."),
    BUTTON("button", "Grant users access to use buttons."),
    DOOR("door", "Grant users access to use doors."),
    ITEM("items", "Grant users access to use most generic items."),
    FROST_WALK("frostwalk", "Grant users to use FrostWalker enchantment."),
    PAIN_BUILD("painbuild", "Users denied building damage themselves."),
    INVITE("invite", "Grant users access to use /f invite."),
    KICK("kick", "Grant users access to use /f kick."),
    BAN("ban", "Grant users access to use /f ban."),
    PROMOTE("promote", "Grant users access to use /f <promote/demote>."),
    CHEST("chest", "Grant users access to use /f chest."),
    VAULT("vault","Grant users access to use /f vault."),
    SETHOME("sethome", "Grant users access to use /f sethome."),
    HOME("home", "Grant users access to use /f home."),
    WITHDRAW("withdraw", "Grant users access to use /f withdraw."),
    SETWARP("setwarp","Grant users access to use /f setwarp."),
    WARP("warp", "Grant users access to use /f warp."),
    TERRITORY("territory", "Grant users access to manage claims."),
    DISBAND("disband", "Grant users access to use /f disband."),
    FLY("fly","Grant users access to use /f fly."),
    //DRAIN("drain", "Grant users access to use /f drain."),
    //ACCESS("access"),
    //TNTBANK("tntbank", "Grant users access to manage /f tnt virtual bank."),
    //TNTFILL("tntfill", "Grant users access to use /f tntfill."),
    SPAWNER("spawner", "Grant users access to manage spawners."),
    CHECK("check", "Grant users access to manage /f check system.");

    private final String name;
    private final String description;

    PermissableAction(String name) {
        this(name, "");
    }

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