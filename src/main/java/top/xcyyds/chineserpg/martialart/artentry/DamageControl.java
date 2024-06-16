package top.xcyyds.chineserpg.martialart.artentry;

import com.google.gson.annotations.SerializedName;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtFloat;
import net.minecraft.nbt.NbtList;

/**
 * DamageControl 负责管理伤害判定和时间控制。
 */
public class DamageControl {
    @SerializedName("damageType")
    private String damageType; // 伤害类型（射线、立方体、扇形等）

    @SerializedName("damageSourceCoordinates")
    private float[] damageSourceCoordinates; // 伤害源坐标

    @SerializedName("damageRadius")
    private float damageRadius; // 伤害半径

    @SerializedName("startCoordinates")
    private float[] startCoordinates; // 伤害起始位置坐标（可选）

    @SerializedName("endCoordinates")
    private float[] endCoordinates; // 伤害结束位置坐标（可选）

    @SerializedName("angle")
    private float angle; // 技能角度

    @SerializedName("startupTime")
    private int startupTime; // 前摇时间

    @SerializedName("actionTime")
    private int actionTime; // 动作时间

    @SerializedName("recoveryTime")
    private int recoveryTime; // 后摇时间

    public DamageControl(String damageType, float[] damageSourceCoordinates, float damageRadius, float[] startCoordinates,
                         float[] endCoordinates, float angle, int startupTime, int actionTime, int recoveryTime) {
        this.damageType = damageType != null ? damageType : "unknown";
        this.damageSourceCoordinates = damageSourceCoordinates != null ? damageSourceCoordinates : new float[]{0.0f, 0.0f, 0.0f};
        this.damageRadius = damageRadius;
        this.startCoordinates = startCoordinates != null ? startCoordinates : new float[]{0.0f, 0.0f, 0.0f};
        this.endCoordinates = endCoordinates != null ? endCoordinates : new float[]{0.0f, 0.0f, 0.0f};
        this.angle = angle;
        this.startupTime = startupTime;
        this.actionTime = actionTime;
        this.recoveryTime = recoveryTime;
    }

    public String getDamageType() {
        return damageType;
    }

    public float[] getDamageSourceCoordinates() {
        return damageSourceCoordinates;
    }

    public float getDamageRadius() {
        return damageRadius;
    }

    public float[] getStartCoordinates() {
        return startCoordinates;
    }

    public float[] getEndCoordinates() {
        return endCoordinates;
    }

    public float getAngle() {
        return angle;
    }

    public int getStartupTime() {
        return startupTime;
    }

    public int getActionTime() {
        return actionTime;
    }

    public int getRecoveryTime() {
        return recoveryTime;
    }

    public void writeToNbt(NbtCompound nbt) {
        nbt.putString("DamageType", damageType != null ? damageType : "unknown");
        nbt.put("DamageSourceCoordinates", floatArrayToNbtList(damageSourceCoordinates));
        nbt.putFloat("DamageRadius", damageRadius);
        if (startCoordinates != null) {
            nbt.put("StartCoordinates", floatArrayToNbtList(startCoordinates));
        }
        if (endCoordinates != null) {
            nbt.put("EndCoordinates", floatArrayToNbtList(endCoordinates));
        }
        nbt.putFloat("Angle", angle);
        nbt.putInt("StartupTime", startupTime);
        nbt.putInt("ActionTime", actionTime);
        nbt.putInt("RecoveryTime", recoveryTime);
    }

    private NbtList floatArrayToNbtList(float[] array) {
        NbtList nbtList = new NbtList();
        for (float value : array) {
            nbtList.add(NbtFloat.of(value));
        }
        return nbtList;
    }

    public static DamageControl readFromNbt(NbtCompound nbt) {
        String damageType = nbt.getString("DamageType");
        float[] damageSourceCoordinates = nbtListToFloatArray(nbt.getList("DamageSourceCoordinates", 5));
        float damageRadius = nbt.getFloat("DamageRadius");
        float[] startCoordinates = nbt.contains("StartCoordinates") ? nbtListToFloatArray(nbt.getList("StartCoordinates", 5)) : new float[]{0.0f, 0.0f, 0.0f};
        float[] endCoordinates = nbt.contains("EndCoordinates") ? nbtListToFloatArray(nbt.getList("EndCoordinates", 5)) : new float[]{0.0f, 0.0f, 0.0f};
        float angle = nbt.getFloat("Angle");
        int startupTime = nbt.getInt("StartupTime");
        int actionTime = nbt.getInt("ActionTime");
        int recoveryTime = nbt.getInt("RecoveryTime");

        return new DamageControl(damageType, damageSourceCoordinates, damageRadius, startCoordinates, endCoordinates, angle, startupTime, actionTime, recoveryTime);
    }

    private static float[] nbtListToFloatArray(NbtList nbtList) {
        float[] array = new float[nbtList.size()];
        for (int i = 0; i < nbtList.size(); i++) {
            array[i] = nbtList.getFloat(i);
        }
        return array;
    }
}
