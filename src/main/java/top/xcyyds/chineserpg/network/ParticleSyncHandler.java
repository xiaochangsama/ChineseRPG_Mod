package top.xcyyds.chineserpg.network;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import top.xcyyds.chineserpg.ChineseRPG;

public class ParticleSyncHandler {

    public static final Identifier PARTICLE_PACKET_ID = new Identifier(ChineseRPG.MOD_ID, "particle_packet");

    public static void register() {
        // Register the server packet handler if needed
    }

    public static void sendParticlePacket(ServerPlayerEntity player, Vec3d pos, int particleCount, int effectType) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeDouble(pos.x);
        buf.writeDouble(pos.y);
        buf.writeDouble(pos.z);
        buf.writeInt(particleCount);
        buf.writeInt(effectType);
        ServerPlayNetworking.send(player, PARTICLE_PACKET_ID, buf);
    }
}
