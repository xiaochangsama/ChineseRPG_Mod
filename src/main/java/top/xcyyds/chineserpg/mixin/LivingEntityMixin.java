package top.xcyyds.chineserpg.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.xcyyds.chineserpg.event.PlayerDamageCallback;
import top.xcyyds.chineserpg.event.PlayerFallCallback;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @SuppressWarnings("UnreachableCode")
    @Inject(method = "handleFallDamage", at = @At("HEAD"), cancellable = true)
    private void handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource, CallbackInfoReturnable<Boolean> cir) {
        if ((Object)this instanceof PlayerEntity) {
             PlayerEntity player = (PlayerEntity) (Object) this;
             // 广播事件
            float adjustedFallDistance = PlayerFallCallback.EVENT.invoker().onFall(player, fallDistance,damageMultiplier,damageSource);

            //如果调整后的摔落距离为 0.0f，说明根据自定义逻辑（如轻功技能），玩家的摔落距离已经被完全减免，不会造成任何伤害。
            // 在这种情况下，通过调用 cir.setReturnValue(false) 来取消摔落伤害的处理。
            if (adjustedFallDistance == 0.0f) {
                cir.setReturnValue(false);
            } else {
                player.fallDistance = adjustedFallDistance;
            }
        }
    }
    @SuppressWarnings("UnreachableCode")
    @Inject(method = "damage", at = @At("HEAD"), cancellable = true)
    private void onDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if ((Object) this instanceof PlayerEntity player) {

            // 广播事件
            boolean cancelled = PlayerDamageCallback.EVENT.invoker().onDamage(player, source, amount);

            if (cancelled) {
                cir.setReturnValue(false); // 取消伤害
            }
        }
    }
}
