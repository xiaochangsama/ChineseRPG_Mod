package top.xcyyds.chineserpg.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.nbt.NbtCompound;
import top.xcyyds.chineserpg.PlayerPersistentDataProvider;

import static top.xcyyds.chineserpg.ChineseRPG.MODID;

public class ClientPlayerDataSyncHandler {
    public static void registerClientReceiver() {
        ClientPlayNetworking.registerGlobalReceiver(new Identifier(MODID, "player_data_sync"), (client, handler, buf, responseSender) -> {
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
