package top.xcyyds.chineserpg.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.util.Identifier;
import net.minecraft.nbt.NbtCompound;
import top.xcyyds.chineserpg.player.PlayerDataProvider;

import static top.xcyyds.chineserpg.ChineseRPG.MOD_ID;

public class ClientPlayerDataSyncHandler {
    //注册客户端接收数据的监听器，使用lambda表达式定义了接收数据和处理过程（可以不使用lambda）

    public static void registerClientReceiver() {
        ClientPlayNetworking.registerGlobalReceiver(new Identifier(MOD_ID, "player_data_sync"), (client, handler, buf, responseSender) -> {
            NbtCompound data = buf.readNbt();
            client.execute(() -> {
                // 此 lambda 中的所有内容都在渲染线程上运行
                if (client.player instanceof PlayerDataProvider) {
                    ((PlayerDataProvider) client.player).getPersistentData().readFromNbt(data);
                    client.player.readCustomDataFromNbt(data);
                    // Log data for debugging
                    System.out.println("Client InnerPower: " + ((PlayerDataProvider) client.player).getPersistentData().getInnerPower());
                }
            });
        });
    }
}
