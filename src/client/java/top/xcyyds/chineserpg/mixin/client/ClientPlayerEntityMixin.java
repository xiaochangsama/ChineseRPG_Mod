package top.xcyyds.chineserpg.mixin.client;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import top.xcyyds.chineserpg.player.PlayerData;
import top.xcyyds.chineserpg.player.IPlayerDataProvider;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin implements IPlayerDataProvider {
    private final PlayerData persistentData = new PlayerData();

    @Override
    public PlayerData getPlayerData() {
        return persistentData;
    }

    @Unique
    public void readCustomDataFromNbt(NbtCompound nbt) {
        persistentData.readFromNbt(nbt);
    }
}
