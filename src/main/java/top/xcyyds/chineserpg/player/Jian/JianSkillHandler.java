package top.xcyyds.chineserpg.player.Jian;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import top.xcyyds.chineserpg.martialart.artentry.DamageControl;
import top.xcyyds.chineserpg.martialart.artentry.OuterSkillEntry;
import top.xcyyds.chineserpg.network.ParticleSyncHandler;
import top.xcyyds.chineserpg.player.MartialArtDamageSource;
import top.xcyyds.chineserpg.player.data.IPlayerDataProvider;
import top.xcyyds.chineserpg.player.data.PlayerData;
import top.xcyyds.chineserpg.player.jump.PlayerJumpHelper;

import java.util.List;

import static top.xcyyds.chineserpg.player.MartialArtDamageType.JIAN_DAMAGE_TYPE;

public class JianSkillHandler {
    public static final String PIERCE_THRUST = "平刺"; // Piercing Thrust
    public static final String VERTICAL_SLASH = "劈砍"; // Vertical Slash
    public static final String HORIZONTAL_SWEEP = "横扫"; // Horizontal Sweep
    public static final String UPWARD_SLICE = "挑斩"; // Upward Slice
    /**
     * 使用玩家装备的剑法技能。
     * @param player 玩家实体
     * @param skillEntry 外功技能词条
     */
    public static void useJianSkill(PlayerEntity player, OuterSkillEntry skillEntry) {
        PlayerData playerData = ((IPlayerDataProvider) player).getPlayerData();
        // 尝试消耗内力释放技能
        if (PlayerJumpHelper.consumeInnerPower(playerData, skillEntry.getMinInnerPowerConsumption())) {
            castJianSkill(player, playerData, skillEntry);
        } else {
            // 内力不足，发送消息给玩家
            player.sendMessage(Text.translatable("message.chineserpg.insufficient_inner_power").formatted(Formatting.DARK_RED, Formatting.BOLD), true);
        }
    }

    /**
     * 释放剑法技能。
     * @param player 玩家实体
     * @param playerData 玩家数据
     * @param skill 外功技能词条
     */
    public static void castJianSkill(PlayerEntity player, PlayerData playerData, OuterSkillEntry skill) {
        World world = player.getWorld();

        // 根据技能类型处理不同的伤害逻辑
        DamageControl damageControl = skill.getDamageControl();
        switch (damageControl.getDamageType()) {
            case "扇形" -> handleFanShapeSkill(player, world, skill);
            case "射线" -> handleRaySkill(player, world, skill);
            case "立方体" -> handleCubeSkill(player, world, skill);
        }
        // 应用技能冷却时间
        player.getItemCooldownManager().set(player.getMainHandStack().getItem(), skill.getCooldown());

        // 发送粒子效果数据包，用于显示技能效果
        if (player instanceof ServerPlayerEntity) {
            ParticleSyncHandler.sendParticlePacket((ServerPlayerEntity) player, player.getPos(), 10, 1); // 示例粒子效果
        }
    }

    /**
     * 处理扇形伤害类型的技能。
     * @param player 玩家实体
     * @param world 世界对象
     * @param skill 外功技能词条
     */
    private static void handleFanShapeSkill(PlayerEntity player, World world, OuterSkillEntry skill) {
        Vec3d startPos = player.getEyePos();
        Vec3d direction = player.getRotationVec(1.0F);
        double angle = Math.toRadians(skill.getDamageControl().getAngle() / 2);

        // 获取玩家附近的实体
        List<Entity> entities = world.getOtherEntities(player, new Box(startPos.subtract(3, 3, 3), startPos.add(3, 3, 3)));
        for (Entity entity : entities) {
            if (entity != player) {
                // 计算玩家与实体之间的角度，判断是否在技能范围内
                Vec3d toEntity = entity.getEyePos().subtract(startPos).normalize();
                double angleBetween = direction.dotProduct(toEntity);
                if (angleBetween >= Math.cos(angle)) {
                    // 对目标造成伤害
                    entity.damage(new MartialArtDamageSource(RegistryEntry.of(JIAN_DAMAGE_TYPE), (LivingEntity) entity,player), skill.getDamage());
                }
            }
        }
    }

    /**
     * 处理射线伤害类型的技能。
     * @param player 玩家实体
     * @param world 世界对象
     * @param skill 外功技能词条
     */
    private static void handleRaySkill(PlayerEntity player, World world, OuterSkillEntry skill) {
        Vec3d startPos = player.getEyePos();
        Vec3d direction = player.getRotationVec(1.0F);
        Vec3d endPos = startPos.add(direction.multiply(skill.getRange()));

        // 使用射线检测命中实体
        HitResult hitResult = player.raycast(skill.getRange(), 1.0F, false);
        if (hitResult.getType() == HitResult.Type.ENTITY) {
            EntityHitResult entityHitResult = (EntityHitResult) hitResult;
            entityHitResult.getEntity().damage(new MartialArtDamageSource(RegistryEntry.of(JIAN_DAMAGE_TYPE), (LivingEntity) entityHitResult.getEntity(),player), skill.getDamage());
        }
    }

    /**
     * 处理立方体伤害类型的技能。
     * @param player 玩家实体
     * @param world 世界对象
     * @param skill 外功技能词条
     */
    private static void handleCubeSkill(PlayerEntity player, World world, OuterSkillEntry skill) {
        Vec3d startPos = player.getEyePos();
        Box box = new Box(startPos, startPos.add(player.getRotationVec(1.0F).multiply(skill.getRange())));

        // 获取范围内的实体并造成伤害
        List<Entity> entities = world.getOtherEntities(player, box);
        for (Entity entity : entities) {
            if (entity != player && entity instanceof LivingEntity) {

                entity.damage(new MartialArtDamageSource(RegistryEntry.of(JIAN_DAMAGE_TYPE), (LivingEntity) entity,player), skill.getDamage());
            }
        }
    }
}
