package top.xcyyds.chineserpg.registry;

import top.xcyyds.chineserpg.martialart.MartialArt;
import top.xcyyds.chineserpg.martialart.MartialArts;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MartialArtRegistry {
    private static final Map<UUID, MartialArt> registry = new HashMap<>();

    public static void registerMartialArt(MartialArt martialArt) {
        registry.put(martialArt.getUuid(), martialArt);
    }

    public static MartialArt getMartialArt(UUID uuid) {
        return registry.get(uuid);
    }

    public static void initializeRegistry() {
        // Register all predefined martial arts
        MartialArts.registerAll();
    }
}
