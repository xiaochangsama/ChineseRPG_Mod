package top.xcyyds.chineserpg.player;

import net.minecraft.nbt.NbtCompound;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerDataStorage {
    private static final Map<UUID, NbtCompound> dataMap = new HashMap<>();

    public static void set(UUID uuid, NbtCompound nbt) {
        dataMap.put(uuid, nbt);
    }

    public static NbtCompound get(UUID uuid) {
        return dataMap.get(uuid);
    }

    public static void remove(UUID uuid) {
        dataMap.remove(uuid);
    }
}
