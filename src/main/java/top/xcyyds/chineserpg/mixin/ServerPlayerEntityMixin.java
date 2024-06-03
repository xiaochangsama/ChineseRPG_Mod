package top.xcyyds.chineserpg.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.xcyyds.chineserpg.PlayerDataStorage;
import top.xcyyds.chineserpg.network.PlayerDataSyncHandler;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin {

    @Inject(method = "copyFrom", at = @At("TAIL"))
    private void copyFrom(ServerPlayerEntity oldPlayer, boolean alive, CallbackInfo info) {
        ServerPlayerEntity newPlayer = (ServerPlayerEntity) (Object) this;

        if (oldPlayer.getWorld().getRegistryKey() == World.END) {
            NbtCompound nbt = new NbtCompound();
            oldPlayer.writeCustomDataToNbt(nbt);
            PlayerDataStorage.set(oldPlayer.getUuid(), nbt);

            PlayerDataSyncHandler.send(oldPlayer, nbt);
        }
    }

    @Inject(method = "onSpawn", at = @At("HEAD"))
    private void onSpawn(CallbackInfo info) {
        ServerPlayerEntity player = (ServerPlayerEntity) (Object) this;

        if (player.getWorld().getRegistryKey() == World.OVERWORLD) {
            NbtCompound nbt = PlayerDataStorage.get(player.getUuid());
            if (nbt != null) {
                player.readCustomDataFromNbt(nbt);
                PlayerDataStorage.remove(player.getUuid());

                PlayerDataSyncHandler.send(player, nbt);

                // Ensure the player is alive and has full health
                if (player.getHealth() <= 0.0F) {
                    player.setHealth(player.getMaxHealth());
                }
            }
        }
    }
}
