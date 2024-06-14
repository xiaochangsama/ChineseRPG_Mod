package top.xcyyds.chineserpg.item;

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

import static net.minecraft.registry.Registries.ITEM;

public class ChineseRPGJianItem extends SwordItem implements GeoAnimatable {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public ChineseRPGJianItem(ToolMaterials material, int attackDamage, float attackSpeed, Settings settings) {
        super(material, attackDamage, attackSpeed, settings);
    }

    public static final Item IRON_JIAN = new ChineseRPGJianItem(ToolMaterials.IRON, 3, -2.2F, new Item.Settings());

    public static void register() {
        Registry.register(ITEM, new Identifier("chineserpg", "iron_jian"), IRON_JIAN);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", 20, event -> {
            event.getController().setAnimation(RawAnimation.begin().thenLoop("animation.iron_jian.piercing_thrust"));
            return PlayState.CONTINUE;
        }));
        // 添加其他动画控制器
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public double getTick(Object object) {
        return 0;
    }



}
