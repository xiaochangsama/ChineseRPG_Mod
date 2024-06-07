package top.xcyyds.chineserpg;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import top.xcyyds.chineserpg.hud.PlayerHudOverlay;
import top.xcyyds.chineserpg.key.JumpKey;
import top.xcyyds.chineserpg.network.ClientParticleSyncHandler;
import top.xcyyds.chineserpg.network.ClientPlayerDataSyncHandler;

public class ChineseRPGClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ClientPlayerDataSyncHandler.registerClientReceiver();
		JumpKey.registryJumpKey();
		ClientParticleSyncHandler.register();
		HudRenderCallback.EVENT.register(new PlayerHudOverlay());
	}
}
