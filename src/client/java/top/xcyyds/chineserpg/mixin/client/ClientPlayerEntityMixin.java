package top.xcyyds.chineserpg.mixin.client;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import top.xcyyds.chineserpg.player.PlayerData;
import top.xcyyds.chineserpg.player.PlayerDataProvider;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin implements PlayerDataProvider {
    private final PlayerData persistentData = new PlayerData();

    @Override
    public PlayerData getPersistentData() {
        return persistentData;
    }

    @Unique
    public void readCustomDataFromNbt(NbtCompound nbt) {
        persistentData.readFromNbt(nbt);
    }
}
