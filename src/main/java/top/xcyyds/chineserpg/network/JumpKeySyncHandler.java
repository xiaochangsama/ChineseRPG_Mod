package top.xcyyds.chineserpg.network;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;
import top.xcyyds.chineserpg.ChineseRPG;


/**
 * 当客户端玩家在空中跳跃的时候发送空包
 */
public class JumpKeySyncHandler {

    public static final Identifier JUMP_KEY_SYNC = new Identifier(ChineseRPG.MOD_ID, "jump_key_sync");

    public static void register() {
        ServerPlayNetworking.registerGlobalReceiver(JUMP_KEY_SYNC, (server, player, handler, buf, responseSender) -> {


            server.execute(() -> {
                if (player != null) {


                }
            });
        });
    }
}
