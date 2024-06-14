package top.xcyyds.chineserpg.network;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import top.xcyyds.chineserpg.ChineseRPG;
import top.xcyyds.chineserpg.player.ActionType;
import top.xcyyds.chineserpg.player.data.IPlayerDataProvider;
import top.xcyyds.chineserpg.player.data.PlayerData;

public class ClientMouseKeySyncHandler {
    public static final Identifier LEFT_MOUSE_KEY_SYNC = new Identifier(ChineseRPG.MOD_ID, "left_mouse_key_sync");
    public static final Identifier RIGHT_MOUSE_KEY_SYNC = new Identifier(ChineseRPG.MOD_ID, "right_mouse_key_sync");

    public static void sendLeftMouseKeyStatus(boolean leftPressed) {
        if (MinecraftClient.getInstance().getNetworkHandler() != null) {
            PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
            buf.writeBoolean(leftPressed);
            ClientPlayNetworking.send(LEFT_MOUSE_KEY_SYNC, buf);
            PlayerData playerData = ((IPlayerDataProvider) MinecraftClient.getInstance().player).getPlayerData();
            if(leftPressed){playerData.recordAction(ActionType.LEFT_CLICK);}
        }
    }

    public static void sendRightMouseKeyStatus(boolean rightPressed) {
        if (MinecraftClient.getInstance().getNetworkHandler() != null) {
            PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
            buf.writeBoolean(rightPressed);
            ClientPlayNetworking.send(RIGHT_MOUSE_KEY_SYNC, buf);
            PlayerData playerData = ((IPlayerDataProvider) MinecraftClient.getInstance().player).getPlayerData();
            if(rightPressed){playerData.recordAction(ActionType.RIGHT_CLICK);}
        }
    }
}
