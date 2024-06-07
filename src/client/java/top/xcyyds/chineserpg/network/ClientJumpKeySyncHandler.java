package top.xcyyds.chineserpg.network;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import top.xcyyds.chineserpg.ChineseRPG;


/**
 * 当玩家在空中跳跃的时候，从客户端向服务端发送空包
 */
public class ClientJumpKeySyncHandler {

    public static final Identifier JUMP_KEY_SYNC = new Identifier(ChineseRPG.MOD_ID, "jump_key_sync");

    //只会在空中的时候发包，所以发送空包
    public static void sendJumpKeyStatus(boolean onGround) {
        if (MinecraftClient.getInstance().getNetworkHandler() != null) {
            PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
                buf.writeBoolean(onGround);
            ClientPlayNetworking.send(JUMP_KEY_SYNC, buf);
        }
    }
}
