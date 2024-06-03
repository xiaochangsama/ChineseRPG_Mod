package top.xcyyds.chineserpg.mixin;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.xcyyds.chineserpg.PlayerDataStorage;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin {

    @Inject(method = "copyFrom", at = @At("TAIL"))
    private void copyFrom(ServerPlayerEntity oldPlayer, boolean alive, CallbackInfo info) {
        ServerPlayerEntity newPlayer = (ServerPlayerEntity) (Object) this;

        // Check if the player is returning from the End dimension
        if (oldPlayer.getWorld().getRegistryKey() == World.END) {
            NbtCompound nbt = new NbtCompound();
            oldPlayer.writeCustomDataToNbt(nbt);
            PlayerDataStorage.set(oldPlayer.getUuid(), nbt);
        }
    }

    @Inject(method = "onSpawn", at = @At("HEAD"))
    private void onSpawn(CallbackInfo info) {
        ServerPlayerEntity player = (ServerPlayerEntity) (Object) this;

        // Check if the player is returning to the Overworld
        if (player.getWorld().getRegistryKey() == World.OVERWORLD) {
            NbtCompound nbt = PlayerDataStorage.get(player.getUuid());
            if (nbt != null) {
                player.readCustomDataFromNbt(nbt);
                PlayerDataStorage.remove(player.getUuid());
            }
        }
    }
}
