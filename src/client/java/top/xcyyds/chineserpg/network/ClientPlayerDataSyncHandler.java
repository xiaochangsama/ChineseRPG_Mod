package top.xcyyds.chineserpg.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.util.Identifier;
import net.minecraft.nbt.NbtCompound;
import top.xcyyds.chineserpg.PlayerPersistentDataProvider;

import static top.xcyyds.chineserpg.ChineseRPG.MOD_ID;

public class ClientPlayerDataSyncHandler {
    //注册客户端接收数据的监听器，使用lambda表达式定义了接收数据和处理过程（可以不使用lambda）

    public static void registerClientReceiver() {
        ClientPlayNetworking.registerGlobalReceiver(new Identifier(MOD_ID, "player_data_sync"), (client, handler, buf, responseSender) -> {
            NbtCompound data = buf.readNbt();
            client.execute(() -> {
                if (client.player instanceof PlayerPersistentDataProvider) {
                    ((PlayerPersistentDataProvider) client.player).getPersistentData().readFromNbt(data);
                    client.player.readCustomDataFromNbt(data);
                    // Log data for debugging
                    System.out.println("Client InnerPower: " + ((PlayerPersistentDataProvider) client.player).getPersistentData().getInnerPower());
                }
            });
        });
    }
}
