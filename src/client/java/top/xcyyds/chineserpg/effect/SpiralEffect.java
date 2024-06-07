package top.xcyyds.chineserpg.effect;

import net.minecraft.client.MinecraftClient;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.Vec3d;

public class SpiralEffect {
    public static void generateSpiral(MinecraftClient client, Vec3d pos, int particleCount, double height, double radius, double turns) {
        double step = height / particleCount;
        double angleStep = 2 * Math.PI * turns / particleCount;
        for (int i = 0; i < particleCount; i++) {
            double y = pos.y + i * step;
            double angle = i * angleStep;
            double x = pos.x + radius * Math.cos(angle);
            double z = pos.z + radius * Math.sin(angle);
            client.world.addParticle(ParticleTypes.CLOUD, x, y, z, 0, 0, 0);
        }
    }
}
