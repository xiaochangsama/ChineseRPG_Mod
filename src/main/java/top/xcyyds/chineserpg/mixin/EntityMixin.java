package top.xcyyds.chineserpg.mixin;


import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class EntityMixin {


    @Shadow public abstract EntityType<?> getType();

    @Shadow public abstract DataTracker getDataTracker();

    @Shadow public abstract boolean isPlayer();

    @Inject(at = @At("HEAD"), method = "tick")
    private void tickHead(CallbackInfo ci){
    }

    @Inject(at = @At("RETURN"), method = "setPose")
    private void setPoseReturn(EntityPose pose, CallbackInfo ci){

        if(this.isPlayer()){
//            DataTracker dataTracker = getDataTracker();
//
//            //通过EntityAccessor类访问原本不能访问的字段
//            TrackedData<EntityPose> POSE = ((EntityAccessor)this).getPOSE();
//
//            dataTracker.set(POSE,EntityPose.SLEEPING );
        }
    }

}
