package top.xcyyds.chineserpg.event;

import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import top.xcyyds.chineserpg.martialart.artentry.LightSkillEntry;
import top.xcyyds.chineserpg.martialart.artentry.MartialArtEntry;
import top.xcyyds.chineserpg.martialart.skill.MartialArt;
import top.xcyyds.chineserpg.player.data.IPlayerDataProvider;
import top.xcyyds.chineserpg.player.data.PlayerData;
import top.xcyyds.chineserpg.registry.MartialArtRegistry;

import java.util.List;
import java.util.Random;
import java.util.UUID;

public class PlayerDamageEvent {
    public static void register() {
        PlayerDamageCallback.EVENT.register((player, source, amount) -> {
            Entity attacker = source.getAttacker();
            if (attacker instanceof MobEntity || attacker instanceof ProjectileEntity) {
                PlayerData playerData = ((IPlayerDataProvider) player).getPlayerData();
                float totalDodgeRate = 0.0f;

                // 获取装备的武功
                UUID equippedSkill = playerData.getEquippedLightSkill();
                MartialArt martialArt = MartialArtRegistry.getMartialArt(equippedSkill);
                if (martialArt != null) {
                    List<MartialArtEntry> entries = martialArt.getEntries();
                    for (MartialArtEntry entry : entries) {
                        if (entry instanceof LightSkillEntry lightSkillEntry) {
                            totalDodgeRate += lightSkillEntry.getDodgeRate();
                        }
                    }
                }
                totalDodgeRate = totalDodgeRate / 100;

                // 判断是否成功闪避
                if (totalDodgeRate > 0) {
                    Random random = new Random();
                    if (random.nextFloat() < totalDodgeRate) {
                        player.sendMessage(Text.translatable("message.chineserpg.dodge_success").formatted(Formatting.WHITE), true);
                        return true; // 取消伤害
                    }
                }
            }
            return false; // 不取消伤害
        });
    }
}
