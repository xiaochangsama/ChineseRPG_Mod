package top.xcyyds.chineserpg.key;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;
import top.xcyyds.chineserpg.network.ClientFunctionKeySyncHandler;

public class FunctionKeyHandler {
    private static KeyBinding functionKey;
    private static boolean wasPressed = false;

    public static void registerFunctionKeyHandler() {
        // 初始化功能键
        functionKey = new KeyBinding(
                "key.chineserpg.function",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_LEFT_ALT,
                "key.categories.chineserpg"
        );

        // 注册按键事件
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            boolean isPressed = functionKey.isPressed();
            if (isPressed != wasPressed) {
                ClientFunctionKeySyncHandler.sendFunctionKeyStatus(isPressed);
                wasPressed = isPressed;
            }
        });
    }
}
