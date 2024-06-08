package top.xcyyds.chineserpg.martialart.artentry;

import com.google.gson.annotations.SerializedName;
import net.minecraft.nbt.NbtCompound;

public class LightSkillEntry extends MartialArtEntry {
    @SerializedName("jumpType")
    private String jumpType = "";
    @SerializedName("jumpCount")
    private int jumpCount = 0;
    @SerializedName("innerPowerConsumption")
    private float innerPowerConsumption = 0;
    @SerializedName("velocityYIncrease")
    private double velocityYIncrease = 0; // 跳跃的力度
    @SerializedName("particleCount")
    private int particleCount = 0; // 跳跃产生的气团数量
    @SerializedName("damageReductionHeight")
    private float damageReductionHeight = 0;
    @SerializedName("damageReductionPercentage")
    private float damageReductionPercentage = 0;
    @SerializedName("dodgeRate")
    private float dodgeRate = 0;
    @SerializedName("directionalVelocity")
    private double directionalVelocity = 0; // 朝向加速度，玩家加速度

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
