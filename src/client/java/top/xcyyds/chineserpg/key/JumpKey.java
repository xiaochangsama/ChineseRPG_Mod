package top.xcyyds.chineserpg.key;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;
import top.xcyyds.chineserpg.network.ClientJumpKeySyncHandler;

import java.util.Objects;

public class JumpKey {
    private static boolean wasPressed = false;
    private static boolean wasOnGround = false;

    public static void registryJumpKey() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            boolean isPressed = InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), GLFW.GLFW_KEY_SPACE);

            if (MinecraftClient.getInstance().player != null) {
                boolean isOnGround = MinecraftClient.getInstance().player.isOnGround();

            if ((isPressed && !wasPressed) && (isOnGround == wasOnGround ) ) {
                if (MinecraftClient.getInstance().player != null && MinecraftClient.getInstance().getNetworkHandler() != null) {
                    ClientJumpKeySyncHandler.sendJumpKeyStatus(true, isOnGround);
                }
            }
            wasPressed = isPressed;
            wasOnGround = isOnGround;
            }
        });
    }
}
