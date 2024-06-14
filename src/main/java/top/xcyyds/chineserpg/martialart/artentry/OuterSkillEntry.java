package top.xcyyds.chineserpg.martialart.artentry;

import com.google.gson.annotations.SerializedName;
import net.minecraft.nbt.NbtCompound;

/**
 * OuterSkillEntry represents a specific type of martial art skill related to "outer" or offensive abilities.
 * This class extends MartialArtEntry and includes additional properties specific to outer skills.
 */
public class OuterSkillEntry extends MartialArtEntry {
    @SerializedName("damage")
    private float damage; // 伤害值

    @SerializedName("cooldown")
    private int cooldown; // 冷却时间

    @SerializedName("range")
    private float range; // 攻击范围

    /**
     * Constructs a new OuterSkillEntry with the specified parameters.
     *
     * @param name      The name of the skill entry. 技能词条的名称
     * @param level     The level of the skill. 技能的等级
     * @param damage    The damage of the skill. 技能的伤害值
     * @param cooldown  The cooldown time of the skill. 技能的冷却时间
     * @param range     The range of the skill. 技能的攻击范围
     */
    public OuterSkillEntry(String name, int level, float damage, int cooldown, float range) {
        super(name, level);
        this.damage = damage;
        this.cooldown = cooldown;
        this.range = range;
    }

    public float getDamage() {
        return damage;
    }

    public int getCooldown() {
        return cooldown;
    }

    public float getRange() {
        return range;
    }

    @Override
    public void writeToNbt(NbtCompound nbt) {
        nbt.putString("Name", getName());
        nbt.putInt("Level", getLevel());
        nbt.putString("Type", "OuterSkillEntry");
        nbt.putFloat("Damage", damage);
        nbt.putInt("Cooldown", cooldown);
        nbt.putFloat("Range", range);
    }
}
