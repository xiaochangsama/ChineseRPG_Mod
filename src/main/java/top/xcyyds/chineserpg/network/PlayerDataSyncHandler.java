package top.xcyyds.chineserpg.network;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.nbt.NbtCompound;
import top.xcyyds.chineserpg.PlayerPersistentDataProvider;

import static top.xcyyds.chineserpg.ChineseRPG.MODID;

public class PlayerDataSyncHandler {
    public static void send(ServerPlayerEntity player, NbtCompound data) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeNbt(data);
        ServerPlayNetworking.send(player, new Identifier(MODID, "player_data_sync"), buf);
    }

    public static void receive(MinecraftServer server, ServerPlayerEntity player, PacketByteBuf buf, PacketSender responseSender) {
        NbtCompound data = buf.readNbt();
        server.execute(() -> {
            if (data != null && player instanceof PlayerPersistentDataProvider) {
                ((PlayerPersistentDataProvider) player).getPersistentData().readFromNbt(data);
            }
        });
    }

    public static void registerServerReceiver() {
        ServerPlayNetworking.registerGlobalReceiver(new Identifier(MODID, "player_data_sync"), (server, player, handler, buf, responseSender) -> {
            receive(server, player, buf, responseSender);
        });
    }
}
