package top.xcyyds.chineserpg.player;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.particle.ParticleTypes;
import top.xcyyds.chineserpg.constants.JumpConstants;
import top.xcyyds.chineserpg.martialart.MartialArt;
import top.xcyyds.chineserpg.martialart.MartialArtEntry;

public class PlayerJumpHandler {

    public static final String DOUBLE_JUMP = "二段跳";

    public static void basicJump(ServerPlayerEntity player, double velocityIncrease, int particleCount) {
        // 获得玩家的速度
        Vec3d currentVelocity = player.getVelocity();
        Vec3d newVelocity = currentVelocity.add(0, velocityIncrease, 0);
        player.setVelocity(newVelocity);
        // 应用更改
        player.velocityDirty = true;

        // 生成跳跃气团
        generateJumpParticles(player, particleCount);
    }

    // 生成跳跃气团
    private static void generateJumpParticles(PlayerEntity player, int particleCount) {
        World world = player.getEntityWorld();
        Vec3d pos = player.getPos();
        for (int i = 0; i < particleCount; i++) {
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
