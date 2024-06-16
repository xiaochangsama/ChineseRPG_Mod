package top.xcyyds.chineserpg.martialart.artentry;

import com.google.gson.annotations.SerializedName;
import net.minecraft.nbt.NbtCompound;

/**
 * OuterSkillEntry 代表特定类型的外功武术技能。
 * 该类扩展自 MartialArtEntry，包含特定的外功技能属性。
 */
public class OuterSkillEntry extends MartialArtEntry {
    @SerializedName("damage")
    private float damage; // 伤害值

    @SerializedName("cooldown")
    private int cooldown; // 冷却时间

    @SerializedName("range")
    private float range; // 攻击范围

    // 伤害判定和时间控制
    @SerializedName("damageControl")
    private DamageControl damageControl; // 伤害控制

    // 特殊状态下的伤害倍率
    @SerializedName("maxInnerPowerMultiplier")
    private float maxInnerPowerMultiplier; // 内力最大值加成

    @SerializedName("currentInnerPowerMultiplier")
    private float currentInnerPowerMultiplier; // 当前内力加成

    @SerializedName("manualPowerMultiplier")
    private float manualPowerMultiplier; // 手动加力加成

    // 内力消耗
    @SerializedName("maxInnerPowerConsumption")
    private float maxInnerPowerConsumption; // 最大消耗内力量

    @SerializedName("minInnerPowerConsumption")
    private float minInnerPowerConsumption; // 最小消耗内力量

    @SerializedName("outerType")
    private String outerType; // 外功类型（剑法、拳法、枪法等）

    @SerializedName("releaseMethod")
    private String releaseMethod; // 释放方式（RL、LL、RLR、RRLL等）

    @SerializedName("animationName")
    private String animationName; // 动画名称

    /**
     * 构造一个新的 OuterSkillEntry 对象，并指定各属性的参数。
     *
     * @param name                      技能词条的名称
     * @param level                     技能的等级
     * @param damage                    技能的伤害值
     * @param cooldown                  技能的冷却时间
     * @param range                     技能的攻击范围
     * @param damageControl             伤害控制对象
     * @param maxInnerPowerMultiplier   内力最大值加成
     * @param currentInnerPowerMultiplier 当前内力加成
     * @param manualPowerMultiplier     手动加力加成
     * @param maxInnerPowerConsumption  最大消耗内力量
     * @param minInnerPowerConsumption  最小消耗内力量
     * @param outerType                 外功类型
     * @param releaseMethod             释放方式
     * @param animationName             动画名称
     */
    public OuterSkillEntry(String name, int level, float damage, int cooldown, float range, DamageControl damageControl,
                           float maxInnerPowerMultiplier, float currentInnerPowerMultiplier, float manualPowerMultiplier,
                           float maxInnerPowerConsumption, float minInnerPowerConsumption, String outerType,
                           String releaseMethod, String animationName) {
        super(name, level);
        this.damage = damage;
        this.cooldown = cooldown;
        this.range = range;
        this.damageControl = damageControl != null ? damageControl : new DamageControl("default", new float[3], 0.0f, new float[3], new float[3], 0.0f, 0, 0, 0);
        this.maxInnerPowerMultiplier = maxInnerPowerMultiplier;
        this.currentInnerPowerMultiplier = currentInnerPowerMultiplier;
        this.manualPowerMultiplier = manualPowerMultiplier;
        this.maxInnerPowerConsumption = maxInnerPowerConsumption;
        this.minInnerPowerConsumption = minInnerPowerConsumption;
        this.outerType = outerType;
        this.releaseMethod = releaseMethod;
        this.animationName = animationName;
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

    public DamageControl getDamageControl() {
        return damageControl;
    }

    public float getMaxInnerPowerMultiplier() {
        return maxInnerPowerMultiplier;
    }

    public float getCurrentInnerPowerMultiplier() {
        return currentInnerPowerMultiplier;
    }

    public float getManualPowerMultiplier() {
        return manualPowerMultiplier;
    }

    public float getMaxInnerPowerConsumption() {
        return maxInnerPowerConsumption;
    }

    public float getMinInnerPowerConsumption() {
        return minInnerPowerConsumption;
    }

    public String getOuterType() {
        return outerType;
    }

    public String getReleaseMethod() {
        return releaseMethod;
    }

    public String getAnimationName() {
        return animationName;
    }

    @Override
    public void writeToNbt(NbtCompound nbt) {
        nbt.putString("Name", getName());
        nbt.putInt("Level", getLevel());
        nbt.putString("Type", "OuterSkillEntry");
        nbt.putFloat("Damage", damage);
        nbt.putInt("Cooldown", cooldown);
        nbt.putFloat("Range", range);
        NbtCompound damageControlNbt = new NbtCompound();
        damageControl.writeToNbt(damageControlNbt);
        nbt.put("DamageControl", damageControlNbt);
        nbt.putFloat("MaxInnerPowerMultiplier", maxInnerPowerMultiplier);
        nbt.putFloat("CurrentInnerPowerMultiplier", currentInnerPowerMultiplier);
        nbt.putFloat("ManualPowerMultiplier", manualPowerMultiplier);
        nbt.putFloat("MaxInnerPowerConsumption", maxInnerPowerConsumption);
        nbt.putFloat("MinInnerPowerConsumption", minInnerPowerConsumption);
        nbt.putString("OuterType", outerType);
        nbt.putString("ReleaseMethod", releaseMethod);
        nbt.putString("AnimationName", animationName);
    }

    public static OuterSkillEntry readFromNbt(NbtCompound nbt) {
        String name = nbt.getString("Name");
        int level = nbt.getInt("Level");
        float damage = nbt.getFloat("Damage");
        int cooldown = nbt.getInt("Cooldown");
        float range = nbt.getFloat("Range");
        DamageControl damageControl = DamageControl.readFromNbt(nbt.getCompound("DamageControl"));
        float maxInnerPowerMultiplier = nbt.getFloat("MaxInnerPowerMultiplier");
        float currentInnerPowerMultiplier = nbt.getFloat("CurrentInnerPowerMultiplier");
        float manualPowerMultiplier = nbt.getFloat("ManualPowerMultiplier");
        float maxInnerPowerConsumption = nbt.getFloat("MaxInnerPowerConsumption");
        float minInnerPowerConsumption = nbt.getFloat("MinInnerPowerConsumption");
        String outerType = nbt.getString("OuterType");
        String releaseMethod = nbt.getString("ReleaseMethod");
        String animationName = nbt.getString("AnimationName");

        return new OuterSkillEntry(name, level, damage, cooldown, range, damageControl, maxInnerPowerMultiplier,
                currentInnerPowerMultiplier, manualPowerMultiplier, maxInnerPowerConsumption,
                minInnerPowerConsumption, outerType, releaseMethod, animationName);
    }
}
