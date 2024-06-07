package top.xcyyds.chineserpg.event;

import top.xcyyds.chineserpg.martialart.MartialArt;
import top.xcyyds.chineserpg.martialart.MartialArtEntry;
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
            UUID equippedSkill = playerData.getEquippedSkill();
            MartialArt martialArt = MartialArtRegistry.getMartialArt(equippedSkill);
            if (martialArt != null) {
//                //取首个词条的这些数据
//                for (MartialArtEntry entry : martialArt.getEntries()) {
//                    reductionHeight = entry.getDamageReductionHeight();
//                    reductionPercentage = entry.getDamageReductionPercentage();
//                    break;
//                }
                //叠加所有词条
                for (MartialArtEntry entry : martialArt.getEntries()) {
                    reductionHeight += entry.getDamageReductionHeight();
                    reductionPercentage += entry.getDamageReductionPercentage();
                }

            }

            // 应用高度减免
            float adjustedFallDistance = Math.max(0.0f, fallDistance - reductionHeight);
            if (adjustedFallDistance <= 3.0f) {
                return 0.0f; // 不造成伤害
            }

            // 计算减免后的伤害
            float damage = adjustedFallDistance * damageMultiplier * (1 - reductionPercentage / 100);
            return damage;
        });
    }
}
