package top.xcyyds.chineserpg;

import net.fabricmc.api.ClientModInitializer;
import top.xcyyds.chineserpg.network.ClientPlayerDataSyncHandler;
import top.xcyyds.chineserpg.key.JumpKey;

//https://fabricmc.net/wiki/zh_cn:documentation:entrypoint
public class ChineseRPGClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ClientPlayerDataSyncHandler.registerClientReceiver();

		JumpKey.registryJumpKey();
	}
}
