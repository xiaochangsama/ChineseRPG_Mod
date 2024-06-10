package top.xcyyds.chineserpg.martialart;

import top.xcyyds.chineserpg.martialart.skill.MartialArt;
import top.xcyyds.chineserpg.registry.MartialArtRegistry;

import java.util.List;

public class MartialArts {

    public static void registerAll() {
        List<MartialArt> martialArts = MartialArtLoader.loadMartialArts();
        for (MartialArt martialArt : martialArts) {
            MartialArtRegistry.registerMartialArt(martialArt);
        }
    }

    public static MartialArt getRandomLightSkill() {
        return MartialArtRegistry.getRandomMartialArt();
    }
}
