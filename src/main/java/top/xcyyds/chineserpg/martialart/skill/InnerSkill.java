package top.xcyyds.chineserpg.martialart.skill;

import java.util.List;
import java.util.UUID;

public class InnerSkill extends MartialArt {
    public InnerSkill(String name, int level, float completeness, List<String> description, String author, UUID uuid) {
        super(name, "内功", level, completeness, description, author, uuid);
    }

    public InnerSkill(String name, int level, float completeness, List<String> description, String author) {
        super(name, "内功", level, completeness, description, author);
    }
}
