package top.xcyyds.chineserpg.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;

public interface PlayerDamageCallback {
    Event<PlayerDamageCallback> EVENT = EventFactory.createArrayBacked(PlayerDamageCallback.class,
            (listeners) -> (player, source, amount) -> {
                for (PlayerDamageCallback event : listeners) {
                    boolean result = event.onDamage(player, source, amount);
                    if (result) {
                        return true;
                    }
                }
                return false;
            });

    boolean onDamage(PlayerEntity player, DamageSource source, float amount);
}
