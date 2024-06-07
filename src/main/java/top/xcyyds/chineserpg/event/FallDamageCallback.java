package top.xcyyds.chineserpg.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.player.PlayerEntity;

public interface FallDamageCallback {
    Event<FallDamageCallback> EVENT = EventFactory.createArrayBacked(FallDamageCallback.class,
            (listeners) -> (player, fallDistance) -> {
                for (FallDamageCallback listener : listeners) {
                    float result = listener.onFallDamage(player, fallDistance);
                    if (result != fallDistance) {
                        return result;
                    }
                }
                return fallDistance;
            });

    float onFallDamage(PlayerEntity player, float fallDistance);
}
