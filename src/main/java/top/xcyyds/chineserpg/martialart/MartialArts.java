package top.xcyyds.chineserpg.martialart;

import top.xcyyds.chineserpg.martialart.skill.MartialArt;
import top.xcyyds.chineserpg.registry.MartialArtRegistry;

import java.util.List;
import java.util.stream.Collectors;

public class MartialArts {

    public static void registerAll() {
        List<MartialArt> martialArts = MartialArtLoader.loadMartialArts();
        for (MartialArt martialArt : martialArts) {
            MartialArtRegistry.registerMartialArt(martialArt);
        }
    }

    public static MartialArt getRandomLightSkill() {
        List<MartialArt> lightSkills = MartialArtRegistry.getMartialArts().stream()
                .filter(martialArt -> "轻功".equals(martialArt.getType()))
                .collect(Collectors.toList());
        return lightSkills.isEmpty() ? null : lightSkills.get((int) (Math.random() * lightSkills.size()));
    }

    public static MartialArt getRandomOuterSkill() {
        List<MartialArt> outerSkills = MartialArtRegistry.getMartialArts().stream()
                .filter(martialArt -> "外功".equals(martialArt.getType()))
                .collect(Collectors.toList());
        return outerSkills.isEmpty() ? null : outerSkills.get((int) (Math.random() * outerSkills.size()));
    }
}
