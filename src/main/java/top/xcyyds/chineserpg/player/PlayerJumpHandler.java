package top.xcyyds.chineserpg.player;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;

// 这个类控制玩家跳跃,定义多种跳跃方式
/*
单击
快速双击
组合键位
长按蓄力（暂定）

大跳
蓄力跳
空中跳
蹬墙跳
水上漂
 */
public class PlayerJumpHandler {

    // 第一种跳跃方式：增加玩家的垂直速度
    public static void basicJump(PlayerEntity player, double velocityIncrease) {
        // 获取玩家当前的速度
        Vec3d currentVelocity = player.getVelocity();

        // 增加垂直方向上的速度
        Vec3d newVelocity = currentVelocity.add(0, velocityIncrease, 0);

        // 设置玩家新的速度
        player.setVelocity(newVelocity);

        // 更新玩家的速度（这一行代码用于通知游戏系统玩家的速度已经改变，需要更新）
        player.velocityDirty = true;
    }
}
