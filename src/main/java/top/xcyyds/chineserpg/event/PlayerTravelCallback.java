package top.xcyyds.chineserpg.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.Vec3d;

public interface PlayerTravelCallback {
    Event<PlayerTravelCallback> EVENT = EventFactory.createArrayBacked(PlayerTravelCallback.class,
            (listeners) -> (player, travelVector) -> {
                for (PlayerTravelCallback listener : listeners) {
                    ActionResult result = listener.onTravel(player, travelVector);
                    if (result != ActionResult.PASS) {
                        return result;
                    }
                }
                return ActionResult.PASS;
            }
    );

    ActionResult onTravel(PlayerEntity player, Vec3d travelVector);
}
