package top.xcyyds.chineserpg;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.entity.EntityType;
import top.xcyyds.chineserpg.hud.PlayerHudOverlay;
import top.xcyyds.chineserpg.key.FunctionKeyHandler;
import top.xcyyds.chineserpg.key.JumpKey;
import top.xcyyds.chineserpg.network.ClientParticleSyncHandler;
import top.xcyyds.chineserpg.network.ClientPlayerDataSyncHandler;
import top.xcyyds.chineserpg.renderer.ReplacedPlayerRenderer;

import static top.xcyyds.chineserpg.key.MouseKeyHandler.registerMouseKeyHandler;


public class ChineseRPGClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ClientPlayerDataSyncHandler.registerClientReceiver();
		ClientParticleSyncHandler.register();

		JumpKey.registryJumpKey();
		registerMouseKeyHandler();
		FunctionKeyHandler.registerFunctionKeyHandler();

		HudRenderCallback.EVENT.register(new PlayerHudOverlay());

		EntityRendererRegistry.register(EntityType.PLAYER, ReplacedPlayerRenderer::new);
	}
}
