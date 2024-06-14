package top.xcyyds.chineserpg.key;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.minecraft.client.MinecraftClient;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWMouseButtonCallbackI;
import top.xcyyds.chineserpg.item.ChineseRPGJianItem;
import top.xcyyds.chineserpg.network.ClientMouseKeySyncHandler;

public class MouseKeyHandler {
    private static boolean isLeftPressed = false;
    private static boolean isRightPressed = false;
    private static GLFWMouseButtonCallbackI originalMouseButtonCallback;

    public static void registerMouseKeyHandler() {
        ClientLifecycleEvents.CLIENT_STARTED.register(client -> {
            long windowHandle = MinecraftClient.getInstance().getWindow().getHandle();

            // Save the original callback
            originalMouseButtonCallback = GLFW.glfwSetMouseButtonCallback(windowHandle, (window, button, action, mods) -> {
                if (originalMouseButtonCallback != null) {
                    originalMouseButtonCallback.invoke(window, button, action, mods);
                }

                if (button == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
                    boolean previousLeftPressed = isLeftPressed;
                    isLeftPressed = action == GLFW.GLFW_PRESS;
                    if (isLeftPressed != previousLeftPressed) {
                        ClientMouseKeySyncHandler.sendLeftMouseKeyStatus(isLeftPressed);



                    }
                } else if (button == GLFW.GLFW_MOUSE_BUTTON_RIGHT) {
                    boolean previousRightPressed = isRightPressed;
                    isRightPressed = action == GLFW.GLFW_PRESS;
                    if (isRightPressed != previousRightPressed) {
                        ClientMouseKeySyncHandler.sendRightMouseKeyStatus(isRightPressed);

                        // test：假设你有一个 ChineseRPGJianItem 实例
                        if (MinecraftClient.getInstance().player != null && MinecraftClient.getInstance().player.getMainHandStack().getItem() instanceof ChineseRPGJianItem item) {
                            // 在满足特定条件时调用
                            item.playThrustAnimation(MinecraftClient.getInstance().player);
                        }

                    }
                }
            });
        });
    }
}
