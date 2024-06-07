package top.xcyyds.chineserpg.player.speed;

import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.Objects;
import java.util.UUID;

public class PlayerSpeedHandler {

    private static final UUID SPEED_MODIFIER_UUID = UUID.fromString("D7AF5286-98B1-914B-BD96-198624927AFE");

    // 增加玩家的奔跑速度
    public static void increaseSpeed(ServerPlayerEntity player, double speedMultiplier) {
        EntityAttributeModifier speedModifier = new EntityAttributeModifier(
                SPEED_MODIFIER_UUID,
                "ChineseRPGLightSkillSpeedModifier",
                speedMultiplier,
                EntityAttributeModifier.Operation.MULTIPLY_BASE
        );

        // 确保没有重复添加同一个Modifier
        if (Objects.requireNonNull(player.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED)).getModifier(SPEED_MODIFIER_UUID) != null) {
            Objects.requireNonNull(player.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED)).removeModifier(SPEED_MODIFIER_UUID);
        }

        Objects.requireNonNull(player.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED)).addPersistentModifier(speedModifier);
    }

    // 重置玩家的奔跑速度
    public static void resetSpeed(ServerPlayerEntity player) {
        Objects.requireNonNull(player.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED)).removeModifier(SPEED_MODIFIER_UUID);
    }
}
