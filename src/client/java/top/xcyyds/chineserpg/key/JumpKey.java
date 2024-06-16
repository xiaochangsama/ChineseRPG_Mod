package top.xcyyds.chineserpg.key;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;
import top.xcyyds.chineserpg.animation.PlayerAnimationController;
import top.xcyyds.chineserpg.network.ClientJumpKeySyncHandler;
import top.xcyyds.chineserpg.network.JumpKeySyncHandler;

//每一tick检测，只会在空中点按空格的时候发包
public class JumpKey {
    private static boolean wasPressed = false;
    private static boolean wasOnGround = false;

    public static void registryJumpKey() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            boolean isPressed = InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), GLFW.GLFW_KEY_SPACE);

            if (MinecraftClient.getInstance().player != null) {
                ClientPlayerEntity player = MinecraftClient.getInstance().player;
                boolean isOnGround = player.isOnGround();
                boolean isRiding = player.hasVehicle(); // 检查玩家是否骑马


                if (!isRiding) { // 如果玩家没有骑马
                    //玩家在空中按下空格
                    if (isPressed && !wasPressed && !isOnGround && !wasOnGround) {
                        if (MinecraftClient.getInstance().player != null && MinecraftClient.getInstance().getNetworkHandler() != null) {
                            // 发包
                            PlayerAnimationController.playAnimation(player, "jump");

                            ClientJumpKeySyncHandler.sendJumpKeyStatus(false);
                            JumpKeySyncHandler.wasPressed = true;
                        }
                    } else {
                        if (isPressed && !wasPressed) {
                            if (MinecraftClient.getInstance().player != null && MinecraftClient.getInstance().getNetworkHandler() != null) {
                                // 发包
                                ClientJumpKeySyncHandler.sendJumpKeyStatus(true);
                                JumpKeySyncHandler.wasPressedOnGround = true;
                            }
                        }
                    }
                }
                wasPressed = isPressed;
            wasOnGround = isOnGround;
            }
        });
    }
}
