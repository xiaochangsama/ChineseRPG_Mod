package top.xcyyds.chineserpg.event;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import top.xcyyds.chineserpg.network.PlayerDataSyncHandler;
import top.xcyyds.chineserpg.player.data.IPlayerDataProvider;
import top.xcyyds.chineserpg.player.data.PlayerData;
import top.xcyyds.chineserpg.player.jump.PlayerJumpHelper;


public class EndServerTickEvent {

    public static void register() {
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {

                PlayerData playerData = ((IPlayerDataProvider) player).getPlayerData();
                // 调用updateJumpCount，玩家在地面上时更新跳跃次数
                PlayerJumpHelper.updateJumpCount(player, playerData);
                // 调用内力恢复
                playerData.tickRegenerateInnerPower();

                // 同步全部玩家数据到客户端，之后可能要更改数据同步量节省网络消耗
                NbtCompound data = new NbtCompound();
                playerData.writeToNbt(data);
                PlayerDataSyncHandler.send(player, data);
            }
        });
    }

}
