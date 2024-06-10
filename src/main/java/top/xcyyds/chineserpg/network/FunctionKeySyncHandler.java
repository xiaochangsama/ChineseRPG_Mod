package top.xcyyds.chineserpg.network;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;
import top.xcyyds.chineserpg.ChineseRPG;

/**
 * 当客户端玩家按下功能键时，接收并处理数据包
 */
public class FunctionKeySyncHandler {

    public static final Identifier FUNCTION_KEY_SYNC = new Identifier(ChineseRPG.MOD_ID, "function_key_sync");

    public static void register() {
        ServerPlayNetworking.registerGlobalReceiver(FUNCTION_KEY_SYNC, (server, player, handler, buf, responseSender) -> {
            boolean pressed = buf.readBoolean();

            server.execute(() -> {
                if (player != null) {
                    // 处理功能键按下
                    if (pressed) {
                        // 你的逻辑
                        player.sendMessage(net.minecraft.text.Text.literal("功能键按下"), false);
                    } else {
                        // 你的逻辑
                        player.sendMessage(net.minecraft.text.Text.literal("功能键释放"), false);
                    }
                }
            });
        });
    }
}
