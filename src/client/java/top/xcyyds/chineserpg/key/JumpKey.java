package top.xcyyds.chineserpg.key;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;
import top.xcyyds.chineserpg.network.ClientJumpKeySyncHandler;

public class JumpKey {
    private static boolean wasPressed = false;

    public static void registryJumpKey() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            boolean isPressed = InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), GLFW.GLFW_KEY_SPACE);
            if (isPressed && !wasPressed) {
                if (MinecraftClient.getInstance().player != null && MinecraftClient.getInstance().getNetworkHandler() != null) {
                    //空格被按下一次，发送一次数据包
                    ClientJumpKeySyncHandler.sendJumpKeyStatus(true);
                }
            }
            wasPressed = isPressed;
        });
    }
}
