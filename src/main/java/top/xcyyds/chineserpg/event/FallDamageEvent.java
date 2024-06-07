package top.xcyyds.chineserpg.event;

import top.xcyyds.chineserpg.martialart.MartialArt;
import top.xcyyds.chineserpg.martialart.MartialArtEntry;
import top.xcyyds.chineserpg.player.data.IPlayerDataProvider;
import top.xcyyds.chineserpg.player.data.PlayerData;
import top.xcyyds.chineserpg.registry.MartialArtRegistry;

import java.util.UUID;

public class FallDamageEvent {
    public static void register() {
        FallDamageCallback.EVENT.register((player, fallDistance) -> {
            PlayerData playerData = ((IPlayerDataProvider) player).getPlayerData();
            float reductionHeight = 0.0f;
            float reductionPercentage = 0.0f;
            UUID equippedSkill = playerData.getEquippedSkill();
            MartialArt martialArt = MartialArtRegistry.getMartialArt(equippedSkill);
            if (martialArt != null) {
                for (MartialArtEntry entry : martialArt.getEntries()) {
                    reductionHeight = entry.getDamageReductionHeight();
                    reductionPercentage = entry.getDamageReductionPercentage();
                    break;
                }
            }
            float adjustedFallDistance = Math.max(0.0f, fallDistance - reductionHeight);
            if (adjustedFallDistance <= 3.0f) {
                return 0.0f;
            }
            return adjustedFallDistance * (1 - reductionPercentage);
        });
    }
}
