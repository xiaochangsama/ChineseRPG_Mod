package top.xcyyds.chineserpg.player.jump;


import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import top.xcyyds.chineserpg.martialart.MartialArt;
import top.xcyyds.chineserpg.martialart.MartialArtEntry;
import top.xcyyds.chineserpg.network.ParticleSyncHandler;
import top.xcyyds.chineserpg.player.data.PlayerData;

import java.util.List;
import java.util.UUID;

import static top.xcyyds.chineserpg.player.jump.PlayerJumpHelper.isPlayerInWater;
import static top.xcyyds.chineserpg.registry.MartialArtRegistry.getMartialArt;


/**
 * 定义不同的跳跃类型，在MartialArts定义中使用
 */
public class PlayerJumpHandler {

    //跳跃类型
    public static final String MULTI_JUMP = "多段跳";
    public static final String BIG_JUMP = "大跳";
    public static final String WATER_SKIMMING = "水上漂";


    //根据玩家跳跃类型，选择不同的跳跃方法
    public static void toJump(PlayerEntity player, PlayerData playerData){

        UUID equippedSkill = playerData.getEquippedSkill();
        MartialArt martialArt = getMartialArt(equippedSkill);
        if (martialArt != null) {
            List<MartialArtEntry> martialArtEntries = martialArt.getEntries();
            for (MartialArtEntry martialArtEntry : martialArtEntries) {
                if (martialArtEntry.getJumpType().equals(MULTI_JUMP) && !isPlayerInWater(player)) {
                    basicJump(player, playerData, martialArtEntry.getVelocityYIncrease(), martialArtEntry.getParticleCount(), martialArtEntry.getInnerPowerConsumption(), martialArtEntry.getDirectionalVelocity(),1);
                }
                if (martialArtEntry.getJumpType().equals(WATER_SKIMMING)) {
                    //在这里添加水上漂的跳跃方法
                    waterSkimmingJump(player, playerData, martialArtEntry);
                }
            }
        }
    }
    public static void toJumpOnGround(PlayerEntity player, PlayerData playerData){

        UUID equippedSkill = playerData.getEquippedSkill();
        MartialArt martialArt = getMartialArt(equippedSkill);
        if (martialArt != null) {
            List<MartialArtEntry> martialArtEntries = martialArt.getEntries();
            for (MartialArtEntry martialArtEntry : martialArtEntries) {
                if (martialArtEntry.getJumpType().equals(BIG_JUMP) && !isPlayerInWater(player)) {
                    basicJump(player, playerData, martialArtEntry.getVelocityYIncrease(), martialArtEntry.getParticleCount(), martialArtEntry.getInnerPowerConsumption(), martialArtEntry.getDirectionalVelocity(),0);
                }
            }
        }
    }

    // 新建水上漂的跳跃方法
    public static void waterSkimmingJump(PlayerEntity player, PlayerData playerData, MartialArtEntry martialArtEntry) {
        // 获取玩家脚底位置
        Vec3d playerFeetPos = player.getPos().subtract(0, player.getHeight() / 2.0, 0);

        // 获取玩家脚底下0.5格和脚底上0.5格的位置
        Vec3d posBelow = playerFeetPos.subtract(0, 0.5, 0);
        Vec3d posAbove = playerFeetPos.add(0, 0.5, 0);

        // 获取四舍五入后的整数坐标
        BlockPos blockPosBelow = new BlockPos((int) posBelow.x, (int) Math.round(posBelow.y), (int) posBelow.z);
        BlockPos blockPosFeet = new BlockPos((int) playerFeetPos.x, (int) Math.round(playerFeetPos.y), (int) playerFeetPos.z);
        BlockPos blockPosAbove = new BlockPos((int) posAbove.x, (int) Math.round(posAbove.y), (int) posAbove.z);

        // 判断位置是否是水面
        if (isWaterSurface(player, blockPosBelow) || isWaterSurface(player, blockPosFeet)) {
            if (isAir(player, blockPosFeet) || isWaterSurface(player, blockPosFeet) || isAir(player, blockPosAbove)) {
                basicJump(player, playerData, martialArtEntry.getVelocityYIncrease(), martialArtEntry.getParticleCount(), martialArtEntry.getInnerPowerConsumption(), martialArtEntry.getDirectionalVelocity(), 1);
            }
        }
    }

    private static boolean isWaterSurface(PlayerEntity player, BlockPos pos) {
        BlockState state = player.getEntityWorld().getBlockState(pos);
        return state.getBlock() == Blocks.WATER  || state.getFluidState().isStill();
    }

    private static boolean isAir(PlayerEntity player, BlockPos pos) {
        return player.getEntityWorld().getBlockState(pos).getBlock() == Blocks.AIR;
    }


    // 不确定目前该类型中的跳跃是否能成功
    public static void basicJump(PlayerEntity player, PlayerData playerData, double newY, int particleCount, float innerPowerConsumption, double directionalVelocity,int consumeJumpCount) {

        //默认消耗连跳力1
        if(PlayerJumpHelper.consumeJumpCount(playerData, consumeJumpCount)){
            //消耗内力
            if (PlayerJumpHelper.consumeInnerPower(playerData, innerPowerConsumption)) {

                //获取当前速度
                Vec3d vec3d = player.getVelocity();

                //获取玩家面朝的水平方向向量
                Vec3d direction = PlayerJumpHelper.getPlayerHorizontalDirection(player);

                //设置新的速度，包括朝向的加速度
                Vec3d newVelocity = new Vec3d(vec3d.x + direction.x * directionalVelocity, newY, vec3d.z + direction.z * directionalVelocity);
                // 这里一定要用playerData.setPlayerVelocity(newVelocity)这个方法，因为这个方法能避免服务端和客户端逻辑的冲突，
                // 而player.setVelocity(newVelocity)会在客户端和服务端执行并导致不同步和飞天
                playerData.setPlayerVelocity(newVelocity);


                if (player instanceof ServerPlayerEntity) {
                    ParticleSyncHandler.sendParticlePacket((ServerPlayerEntity) player, player.getPos(), particleCount, 1);
                } else {
                    PlayerJumpHelper.generateRingParticles(player, particleCount,0.1);
                }

            }else{
                player.sendMessage(Text.translatable("message.chineserpg.insufficient_inner_power").formatted(Formatting.DARK_RED, Formatting.BOLD), true);
            }
        }
    }

}
