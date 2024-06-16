package top.xcyyds.chineserpg.animation;

import dev.kosmx.playerAnim.api.firstPerson.FirstPersonConfiguration;
import dev.kosmx.playerAnim.api.firstPerson.FirstPersonMode;
import dev.kosmx.playerAnim.api.layered.IAnimation;
import dev.kosmx.playerAnim.api.layered.KeyframeAnimationPlayer;
import dev.kosmx.playerAnim.api.layered.ModifierLayer;
import dev.kosmx.playerAnim.api.layered.modifier.AbstractFadeModifier;
import dev.kosmx.playerAnim.api.layered.modifier.MirrorModifier;
import dev.kosmx.playerAnim.api.layered.modifier.SpeedModifier;
import dev.kosmx.playerAnim.core.data.KeyframeAnimation;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationAccess;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationFactory;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationRegistry;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PlayerAnimationController {
    public static final Identifier ANIMATION_IDENTIFIER = new Identifier("chineserpg", "animation");
    private static final Logger LOGGER = LogManager.getLogger("ChineseRPG");

    public static void registerPlayerAnimation() {
        // 使用工厂方法为玩家注册动画层
        PlayerAnimationFactory.ANIMATION_DATA_FACTORY.registerFactory(ANIMATION_IDENTIFIER, 42, (player) -> {
            if (player instanceof ClientPlayerEntity) {
                // 创建动画层，并添加速度和镜像修改器
                ModifierLayer<IAnimation> animationLayer = new ModifierLayer<>();
                animationLayer.addModifierBefore(new SpeedModifier(0.5f)); // 动画播放速度减慢
                animationLayer.addModifierBefore(new MirrorModifier(true)); // 动画镜像
                return animationLayer;
            }
            return null;
        });

        // 注册动画事件，确保在所有玩家上添加动画层
        PlayerAnimationAccess.REGISTER_ANIMATION_EVENT.register((player, animationStack) -> {
            ModifierLayer<IAnimation> layer = new ModifierLayer<>();
            animationStack.addAnimLayer(69, layer);
            PlayerAnimationAccess.getPlayerAssociatedData(player).set(ANIMATION_IDENTIFIER, layer);
        });
    }

    public static void playAnimation(ClientPlayerEntity player, String animationKey) {
        // 获取与玩家关联的动画层
        ModifierLayer<IAnimation> animationLayer = (ModifierLayer<IAnimation>) PlayerAnimationAccess.getPlayerAssociatedData(player).get(ANIMATION_IDENTIFIER);
        if (animationLayer != null) {
            KeyframeAnimation animationData = PlayerAnimationRegistry.getAnimation(new Identifier("chineserpg", animationKey));

            if (animationData != null) {
                // 创建并配置关键帧动画播放器
                KeyframeAnimationPlayer animation = new KeyframeAnimationPlayer(animationData)
                        .setFirstPersonMode(FirstPersonMode.THIRD_PERSON_MODEL)
                        .setFirstPersonConfiguration(new FirstPersonConfiguration().setShowRightArm(true).setShowLeftItem(false));
                // 替换当前动画并设置淡入效果
                animationLayer.replaceAnimationWithFade(AbstractFadeModifier.functionalFadeIn(20, (modelName, type, value) -> value), animation);
            } else {
                LOGGER.error("Animation not found for key: " + animationKey);
            }
        } else {
            LOGGER.error("Animation layer not found for player.");
        }
    }
}
