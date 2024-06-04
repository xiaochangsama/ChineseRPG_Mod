package top.xcyyds.chineserpg.network;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import top.xcyyds.chineserpg.PlayerPersistentDataProvider;

import static top.xcyyds.chineserpg.ChineseRPG.MOD_ID;

public class PlayerDataSyncHandler {
    //传入data的buf，将其发送给客户端
    public static void send(ServerPlayerEntity player) {
        PacketByteBuf buf = ((PlayerPersistentDataProvider) player).getPersistentData().writeToBuf();
        ServerPlayNetworking.send(player, new Identifier(MOD_ID, "player_data_sync"),  buf);
    }
////服务端实例接收客户端数据，不处理
//    public static void receive(MinecraftServer server, ServerPlayerEntity player, PacketByteBuf buf, PacketSender responseSender) {
//        NbtCompound data = buf.readNbt();
//        server.execute(() -> {
//        });
//    }
////注册服务端接收器
//    public static void registerServerReceiver() {
//        ServerPlayNetworking.registerGlobalReceiver(new Identifier(MOD_ID, "player_data_sync"), (server, player, handler, buf, responseSender) -> {
//            receive(server, player, buf, responseSender);
//        });
//    }
}
