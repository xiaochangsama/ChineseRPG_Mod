package top.xcyyds.chineserpg.martialart.skill;

import java.util.List;
import java.util.UUID;

public class LightSkill extends MartialArt {
    public LightSkill(String name, int level, float completeness, List<String> description, String author, UUID uuid) {
        super(name, "轻功", level, completeness, description, author, uuid);
    }

    public LightSkill(String name, int level, float completeness, List<String> description, String author) {
        super(name, "轻功", level, completeness, description, author);
    }
}
