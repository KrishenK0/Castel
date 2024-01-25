package fr.krishenk.castel.common.fperms;

import java.util.List;

public class Rank {
    private String material;
    private String name;
    private String color;
    private String symbol;
    private int priority;
    private int maxClaims;
    private List<Permission> permissions;

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getMaxClaims() {
        return maxClaims;
    }

    public void setMaxClaims(int maxClaims) {
        this.maxClaims = maxClaims;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    public static class Permission {
        private String deniedMessage;
        private int hash;
        private Namespace namespace;

        public String getDeniedMessage() {
            return deniedMessage;
        }

        public int getHash() {
            return hash;
        }

        public Namespace getNamespace() {
            return namespace;
        }

        @Override
        public String toString() {
            return "Permission[" +namespace +']';
        }
    }

    public static class Namespace {
        private String namespace;
        private String key;

        public String getNamespace() {
            return namespace;
        }

        public String getKey() {
            return key;
        }

        @Override
        public String toString() {
            return namespace+':'+key;
        }
    }
}