package top.xcyyds.chineserpg;

import net.fabricmc.api.ClientModInitializer;
import top.xcyyds.chineserpg.client.PlayerHudOverlay;
import top.xcyyds.chineserpg.network.ClientPlayerDataSyncHandler;
import top.xcyyds.chineserpg.key.JumpKey;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

public class ChineseRPGClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ClientPlayerDataSyncHandler.registerClientReceiver();
		JumpKey.registryJumpKey();
		HudRenderCallback.EVENT.register(new PlayerHudOverlay());
	}
}
