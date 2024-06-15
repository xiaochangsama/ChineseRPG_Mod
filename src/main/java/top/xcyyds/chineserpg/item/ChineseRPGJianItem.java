package top.xcyyds.chineserpg.item;


import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterials;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;
import software.bernie.geckolib.util.RenderUtils;

import static net.minecraft.registry.Registries.ITEM;

public class ChineseRPGJianItem extends SwordItem implements GeoAnimatable {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private final AnimationController<GeoAnimatable> animationController;

    public ChineseRPGJianItem(ToolMaterials material, int attackDamage, float attackSpeed, Settings settings) {
        super(material, attackDamage, attackSpeed, settings);
        this.animationController = new AnimationController<>(this, "controller", 0, event -> PlayState.CONTINUE);
    }

    public static final Item IRON_JIAN = new ChineseRPGJianItem(ToolMaterials.IRON, 3, -2.2F, new Item.Settings());

    public static void register() {
        Registry.register(ITEM, new Identifier("chineserpg", "iron_jian"), IRON_JIAN);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(this.animationController);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public double getTick(Object object) {
        return RenderUtils.getCurrentTick();
    }

    // 添加一个方法来触发特定的动画
    public void playThrustAnimation(PlayerEntity player) {
        // 设置并播放动画
        if (player.getMainHandStack().getItem() == this) {
            this.animationController.setAnimation(RawAnimation.begin().thenPlay("animation.iron_jian.piercing_thrust"));
            this.animationController.forceAnimationReset();
        }
    }

}
