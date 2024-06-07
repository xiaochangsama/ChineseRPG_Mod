package top.xcyyds.chineserpg.network;


import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;
import top.xcyyds.chineserpg.ChineseRPG;
import top.xcyyds.chineserpg.player.data.IPlayerDataProvider;
import top.xcyyds.chineserpg.player.data.PlayerData;



/**
 * 当客户端玩家在空中跳跃的时候发送空包
 */
public class JumpKeySyncHandler {

    public static final Identifier JUMP_KEY_SYNC = new Identifier(ChineseRPG.MOD_ID, "jump_key_sync");
    public static boolean wasPressed = false;

    public static void register() {
        ServerPlayNetworking.registerGlobalReceiver(JUMP_KEY_SYNC, (server, player, handler, buf, responseSender) -> {


            server.execute(() -> {
                if (player != null) {
                    PlayerData playerData = ((IPlayerDataProvider)player).getPlayerData();
//                    //实现对空中眺时对速度的操作
//                    PlayerJumpHandler.toJump(player,playerData);
//                    sendJumpKeyStatus(player);
                    wasPressed = true;


                }
            });
        });
    }
}
