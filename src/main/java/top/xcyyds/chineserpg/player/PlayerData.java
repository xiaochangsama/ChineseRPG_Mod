package top.xcyyds.chineserpg.player;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import top.xcyyds.chineserpg.martialart.MartialArt;
import top.xcyyds.chineserpg.registry.MartialArtRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlayerData {
    private float innerPower = 0;
    private float innerPowerMax = 100; // 默认最大内力
    private int jumpCount = 0;
    private float innerPowerRegenRate = 1; // 默认内力回复速度
    private List<UUID> learnedSkills = new ArrayList<>(); // 已学习轻功技能的列表
    private UUID equippedSkill = null; // 当前装备的轻功技能

    public void writeToNbt(NbtCompound nbt) {
        nbt.putFloat("InnerPower", innerPower);
        nbt.putFloat("InnerPowerMax", innerPowerMax);
        nbt.putInt("JumpCount", jumpCount);
        nbt.putFloat("InnerPowerRegenRate", innerPowerRegenRate);

        // 写入 learnedSkills 列表
        NbtList skillList = new NbtList();
        for (UUID skill : learnedSkills) {
            skillList.add(NbtString.of(skill.toString()));
        }
        nbt.put("LearnedSkills", skillList);

        // 写入 equippedSkill
        if (equippedSkill != null) {
            nbt.putUuid("EquippedSkill", equippedSkill);
        }
    }

    public void readFromNbt(NbtCompound nbt) {
        innerPower = nbt.getFloat("InnerPower");
        innerPowerMax = nbt.getFloat("InnerPowerMax");
        jumpCount = nbt.getInt("JumpCount");
        innerPowerRegenRate = nbt.getFloat("InnerPowerRegenRate");

        // 读取 learnedSkills 列表
        NbtList skillList = nbt.getList("LearnedSkills", 8); // 8 是 NbtString 的类型 ID
        learnedSkills.clear();
        for (int i = 0; i < skillList.size(); i++) {
            learnedSkills.add(UUID.fromString(skillList.getString(i)));
        }

        // 读取 equippedSkill
        if (nbt.contains("EquippedSkill")) {
            equippedSkill = nbt.getUuid("EquippedSkill");
        }
    }

    // 添加一个新的轻功技能
    public boolean addSkill(UUID skill) {
        if (!learnedSkills.contains(skill)) {
            learnedSkills.add(skill);
            return true;
        }
        return false;
    }

    // 批量添加轻功技能
    public void addSkills(List<UUID> skills) {
        for (UUID skill : skills) {
            addSkill(skill);
        }
    }

    // 获取已学习的轻功技能
    public List<UUID> getLearnedSkills() {
        return learnedSkills;
    }

    // 装备轻功技能
    public void equipSkill(UUID skill) {
        if (learnedSkills.contains(skill)) {
            equippedSkill = skill;
        }
    }

    // 获取当前装备的轻功技能
    public UUID getEquippedSkill() {
        return equippedSkill;
    }

    // 获取已学习的武功详情
    public List<MartialArt> getLearnedMartialArts() {
        List<MartialArt> martialArts = new ArrayList<>();
        for (UUID skill : learnedSkills) {
            MartialArt martialArt = MartialArtRegistry.getMartialArt(skill);
            if (martialArt != null) {
                martialArts.add(martialArt);
            }
        }
        return martialArts;
    }

    // 获取当前装备的武功详情
    public MartialArt getEquippedMartialArt() {
        return MartialArtRegistry.getMartialArt(equippedSkill);
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

    public float getInnerPowerRegenRate() {
        return innerPowerRegenRate;
    }

    public void setInnerPowerRegenRate(float innerPowerRegenRate) {
        this.innerPowerRegenRate = innerPowerRegenRate;
    }

    public void regenerateInnerPower() {
        if (innerPower < innerPowerMax) {
            innerPower = Math.min(innerPower + innerPowerRegenRate, innerPowerMax);
        }
    }
}
