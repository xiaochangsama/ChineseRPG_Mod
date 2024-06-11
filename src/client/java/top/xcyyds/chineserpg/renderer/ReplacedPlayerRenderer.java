package top.xcyyds.chineserpg.renderer;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.bernie.geckolib.renderer.GeoReplacedEntityRenderer;
import top.xcyyds.chineserpg.entity.ReplacedPlayerEntity;
import top.xcyyds.chineserpg.model.ReplacedPlayerModel;

public class ReplacedPlayerRenderer extends GeoReplacedEntityRenderer<PlayerEntity, ReplacedPlayerEntity> {
    private static final Logger LOGGER = LogManager.getLogger();

    public ReplacedPlayerRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new ReplacedPlayerModel(), null);
    }

    @Override
    public void render(PlayerEntity entity, float entityYaw, float partialTicks, MatrixStack matrixStackIn, VertexConsumerProvider bufferIn, int packedLightIn) {
        // 确保当前玩家实例不为空
        if (entity != null) {
            LOGGER.info("Rendering player: " + entity.getName().getString());

            // 动态创建并初始化 ReplacedPlayerEntity
            if (entity instanceof ClientPlayerEntity clientPlayer) {
                ReplacedPlayerEntity replacedEntity = new ReplacedPlayerEntity(clientPlayer);
                replacedEntity.initialize();
                super.render(replacedEntity, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
            } else {
                LOGGER.warn("当前玩家实例不是 ClientPlayerEntity 类型！");
            }
        } else {
            LOGGER.warn("当前玩家实例为空！");
        }
    }
}
