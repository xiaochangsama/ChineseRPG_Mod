package top.xcyyds.chineserpg.network;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import static top.xcyyds.chineserpg.ChineseRPG.MOD_ID;

public class PlayerDataSyncHandler {
    // 传入data，将其发送给客户端
    public static void send(ServerPlayerEntity player, NbtCompound data) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeNbt(data);
        ServerPlayNetworking.send(player, new Identifier(MOD_ID, "player_data_sync"), buf);
    }
}
