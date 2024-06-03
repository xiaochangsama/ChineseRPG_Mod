package top.xcyyds.chineserpg.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerEntityMixin.class);

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }


    @Shadow public abstract void sendMessage(Text message, boolean overlay);

    private static final TrackedData<Float> INNER_POWER = DataTracker.registerData(PlayerEntityMixin.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<Float> INNER_POWER_MAX = DataTracker.registerData(PlayerEntityMixin.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<Integer> JUMP_COUNT = DataTracker.registerData(PlayerEntityMixin.class, TrackedDataHandlerRegistry.INTEGER);


    @Inject(at = @At("HEAD"), method = "initDataTracker")
    private void initDataTracker(CallbackInfo info) {
        this.getDataTracker().startTracking(INNER_POWER, 0.0f);
        this.getDataTracker().startTracking(INNER_POWER_MAX, 100.0f);
        this.getDataTracker().startTracking(JUMP_COUNT, 0);

    }

    @Inject(at = @At("HEAD"), method = "writeCustomDataToNbt")
    private void writeCustomDataToNbt(NbtCompound nbt, CallbackInfo info) {
        nbt.putFloat("InnerPower", this.getDataTracker().get(INNER_POWER));
        nbt.putFloat("InnerPowerMax", this.getDataTracker().get(INNER_POWER_MAX));
        nbt.putInt("JumpCount", this.getDataTracker().get(JUMP_COUNT));
    }

    @Inject(at = @At("HEAD"), method = "readCustomDataFromNbt")
    private void readCustomDataFromNbt(NbtCompound nbt, CallbackInfo info) {
        this.getDataTracker().set(INNER_POWER, nbt.getFloat("InnerPower"));
        this.getDataTracker().set(INNER_POWER_MAX, nbt.getFloat("InnerPowerMax"));
        this.getDataTracker().set(JUMP_COUNT, nbt.getInt("JumpCount"));
    }

    @Inject(at = @At("HEAD"), method = "tick")
    private void tick(CallbackInfo info) {
        //以下的数据处理逻辑仅用于测试
        Integer jumpCount = this.getDataTracker().get(JUMP_COUNT);
        this.getDataTracker().set(JUMP_COUNT, jumpCount + 1);

        Float innerPower = this.getDataTracker().get(INNER_POWER);
        this.getDataTracker().set(INNER_POWER, innerPower + 1);

        Float innerPowerMax = this.getDataTracker().get(INNER_POWER_MAX);
        this.getDataTracker().set(INNER_POWER_MAX, innerPowerMax + 1);

        LOGGER.info("InnerPower: {}", innerPower);
//        LOGGER.info("InnerPowerMax: {}", innerPowerMax);
//        LOGGER.info("JumpCount: {}", jumpCount);

    }

}
