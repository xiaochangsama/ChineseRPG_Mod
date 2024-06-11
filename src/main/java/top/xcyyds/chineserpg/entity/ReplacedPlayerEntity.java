package top.xcyyds.chineserpg.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import software.bernie.geckolib.animatable.GeoReplacedEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.UUID;

public class ReplacedPlayerEntity implements GeoReplacedEntity {
    private final PlayerEntity player;
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public ReplacedPlayerEntity(PlayerEntity player) {
        this.player = player;
    }

    public PlayerEntity getPlayer() {
        return player;
    }

    public UUID getPlayerUUID() {
        return player.getUuid();
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
        // 注册动画控制器
    }

    @Override
    public double getTick(Object entity) {
        return ((PlayerEntity) entity).age;
    }
}
