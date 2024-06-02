package xcyyds.chineserpg.event;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.entity.EntityPose;
import net.minecraft.util.ActionResult;

import static net.minecraft.util.ActionResult.PASS;

public class FabricEventManager {
    public static void registryEvent() {
        // register events here
        PlayerTravelEventHandler.register();
//        // example:
//        /* 当玩家击打不能徒手挖掘的方块的时候，给予提示信息
//         * 想要mixin新的事件，只需要三步
//         * 第一步mixin
//         * 第二步创建事件接口（其实没有必要，因为如果我想要用事件，一般只会用一次，直接mixin然后调用就行了）
//         * 第三步注册事件（如果没有第二步，直接调用即可）
//         */
//        AttackBlockCallback.EVENT.register((player, world, hand, pos, direction) -> {
//            BlockState state = world.getBlockState(pos);
//            if (state.isToolRequired() && !player.isSpectator() &&//方块需要工具，玩家不是旁观，玩家主手空
//                    player.getMainHandStack().isEmpty()) {
//                player.sendMessage(Text.of("你不能用手挖这个方块！"));
//                return ActionResult.FAIL;//不执行方块放置，其他操作也不进行
//            }
//            return ActionResult.PASS;
//        });

        // example:
        ServerTickEvents.START_WORLD_TICK.register((server) -> {
            // do something every tick
//            server.getRandomAlivePlayer().setPose(EntityPose.SLEEPING);

        });
        AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
//            player.setPose(EntityPose.CROUCHING);
            return PASS;
        });

            }
        }


