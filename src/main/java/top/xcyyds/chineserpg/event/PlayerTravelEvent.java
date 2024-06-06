package top.xcyyds.chineserpg.event;


import net.minecraft.util.ActionResult;

/**
 * 在这个类里可以修改玩家的速度矢量并应用
 */
public class PlayerTravelEvent {
    public static void register() {
        PlayerTravelCallback.EVENT.register((player, travelVector) -> {
            // 处理事件逻辑

            return ActionResult.PASS;
        });
    }
}
