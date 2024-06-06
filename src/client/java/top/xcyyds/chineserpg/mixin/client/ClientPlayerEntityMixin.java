package top.xcyyds.chineserpg.mixin.client;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import top.xcyyds.chineserpg.player.data.IPlayerDataProvider;
import top.xcyyds.chineserpg.player.data.PlayerData;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin implements IPlayerDataProvider {
    private final PlayerData playerData = new PlayerData();

    @Override
    public PlayerData getPlayerData() {
        return playerData;
    }

    @Unique
    public void readCustomDataFromNbt(NbtCompound nbt) {
        playerData.readFromNbt(nbt);
    }
}
