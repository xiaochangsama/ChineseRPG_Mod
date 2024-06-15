package top.xcyyds.chineserpg.network;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import static top.xcyyds.chineserpg.ChineseRPG.MOD_ID;

public class AnimationSyncHandler {
    public static final Identifier TRIGGER_ANIMATION_ID = new Identifier(MOD_ID, "trigger_animation");

    // 服务端调用，发送数据包给客户端
    public static void sendAnimationPacket(ServerPlayerEntity player, Identifier animationId) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeIdentifier(animationId);
        ServerPlayNetworking.send(player, TRIGGER_ANIMATION_ID, buf);
    }


}
