package top.xcyyds.chineserpg;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import top.xcyyds.chineserpg.event.FabricEventManager;
import top.xcyyds.chineserpg.item.BooksItem;
import top.xcyyds.chineserpg.itemgroup.ChineseRPGItemGroup;
import top.xcyyds.chineserpg.network.JumpKeySyncHandler;
import top.xcyyds.chineserpg.network.PlayerDataSyncHandler;
import top.xcyyds.chineserpg.registry.MartialArtRegistry;

public class ChineseRPG implements ModInitializer {
	// 此记录器用于将文本写入控制台和日志文件。
	// 使用您的mod id作为记录器的名称被认为是最佳实践。
	// 这样，很清楚哪个mod写了信息，警告和错误。
    public static final Logger LOGGER = LoggerFactory.getLogger("chineserpg");
	public static final String MOD_ID = "chineserpg";
	@Override
	public void onInitialize() {
	// 一旦Minecraft处于mod-load-ready状态，此代码就会运行。
	// 但是，有些东西 (如资源) 可能仍然未初始化。
	// 谨慎行事。

		//注册物品
		BooksItem.registryItem();

		//注册物品组
		ChineseRPGItemGroup.registryItemGroup();

		//注册事件
		FabricEventManager.registryEvent();

		//注册注册表
		MartialArtRegistry.initializeRegistry();

		//注册服务器接收同步数据包
		JumpKeySyncHandler.register();

		LOGGER.info("Hello Fabric world!I'm ChineseRPG!");
	}

}