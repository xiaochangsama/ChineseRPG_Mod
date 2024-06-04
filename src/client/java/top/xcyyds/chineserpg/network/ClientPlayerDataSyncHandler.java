package top.xcyyds.chineserpg.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.util.Identifier;
import net.minecraft.nbt.NbtCompound;
import top.xcyyds.chineserpg.PlayerPersistentDataProvider;

import static top.xcyyds.chineserpg.ChineseRPG.MOD_ID;

public class ClientPlayerDataSyncHandler {
    public static void registerClientReceiver() {
        ClientPlayNetworking.registerGlobalReceiver(new Identifier(MOD_ID, "player_data_sync"), (client, handler, buf, responseSender) -> {
            NbtCompound data = buf.readNbt();
            client.execute(() -> {
                if (client.player != null && client.player instanceof PlayerPersistentDataProvider) {
                    ((PlayerPersistentDataProvider) client.player).getPersistentData().readFromNbt(data);
                    // Log data for debugging
                    System.out.println("Client InnerPower: " + ((PlayerPersistentDataProvider) client.player).getPersistentData().getInnerPower());
                }
            });
        });
    }
}
