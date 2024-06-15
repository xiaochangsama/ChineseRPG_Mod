package top.xcyyds.chineserpg.event;

import top.xcyyds.chineserpg.martialart.artentry.LightSkillEntry;
import top.xcyyds.chineserpg.martialart.artentry.MartialArtEntry;
import top.xcyyds.chineserpg.martialart.skill.MartialArt;
import top.xcyyds.chineserpg.player.data.IPlayerDataProvider;
import top.xcyyds.chineserpg.player.data.PlayerData;
import top.xcyyds.chineserpg.registry.MartialArtRegistry;

import java.util.UUID;

public class PlayerFallEvent {
    public static void register() {
        PlayerFallCallback.EVENT.register((player, fallDistance, damageMultiplier, damageSource) -> {
            PlayerData playerData = ((IPlayerDataProvider) player).getPlayerData();
            float reductionHeight = 0.0f;
            float reductionPercentage = 0.0f;
            UUID equippedSkill = playerData.getEquippedLightSkill();
            MartialArt martialArt = MartialArtRegistry.getMartialArt(equippedSkill);
            if (martialArt != null) {
                for (MartialArtEntry entry : martialArt.getEntries()) {
                    if (entry instanceof LightSkillEntry lightSkillEntry) {
                        reductionHeight += lightSkillEntry.getDamageReductionHeight();
                        reductionPercentage += lightSkillEntry.getDamageReductionPercentage();
                    }
                }
            }

            // 应用高度减免
            float adjustedFallDistance = Math.max(0.0f, fallDistance - reductionHeight);
            if (adjustedFallDistance <= 3.0f) {
                return 0.0f; // 不造成伤害
            }

            // 计算减免后的伤害
            return adjustedFallDistance * damageMultiplier * (1 - reductionPercentage / 100);
        });
    }
}
