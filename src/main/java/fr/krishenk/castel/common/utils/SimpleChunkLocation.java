package fr.krishenk.castel.common.utils;

import java.util.Objects;

public class SimpleChunkLocation implements Cloneable {
    private String world;
    private int x;
    private int z;

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

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public WorldlessWrapper worldlessWrapper() {
        return new WorldlessWrapper(this.x, this.z);
    }

    public int hashCode() {
        int prime = 31;
        int result = 19;
        result = prime * result + this.world.hashCode();
        result = prime * result + this.x;
        result = prime * result + this.z;
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof SimpleChunkLocation) {
            SimpleChunkLocation chunk = (SimpleChunkLocation)obj;
            return this.x == chunk.x && this.z == chunk.z && Objects.equals(this.world, chunk.world);
        }
        return false;
    }

    public String toString() {
        return '{'+this.world + ", " + this.x + ", " + this.z+'}';
    }

    public static class WorldlessWrapper {
        private final int x;
        private final int z;

        public WorldlessWrapper(int x, int z) {
            this.x = x;
            this.z = z;
        }
        public WorldlessWrapper getRelative(int x, int z) {
            return new WorldlessWrapper(this.x + x, this.z + z);
        }

        public int getX() {
            return this.x;
        }

        public int getZ() {
            return this.z;
        }

        public int hashCode() {
            int var2 = 1664525 * this.x + 1013904223;
            int var3 = 1664525 * (this.z ^ 0xDEADBEEF) + 1013904223;
            return var2 ^ var3;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof WorldlessWrapper)) {
                return false;
            }
            WorldlessWrapper location = (WorldlessWrapper)obj;
            return this.x == location.x && this.z == location.z;
        }
    }
}


