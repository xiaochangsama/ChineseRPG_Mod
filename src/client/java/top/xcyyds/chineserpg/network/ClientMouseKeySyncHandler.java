package top.xcyyds.chineserpg.network;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import top.xcyyds.chineserpg.ChineseRPG;

public class ClientMouseKeySyncHandler {
    public static final Identifier MOUSE_KEY_SYNC = new Identifier(ChineseRPG.MOD_ID, "mouse_key_sync");

    public static void sendMouseKeyStatus(boolean leftPressed, boolean rightPressed) {
        if (MinecraftClient.getInstance().getNetworkHandler() != null) {
            PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());

                buf.writeBoolean(leftPressed);
                buf.writeBoolean(rightPressed);
                ClientPlayNetworking.send(MOUSE_KEY_SYNC, buf);

            }
        }
    }

