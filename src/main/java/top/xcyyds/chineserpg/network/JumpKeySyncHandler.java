package top.xcyyds.chineserpg.network;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;
import top.xcyyds.chineserpg.ChineseRPG;

public class JumpKeySyncHandler {

    public static final Identifier JUMP_KEY_SYNC = new Identifier(ChineseRPG.MOD_ID, "jump_key_sync");

    public static void register() {
        ServerPlayNetworking.registerGlobalReceiver(JUMP_KEY_SYNC, (server, player, handler, buf, responseSender) -> {
            boolean isJumping = buf.readBoolean();

            server.execute(() -> {
                if (player != null) {
                    // 处理跳跃逻辑，这里你可以根据需要进行扩展
                    if (isJumping) {
                        player.jump();
                    }
                }
            });
        });
    }
}
