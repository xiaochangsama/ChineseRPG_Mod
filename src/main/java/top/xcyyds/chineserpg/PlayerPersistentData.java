package top.xcyyds.chineserpg;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;


public class PlayerPersistentData {
    private float innerPower;
    private float innerPowerMax;
    private int jumpCount;

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

    public PacketByteBuf writeToBuf(){
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeFloat(innerPower);
        buf.writeFloat(innerPowerMax);
        buf.writeInt(jumpCount);
        return buf;
    }
    public void readFromBuf(PacketByteBuf buf){
        innerPower = buf.readFloat();
        innerPowerMax = buf.readFloat();
        jumpCount = buf.readInt();


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
