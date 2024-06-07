package top.xcyyds.chineserpg.effect;

import net.minecraft.client.MinecraftClient;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.Vec3d;

public class SphereEffect {
    public static void generateSphere(MinecraftClient client, Vec3d pos, int particleCount, double radius) {
        for (int i = 0; i < particleCount; i++) {
            double phi = Math.acos(2 * client.world.random.nextDouble() - 1);
            double theta = 2 * Math.PI * client.world.random.nextDouble();
            double x = pos.x + radius * Math.sin(phi) * Math.cos(theta);
            double y = pos.y + radius * Math.sin(phi) * Math.sin(theta);
            double z = pos.z + radius * Math.cos(phi);
            client.world.addParticle(ParticleTypes.CLOUD, x, y, z, 0, 0, 0);
        }
    }
}
