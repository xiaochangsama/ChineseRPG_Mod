package top.xcyyds.chineserpg.martialart.artentry;

import com.google.gson.annotations.SerializedName;
import net.minecraft.nbt.NbtCompound;

/**
 * LightSkillEntry represents a specific type of martial art skill related to "light" or agility-based abilities.
 * This class extends MartialArtEntry and includes additional properties specific to light skills.
 * LightSkillEntry 表示与“轻功”或基于敏捷的技能相关的特定类型的武术技能。
 * 该类继承自 MartialArtEntry 并包括轻功特有的额外属性。
 */
public class LightSkillEntry extends MartialArtEntry {
    @SerializedName("jumpType")
    private String jumpType = ""; // The type of jump (e.g., "multi-jump", "dash", etc.) 跳跃类型 (例如，“多段跳”、“冲刺”等)

    @SerializedName("jumpCount")
    private int jumpCount = 0; // The number of jumps allowed 允许的跳跃次数

    @SerializedName("innerPowerConsumption")
    private float innerPowerConsumption = 0; // The amount of inner power consumed 消耗的内力量

    @SerializedName("velocityYIncrease")
    private double velocityYIncrease = 0; // The vertical velocity increase for the jump 跳跃的垂直速度增加量

    @SerializedName("particleCount")
    private int particleCount = 0; // The number of particles generated during the jump 跳跃期间生成的粒子数量

    @SerializedName("damageReductionHeight")
    private float damageReductionHeight = 0; // The height at which damage reduction starts 开始减伤的高度

    @SerializedName("damageReductionPercentage")
    private float damageReductionPercentage = 0; // The percentage of damage reduction 减伤百分比

    @SerializedName("dodgeRate")
    private float dodgeRate = 0; // The dodge rate increase 闪避率增加

    @SerializedName("directionalVelocity")
    private double directionalVelocity = 0; // The directional velocity for the player 玩家朝向的加速度

    /**
     * Constructs a new LightSkillEntry with the specified parameters.
     * 使用指定参数构造一个新的 LightSkillEntry。
     *
     * @param name                    The name of the skill entry. 技能词条的名称
     * @param level                   The level of the skill. 技能的等级
     * @param jumpType                The type of jump. 跳跃类型
     * @param jumpCount               The number of jumps allowed. 允许的跳跃次数
     * @param innerPowerConsumption   The amount of inner power consumed. 消耗的内力量
     * @param velocityYIncrease       The vertical velocity increase for the jump. 跳跃的垂直速度增加量
     * @param particleCount           The number of particles generated during the jump. 跳跃期间生成的粒子数量
     * @param damageReductionHeight   The height at which damage reduction starts. 开始减伤的高度
     * @param damageReductionPercentage The percentage of damage reduction. 减伤百分比
     * @param dodgeRate               The dodge rate increase. 闪避率增加
     * @param directionalVelocity     The directional velocity for the player. 玩家朝向的加速度
     */
    public LightSkillEntry(String name, int level, String jumpType, int jumpCount, float innerPowerConsumption, double velocityYIncrease, int particleCount, float damageReductionHeight, float damageReductionPercentage, float dodgeRate, double directionalVelocity) {
        super(name, level);
        this.jumpType = jumpType;
        this.jumpCount = jumpCount;
        this.innerPowerConsumption = innerPowerConsumption;
        this.velocityYIncrease = velocityYIncrease;
        this.particleCount = particleCount;
        this.damageReductionHeight = damageReductionHeight;
        this.damageReductionPercentage = damageReductionPercentage;
        this.dodgeRate = dodgeRate;
        this.directionalVelocity = directionalVelocity;
    }

    public String getJumpType() {
        return jumpType;
    }

    public int getJumpCount() {
        return jumpCount;
    }

    public float getInnerPowerConsumption() {
        return innerPowerConsumption;
    }

    public double getVelocityYIncrease() {
        return velocityYIncrease;
    }

    public int getParticleCount() {
        return particleCount;
    }

    public float getDamageReductionHeight() {
        return damageReductionHeight;
    }

    public float getDamageReductionPercentage() {
        return damageReductionPercentage;
    }

    public float getDodgeRate() {
        return dodgeRate;
    }

    public double getDirectionalVelocity() {
        return directionalVelocity;
    }

    /**
     * Writes the LightSkillEntry properties to an NbtCompound.
     * 将 LightSkillEntry 的属性写入 NbtCompound。
     *
     * @param nbt The NbtCompound to write the properties to. 要写入属性的 NbtCompound
     */
    @Override
    public void writeToNbt(NbtCompound nbt) {
        nbt.putString("Name", getName());
        nbt.putInt("Level", getLevel());
        nbt.putString("Type", "LightSkillEntry");
        nbt.putString("JumpType", jumpType);
        nbt.putInt("JumpCount", jumpCount);
        nbt.putFloat("InnerPowerConsumption", innerPowerConsumption);
        nbt.putDouble("VelocityYIncrease", velocityYIncrease);
        nbt.putInt("ParticleCount", particleCount);
        nbt.putFloat("DamageReductionHeight", damageReductionHeight);
        nbt.putFloat("DamageReductionPercentage", damageReductionPercentage);
        nbt.putFloat("DodgeRate", dodgeRate);
        nbt.putDouble("DirectionalVelocity", directionalVelocity);
    }
}
