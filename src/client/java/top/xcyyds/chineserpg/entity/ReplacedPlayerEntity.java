package top.xcyyds.chineserpg.entity;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.recipebook.ClientRecipeBook;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.EntityType;
import net.minecraft.stat.StatHandler;
import software.bernie.geckolib.animatable.GeoReplacedEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;
import top.xcyyds.chineserpg.animation.PlayerAnimationController;

public class ReplacedPlayerEntity extends ClientPlayerEntity implements GeoReplacedEntity {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public ReplacedPlayerEntity(MinecraftClient client, ClientWorld world, ClientPlayNetworkHandler networkHandler, GameProfile profile, StatHandler statHandler, ClientRecipeBook recipeBook) {
        super(client, world, networkHandler, statHandler, recipeBook, false, false);
        initialize();
    }

    public ReplacedPlayerEntity(ClientPlayerEntity player) {
        this(MinecraftClient.getInstance(), (ClientWorld) player.getWorld(), player.networkHandler, player.getGameProfile(), player.getStatHandler(), player.getRecipeBook());
    }

    @Override
    public void tick() {
        super.tick();
        if (!getWorld().isClient) {
            initialize();
        }
    }

    @Override
    public boolean isSpectator() {
        return false;
    }

    @Override
    public boolean isCreative() {
        return false;
    }

    public void initialize() {
        AnimatableManager<?> manager = this.getAnimatableInstanceCache().getManagerForId(this.getId());
        if (manager.getAnimationControllers().isEmpty()) {
            manager.addController(new software.bernie.geckolib.core.animation.AnimationController<>(this, "controller", 10, PlayerAnimationController::predicate));
        }
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
        controllers.add(new software.bernie.geckolib.core.animation.AnimationController<>(this, "controller", 10, PlayerAnimationController::predicate));
    }

    @Override
    public double getTick(Object entity) {
        return this.age;
    }
}
