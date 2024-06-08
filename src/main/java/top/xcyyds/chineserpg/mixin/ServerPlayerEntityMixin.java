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
import top.xcyyds.chineserpg.martialart.artentry.LightSkillEntry;
import top.xcyyds.chineserpg.martialart.artentry.MartialArtEntry;
import top.xcyyds.chineserpg.martialart.skill.MartialArt;
import top.xcyyds.chineserpg.player.data.IPlayerDataProvider;
import top.xcyyds.chineserpg.player.data.PlayerData;
import top.xcyyds.chineserpg.player.data.PlayerDataStorage;
import top.xcyyds.chineserpg.player.speed.PlayerSpeedHandler;
import top.xcyyds.chineserpg.player.speed.PlayerSpeedHelper;

import java.util.UUID;

import static top.xcyyds.chineserpg.registry.MartialArtRegistry.getMartialArt;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin extends PlayerEntity implements IPlayerDataProvider {
    private final PlayerData playerData = new PlayerData();

    public ServerPlayerEntityMixin(World world, BlockPos pos, float yaw, GameProfile gameProfile) {
        super(world, pos, yaw, gameProfile);
    }

    @Override
    public PlayerData getPlayerData() {
        return playerData;
    }

    @SuppressWarnings("UnreachableCode")
    @Inject(method = "copyFrom", at = @At("TAIL"))
    private void copyFrom(ServerPlayerEntity oldPlayer, boolean alive, CallbackInfo info) {
        if (alive) {
            NbtCompound nbt = new NbtCompound();
            //将自定义数据写入nbt
            ((IPlayerDataProvider) oldPlayer).getPlayerData().writeToNbt(nbt);
            PlayerDataStorage.set(oldPlayer.getUuid(), nbt);
        }
    }

    @SuppressWarnings("UnreachableCode")
    @Inject(method = "onSpawn", at = @At("HEAD"))
    private void onSpawn(CallbackInfo info) {
        ServerPlayerEntity player = (ServerPlayerEntity) (Object) this;
        UUID equippedSkill = playerData.getEquippedSkill();
        MartialArt martialArt = getMartialArt(equippedSkill);
        PlayerSpeedHelper.resetSpeed(player);
        if (martialArt != null) {
            for (MartialArtEntry entry : martialArt.getEntries()) {
                if (entry instanceof LightSkillEntry) {
                    LightSkillEntry lightSkillEntry = (LightSkillEntry) entry;
                    if (PlayerSpeedHandler.SPRINT_SPEED_UP.equals(lightSkillEntry.getJumpType())) {
                        PlayerSpeedHelper.increaseSpeed(player, lightSkillEntry.getDirectionalVelocity());
                        break;
                    }
                }
            }
        }

        NbtCompound nbt = PlayerDataStorage.get(player.getUuid());
        if (nbt != null) {
            player.readCustomDataFromNbt(nbt);
            this.getPlayerData().readFromNbt(nbt);
            PlayerDataStorage.remove(player.getUuid());
        }
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("HEAD"))
    private void writeCustomDataToNbt(NbtCompound nbt, CallbackInfo info) {
        NbtCompound data = new NbtCompound();
        this.getPlayerData().writeToNbt(data);
        nbt.put("PlayerData", data);
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("HEAD"))
    private void readCustomDataFromNbt(NbtCompound nbt, CallbackInfo info) {
        if (nbt.contains("PlayerData", 10)) {
            this.getPlayerData().readFromNbt(nbt.getCompound("PlayerData"));
        }
    }
}
