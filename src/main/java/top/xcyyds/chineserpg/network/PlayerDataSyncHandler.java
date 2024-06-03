package top.xcyyds.chineserpg.network;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import top.xcyyds.chineserpg.PlayerPersistentData;

import static top.xcyyds.chineserpg.ChineseRPG.MODID;

public class PlayerDataSyncHandler {
    private static final int MESSAGE_ID = 0; // 消息ID

    public static void send(ServerPlayerEntity player, NbtCompound data) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeNbt(data);
        ServerPlayNetworking.send(player, new Identifier(MODID, "player_data_sync"), buf);
    }

    public static void receive(MinecraftServer server, ServerPlayerEntity player, PacketByteBuf buf, PacketSender sender) {
        NbtCompound data = buf.readNbt();
        // 这里可以添加接收数据后的处理逻辑，比如更新客户端数据
        //不确定是否正常
        player.readCustomDataFromNbt(data);
    }

    // 在你的Mod初始化代码中注册消息
    // ServerPlayNetworking.registerGlobalReceiver(new Identifier(MODID, "player_data_sync"), PlayerDataSyncHandler::receive);
}
