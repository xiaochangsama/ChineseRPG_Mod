package top.xcyyds.chineserpg.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;

public interface PlayerTickCallback {
    Event<PlayerTickCallback> EVENT = EventFactory.createArrayBacked(PlayerTickCallback.class,
            (listeners) -> (player) -> {
                for (PlayerTickCallback listener : listeners) {
                    ActionResult result = listener.interact(player);

                    if (result != ActionResult.PASS) {
                        return result;
                    }
                }

                return ActionResult.PASS;
            });

    ActionResult interact(PlayerEntity player);
}
