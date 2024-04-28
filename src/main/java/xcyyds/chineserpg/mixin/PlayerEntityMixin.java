package xcyyds.chineserpg.mixin;

import com.google.common.collect.ImmutableList;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xcyyds.chineserpg.event.PlayerTickCallback;


/*
 * 双重转型：
 * ((PlayerEntity)(object)this)为了调用目标类的方法，不建议使用这种方式
 *
 * 方法、字段来自目标类，可以使用@Shadow来获取
 *
 * 方法字段来自父类，可以直接让此类继承父类，然后调用父类方法（下面代码继承了爷爷类）
 */
@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends Entity {//这里继承了爷爷类，因此可以使用爷爷类的方法
    //因为要继承爷爷类
    public PlayerEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    //代码执行前执行
    @Inject(at = @At("HEAD"), method = "tick")
    private void tickHead(CallbackInfo info){
//        //让玩家躺下，测试用;运行结果是，只在某些情况下才能触发，其他地方调用的此方法导致运行出错
//        this.setPose(EntityPose.SLEEPING);
    }

    //代码返回前执行
    @Inject(at = @At("RETURN"), method = "tick")
    private void tickReturn(CallbackInfo info){
    }

//    //定义一个事件
//    @Inject(at = @At(value = "INVOKE", target = ""), method = "tick")
//    private void onPlayerTick(CallbackInfo info){
//
//            ActionResult result = PlayerTickCallback.EVENT.invoker().interact((PlayerEntity)(Object)this);
//
//            if(result == ActionResult.FAIL) {
//                info.cancel();
//            }
//
//    }
}
