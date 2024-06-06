package top.xcyyds.chineserpg.hud;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;
import top.xcyyds.chineserpg.player.data.IPlayerDataProvider;
import top.xcyyds.chineserpg.player.data.PlayerData;

public class PlayerHudOverlay implements HudRenderCallback {

    private static final Identifier ICON_TEXTURE = new Identifier("chineserpg", "textures/gui/icons.png");

    @Override
    public void onHudRender(DrawContext drawContext, float tickDelta) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player != null && client.player instanceof IPlayerDataProvider) {
            PlayerData playerData = ((IPlayerDataProvider) client.player).getPlayerData();
            renderInnerPowerBar(drawContext, playerData);
            renderMartialArtName(drawContext, playerData);
        }
    }

    private void renderInnerPowerBar(DrawContext drawContext, PlayerData playerData) {
        int x = 10;
        int y = 10;
        int width = 100;
        int height = 10;

        int maxInnerPower = (int) playerData.getInnerPowerMax();
        int currentInnerPower = (int) playerData.getInnerPower();

        // 背景条
        drawContext.fill(x, y, x + width, y + height, 0xFF555555);
        // 前景条
        if (maxInnerPower > 0) {
            int barWidth = (int) ((currentInnerPower / (float) maxInnerPower) * width);
            drawContext.fill(x, y, x + barWidth, y + height, 0xFF00FF00);
        }

        // 渲染内力值文本
        String innerPowerText = "内力: " + currentInnerPower + "/" + maxInnerPower;
        MinecraftClient client = MinecraftClient.getInstance();
        drawContext.drawTextWithShadow(client.textRenderer, innerPowerText, x, y + height + 2, 0xFFFFFF);
    }

    private void renderMartialArtName(DrawContext drawContext, PlayerData playerData) {
        if (playerData.getEquippedMartialArt() != null) {
            String martialArtName = playerData.getEquippedMartialArt().getName();
            MinecraftClient client = MinecraftClient.getInstance();
            int x = 10;
            int y = 30; // 调整 y 位置以更好地间隔
            drawContext.drawTextWithShadow(client.textRenderer, martialArtName, x, y, 0xFFFFFF);
        }
    }

    public static void register() {
        HudRenderCallback.EVENT.register(new PlayerHudOverlay());
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            // 可以在这里处理其他客户端tick事件
        });
    }
}
