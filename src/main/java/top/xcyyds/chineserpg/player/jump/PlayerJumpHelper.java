package top.xcyyds.chineserpg.player.jump;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import top.xcyyds.chineserpg.martialart.MartialArt;
import top.xcyyds.chineserpg.martialart.MartialArtEntry;
import top.xcyyds.chineserpg.player.data.PlayerData;

public class PlayerJumpHelper {


    // 消耗内力
    public static boolean consumeInnerPower(PlayerData playerData, float amount) {
        if (playerData.getInnerPower() >= amount) {
            playerData.setInnerPower(playerData.getInnerPower() - amount);
            return true;
        }
        return false;
    }

    // 消耗内力

    /**
     *
     * @param playerData 玩家属性
     * @param amount 空中连跳力消耗量
     * @return 是否允许跳跃
     */
    public static boolean consumeJumpCount(PlayerData playerData, int amount) {
        if (playerData.getJumpCount() >= amount) {
            playerData.setJumpCount(playerData.getJumpCount() - amount);
            return true;
        }
        return false;
    }

    public static boolean isPlayerInWater(PlayerEntity player){
        return player.isTouchingWater();
    }

    static Vec3d getPlayerHorizontalDirection(PlayerEntity player) {
        //计算玩家面朝的水平方向向量
        float yaw = player.getYaw() * 0.017453292F; //将角度转换为弧度

        double x = -Math.sin(yaw);
        double z = Math.cos(yaw);

        return new Vec3d(x, 0, z);
    }
    // 生成最普通的跳跃气团
    static void generateJumpParticles(PlayerEntity player, int particleCount) {
        World world = player.getEntityWorld();
        Vec3d pos = player.getPos();
        for (int i = 0; i < particleCount; i++) {
            world.addParticle(ParticleTypes.CLOUD, pos.x, pos.y, pos.z, 0, 0, 0);

        }
    }
    //生成环形跳跃气团
    static void generateRingParticles(PlayerEntity player, int particleCount, double radius) {
        World world = player.getEntityWorld();
        Vec3d pos = player.getPos();
        for (int i = 0; i < particleCount; i++) {
            double angle = 2 * Math.PI * i / particleCount;
            double xOffset = radius * Math.cos(angle);
            double zOffset = radius * Math.sin(angle);
            world.addParticle(ParticleTypes.CLOUD, pos.x + xOffset, pos.y, pos.z + zOffset, 0, 0, 0);
        }
    }

    public static void updateJumpCount(ServerPlayerEntity player, PlayerData playerData) {
        if (player.isOnGround()) {
            MartialArt equippedMartialArt = playerData.getEquippedMartialArt();
            if (equippedMartialArt != null) {
                for (MartialArtEntry entry : equippedMartialArt.getEntries()) {
                    if (PlayerJumpHandler.MULTI_JUMP.equals(entry.getJumpType()) || PlayerJumpHandler.WATER_SKIMMING.equals(entry.getJumpType())) {
                        playerData.setJumpCount(entry.getJumpCount());
                        break;
                    }
                }
            }
        }
    }
}
