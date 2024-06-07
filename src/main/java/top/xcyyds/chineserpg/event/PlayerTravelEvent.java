package top.xcyyds.chineserpg.event;


import net.minecraft.util.ActionResult;
import net.minecraft.util.math.Vec3d;
import top.xcyyds.chineserpg.player.data.IPlayerDataProvider;
import top.xcyyds.chineserpg.player.data.PlayerData;

/**
 * 将PlayerData种存储的速度更改内容应用
 */
public class PlayerTravelEvent {
    public static void register() {
        PlayerTravelCallback.EVENT.register((player, travelVector) -> {
            // 处理事件逻辑
            PlayerData playerData = ((IPlayerDataProvider)player).getPlayerData();



            //我的playerData存储的速度信息目前会覆盖全部原版的速度更改
            if (playerData.isVelocityDirty()) {
                Vec3d vec3d = playerData.getPlayerVelocity();

                player.setVelocity(vec3d);
                player.velocityDirty = true;


                playerData.setVelocityDirty(false);
            }
            return ActionResult.PASS;
        });
    }
}
