package top.xcyyds.chineserpg.player.jump;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import top.xcyyds.chineserpg.martialart.artentry.LightSkillEntry;
import top.xcyyds.chineserpg.martialart.artentry.MartialArtEntry;
import top.xcyyds.chineserpg.martialart.skill.MartialArt;
import top.xcyyds.chineserpg.network.ParticleSyncHandler;
import top.xcyyds.chineserpg.player.data.PlayerData;

import java.util.List;
import java.util.UUID;

import static top.xcyyds.chineserpg.player.jump.PlayerJumpHelper.isPlayerInWater;
import static top.xcyyds.chineserpg.registry.MartialArtRegistry.getMartialArt;

public class PlayerJumpHandler {

    public static final String MULTI_JUMP = "多段跳";
    public static final String BIG_JUMP = "大跳";
    public static final String WATER_SKIMMING = "水上漂";

    public static void toJump(PlayerEntity player, PlayerData playerData){
        UUID equippedLightSkill = playerData.getEquippedLightSkillUUID();
        MartialArt lightSkill = getMartialArt(equippedLightSkill);
        if (lightSkill != null) {
            List<MartialArtEntry> lightSkillEntries = lightSkill.getEntries();
            for (MartialArtEntry entry : lightSkillEntries) {
                if (entry instanceof LightSkillEntry lightSkillEntry) {
                    if (lightSkillEntry.getJumpType().equals(MULTI_JUMP) && !isPlayerInWater(player)) {
                        basicJump(player, playerData, lightSkillEntry.getVelocityYIncrease(), lightSkillEntry.getParticleCount(), lightSkillEntry.getInnerPowerConsumption(), lightSkillEntry.getDirectionalVelocity(), 1);
                    }
                    if (lightSkillEntry.getJumpType().equals(WATER_SKIMMING)) {
                        waterSkimmingJump(player, playerData, lightSkillEntry, 0.7f, 0.5f);
                    }
                }
            }
        }
    }

    public static void toJumpOnGround(PlayerEntity player, PlayerData playerData){
        UUID equippedLightSkill = playerData.getEquippedLightSkillUUID();
        MartialArt lightSkill = getMartialArt(equippedLightSkill);
        if (lightSkill != null) {
            List<MartialArtEntry> lightSkillEntries = lightSkill.getEntries();
            for (MartialArtEntry entry : lightSkillEntries) {
                if (entry instanceof LightSkillEntry lightSkillEntry) {
                    if (lightSkillEntry.getJumpType().equals(BIG_JUMP) && !isPlayerInWater(player)) {
                        basicJump(player, playerData, lightSkillEntry.getVelocityYIncrease(), lightSkillEntry.getParticleCount(), lightSkillEntry.getInnerPowerConsumption(), lightSkillEntry.getDirectionalVelocity(), 0);
                    }
                }
            }
        }
    }

    public static void waterSkimmingJump(PlayerEntity player, PlayerData playerData, LightSkillEntry lightSkillEntry, float underWater, float aboveWater) {
        Vec3d playerFeetPos = player.getPos();
        Vec3d posBelow = playerFeetPos.subtract(0, aboveWater, 0);
        Vec3d posAbove = playerFeetPos.add(0, underWater, 0);
        BlockPos blockPosBelow = new BlockPos((int) posBelow.x, (int)Math.round(posBelow.y), (int)posBelow.z);
        BlockPos blockPosAbove = new BlockPos((int)posAbove.x,(int) Math.round(posAbove.y), (int)posAbove.z);
        if (isWaterSurface(player, blockPosBelow) && isAir(player, blockPosAbove)) {
            basicJump(player, playerData, lightSkillEntry.getVelocityYIncrease(), lightSkillEntry.getParticleCount(), lightSkillEntry.getInnerPowerConsumption(), lightSkillEntry.getDirectionalVelocity(), 1);
        }
    }

    private static boolean isWaterSurface(PlayerEntity player, BlockPos pos) {
        BlockState state = player.getEntityWorld().getBlockState(pos);
        return state.getBlock() == Blocks.WATER || state.getFluidState().isStill();
    }

    private static boolean isAir(PlayerEntity player, BlockPos pos) {
        return player.getEntityWorld().getBlockState(pos).getBlock() == Blocks.AIR;
    }

    public static void basicJump(PlayerEntity player, PlayerData playerData, double newY, int particleCount, float innerPowerConsumption, double directionalVelocity, int consumeJumpCount) {
        if (PlayerJumpHelper.consumeJumpCount(playerData, consumeJumpCount)) {
            if (PlayerJumpHelper.consumeInnerPower(playerData, innerPowerConsumption)) {
                Vec3d vec3d = player.getVelocity();
                Vec3d direction = PlayerJumpHelper.getPlayerHorizontalDirection(player);
                Vec3d newVelocity = new Vec3d(vec3d.x + direction.x * directionalVelocity, newY, vec3d.z + direction.z * directionalVelocity);
                playerData.setPlayerVelocity(newVelocity);

                if (player instanceof ServerPlayerEntity) {
                    ParticleSyncHandler.sendParticlePacket((ServerPlayerEntity) player, player.getPos(), particleCount, 1);
                } else {
                    PlayerJumpHelper.generateRingParticles(player, particleCount, 0.1);
                }
            } else {
                player.sendMessage(Text.translatable("message.chineserpg.insufficient_inner_power").formatted(Formatting.DARK_RED, Formatting.BOLD), true);
            }
        }
    }
}
