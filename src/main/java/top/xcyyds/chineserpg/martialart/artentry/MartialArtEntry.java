package top.xcyyds.chineserpg.martialart.artentry;

import com.google.gson.annotations.SerializedName;
import net.minecraft.nbt.NbtCompound;

public abstract class MartialArtEntry {
    @SerializedName("name")
    private String name = "";
    @SerializedName("level")
    private int level = 1;

    public MartialArtEntry(String name, int level) {
        this.name = name;
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public abstract void writeToNbt(NbtCompound nbt);

    public static MartialArtEntry readFromNbt(NbtCompound nbt) {
        String name = nbt.getString("Name");
        int level = nbt.getInt("Level");
        //用这个属性来判断应该是哪种武功
        String type = nbt.getString("Type");
        return switch (type) {
            case "LightSkillEntry" -> new LightSkillEntry(name, level,
                    nbt.getString("JumpType"),
                    nbt.getInt("JumpCount"),
                    nbt.getFloat("InnerPowerConsumption"),
                    nbt.getDouble("VelocityYIncrease"),
                    nbt.getInt("ParticleCount"),
                    nbt.getFloat("DamageReductionHeight"),
                    nbt.getFloat("DamageReductionPercentage"),
                    nbt.getFloat("DodgeRate"),
                    nbt.getDouble("DirectionalVelocity"));
            case "OuterSkillEntry" -> new OuterSkillEntry(name, level,
                    nbt.getFloat("Damage"),
                    nbt.getInt("Cooldown"),
                    nbt.getFloat("Range"));
            // 其他类型的处理
            default -> throw new IllegalArgumentException("Unknown martial art entry type: " + type);
        };
    }
}
