package top.xcyyds.chineserpg;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.xcyyds.chineserpg.event.FabricEventManager;
import top.xcyyds.chineserpg.event.PlayerJoinCallback;
import top.xcyyds.chineserpg.item.BooksItem;
import top.xcyyds.chineserpg.itemgroup.ChineseRPGItemGroup;
import top.xcyyds.chineserpg.network.JumpKeySyncHandler;
import top.xcyyds.chineserpg.player.PlayerTick;
import top.xcyyds.chineserpg.registry.MartialArtRegistry;

public class ChineseRPG implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("chineserpg");
	public static final String MOD_ID = "chineserpg";

	@Override
	public void onInitialize() {
		// 注册物品
		BooksItem.registryItem();

		// 注册物品组
		ChineseRPGItemGroup.registryItemGroup();

		// 注册事件
		FabricEventManager.registryEvent();

		// 注册玩家加入事件
		PlayerJoinCallback.register();

		// 注册注册表
		MartialArtRegistry.initializeRegistry();

		// 注册服务器接收同步数据包
		JumpKeySyncHandler.register();

		// 注册玩家每一刻的更新逻辑
		PlayerTick.register();

		LOGGER.info("Hello Fabric world! I'm ChineseRPG!");
	}
}
