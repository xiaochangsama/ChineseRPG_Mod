package top.xcyyds.chineserpg.martialart.skill;

import java.util.List;
import java.util.UUID;

public class OuterSkill extends MartialArt {
    public OuterSkill(String name, int level, float completeness, List<String> description, String author, UUID uuid) {
        super(name, "外功", level, completeness, description, author, uuid);
    }

    public OuterSkill(String name, int level, float completeness, List<String> description, String author) {
        super(name, "外功", level, completeness, description, author);
    }
}
