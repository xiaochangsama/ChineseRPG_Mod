package top.xcyyds.chineserpg.player.jump;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Vec3d;
import top.xcyyds.chineserpg.player.data.PlayerData;


/**
 * 定义不同的跳跃类型，在MartialArts定义中使用
 * 定义跳跃附带的属性变化，附带跳跃特效的定义
 */
public class PlayerJumpHandler {

    public static final String DOUBLE_JUMP = "二段跳";

    public static void basicJump(ServerPlayerEntity player, PlayerData playerData, double velocityIncrease, int particleCount, float innerPowerConsumption) {
        // 获得玩家的速度
        Vec3d currentVelocity = player.getVelocity();
        Vec3d newVelocity = currentVelocity.add(0, velocityIncrease, 0);
        player.setVelocity(newVelocity);
        // 应用更改
        player.velocityDirty = true;

        // 生成跳跃气团
        PlayerJumpHelper.generateJumpParticles(player, particleCount);
        // 消耗内力
        PlayerJumpHelper.consumeInnerPower(playerData, innerPowerConsumption);
    }

}
