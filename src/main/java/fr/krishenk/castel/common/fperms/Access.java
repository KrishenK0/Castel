package fr.krishenk.castel.common.fperms;

public enum Access {
    ALLOW("Allow"),
    DENY("Deny"),
    UNDEFINED("Undefined");

    private final String name;

    Access(String name) {
        this.name = name;
    }

    public static Access fromString(String check) {
        Access[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            Access access = var1[var3];
            if (access.name().equalsIgnoreCase(check)) {
                return access;
            }
        }

        return null;
    }

    public static Access booleanToAccess(boolean access) {
        return access ? ALLOW : DENY;
    }

    public String getName() {
        return this.name.toLowerCase();
    }

    public String toString() {
        return this.name();
    }
}