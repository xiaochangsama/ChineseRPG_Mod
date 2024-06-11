package top.xcyyds.chineserpg.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import software.bernie.geckolib.animatable.GeoReplacedEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.util.GeckoLibUtil;
import top.xcyyds.chineserpg.animation.PlayerAnimationController;


public class ReplacedPlayerEntity implements GeoReplacedEntity {
    private final PlayerEntity player;
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public ReplacedPlayerEntity(PlayerEntity player) {
        this.player = player;
    }

    public PlayerEntity getPlayer() {
        return player;
    }

    @Override
    public EntityType<?> getReplacingEntityType() {
        return EntityType.PLAYER;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", 10, PlayerAnimationController::predicate));
    }

    @Override
    public double getTick(Object entity) {
        return ((PlayerEntity) entity).age;
    }
}
