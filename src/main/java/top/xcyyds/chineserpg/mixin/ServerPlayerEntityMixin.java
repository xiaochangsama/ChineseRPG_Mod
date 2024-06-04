package top.xcyyds.chineserpg.mixin;

import com.mojang.authlib.GameProfile;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.xcyyds.chineserpg.player.PlayerData;
import top.xcyyds.chineserpg.player.PlayerDataStorage;
import top.xcyyds.chineserpg.player.IPlayerDataProvider;

/*
先前通过将数据写入玩家的datatracker中，现在通过PlayerPersistentData来实现；
因为datatracker存储的数据以及交互逻辑过于复杂，我们的数据很难在其中存储和同步，所以使用PlayerPersistentData来实现更合适

这个逻辑（将玩家copy和spawn的时候的数据备份并存储，避免玩家从非死亡情况丢失数据）只在服务端玩家实体实现，因此玩家的服务端和客户端数据会不同步，因此需要网络发包的过程来同步数据
 */
@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin extends PlayerEntity implements IPlayerDataProvider {
    private final PlayerData persistentData = new PlayerData();

    public ServerPlayerEntityMixin(World world, BlockPos pos, float yaw, GameProfile gameProfile) {
        super(world, pos, yaw, gameProfile);
    }


    @Override
    public PlayerData getPlayerData() {
        return persistentData;
    }

    @SuppressWarnings("UnreachableCode")
    @Inject(method = "copyFrom", at = @At("TAIL"))
    private void copyFrom(ServerPlayerEntity oldPlayer, boolean alive, CallbackInfo info) {
        ServerPlayerEntity newPlayer = (ServerPlayerEntity) (Object) this;

        if (alive) {
            NbtCompound nbt = new NbtCompound();
            oldPlayer.writeCustomDataToNbt(nbt);
            PlayerDataStorage.set(oldPlayer.getUuid(), nbt);

//            //网络发包同步数据
//            PlayerDataSyncHandler.send(oldPlayer, nbt);
        }
    }

    @SuppressWarnings("UnreachableCode")
    @Inject(method = "onSpawn", at = @At("HEAD"))
    private void onSpawn(CallbackInfo info) {
        ServerPlayerEntity player = (ServerPlayerEntity) (Object) this;



            NbtCompound nbt = PlayerDataStorage.get(player.getUuid());
            //如果nbt数据存在
            if (nbt != null) {
                player.readCustomDataFromNbt(nbt);
                PlayerDataStorage.remove(player.getUuid());

//                //网络发包同步数据
//                PlayerDataSyncHandler.send(player, nbt);



            }

    }


}
