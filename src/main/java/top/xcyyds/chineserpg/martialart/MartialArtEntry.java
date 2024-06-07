package top.xcyyds.chineserpg.martialart;

import com.google.gson.annotations.SerializedName;
import net.minecraft.nbt.NbtCompound;

public class MartialArtEntry {
    @SerializedName("name")
    private String name;
    @SerializedName("level")
    private int level;
    @SerializedName("jumpType")
    private String jumpType;
    @SerializedName("jumpCount")
    private int jumpCount;
    @SerializedName("innerPowerConsumption")
    private float innerPowerConsumption;
    @SerializedName("velocityYIncrease")
    private double velocityYIncrease; // 跳跃的力度
    @SerializedName("particleCount")
    private int particleCount; // 跳跃产生的气团数量
    @SerializedName("damageReductionHeight")
    private float damageReductionHeight;
    @SerializedName("damageReductionPercentage")
    private float damageReductionPercentage;
    @SerializedName("dodgeRate")
    private float dodgeRate;
    @SerializedName("directionalVelocity")
    private double directionalVelocity; // 朝向加速度

    // 构造函数
    public MartialArtEntry(String name, int level, String jumpType, int jumpCount, float innerPowerConsumption, double velocityYIncrease, int particleCount, float damageReductionHeight, float damageReductionPercentage, float dodgeRate, double directionalVelocity) {
        this.name = name;
        this.level = level;
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

    // Getters and Setters

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

    public double getDirectionalVelocity() {
        return directionalVelocity;
    }

    public void setDirectionalVelocity(double directionalVelocity) {
        this.directionalVelocity = directionalVelocity;
    }

    // 序列化和反序列化方法

    public void writeToNbt(NbtCompound nbt) {
        nbt.putString("Name", name);
        nbt.putInt("Level", level);
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

    public static MartialArtEntry readFromNbt(NbtCompound nbt) {
        String name = nbt.getString("Name");
        int level = nbt.getInt("Level");
        String jumpType = nbt.getString("JumpType");
        int jumpCount = nbt.getInt("JumpCount");
        float innerPowerConsumption = nbt.getFloat("InnerPowerConsumption");
        double velocityIncrease = nbt.getDouble("VelocityYIncrease");
        int particleCount = nbt.getInt("ParticleCount");
        float damageReductionHeight = nbt.getFloat("DamageReductionHeight");
        float damageReductionPercentage = nbt.getFloat("DamageReductionPercentage");
        float dodgeRate = nbt.getFloat("DodgeRate");
        double directionalVelocity = nbt.getDouble("DirectionalVelocity");

        return new MartialArtEntry(name, level, jumpType, jumpCount, innerPowerConsumption, velocityIncrease, particleCount, damageReductionHeight, damageReductionPercentage, dodgeRate, directionalVelocity);
    }

    public float getDamageReductionHeight() {
        return damageReductionHeight;
    }

    public float getDamageReductionPercentage() {
        return damageReductionPercentage;
    }


}
