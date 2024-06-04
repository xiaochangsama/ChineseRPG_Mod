package top.xcyyds.chineserpg.player;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;

import top.xcyyds.chineserpg.registry.MartialArt;
import top.xcyyds.chineserpg.registry.MartialArtRegistry;

import java.util.ArrayList;
import java.util.List;

public class PlayerData {
    private float innerPower = 0;
    private float innerPowerMax = 0;
    private int jumpCount = 0;
    private List<String> learnedSkills = new ArrayList<>(); // 已学习轻功技能的列表
    private String equippedSkill = ""; // 当前装备的轻功技能

    public void writeToNbt(NbtCompound nbt) {
        nbt.putFloat("InnerPower", innerPower);
        nbt.putFloat("InnerPowerMax", innerPowerMax);
        nbt.putInt("JumpCount", jumpCount);

        // 写入 learnedSkills 列表
        NbtList skillList = new NbtList();
        for (String skill : learnedSkills) {
            skillList.add(NbtString.of(skill));
        }
        nbt.put("LearnedSkills", skillList);

        // 写入 equippedSkill
        nbt.putString("EquippedSkill", equippedSkill);
    }

    public void readFromNbt(NbtCompound nbt) {
        innerPower = nbt.getFloat("InnerPower");
        innerPowerMax = nbt.getFloat("InnerPowerMax");
        jumpCount = nbt.getInt("JumpCount");

        // 读取 learnedSkills 列表
        NbtList skillList = nbt.getList("LearnedSkills", 8); // 8 是 NbtString 的类型 ID
        learnedSkills.clear();
        for (int i = 0; i < skillList.size(); i++) {
            learnedSkills.add(skillList.getString(i));
        }

        // 读取 equippedSkill
        equippedSkill = nbt.getString("EquippedSkill");
    }

    // 添加一个新的轻功技能
    public boolean addSkill(String skill) {
        if (!learnedSkills.contains(skill)) {
            learnedSkills.add(skill);
            return true;
        }
        return false;
    }

    // 批量添加轻功技能
    public void addSkills(List<String> skills) {
        for (String skill : skills) {
            addSkill(skill);
        }
    }

    // 获取已学习的轻功技能
    public List<String> getLearnedSkills() {
        return learnedSkills;
    }

    // 装备轻功技能
    public void equipSkill(String skill) {
        if (learnedSkills.contains(skill)) {
            equippedSkill = skill;
        }
    }

    // 获取当前装备的轻功技能
    public String getEquippedSkill() {
        return equippedSkill;
    }

    // 获取已学习的武功详情
    public List<MartialArt> getLearnedMartialArts() {
        List<MartialArt> martialArts = new ArrayList<>();
        for (String skill : learnedSkills) {
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
}
