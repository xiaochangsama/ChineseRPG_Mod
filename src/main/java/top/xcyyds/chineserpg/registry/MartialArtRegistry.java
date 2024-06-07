package top.xcyyds.chineserpg.registry;

import net.minecraft.server.world.ServerWorld;
import top.xcyyds.chineserpg.martialart.MartialArt;
import top.xcyyds.chineserpg.martialart.MartialArts;

import java.util.Collection;
import java.util.Random;
import java.util.UUID;

public class MartialArtRegistry {
    private static MartialArtRegistryData registryData;

    public static void registerMartialArt(MartialArt martialArt) {
        if (registryData != null) {
            registryData.registerMartialArt(martialArt);
        }
    }

    public static MartialArt getMartialArt(UUID uuid) {
        if (registryData != null) {
            return registryData.getMartialArt(uuid);
        }
        return null;
    }

    public static Collection<MartialArt> getAllMartialArts() {
        if (registryData != null) {
            return registryData.getAllMartialArts();
        }
        return null;
    }

    public static MartialArt getRandomMartialArt() {
        Collection<MartialArt> martialArts = getAllMartialArts();
        if (martialArts != null && !martialArts.isEmpty()) {
            int index = new Random().nextInt(martialArts.size());
            return martialArts.toArray(new MartialArt[0])[index];
        }
        return null;
    }

    public static void initializeRegistry(ServerWorld world) {
        registryData = MartialArtRegistryData.get(world);
        MartialArts.registerAll();
    }
}
