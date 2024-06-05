package top.xcyyds.chineserpg.registry;

import net.minecraft.server.world.ServerWorld;
import top.xcyyds.chineserpg.martialart.MartialArt;
import top.xcyyds.chineserpg.martialart.MartialArts;

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

    public static void initializeRegistry(ServerWorld world) {
        registryData = MartialArtRegistryData.get(world);
        MartialArts.registerAll();
    }
}
