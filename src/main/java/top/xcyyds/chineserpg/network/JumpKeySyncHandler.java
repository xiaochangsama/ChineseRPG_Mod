package top.xcyyds.chineserpg.network;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;
import top.xcyyds.chineserpg.ChineseRPG;
import top.xcyyds.chineserpg.player.IPlayerDataProvider;
import top.xcyyds.chineserpg.player.PlayerData;


/**
 * 从客户端收取跳跃按键的状态以及玩家是否在地面上的状态
 */
public class JumpKeySyncHandler {

    public static final Identifier JUMP_KEY_SYNC = new Identifier(ChineseRPG.MOD_ID, "jump_key_sync");

    public static void register() {
        ServerPlayNetworking.registerGlobalReceiver(JUMP_KEY_SYNC, (server, player, handler, buf, responseSender) -> {
            boolean isJumping = buf.readBoolean();
            boolean isOnGround = buf.readBoolean();

            server.execute(() -> {
                if (player != null) {
                    PlayerData playerData = ((IPlayerDataProvider) player).getPlayerData();
                    playerData.setJumping(isJumping);
                    playerData.setOnGround(isOnGround);  // 保存玩家是否在地面上的状态
                }
            });
        });
    }
}
