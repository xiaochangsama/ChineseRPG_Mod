package top.xcyyds.chineserpg.martialart;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import top.xcyyds.chineserpg.player.data.PlayerData;
import top.xcyyds.chineserpg.player.speed.PlayerSpeedHandler;
import top.xcyyds.chineserpg.player.speed.PlayerSpeedHelper;

public class PlayerMartialArtHandler {
    // 学习武功
    public static boolean learnMartialArt(PlayerData playerData, MartialArt martialArt, PlayerEntity user) {
        if (playerData.addSkill(martialArt.getUuid())) {
            user.sendMessage(Text.translatable("message.chineserpg.learned_skill", martialArt.getName()).formatted(Formatting.AQUA, Formatting.BOLD), true);
            return true;
        }
        return false;
    }

    // 装备武功
    public static boolean equipMartialArt(PlayerData playerData, MartialArt martialArt, PlayerEntity user) {
        if (!martialArt.getUuid().equals(playerData.getEquippedSkill())) {
            playerData.equipSkill(martialArt.getUuid());//在这里装备了武功
            //根据装备的武功处理玩家速度
            if (user instanceof ServerPlayerEntity) {
                PlayerSpeedHelper.resetSpeed((ServerPlayerEntity) user);
                for (MartialArtEntry entry : martialArt.getEntries()) {
                   if (PlayerSpeedHandler.SPRINT_SPEED_UP.equals(entry.getJumpType())) {
                        PlayerSpeedHelper.increaseSpeed((ServerPlayerEntity) user, entry.getDirectionalVelocity());
                        break;
                    }
                }
            }
            user.sendMessage(Text.translatable("message.chineserpg.equipped_skill", martialArt.getName()).formatted(Formatting.GOLD, Formatting.BOLD), true);
            return true;
        }
        return false;
    }
}
