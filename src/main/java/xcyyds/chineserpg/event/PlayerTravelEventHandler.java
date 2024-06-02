package xcyyds.chineserpg.event;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerTravelEventHandler {
    private static final Map<UUID, NbtCompound> playerDataMap = new HashMap<>();

    public static void register() {
        ServerEntityEvents.ENTITY_LOAD.register((entity, world) -> {
            if (entity instanceof ServerPlayerEntity) {
                ServerPlayerEntity player = (ServerPlayerEntity) entity;
                if (playerDataMap.containsKey(player.getUuid())) {
                    NbtCompound nbt = playerDataMap.get(player.getUuid());
                    player.readNbt(nbt);
                    playerDataMap.remove(player.getUuid());
                    System.out.println("Restored data for player: " + player.getUuid());
                }
            }
        });

        ServerEntityEvents.ENTITY_UNLOAD.register((entity, world) -> {
            if (entity instanceof ServerPlayerEntity) {
                ServerPlayerEntity player = (ServerPlayerEntity) entity;
                NbtCompound nbt = new NbtCompound();
                player.writeNbt(nbt);
                playerDataMap.put(player.getUuid(), nbt);
                System.out.println("Saved data for player: " + player.getUuid());
            }
        });
    }
}
