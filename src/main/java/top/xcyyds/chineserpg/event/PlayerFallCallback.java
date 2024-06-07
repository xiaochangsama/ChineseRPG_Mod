package top.xcyyds.chineserpg.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;

public interface PlayerFallCallback {
    Event<PlayerFallCallback> EVENT = EventFactory.createArrayBacked(PlayerFallCallback.class,
            (listeners) -> (player, fallDistance, damageMultiplier, damageSource) -> {
                for (PlayerFallCallback listener : listeners) {
                    float result = listener.onFall(player, fallDistance, damageMultiplier, damageSource);
                    if (result != -1) {
                        return result;
                    }
                }
                return fallDistance * damageMultiplier;  // 默认处理逻辑
            }
    );

    float onFall(PlayerEntity player, float fallDistance, float damageMultiplier, DamageSource damageSource);
}
