package top.xcyyds.chineserpg.network;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;
import top.xcyyds.chineserpg.ChineseRPG;
import top.xcyyds.chineserpg.constants.JumpConstants;
import top.xcyyds.chineserpg.martialart.MartialArtEntry;
import top.xcyyds.chineserpg.player.IPlayerDataProvider;
import top.xcyyds.chineserpg.player.PlayerData;
import top.xcyyds.chineserpg.player.PlayerJumpHandler;

public class JumpKeySyncHandler {

    public static final Identifier JUMP_KEY_SYNC = new Identifier(ChineseRPG.MOD_ID, "jump_key_sync");

    public static void register() {
        ServerPlayNetworking.registerGlobalReceiver(JUMP_KEY_SYNC, (server, player, handler, buf, responseSender) -> {
            boolean isJumping = buf.readBoolean();

            server.execute(() -> {
                if (player != null && isJumping) {
                    PlayerData playerData = ((IPlayerDataProvider) player).getPlayerData();

                    // 如果玩家不在地面上，且装备了二段跳，并且跳跃计数大于等于1
                    if (!player.isOnGround() && playerData.getEquippedMartialArt() != null) {
                        for (MartialArtEntry entry : playerData.getEquippedMartialArt().getEntries()) {
                            if ("二段跳".equals(entry.getJumpType()) && playerData.getJumpCount() >= 1) {
                                PlayerJumpHandler.basicJump(player, JumpConstants.BASE_JUMP_VELOCITY);
                                playerData.setJumpCount(playerData.getJumpCount() - 1);
                                break;
                            }
                        }
                    }
                }
            });
        });
    }
}
