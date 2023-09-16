package fr.krishenk.castel;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.minecraft.entity.player.PlayerEntity;

import java.util.Map;

public class PlayerInfo {

    private String role;
    private FactionInfo factionInfo;

    public PlayerInfo() {
        this.factionInfo = FactionInfo.getInstance();
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
