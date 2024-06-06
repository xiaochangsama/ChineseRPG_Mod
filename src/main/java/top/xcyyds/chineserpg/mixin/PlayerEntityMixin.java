package top.xcyyds.chineserpg.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.xcyyds.chineserpg.event.PlayerTravelCallback;
import top.xcyyds.chineserpg.player.data.IPlayerDataProvider;
import top.xcyyds.chineserpg.player.data.PlayerData;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity implements IPlayerDataProvider {
    private final PlayerData playerData = new PlayerData();

    @Override
    public PlayerData getPlayerData() {
        return playerData;
    }

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Shadow
    public abstract void travel(Vec3d travelVector);

    @Inject(method = "travel", at = @At("HEAD"))
    private void onTravelHead(Vec3d travelVector, CallbackInfo ci) {
        PlayerTravelCallback.EVENT.invoker().onTravel((PlayerEntity) (Object) this, travelVector);
    }
}
