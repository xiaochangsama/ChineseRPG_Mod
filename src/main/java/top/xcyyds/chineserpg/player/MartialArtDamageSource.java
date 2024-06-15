package top.xcyyds.chineserpg.player;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Text;

public class MartialArtDamageSource extends DamageSource {



    public MartialArtDamageSource(RegistryEntry<DamageType> type, LivingEntity source, LivingEntity attacker) {
        super(type,source,attacker);
    }

    @Override
    public Text getDeathMessage(LivingEntity killed) {
        String entityName = killed.getDisplayName().getString();
        String deathMessageKey = "death.attack." + this.getName() + ".player";
        return Text.translatable(deathMessageKey,entityName);
    }
}
