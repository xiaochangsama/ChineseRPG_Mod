package top.xcyyds.chineserpg.event;

import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import top.xcyyds.chineserpg.network.PlayerDataSyncHandler;
import top.xcyyds.chineserpg.player.IPlayerDataProvider;

public class PlayerJoinCallback {

    public static void register() {
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            ServerPlayerEntity player = handler.getPlayer();
            if (player instanceof IPlayerDataProvider) {
                NbtCompound data = new NbtCompound();
                ((IPlayerDataProvider) player).getPlayerData().writeToNbt(data);
                PlayerDataSyncHandler.send(player, data);
            }
        });
    }
}
