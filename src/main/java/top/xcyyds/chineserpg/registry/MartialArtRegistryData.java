package top.xcyyds.chineserpg.registry;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.PersistentState;
import top.xcyyds.chineserpg.martialart.MartialArt;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MartialArtRegistryData extends PersistentState {
    private static final String DATA_NAME = "chineserpg_martialart_registry";
    private final Map<UUID, MartialArt> registry = new HashMap<>();

    public MartialArtRegistryData() {
    }

    public static MartialArtRegistryData get(ServerWorld world) {
        return world.getPersistentStateManager().getOrCreate(MartialArtRegistryData::fromNbt, MartialArtRegistryData::new, DATA_NAME);
    }

    public static MartialArtRegistryData fromNbt(NbtCompound nbt) {
        MartialArtRegistryData data = new MartialArtRegistryData();
        NbtCompound martialArtsNbt = nbt.getCompound("MartialArts");
        for (String key : martialArtsNbt.getKeys()) {
            UUID uuid = UUID.fromString(key);
            MartialArt martialArt = MartialArt.readFromNbt(martialArtsNbt.getCompound(key));
            data.registry.put(uuid, martialArt);
        }
        return data;
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        NbtCompound martialArtsNbt = new NbtCompound();
        for (Map.Entry<UUID, MartialArt> entry : registry.entrySet()) {
            NbtCompound martialArtNbt = new NbtCompound();
            entry.getValue().writeToNbt(martialArtNbt);
            martialArtsNbt.put(entry.getKey().toString(), martialArtNbt);
        }
        nbt.put("MartialArts", martialArtsNbt);
        return nbt;
    }

    public void registerMartialArt(MartialArt martialArt) {
        registry.put(martialArt.getUuid(), martialArt);
        markDirty();
    }

    public MartialArt getMartialArt(UUID uuid) {
        return registry.get(uuid);
    }

    public Collection<MartialArt> getAllMartialArts() {
        return registry.values();
    }
}
