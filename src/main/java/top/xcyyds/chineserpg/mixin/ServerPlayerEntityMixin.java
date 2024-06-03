package top.xcyyds.chineserpg.mixin;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;



//这里的代码解决不了末地返回的问题，但是可以正常运行，暂时保留
//此类无用
@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServerPlayerEntityMixin.class);

    private static final Map<UUID, NbtCompound> playerDataMap = new HashMap<>();

    @Inject(at = @At("HEAD"), method = "onDeath", cancellable = true)
    private  void onDeath(DamageSource damageSource, CallbackInfo info) {

    }
//    @Inject(at = @At("HEAD"), method = "moveToWorld")
//    private void beforeMoveToWorld(ServerWorld destination, CallbackInfoReturnable<Entity> cir) {
//        ServerPlayerEntity player = (ServerPlayerEntity) (Object) this;
//        NbtCompound nbt = new NbtCompound();
//        player.writeCustomDataToNbt(nbt);
//        playerDataMap.put(player.getUuid(), nbt);
//        LOGGER.info("Saved data for player: {}", player.getUuid());
//    }
//
//    @Inject(at = @At("RETURN"), method = "moveToWorld")
//    private void afterMoveToWorld(ServerWorld destination, CallbackInfoReturnable<Entity> cir) {
//        ServerPlayerEntity player = (ServerPlayerEntity) (Object) this;
//        if (playerDataMap.containsKey(player.getUuid())) {
//            NbtCompound nbt = playerDataMap.get(player.getUuid());
//            player.readCustomDataFromNbt(nbt);
//            playerDataMap.remove(player.getUuid());
//            LOGGER.info("Restored data for player: {}", player.getUuid());
//        }
//    }
}
