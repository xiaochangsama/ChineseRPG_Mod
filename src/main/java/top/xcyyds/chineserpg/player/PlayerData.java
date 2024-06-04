package top.xcyyds.chineserpg.player;

import net.minecraft.nbt.NbtCompound;

public class PlayerData {
    private float innerPower = 0;
    private float innerPowerMax = 0;
    private int jumpCount = 0;

    public void writeToNbt(NbtCompound nbt) {

        nbt.putFloat("InnerPower", innerPower);
        nbt.putFloat("InnerPowerMax", innerPowerMax);
        nbt.putInt("JumpCount", jumpCount);
    }

    public void readFromNbt(NbtCompound nbt) {

        innerPower = nbt.getFloat("InnerPower");
        innerPowerMax = nbt.getFloat("InnerPowerMax");
        jumpCount = nbt.getInt("JumpCount");

    }

    public void reset() {
        this.innerPower = 0;
        this.innerPowerMax = 0;
        this.jumpCount = 0;
    }


    // Getters and Setters for the fields
    public float getInnerPower() {
        return innerPower;
    }

    public void setInnerPower(float innerPower) {
        this.innerPower = innerPower;
    }

    public float getInnerPowerMax() {
        return innerPowerMax;
    }

    public void setInnerPowerMax(float innerPowerMax) {
        this.innerPowerMax = innerPowerMax;
    }

    public int getJumpCount() {
        return jumpCount;
    }

    public void setJumpCount(int jumpCount) {
        this.jumpCount = jumpCount;
    }


}
