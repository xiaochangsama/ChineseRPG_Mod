package xcyyds.chineserpg.mixin;

import com.google.common.collect.ImmutableList;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class EntityMixin {


    @Shadow public abstract EntityType<?> getType();

    @Shadow public abstract DataTracker getDataTracker();

    @Inject(at = @At("HEAD"), method = "tick")
    private void tickHead(CallbackInfo ci){
    }

    @Inject(at = @At("RETURN"), method = "setPose")
    private void setPoseReturn(EntityPose pose, CallbackInfo ci){
        if(this.getType() == EntityType.PLAYER){
//            DataTracker dataTracker = getDataTracker();
//
//            //通过EntityAccessor类访问原本不能访问的字段
//            TrackedData<EntityPose> POSE = ((EntityAccessor)this).getPOSE();
//
//            dataTracker.set(POSE,EntityPose.SLEEPING );
        }
    }

}
