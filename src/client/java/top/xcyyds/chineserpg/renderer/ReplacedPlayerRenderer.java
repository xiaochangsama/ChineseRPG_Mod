package top.xcyyds.chineserpg.renderer;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import software.bernie.geckolib.renderer.GeoReplacedEntityRenderer;
import top.xcyyds.chineserpg.entity.ReplacedPlayerEntity;
import top.xcyyds.chineserpg.model.ReplacedPlayerModel;

public class ReplacedPlayerRenderer extends GeoReplacedEntityRenderer<PlayerEntity, ReplacedPlayerEntity> {
    public ReplacedPlayerRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new ReplacedPlayerModel(), new ReplacedPlayerEntity(null)); // 用null简化构造
    }

    @Override
    public void render(PlayerEntity entity, float entityYaw, float partialTicks, MatrixStack matrixStackIn, VertexConsumerProvider bufferIn, int packedLightIn) {
        ReplacedPlayerEntity replacedEntity = new ReplacedPlayerEntity(entity);
        super.render(entity, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }
}
