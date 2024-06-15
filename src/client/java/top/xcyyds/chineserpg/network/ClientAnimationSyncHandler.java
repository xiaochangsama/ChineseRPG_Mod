package top.xcyyds.chineserpg.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.util.Identifier;
import top.xcyyds.chineserpg.item.ChineseRPGJianItem;

import static top.xcyyds.chineserpg.ChineseRPG.MOD_ID;
import static top.xcyyds.chineserpg.network.AnimationSyncHandler.TRIGGER_ANIMATION_ID;

public class ClientAnimationSyncHandler {
    // 客户端注册处理器，接收数据包并触发动画
    public static void registerClientHandler() {
        ClientPlayNetworking.registerGlobalReceiver(TRIGGER_ANIMATION_ID, (client, handler, buf, responseSender) -> {
            Identifier animationId = buf.readIdentifier();
            client.execute(() -> {
                if (client.player != null && client.player.getMainHandStack().getItem() instanceof ChineseRPGJianItem item) {
                    if (animationId.equals(new Identifier(MOD_ID, "piercing_thrust"))) {
                        item.playThrustAnimation(client.player);
                    }
                }
            });
        });
    }
}
