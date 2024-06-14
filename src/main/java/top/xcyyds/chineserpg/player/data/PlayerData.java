package top.xcyyds.chineserpg.player.data;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.util.math.Vec3d;
import top.xcyyds.chineserpg.martialart.skill.MartialArt;
import top.xcyyds.chineserpg.player.ActionType;
import top.xcyyds.chineserpg.player.PlayerActionManager;
import top.xcyyds.chineserpg.registry.MartialArtRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlayerData {
    private final PlayerEntity player;
    private float innerPower = 0;
    private float innerPowerMax = 100;
    private int jumpCount = 0;
    private float innerPowerRegenRate = 1;
    private Vec3d playerVelocity = Vec3d.ZERO;
    private boolean velocityDirty = false;

    private final List<UUID> learnedLightSkills = new ArrayList<>();
    private UUID equippedLightSkill = null;

    private final List<UUID> learnedOuterSkills = new ArrayList<>();
    private UUID equippedOuterSkill = null;

    // 添加 PlayerActionManager 实例
    private PlayerActionManager actionManager;


    public PlayerData(PlayerEntity player) {
        this.player = player;
        this.actionManager = new PlayerActionManager(player);
    }

    public void writeToNbt(NbtCompound nbt) {
        nbt.putFloat("InnerPower", innerPower);
        nbt.putFloat("InnerPowerMax", innerPowerMax);
        nbt.putInt("JumpCount", jumpCount);
        nbt.putFloat("InnerPowerRegenRate", innerPowerRegenRate);

        // 写入 playerVelocity
        NbtCompound velocityNbt = new NbtCompound();
        velocityNbt.putDouble("x", playerVelocity.x);
        velocityNbt.putDouble("y", playerVelocity.y);
        velocityNbt.putDouble("z", playerVelocity.z);
        nbt.put("PlayerVelocity", velocityNbt);

        nbt.putBoolean("VelocityDirty", velocityDirty);

        // 写入 learnedLightSkills 列表
        NbtList lightSkillList = new NbtList();
        for (UUID skill : learnedLightSkills) {
            lightSkillList.add(NbtString.of(skill.toString()));
        }
        nbt.put("LearnedLightSkills", lightSkillList);

        // 写入 equippedLightSkill
        if (equippedLightSkill != null) {
            nbt.putUuid("EquippedLightSkill", equippedLightSkill);
        }

        // 写入 learnedOuterSkills 列表
        NbtList outerSkillList = new NbtList();
        for (UUID skill : learnedOuterSkills) {
            outerSkillList.add(NbtString.of(skill.toString()));
        }
        nbt.put("LearnedOuterSkills", outerSkillList);

        // 写入 equippedOuterSkill
        if (equippedOuterSkill != null) {
            nbt.putUuid("EquippedOuterSkill", equippedOuterSkill);
        }
    }

    public void readFromNbt(NbtCompound nbt) {
        innerPower = nbt.getFloat("InnerPower");
        innerPowerMax = nbt.getFloat("InnerPowerMax");
        jumpCount = nbt.getInt("JumpCount");
        innerPowerRegenRate = nbt.getFloat("InnerPowerRegenRate");

        // 读取 playerVelocity
        NbtCompound velocityNbt = nbt.getCompound("PlayerVelocity");
        playerVelocity = new Vec3d(velocityNbt.getDouble("x"), velocityNbt.getDouble("y"), velocityNbt.getDouble("z"));

        velocityDirty = nbt.getBoolean("VelocityDirty");

        // 读取 learnedLightSkills 列表
        NbtList lightSkillList = nbt.getList("LearnedLightSkills", 8);
        learnedLightSkills.clear();
        for (int i = 0; i < lightSkillList.size(); i++) {
            learnedLightSkills.add(UUID.fromString(lightSkillList.getString(i)));
        }

        // 读取 equippedLightSkill
        if (nbt.contains("EquippedLightSkill")) {
            equippedLightSkill = nbt.getUuid("EquippedLightSkill");
        }

        // 读取 learnedOuterSkills 列表
        NbtList outerSkillList = nbt.getList("LearnedOuterSkills", 8);
        learnedOuterSkills.clear();
        for (int i = 0; i < outerSkillList.size(); i++) {
            learnedOuterSkills.add(UUID.fromString(outerSkillList.getString(i)));
        }

        // 读取 equippedOuterSkill
        if (nbt.contains("EquippedOuterSkill")) {
            equippedOuterSkill = nbt.getUuid("EquippedOuterSkill");
        }
    }

    // 添加一个新的轻功技能
    public boolean addLightSkill(UUID skill) {
        if (!learnedLightSkills.contains(skill)) {
            learnedLightSkills.add(skill);
            return true;
        }
        return false;
    }

    // 批量添加轻功技能
    public void addLightSkills(List<UUID> skills) {
        for (UUID skill : skills) {
            addLightSkill(skill);
        }
    }

    // 获取已学习的轻功技能
    public List<UUID> getLearnedLightSkills() {
        return learnedLightSkills;
    }

    // 装备轻功技能
    public void equipLightSkill(UUID skill) {
        if (learnedLightSkills.contains(skill)) {
            equippedLightSkill = skill;
        }
    }

    // 获取当前装备的轻功技能
    public UUID getEquippedLightSkill() {
        return equippedLightSkill;
    }

    // 添加一个新的外功技能
    public boolean addOuterSkill(UUID skill) {
        if (!learnedOuterSkills.contains(skill)) {
            learnedOuterSkills.add(skill);
            return true;
        }
        return false;
    }

    // 批量添加外功技能
    public void addOuterSkills(List<UUID> skills) {
        for (UUID skill : skills) {
            addOuterSkill(skill);
        }
    }

    // 获取已学习的外功技能
    public List<UUID> getLearnedOuterSkills() {
        return learnedOuterSkills;
    }

    // 装备外功技能
    public void equipOuterSkill(UUID skill) {
        if (learnedOuterSkills.contains(skill)) {
            equippedOuterSkill = skill;
        }
    }

    // 获取当前装备的外功技能
    public UUID getEquippedOuterSkill() {
        return equippedOuterSkill;
    }

    // 获取已学习的武功详情
    public List<MartialArt> getLearnedMartialArts() {
        List<MartialArt> martialArts = new ArrayList<>();
        for (UUID skill : learnedLightSkills) {
            MartialArt martialArt = MartialArtRegistry.getMartialArt(skill);
            if (martialArt != null) {
                martialArts.add(martialArt);
            }
        }
        for (UUID skill : learnedOuterSkills) {
            MartialArt martialArt = MartialArtRegistry.getMartialArt(skill);
            if (martialArt != null) {
                martialArts.add(martialArt);
            }
        }
        return martialArts;
    }

    // 获取当前装备的武功详情
    public MartialArt getEquippedMartialArt() {
        MartialArt martialArt = MartialArtRegistry.getMartialArt(equippedLightSkill);
        if (martialArt == null) {
            martialArt = MartialArtRegistry.getMartialArt(equippedOuterSkill);
        }
        return martialArt;
    }

    public void tickRegenerateInnerPower() {
        if (innerPower < innerPowerMax) {
            innerPower = Math.min(innerPower + innerPowerRegenRate, innerPowerMax);
        }
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

    public Vec3d getPlayerVelocity() {
        return playerVelocity;
    }

    public void setPlayerVelocity(Vec3d playerVelocity) {
        this.playerVelocity = playerVelocity;
        this.velocityDirty = true;
    }

    public boolean isVelocityDirty() {
        return velocityDirty;
    }

    public void setVelocityDirty(boolean velocityDirty) {
        this.velocityDirty = velocityDirty;
    }

    public void setPlayerVelocity(double x, double y, double z) {
        this.playerVelocity = new Vec3d(x, y, z);
        this.velocityDirty = true;
    }

    public void resetJumpCount() {
        this.jumpCount = 0;
    }

    // 记录玩家动作
    public void recordAction(ActionType action) {
        actionManager.recordAction(action);
    }

    public void startRecordingActions() {
        actionManager.startRecording();
    }

    public void stopRecordingActions() {
        actionManager.stopRecording();
    }

    public PlayerEntity getPlayer() {
        return player;
    }
}