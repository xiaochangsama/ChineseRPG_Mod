package xcyyds.chineserpg.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends Entity {

    @Shadow public abstract void sendMessage(Text message, boolean overlay);

    private static final TrackedData<Float> INNER_POWER = DataTracker.registerData(PlayerEntityMixin.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<Float> INNER_POWER_MAX = DataTracker.registerData(PlayerEntityMixin.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<Integer> JUMP_COUNT = DataTracker.registerData(PlayerEntityMixin.class, TrackedDataHandlerRegistry.INTEGER);

    private NbtCompound customData;

    public PlayerEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(at = @At("HEAD"), method = "initDataTracker")
    private void initDataTracker(CallbackInfo info) {
        this.getDataTracker().startTracking(INNER_POWER, 0.0f);
        this.getDataTracker().startTracking(INNER_POWER_MAX, 100.0f);
        this.getDataTracker().startTracking(JUMP_COUNT, 0);
    }

    @Inject(at = @At("HEAD"), method = "writeCustomDataToNbt")
    private void writeCustomDataToNbt(NbtCompound nbt, CallbackInfo info) {
        nbt.putDouble("InnerPower", this.getDataTracker().get(INNER_POWER));
        nbt.putDouble("InnerPowerMax", this.getDataTracker().get(INNER_POWER_MAX));
        nbt.putInt("JumpCount", this.getDataTracker().get(JUMP_COUNT));
        customData = nbt;
    }

    @Inject(at = @At("HEAD"), method = "readCustomDataFromNbt")
    private void readCustomDataFromNbt(NbtCompound nbt, CallbackInfo info) {
        this.getDataTracker().set(INNER_POWER, nbt.getFloat("InnerPower"));
        this.getDataTracker().set(INNER_POWER_MAX, nbt.getFloat("InnerPowerMax"));
        this.getDataTracker().set(JUMP_COUNT, nbt.getInt("JumpCount"));
        customData = nbt;
    }

    @Inject(at = @At("HEAD"), method = "tick")
    private void tick(CallbackInfo info) {
        // 示例：每个tick增加自定义数据中的整数
        Integer jumpCount = this.getDataTracker().get(JUMP_COUNT);
        this.getDataTracker().set(JUMP_COUNT, jumpCount+1);

        Float innerPower = this.getDataTracker().get(INNER_POWER);
        this.getDataTracker().set(INNER_POWER, innerPower+1);

        Float innerPowerMax = this.getDataTracker().get(INNER_POWER_MAX);
        this.getDataTracker().set(INNER_POWER_MAX, innerPowerMax+1);

        this.sendMessage(Text.of("InnerPower:"+this.getDataTracker().get(INNER_POWER)));
        this.sendMessage(Text.of("InnerPowerMax:"+this.getDataTracker().get(INNER_POWER_MAX)));
        this.sendMessage(Text.of("JumpCount:"+this.getDataTracker().get(JUMP_COUNT)));
    }
}
