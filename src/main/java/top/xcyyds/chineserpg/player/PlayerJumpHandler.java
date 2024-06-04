package top.xcyyds.chineserpg.player;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.particle.ParticleTypes;
import top.xcyyds.chineserpg.constants.JumpConstants;
import top.xcyyds.chineserpg.martialart.MartialArt;
import top.xcyyds.chineserpg.martialart.MartialArtEntry;

public class PlayerJumpHandler {

    // 基本跳跃，增加玩家的垂直速度
    public static void basicJump(PlayerEntity player, double velocityIncrease) {
        Vec3d currentVelocity = player.getVelocity();
        Vec3d newVelocity = currentVelocity.add(0, velocityIncrease, 0);
        player.setVelocity(newVelocity);
        player.velocityDirty = true;

        // 生成跳跃气团
        generateJumpParticles(player);

        // 扣除内力
        consumeInnerPower(((IPlayerDataProvider) player).getPlayerData(), JumpConstants.INNER_POWER_CONSUMPTION);
    }

    // 处理玩家跳跃逻辑，包括二段跳
    public static void handleJump(PlayerEntity player, PlayerData playerData) {
        MartialArt equippedMartialArt = playerData.getEquippedMartialArt();

        if (equippedMartialArt != null) {
            for (MartialArtEntry entry : equippedMartialArt.getEntries()) {
                if ("二段跳".equals(entry.getJumpType())) {
                    if (player.isOnGround()) {
                        playerData.setJumpCount(entry.getJumpCount());
                    } else if (playerData.getJumpCount() > 0) {
                        if (consumeInnerPower(playerData, entry.getInnerPowerConsumption())) {
                            basicJump(player, JumpConstants.BASE_JUMP_VELOCITY); // 调整跳跃的力度
                            playerData.setJumpCount(playerData.getJumpCount() - 1);
                        }
                    }
                }
            }
        }
    }

    // 生成跳跃气团
    private static void generateJumpParticles(PlayerEntity player) {
        World world = player.getEntityWorld();
        Vec3d pos = player.getPos();
        for (int i = 0; i < JumpConstants.PARTICLE_COUNT; i++) {
            world.addParticle(ParticleTypes.CLOUD, pos.x, pos.y, pos.z, 0, 0, 0);
        }
    }

    // 消耗内力
    private static boolean consumeInnerPower(PlayerData playerData, float amount) {
        if (playerData.getInnerPower() >= amount) {
            playerData.setInnerPower(playerData.getInnerPower() - amount);
            return true;
        }
        return false;
    }
}
