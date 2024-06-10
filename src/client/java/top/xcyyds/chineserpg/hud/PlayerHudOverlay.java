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
    private static final int INNER_POWER_BAR_X = 10;
    private static final int INNER_POWER_BAR_Y = 10;
    private static final int INNER_POWER_BAR_WIDTH = 100;
    private static final int INNER_POWER_BAR_HEIGHT = 10;
    private static final int TEXT_COLOR = 0xFFFFFF;
    private static final int BACKGROUND_COLOR = 0xFF555555;
    private static final int FOREGROUND_START_COLOR = 0xFF00FF00;
    private static final int FOREGROUND_END_COLOR = 0xFF0000FF; // 渐变色结束颜色
    private static final int BORDER_COLOR = 0xFF000000;
    private static final int BORDER_WIDTH = 1;

    @Override
    public void onHudRender(DrawContext drawContext, float tickDelta) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player instanceof IPlayerDataProvider) {
            PlayerData playerData = ((IPlayerDataProvider) client.player).getPlayerData();
            renderInnerPowerBar(drawContext, client, playerData);
            renderMartialArtName(drawContext, client, playerData);
        }
    }

    /**
     * Renders the inner power bar on the HUD.
     *
     * @param drawContext The draw context.
     * @param client      The Minecraft client instance.
     * @param playerData  The player data.
     */
    private void renderInnerPowerBar(DrawContext drawContext, MinecraftClient client, PlayerData playerData) {
        int maxInnerPower = (int) playerData.getInnerPowerMax();
        int currentInnerPower = (int) playerData.getInnerPower();

        // Draw background bar with border
        drawContext.fill(INNER_POWER_BAR_X - BORDER_WIDTH, INNER_POWER_BAR_Y - BORDER_WIDTH,
                INNER_POWER_BAR_X + INNER_POWER_BAR_WIDTH + BORDER_WIDTH, INNER_POWER_BAR_Y + INNER_POWER_BAR_HEIGHT + BORDER_WIDTH, BORDER_COLOR);
        drawContext.fill(INNER_POWER_BAR_X, INNER_POWER_BAR_Y,
                INNER_POWER_BAR_X + INNER_POWER_BAR_WIDTH, INNER_POWER_BAR_Y + INNER_POWER_BAR_HEIGHT, BACKGROUND_COLOR);

        // Draw foreground bar with gradient
        if (maxInnerPower > 0) {
            int barWidth = (int) ((currentInnerPower / (float) maxInnerPower) * INNER_POWER_BAR_WIDTH);
            drawGradientRect(drawContext, INNER_POWER_BAR_X, INNER_POWER_BAR_Y, INNER_POWER_BAR_X + barWidth, INNER_POWER_BAR_Y + INNER_POWER_BAR_HEIGHT,
                    FOREGROUND_START_COLOR, FOREGROUND_END_COLOR);
        }

        // Render inner power text
        String innerPowerText = "内力: " + currentInnerPower + "/" + maxInnerPower;
        drawContext.drawTextWithShadow(client.textRenderer, innerPowerText, INNER_POWER_BAR_X, INNER_POWER_BAR_Y + INNER_POWER_BAR_HEIGHT + 2, TEXT_COLOR);
    }

    /**
     * Draws a gradient rectangle.
     *
     * @param drawContext The draw context.
     * @param left        The left position.
     * @param top         The top position.
     * @param right       The right position.
     * @param bottom      The bottom position.
     * @param startColor  The start color.
     * @param endColor    The end color.
     */
    private void drawGradientRect(DrawContext drawContext, int left, int top, int right, int bottom, int startColor, int endColor) {
        // Custom gradient drawing implementation can be added here
        // This is a placeholder for the gradient drawing logic
        drawContext.fill(left, top, right, bottom, startColor); // Placeholder, replace with gradient logic
    }

    /**
     * Renders the name of the equipped martial art on the HUD.
     *
     * @param drawContext The draw context.
     * @param client      The Minecraft client instance.
     * @param playerData  The player data.
     */
    private void renderMartialArtName(DrawContext drawContext, MinecraftClient client, PlayerData playerData) {
        if (playerData.getEquippedMartialArt() != null) {
            String martialArtName = playerData.getEquippedMartialArt().getName();
            int nameX = INNER_POWER_BAR_X;
            int nameY = INNER_POWER_BAR_Y + INNER_POWER_BAR_HEIGHT + 20;

            // Draw background box with border
            drawContext.fill(nameX - BORDER_WIDTH, nameY - BORDER_WIDTH,
                    nameX + client.textRenderer.getWidth(martialArtName) + BORDER_WIDTH, nameY + client.textRenderer.fontHeight + BORDER_WIDTH, BORDER_COLOR);
            drawContext.fill(nameX, nameY,
                    nameX + client.textRenderer.getWidth(martialArtName), nameY + client.textRenderer.fontHeight, BACKGROUND_COLOR);

            // Render martial art name text
            drawContext.drawTextWithShadow(client.textRenderer, martialArtName, nameX, nameY, TEXT_COLOR);
        }
    }

    /**
     * Registers the HUD overlay and client tick events.
     */
    public static void register() {
        HudRenderCallback.EVENT.register(new PlayerHudOverlay());
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            // Handle other client tick events here if needed
        });
    }
}
