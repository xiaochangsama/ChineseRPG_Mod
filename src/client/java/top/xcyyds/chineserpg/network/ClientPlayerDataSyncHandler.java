package top.xcyyds.chineserpg.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.util.Identifier;
import net.minecraft.nbt.NbtCompound;
import top.xcyyds.chineserpg.player.IPlayerDataProvider;

import static top.xcyyds.chineserpg.ChineseRPG.MOD_ID;

public class ClientPlayerDataSyncHandler {
    //注册客户端接收数据的监听器，使用lambda表达式定义了接收数据和处理过程（可以不使用lambda）
    /*
    https://fabricmc.net/wiki/zh_cn:tutorial:networking
    你应该先从网络线程上的数据包中读取所有数据然后再在客户端线程上安排任务。
    如果你尝试在客户端线程上读取数据，将收到与 ref count 有关的错误。
    如果一定要在客户端线程上读取数据，则需要 retain()（保留）这些数据，然后在客户端线程上读取。
    如果你 retain() 了数据，请确保在不再需要时 release()（释放）数据。
     */
    public static void registerClientReceiver() {
        ClientPlayNetworking.registerGlobalReceiver(new Identifier(MOD_ID, "player_data_sync"), (client, handler, buf, responseSender) -> {
            NbtCompound data = buf.readNbt();
            client.execute(() -> {
                // 此 lambda 中的所有内容都在渲染线程上运行
                if (client.player instanceof IPlayerDataProvider) {
                    ((IPlayerDataProvider) client.player).getPlayerData().readFromNbt(data);
                    client.player.readCustomDataFromNbt(data);
                }
            });
        });
    }
}
