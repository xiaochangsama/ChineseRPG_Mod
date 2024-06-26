package top.xcyyds.chineserpg.martialart;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import top.xcyyds.chineserpg.martialart.artentry.LightSkillEntry;
import top.xcyyds.chineserpg.martialart.artentry.MartialArtEntry;
import top.xcyyds.chineserpg.martialart.skill.MartialArt;
import top.xcyyds.chineserpg.player.data.PlayerData;
import top.xcyyds.chineserpg.player.speed.PlayerSpeedHandler;
import top.xcyyds.chineserpg.player.speed.PlayerSpeedHelper;

public class PlayerMartialArtHandler {
    // 学习轻功
    public static boolean learnLightSkill(PlayerData playerData, MartialArt martialArt, PlayerEntity user) {
        if (playerData.addLightSkill(martialArt.getUuid())) {
            user.sendMessage(Text.translatable("message.chineserpg.learned_skill", martialArt.getName()).formatted(Formatting.AQUA, Formatting.BOLD), true);
            return true;
        }
        return false;
    }

    // 学习外功
    public static boolean learnOuterSkill(PlayerData playerData, MartialArt martialArt, PlayerEntity user) {
        if (playerData.addOuterSkill(martialArt.getUuid())) {
            user.sendMessage(Text.translatable("message.chineserpg.learned_skill", martialArt.getName()).formatted(Formatting.AQUA, Formatting.BOLD), true);
            return true;
        }
        return false;
    }

    // 装备轻功
    public static boolean equipLightSkill(PlayerData playerData, MartialArt martialArt, PlayerEntity user) {
        if (!martialArt.getUuid().equals(playerData.getEquippedLightSkillUUID())) {
            playerData.equipLightSkill(martialArt.getUuid()); // 在这里装备了武功
            playerData.resetJumpCount(); // 重置跳跃计数
            // 根据装备的武功处理玩家速度
            if (user instanceof ServerPlayerEntity) {
                PlayerSpeedHelper.resetSpeed((ServerPlayerEntity) user);
                for (MartialArtEntry entry : martialArt.getEntries()) {
                    if (entry instanceof LightSkillEntry lightSkillEntry) {
                        if (PlayerSpeedHandler.SPRINT_SPEED_UP.equals(lightSkillEntry.getJumpType())) {
                            PlayerSpeedHelper.increaseSpeed((ServerPlayerEntity) user, lightSkillEntry.getDirectionalVelocity());
                            break;
                        }
                    }
                }
            }
            user.sendMessage(Text.translatable("message.chineserpg.equipped_skill", martialArt.getName()).formatted(Formatting.GOLD, Formatting.BOLD), true);
            return true;
        }
        return false;
    }

    // 装备外功
    public static boolean equipOuterSkill(PlayerData playerData, MartialArt martialArt, PlayerEntity user) {
        if (!martialArt.getUuid().equals(playerData.getEquippedJianSkill())) {
            playerData.equipJianSkill(martialArt.getUuid()); // 在这里装备了武功
            // 处理外功相关逻辑
            user.sendMessage(Text.translatable("message.chineserpg.equipped_skill", martialArt.getName()).formatted(Formatting.GOLD, Formatting.BOLD), true);
            return true;
        }
        return false;
    }
}
