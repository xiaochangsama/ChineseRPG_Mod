package top.xcyyds.chineserpg;

import net.fabricmc.api.ClientModInitializer;
import top.xcyyds.chineserpg.network.ClientPlayerDataSyncHandler;
import top.xcyyds.chineserpg.network.PlayerDataSyncHandler;

public class ChineseRPGClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ClientPlayerDataSyncHandler.registerClientReceiver();
	}
}
