package top.xcyyds.chineserpg.animation;

import net.minecraft.entity.player.PlayerEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animation.Animation;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;


public class PlayerAnimationController {

    // 定义动画控制逻辑的谓词方法
    public static <E extends GeoAnimatable> PlayState predicate(AnimationState<E> event) {
        if (!(event.getAnimatable() instanceof PlayerEntity player)) {
            return PlayState.STOP;
        }

        // 根据玩家的状态设置动画
        if (player.isSprinting()) {
            event.getController().setAnimation(RawAnimation.begin().then("animation.player.run", Animation.LoopType.LOOP));
        } else if (player.isInSneakingPose()) {
            event.getController().setAnimation(RawAnimation.begin().then("animation.player.walk", Animation.LoopType.LOOP));
        } else if (player.fallDistance > 0) {
            event.getController().setAnimation(RawAnimation.begin().then("animation.player.jump", Animation.LoopType.HOLD_ON_LAST_FRAME));
        } else if (player.isUsingItem()) {
            // 例如，玩家使用物品时上举手臂
            event.getController().setAnimation(RawAnimation.begin().then("animation.player.up_arm", Animation.LoopType.HOLD_ON_LAST_FRAME));
        } else {
            event.getController().setAnimation(RawAnimation.begin().then("animation.player.idle", Animation.LoopType.LOOP));
        }

        return PlayState.CONTINUE;
    }
}