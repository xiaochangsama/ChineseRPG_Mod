package top.xcyyds.chineserpg;

import net.fabricmc.api.ClientModInitializer;
import top.xcyyds.chineserpg.network.ClientPlayerDataSyncHandler;

public class ChineseRPGClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ClientPlayerDataSyncHandler.registerClientReceiver();
	}
}
