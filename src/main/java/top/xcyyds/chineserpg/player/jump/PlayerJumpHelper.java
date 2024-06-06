package top.xcyyds.chineserpg.player.jump;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import top.xcyyds.chineserpg.player.data.PlayerData;

public class PlayerJumpHelper {
    // 生成跳跃气团
    static void generateJumpParticles(PlayerEntity player, int particleCount) {
        World world = player.getEntityWorld();
        Vec3d pos = player.getPos();
        for (int i = 0; i < particleCount; i++) {
            world.addParticle(ParticleTypes.CLOUD, pos.x, pos.y, pos.z, 0, 0, 0);
        }
    }

    // 消耗内力
    public static boolean consumeInnerPower(PlayerData playerData, float amount) {
        if (playerData.getInnerPower() >= amount) {
            playerData.setInnerPower(playerData.getInnerPower() - amount);
            return true;
        }
        return false;
    }
}
