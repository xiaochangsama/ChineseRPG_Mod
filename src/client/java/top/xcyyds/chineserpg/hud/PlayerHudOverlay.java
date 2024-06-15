package top.xcyyds.chineserpg.hud;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import top.xcyyds.chineserpg.martialart.skill.MartialArt;
import top.xcyyds.chineserpg.player.data.IPlayerDataProvider;
import top.xcyyds.chineserpg.player.data.PlayerData;
import top.xcyyds.chineserpg.registry.MartialArtRegistry;

public class PlayerHudOverlay implements HudRenderCallback {

    @Override
    public void onHudRender(DrawContext drawContext, float tickDelta) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player instanceof IPlayerDataProvider) {
            PlayerData playerData = ((IPlayerDataProvider) client.player).getPlayerData();
            renderInnerPowerBar(drawContext, playerData, 3, 1, 34); // 设置格子宽度为3，间隔为1，格子数量为34
            renderMartialArtNames(drawContext, playerData);
        }
    }

    private void renderInnerPowerBar(DrawContext drawContext, PlayerData playerData, int segmentWidth, int segmentSpacing, int segmentCount) {
        int x = 10;
        int y = 10;
        int width = segmentCount * (segmentWidth + segmentSpacing) - segmentSpacing;
        int height = 10;

        int maxInnerPower = (int) playerData.getInnerPowerMax();
        int currentInnerPower = (int) playerData.getInnerPower();

        // 前景条和颜色渐变
        if (maxInnerPower > 0) {
            int fullSegments = (currentInnerPower * segmentCount) / maxInnerPower;
            for (int i = 0; i < fullSegments; i++) {
                int color = interpolateColor(0xFF00FF00, 0xFF336633, i / (float) segmentCount);
                drawContext.fill(x + i * (segmentWidth + segmentSpacing), y + 1, x + i * (segmentWidth + segmentSpacing) + segmentWidth, y + height - 1, color);
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

    private void renderMartialArtNames(DrawContext drawContext, PlayerData playerData) {
        MinecraftClient client = MinecraftClient.getInstance();
        int x = 10;
        int y = 30;

        // 渲染装备的轻功名称
        if (playerData.getEquippedLightSkill() != null) {
            MartialArt lightSkill = MartialArtRegistry.getMartialArt(playerData.getEquippedLightSkill());
            if (lightSkill != null) {
                String lightSkillName = lightSkill.getName();
                drawContext.drawTextWithShadow(client.textRenderer, "轻功: " + lightSkillName, x, y, 0xFFFFFF);
                y += 10; // 逐行显示，调整 y 位置
            }
        }

        // 渲染装备的外功名称
        if (playerData.getEquippedOuterSkill() != null) {
            MartialArt outerSkill = MartialArtRegistry.getMartialArt(playerData.getEquippedOuterSkill());
            if (outerSkill != null) {
                String outerSkillName = outerSkill.getName();
                drawContext.drawTextWithShadow(client.textRenderer, "外功: " + outerSkillName, x, y, 0xFFFFFF);
            }
        }
    }

    public static void register() {
        HudRenderCallback.EVENT.register(new PlayerHudOverlay());
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            // 可以在这里处理其他客户端tick事件
        });
    }
}
