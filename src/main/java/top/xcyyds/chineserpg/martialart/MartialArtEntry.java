package top.xcyyds.chineserpg.martialart;

import net.minecraft.nbt.NbtCompound;

public class MartialArtEntry {
    private String name;
    private int level;
    private String jumpType;
    private int jumpCount;
    private float innerPowerConsumption;
    private double velocityIncrease; // 跳跃的力度
    private int particleCount; // 跳跃产生的气团数量
    private float damageReductionHeight;
    private float damageReductionPercentage;
    private float dodgeRate;

    // 构造函数
    public MartialArtEntry(String name, int level, String jumpType, int jumpCount, float innerPowerConsumption, double velocityIncrease, int particleCount, float damageReductionHeight, float damageReductionPercentage, float dodgeRate) {
        this.name = name;
        this.level = level;
        this.jumpType = jumpType;
        this.jumpCount = jumpCount;
        this.innerPowerConsumption = innerPowerConsumption;
        this.velocityIncrease = velocityIncrease;
        this.particleCount = particleCount;
        this.damageReductionHeight = damageReductionHeight;
        this.damageReductionPercentage = damageReductionPercentage;
        this.dodgeRate = dodgeRate;
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

    public double getVelocityIncrease() {
        return velocityIncrease;
    }

    public int getParticleCount() {
        return particleCount;
    }

    // 序列化和反序列化方法

    public void writeToNbt(NbtCompound nbt) {
        nbt.putString("Name", name);
        nbt.putInt("Level", level);
        nbt.putString("JumpType", jumpType);
        nbt.putInt("JumpCount", jumpCount);
        nbt.putFloat("InnerPowerConsumption", innerPowerConsumption);
        nbt.putDouble("VelocityIncrease", velocityIncrease);
        nbt.putInt("ParticleCount", particleCount);
        nbt.putFloat("DamageReductionHeight", damageReductionHeight);
        nbt.putFloat("DamageReductionPercentage", damageReductionPercentage);
        nbt.putFloat("DodgeRate", dodgeRate);
    }

    public static MartialArtEntry readFromNbt(NbtCompound nbt) {
        String name = nbt.getString("Name");
        int level = nbt.getInt("Level");
        String jumpType = nbt.getString("JumpType");
        int jumpCount = nbt.getInt("JumpCount");
        float innerPowerConsumption = nbt.getFloat("InnerPowerConsumption");
        double velocityIncrease = nbt.getDouble("VelocityIncrease");
        int particleCount = nbt.getInt("ParticleCount");
        float damageReductionHeight = nbt.getFloat("DamageReductionHeight");
        float damageReductionPercentage = nbt.getFloat("DamageReductionPercentage");
        float dodgeRate = nbt.getFloat("DodgeRate");

        return new MartialArtEntry(name, level, jumpType, jumpCount, innerPowerConsumption, velocityIncrease, particleCount, damageReductionHeight, damageReductionPercentage, dodgeRate);
    }
}
