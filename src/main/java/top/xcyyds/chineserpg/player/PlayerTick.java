package top.xcyyds.chineserpg.player;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import top.xcyyds.chineserpg.event.PlayerTickCallback;
import top.xcyyds.chineserpg.martialart.MartialArt;
import top.xcyyds.chineserpg.martialart.MartialArtEntry;
import top.xcyyds.chineserpg.network.PlayerDataSyncHandler;

/**
 * 用于处理玩家的Tick事件
 */
public class PlayerTick {

    public static void register() {
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
                PlayerTickCallback.EVENT.invoker().interact(player);
                PlayerData playerData = ((IPlayerDataProvider) player).getPlayerData();
                // 调用updateJumpCount，玩家在地面上时更新跳跃次数
                updateJumpCount(player, playerData);
                // 调用内力恢复
                playerData.tickRegenerateInnerPower();

                // 同步全部玩家数据到客户端，之后可能要更改数据同步量节省网络消耗
                NbtCompound data = new NbtCompound();
                playerData.writeToNbt(data);
                PlayerDataSyncHandler.send(player, data);
            }
        });
    }

    public static void updateJumpCount(ServerPlayerEntity player, PlayerData playerData) {
        if (player.isOnGround()) {
            MartialArt equippedMartialArt = playerData.getEquippedMartialArt();
            if (equippedMartialArt != null) {
                for (MartialArtEntry entry : equippedMartialArt.getEntries()) {
                    if (PlayerJumpHandler.DOUBLE_JUMP.equals(entry.getJumpType())) {
                        playerData.setJumpCount(entry.getJumpCount());
                        break;
                    }
                }
            }
        }
    }
}
