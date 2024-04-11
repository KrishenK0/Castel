package fr.krishenk.castel.common.utils;

import java.util.Objects;

public class SimpleLocation implements Cloneable {
    private String world;
    private int x;
    private int y;
    private int z;

    private static double square(int num) {
        return (double)num * (double)num;
    }

    public String getWorld() {
        return world;
    }

    public void setWorld(String world) {
        this.world = world;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public int hashCode() {
        int prime = 31;
        int result = 14;
        result = prime * result + this.world.hashCode();
        result = prime * result + this.x;
        result = prime * result + this.y;
        result = prime * result + this.z;
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof SimpleLocation) {
            SimpleLocation loc = (SimpleLocation)obj;
            return this.x == loc.x && this.y == loc.y && this.z == loc.z && Objects.equals(this.world, loc.world);
        }
        return false;
    }

    public String toString() {
        return this.world + ", " + this.x + ", " + this.y + ", " + this.z;
    }

    public double distance(SimpleLocation location) {
        Objects.requireNonNull(location, "Cannot check distance between a null location");
        if (!Objects.equals(this.world, location.world)) {
            throw new IllegalArgumentException("Cannot measure distance between " + this.world + " and " + location.world);
        }
        return this.distanceIgnoreWorld(location);
    }

    public double distanceIgnoreWorld(SimpleLocation location) {
        Objects.requireNonNull(location, "Cannot check distance between a null location");
        return SimpleLocation.square(this.x - location.x) + SimpleLocation.square(this.y - location.y) + SimpleLocation.square(this.z - location.z);
    }

    public double distanceSquaredIgnoreWorld(SimpleLocation location) {
        Objects.requireNonNull(location, "Cannot check distance between a null location");
        return Math.sqrt(this.distanceIgnoreWorld(location));
    }

    public WorldlessWrapper toWorldLessWrapper() {
        return new WorldlessWrapper(this.x, this.y, this.z);
    }

    public static class WorldlessWrapper {
        private final int x;
        private final int y;
        private final int z;

        public WorldlessWrapper(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public int getX() {
            return this.x;
        }

        public int getY() {
            return this.y;
        }

        public int getZ() {
            return this.z;
        }

        public int hashCode() {
            return this.x + this.z;
        }

        public String toString() {
            return "WorldlessSimpleLocation{" + this.x + ", " + this.y + ", " + this.z + '}';
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof WorldlessWrapper)) {
                return false;
            }
            WorldlessWrapper location = (WorldlessWrapper)obj;
            return this.x == location.x && this.y == location.y && this.z == location.z;
        }
    }
}
