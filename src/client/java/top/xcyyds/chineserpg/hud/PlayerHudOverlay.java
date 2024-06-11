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
        if (client.player instanceof IPlayerDataProvider) {
            PlayerData playerData = ((IPlayerDataProvider) client.player).getPlayerData();
            renderInnerPowerBar(drawContext, playerData);
            renderMartialArtName(drawContext, playerData);
        }
    }

    private void renderInnerPowerBar(DrawContext drawContext, PlayerData playerData) {
        int x = 10;
        int y = 10;
        int width = 101;
        int height = 10;
        int segmentCount = 34; // 将内力条分成34份
        int segmentSpacing = 1; // 每个格子之间的间隔
        int segmentWidth = (width - (segmentSpacing * (segmentCount - 1))) / segmentCount; // 每个格子的宽度

        int maxInnerPower = (int) playerData.getInnerPowerMax();
        int currentInnerPower = (int) playerData.getInnerPower();

        // 背景条
        drawContext.fill(x, y, x + width, y + height, 0xFF555555);

        // 前景条和颜色渐变
        if (maxInnerPower > 0) {
            int fullSegments = (currentInnerPower * segmentCount) / maxInnerPower;
            for (int i = 0; i < fullSegments; i++) {
                int color = interpolateColor(0xFF00FF00, 0xFF003300, i / (float) segmentCount);
                drawContext.fill(x + i * (segmentWidth + segmentSpacing), y, x + i * (segmentWidth + segmentSpacing) + segmentWidth, y + height, color);
            }
        }

        // 渲染内力值文本
        String innerPowerText = currentInnerPower + "/" + maxInnerPower;
        MinecraftClient client = MinecraftClient.getInstance();
        drawContext.drawTextWithShadow(client.textRenderer, innerPowerText, x + width / 2 - client.textRenderer.getWidth(innerPowerText) / 2, y + 1, 0xFFFFFF);
    }

    private int interpolateColor(int startColor, int endColor, float ratio) {
        int startA = (startColor >> 24) & 0xFF;
        int startR = (startColor >> 16) & 0xFF;
        int startG = (startColor >> 8) & 0xFF;
        int startB = startColor & 0xFF;

        int endA = (endColor >> 24) & 0xFF;
        int endR = (endColor >> 16) & 0xFF;
        int endG = (endColor >> 8) & 0xFF;
        int endB = endColor & 0xFF;

        int a = (int) (startA + ratio * (endA - startA));
        int r = (int) (startR + ratio * (endR - startR));
        int g = (int) (startG + ratio * (endG - startG));
        int b = (int) (startB + ratio * (endB - startB));

        return (a << 24) | (r << 16) | (g << 8) | b;
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
