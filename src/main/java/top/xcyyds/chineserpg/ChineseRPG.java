package top.xcyyds.chineserpg;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.bernie.geckolib.GeckoLib;
import top.xcyyds.chineserpg.event.*;
import top.xcyyds.chineserpg.item.BooksItem;
import top.xcyyds.chineserpg.item.ChineseRPGJianItem;
import top.xcyyds.chineserpg.itemgroup.ChineseRPGItemGroup;
import top.xcyyds.chineserpg.network.FunctionKeySyncHandler;
import top.xcyyds.chineserpg.network.JumpKeySyncHandler;
import top.xcyyds.chineserpg.network.MouseKeySyncHandler;
import top.xcyyds.chineserpg.registry.MartialArtRegistry;
import top.xcyyds.chineserpg.trade.TradeRegistry;

public class ChineseRPG implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("chineserpg");
	public static final String MOD_ID = "chineserpg";

	@Override
	public void onInitialize() {
		GeckoLib.initialize();
		// 注册物品
		BooksItem.registryItem();
		ChineseRPGJianItem.register();

		// 注册物品组
		ChineseRPGItemGroup.registryItemGroup();

		// 注册事件
		// 注册玩家（服务器每一刻结尾）每一刻的更新逻辑
		EndServerTickEvent.register();
		// 注册玩家加入世界逻辑
		PlayerJoinEvent.register();
		// 注册玩家移动前，可操作玩家速度的安全区逻辑
		PlayerTravelEvent.register();
		// 注册玩家跌落事件
		PlayerFallEvent.register();
		// 注册玩家受伤事件
		PlayerDamageEvent.register();

		// 注册注册表
		ServerLifecycleEvents.SERVER_STARTED.register(this::onServerStarted);

		// 注册服务器接收同步数据包
		JumpKeySyncHandler.register();
		MouseKeySyncHandler.register();
		FunctionKeySyncHandler.register();

		//注册村民交易
		TradeRegistry.registerTrades();

		LOGGER.info("Hello Fabric world! I'm ChineseRPG!");
	}
	private void onServerStarted(MinecraftServer server) {
		for (ServerWorld world : server.getWorlds()) {
			MartialArtRegistry.initializeRegistry(world);
		}
	}
}
