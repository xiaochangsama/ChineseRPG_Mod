package top.xcyyds.chineserpg.event;


import net.minecraft.util.ActionResult;
import top.xcyyds.chineserpg.player.data.IPlayerDataProvider;
import top.xcyyds.chineserpg.player.data.PlayerData;

/**
 * 在这个类里可以修改玩家的速度矢量并应用
 */
public class PlayerTravelEvent {
    public static void register() {
        PlayerTravelCallback.EVENT.register((player, travelVector) -> {
            // 处理事件逻辑
            PlayerData playerData = ((IPlayerDataProvider)player).getPlayerData();
            //我的playerData存储的速度信息目前会覆盖全部原版的速度更改
            if (playerData.isVelocityDirty()) {
                player.setVelocity(player.getVelocity());
                playerData.setVelocityDirty(false);
            }
            return ActionResult.PASS;
        });
    }
}
