package top.xcyyds.chineserpg.network;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import top.xcyyds.chineserpg.ChineseRPG;


/**
 * 从客户端向服务端发送键盘空格被按压的状态和玩家是否在地面上的状态
 */
public class ClientJumpKeySyncHandler {

    public static final Identifier JUMP_KEY_SYNC = new Identifier(ChineseRPG.MOD_ID, "jump_key_sync");

    //发送客户端键盘空格被按压的状态和玩家是否在地面上
    public static void sendJumpKeyStatus(boolean isJumping, boolean isOnGround) {
        if (MinecraftClient.getInstance().getNetworkHandler() != null) {
            PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
            buf.writeBoolean(isJumping);
            buf.writeBoolean(isOnGround);

            ClientPlayNetworking.send(JUMP_KEY_SYNC, buf);
        }
    }
}
