package top.xcyyds.chineserpg.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import top.xcyyds.chineserpg.ChineseRPG;
import top.xcyyds.chineserpg.effect.RingEffect;
import top.xcyyds.chineserpg.effect.SphereEffect;
import top.xcyyds.chineserpg.effect.SpiralEffect;

public class ClientParticleSyncHandler {

    public static final Identifier PARTICLE_PACKET_ID = new Identifier(ChineseRPG.MOD_ID, "particle_packet");

    public static void register() {
        // Register the client packet handler
        ClientPlayNetworking.registerGlobalReceiver(PARTICLE_PACKET_ID, (client, handler, buf, responseSender) -> {
            Vec3d pos = new Vec3d(buf.readDouble(), buf.readDouble(), buf.readDouble());
            int particleCount = buf.readInt();
            int effectType = buf.readInt();
            client.execute(() -> generateParticles(client, pos, particleCount, effectType));
        });
    }

    private static void generateParticles(MinecraftClient client, Vec3d pos, int particleCount, int effectType) {
        switch (effectType) {
            case 1:
                SphereEffect.generateSphere(client, pos, particleCount, 1.0);
                break;
            case 2:
                SpiralEffect.generateSpiral(client, pos, particleCount, 2.0, 1.0, 3.0);
                break;
            case 3:
                RingEffect.generateRing(client, pos, particleCount, 0.1);
                break;
            default:
                for (int i = 0; i < particleCount; i++) {
                    double xOffset = client.world.random.nextDouble() * 2 - 1;
                    double yOffset = client.world.random.nextDouble() * 2 - 1;
                    double zOffset = client.world.random.nextDouble() * 2 - 1;
                    client.world.addParticle(ParticleTypes.CLOUD, pos.x + xOffset, pos.y + yOffset, pos.z + zOffset, 0, 0, 0);
                }
        }
    }
}
