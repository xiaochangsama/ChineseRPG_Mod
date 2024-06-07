package top.xcyyds.chineserpg.player.jump;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.Vec3d;
import top.xcyyds.chineserpg.martialart.MartialArt;
import top.xcyyds.chineserpg.martialart.MartialArtEntry;
import top.xcyyds.chineserpg.player.data.PlayerData;

import java.util.List;
import java.util.UUID;

import static top.xcyyds.chineserpg.registry.MartialArtRegistry.getMartialArt;


/**
 * 定义不同的跳跃类型，在MartialArts定义中使用
 */
public class PlayerJumpHandler {

    //跳跃类型
    public static final String Multi_JUMP = "多段跳";

    //根据玩家跳跃类型，选择不同的跳跃方法
    public static void toJump(PlayerEntity player, PlayerData playerData){

        UUID equippedSkill = playerData.getEquippedSkill();
        MartialArt martialArt = getMartialArt(equippedSkill);
        if (martialArt != null) {
            List<MartialArtEntry> martialArtEntries = martialArt.getEntries();
            for (MartialArtEntry martialArtEntry : martialArtEntries) {
                if (martialArtEntry.getJumpType().equals(Multi_JUMP)) {
                    basicJump( player, playerData, martialArtEntry.getVelocityYIncrease(), martialArtEntry.getParticleCount(), martialArtEntry.getInnerPowerConsumption());
                }
            }
        }
    }
    // 不确定目前该类型中的跳跃是否能成功
    public static void basicJump(PlayerEntity player, PlayerData playerData, double newY, int particleCount, float innerPowerConsumption) {

        if (PlayerJumpHelper.consumeInnerPower(playerData, innerPowerConsumption)) {

            //这里可能导致bug，因为get到的velocity不确定是什么时候的，可能导致循环等问题
            Vec3d vec3d =player.getVelocity();
            playerData.setPlayerVelocity(vec3d.getX(),newY, vec3d.getZ());

            // 生成跳跃气团
            PlayerJumpHelper.generateJumpParticles(player, particleCount);
        }else{
            player.sendMessage(Text.translatable("message.chineserpg.insufficient_inner_power").formatted(Formatting.DARK_RED, Formatting.BOLD), true);
        }
    }
}
