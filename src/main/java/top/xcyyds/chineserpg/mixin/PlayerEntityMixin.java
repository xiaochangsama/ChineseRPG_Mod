package top.xcyyds.chineserpg.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.xcyyds.chineserpg.PlayerPersistentData;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerEntityMixin.class);

    @Shadow public abstract void sendMessage(net.minecraft.text.Text message, boolean actionBar);

    private PlayerPersistentData persistentData = new PlayerPersistentData();

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("HEAD"))
    private void writeCustomDataToNbt(NbtCompound nbt, CallbackInfo info) {
        NbtCompound data = new NbtCompound();
        persistentData.writeToNbt(data);
        nbt.put("PlayerPersistentData", data);
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("HEAD"))
    private void readCustomDataFromNbt(NbtCompound nbt, CallbackInfo info) {
        if (nbt.contains("PlayerPersistentData", 10)) {
            persistentData.readFromNbt(nbt.getCompound("PlayerPersistentData"));
        }
    }

    @Inject(at = @At("HEAD"), method = "tick")
    private void tick(CallbackInfo info) {
        // Data handling logic for testing
        int jumpCount = persistentData.getJumpCount();
        persistentData.setJumpCount(jumpCount + 1);

        float innerPower = persistentData.getInnerPower();
        persistentData.setInnerPower(innerPower + 1);

        float innerPowerMax = persistentData.getInnerPowerMax();
        persistentData.setInnerPowerMax(innerPowerMax + 1);

        LOGGER.info("InnerPower: {}", innerPower);
    }
}
