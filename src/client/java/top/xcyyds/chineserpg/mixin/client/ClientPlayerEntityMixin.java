package top.xcyyds.chineserpg.mixin.client;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import top.xcyyds.chineserpg.PlayerPersistentData;
import top.xcyyds.chineserpg.PlayerPersistentDataProvider;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin implements PlayerPersistentDataProvider {
    private final PlayerPersistentData persistentData = new PlayerPersistentData();

    @Override
    public PlayerPersistentData getPersistentData() {
        return persistentData;
    }

    @Unique
    public void readCustomDataFromNbt(NbtCompound nbt) {
        persistentData.readFromNbt(nbt);
    }
}
