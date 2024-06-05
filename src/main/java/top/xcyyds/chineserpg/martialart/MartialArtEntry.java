package top.xcyyds.chineserpg.martialart;

import net.minecraft.nbt.NbtCompound;

public class MartialArtEntry {
    private String name;
    private int level;
    private String jumpType;
    private int jumpCount;
    private float innerPowerConsumption;
    private float fallDamageHeight;
    private float fallDamageReduction;
    private float dodgeRate;

    public MartialArtEntry(String name, int level, String jumpType, int jumpCount, float innerPowerConsumption, float fallDamageHeight, float fallDamageReduction, float dodgeRate) {
        this.name = name;
        this.level = level;
        this.jumpType = jumpType;
        this.jumpCount = jumpCount;
        this.innerPowerConsumption = innerPowerConsumption;
        this.fallDamageHeight = fallDamageHeight;
        this.fallDamageReduction = fallDamageReduction;
        this.dodgeRate = dodgeRate;
    }

    public void writeToNbt(NbtCompound nbt) {
        nbt.putString("Name", name);
        nbt.putInt("Level", level);
        nbt.putString("JumpType", jumpType);
        nbt.putInt("JumpCount", jumpCount);
        nbt.putFloat("InnerPowerConsumption", innerPowerConsumption);
        nbt.putFloat("FallDamageHeight", fallDamageHeight);
        nbt.putFloat("FallDamageReduction", fallDamageReduction);
        nbt.putFloat("DodgeRate", dodgeRate);
    }

    public static MartialArtEntry readFromNbt(NbtCompound nbt) {
        String name = nbt.getString("Name");
        int level = nbt.getInt("Level");
        String jumpType = nbt.getString("JumpType");
        int jumpCount = nbt.getInt("JumpCount");
        float innerPowerConsumption = nbt.getFloat("InnerPowerConsumption");
        float fallDamageHeight = nbt.getFloat("FallDamageHeight");
        float fallDamageReduction = nbt.getFloat("FallDamageReduction");
        float dodgeRate = nbt.getFloat("DodgeRate");
        return new MartialArtEntry(name, level, jumpType, jumpCount, innerPowerConsumption, fallDamageHeight, fallDamageReduction, dodgeRate);
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getJumpType() {
        return jumpType;
    }

    public void setJumpType(String jumpType) {
        this.jumpType = jumpType;
    }

    public int getJumpCount() {
        return jumpCount;
    }

    public void setJumpCount(int jumpCount) {
        this.jumpCount = jumpCount;
    }

    public float getInnerPowerConsumption() {
        return innerPowerConsumption;
    }

    public void setInnerPowerConsumption(float innerPowerConsumption) {
        this.innerPowerConsumption = innerPowerConsumption;
    }

    public float getFallDamageHeight() {
        return fallDamageHeight;
    }

    public void setFallDamageHeight(float fallDamageHeight) {
        this.fallDamageHeight = fallDamageHeight;
    }

    public float getFallDamageReduction() {
        return fallDamageReduction;
    }

    public void setFallDamageReduction(float fallDamageReduction) {
        this.fallDamageReduction = fallDamageReduction;
    }

    public float getDodgeRate() {
        return dodgeRate;
    }

    public void setDodgeRate(float dodgeRate) {
        this.dodgeRate = dodgeRate;
    }
}
