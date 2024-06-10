package top.xcyyds.chineserpg.network;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import top.xcyyds.chineserpg.ChineseRPG;

public class ClientFunctionKeySyncHandler {
    public static final Identifier FUNCTION_KEY_SYNC = new Identifier(ChineseRPG.MOD_ID, "function_key_sync");

    public static void sendFunctionKeyStatus(boolean pressed) {
        if (MinecraftClient.getInstance().getNetworkHandler() != null) {
            PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
            buf.writeBoolean(pressed);
            ClientPlayNetworking.send(FUNCTION_KEY_SYNC, buf);
        }
    }
}
