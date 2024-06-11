package top.xcyyds.chineserpg.model;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;
import top.xcyyds.chineserpg.entity.ReplacedPlayerEntity;

public class ReplacedPlayerModel extends GeoModel<ReplacedPlayerEntity> {
    @Override
    public Identifier getModelResource(ReplacedPlayerEntity object) {
        return new Identifier("chineserpg", "geo/player_model.geo.json");
    }

    @Override
    public Identifier getTextureResource(ReplacedPlayerEntity object) {
        PlayerEntity player = object.getPlayer();
        if (player instanceof AbstractClientPlayerEntity clientPlayer) {
            return clientPlayer.getSkinTexture();
        }
        return new Identifier("chineserpg", "textures/entity/player.png"); // 默认皮肤
    }

    @Override
    public Identifier getAnimationResource(ReplacedPlayerEntity animatable) {
        return new Identifier("chineserpg", "animations/player.animation.json");
    }
}
