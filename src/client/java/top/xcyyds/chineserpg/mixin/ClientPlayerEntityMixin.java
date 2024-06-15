package top.xcyyds.chineserpg.mixin;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import top.xcyyds.chineserpg.player.data.IPlayerDataProvider;
import top.xcyyds.chineserpg.player.data.PlayerData;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin implements IPlayerDataProvider {
    @Unique
    private final PlayerData playerData = new PlayerData((PlayerEntity) (Object) this);

    @Override
    public PlayerData getPlayerData() {
        return playerData;
    }
    //这一句很重要，不能删！！！！！！！！！！！！！！！！
    @Unique
    public void readCustomDataFromNbt(NbtCompound nbt) {
        playerData.readFromNbt(nbt);
    }
}
