package xcyyds.chineserpg;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.minecraft.block.BlockState;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xcyyds.chineserpg.register.ChineseRPGRegister;

public class ChineseRPG implements ModInitializer {
	// 此记录器用于将文本写入控制台和日志文件。
	// 使用您的mod id作为记录器的名称被认为是最佳实践。
	// 这样，很清楚哪个mod写了信息，警告和错误。
    public static final Logger LOGGER = LoggerFactory.getLogger("chineserpg");

	@Override
	public void onInitialize() {
	// 一旦Minecraft处于mod-load-ready状态，此代码就会运行。
	// 但是，有些东西 (如资源) 可能仍然未初始化。
	// 谨慎行事。

		// 注册所有资源
		ChineseRPGRegister.registryAllThings();

		/* 当玩家击打不能徒手挖掘的方块的时候，给予提示信息
		 * 想要mixin新的事件，只需要三步
		 * 第一步mixin
		 * 第二步创建事件接口（其实没有必要，因为如果我想要用事件，一般只会用一次，直接mixin然后调用就行了）
		 * 第三步注册事件（如果没有第二步，直接调用即可）
		 */
		AttackBlockCallback.EVENT.register((player, world, hand, pos, direction) -> {
			BlockState state = world.getBlockState(pos);
			if (state.isToolRequired() && !player.isSpectator() &&
					player.getMainHandStack().isEmpty()) {
				player.sendMessage(Text.of("你不能用手挖这个方块！"));
				return ActionResult.FAIL;//不执行方块放置，其他操作也不进行
			}
			return ActionResult.PASS;
		});


		LOGGER.info("Hello Fabric world!I'm ChineseRPG!");
	}

}