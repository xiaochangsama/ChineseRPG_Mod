package top.xcyyds.chineserpg.effect;

import net.minecraft.client.MinecraftClient;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.Vec3d;

public class RingEffect {
    public static void generateRing(MinecraftClient client, Vec3d pos, int particleCount, double radius) {
        double angleStep = 2 * Math.PI / particleCount;
        for (int i = 0; i < particleCount; i++) {
            double angle = i * angleStep;
            double x = pos.x + radius * Math.cos(angle);
            double z = pos.z + radius * Math.sin(angle);
            client.world.addParticle(ParticleTypes.CLOUD, x, pos.y, z, 0, 0, 0);
        }
    }
}
