package top.xcyyds.chineserpg.network;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;
import top.xcyyds.chineserpg.ChineseRPG;
import top.xcyyds.chineserpg.player.ActionType;
import top.xcyyds.chineserpg.player.data.IPlayerDataProvider;
import top.xcyyds.chineserpg.player.data.PlayerData;


/**
 * 当客户端玩家按下鼠标左右键时，接收并处理数据包
 */
public class MouseKeySyncHandler {

    public static final Identifier LEFT_MOUSE_KEY_SYNC = new Identifier(ChineseRPG.MOD_ID, "left_mouse_key_sync");
    public static final Identifier RIGHT_MOUSE_KEY_SYNC = new Identifier(ChineseRPG.MOD_ID, "right_mouse_key_sync");

    public static void register() {
        ServerPlayNetworking.registerGlobalReceiver(LEFT_MOUSE_KEY_SYNC, (server, player, handler, buf, responseSender) -> {
            boolean leftPressed = buf.readBoolean();

            server.execute(() -> {
                if (player != null) {
                    PlayerData playerData = ((IPlayerDataProvider) player).getPlayerData(); // 获取玩家数据

                    // 处理左键按下
                    if (leftPressed) {
                        // 你的逻辑
                        playerData.recordAction(ActionType.LEFT_CLICK);

//                        player.sendMessage(net.minecraft.text.Text.literal("左键按下"), false);
                    } else {
                        // 你的逻辑
//                        player.sendMessage(net.minecraft.text.Text.literal("左键释放"), false);
                    }
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(RIGHT_MOUSE_KEY_SYNC, (server, player, handler, buf, responseSender) -> {
            boolean rightPressed = buf.readBoolean();

            server.execute(() -> {
                if (player != null) {
                    PlayerData playerData = ((IPlayerDataProvider) player).getPlayerData(); // 获取玩家数据

                    // 处理右键按下
                    if (rightPressed) {
                        playerData.recordAction(ActionType.RIGHT_CLICK);

                        // 你的逻辑
//                        player.sendMessage(net.minecraft.text.Text.literal("右键按下"), false);
                    } else {
                        // 你的逻辑
//                        player.sendMessage(net.minecraft.text.Text.literal("右键释放"), false);
                    }
                }
            });
        });
    }
}
