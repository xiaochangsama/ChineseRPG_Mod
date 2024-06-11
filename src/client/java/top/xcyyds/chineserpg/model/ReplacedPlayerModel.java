package top.xcyyds.chineserpg.model;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import top.xcyyds.chineserpg.entity.ReplacedPlayerEntity;

import static top.xcyyds.chineserpg.ChineseRPG.MOD_ID;

public class ReplacedPlayerModel extends DefaultedEntityGeoModel<ReplacedPlayerEntity> {
    public ReplacedPlayerModel() {
        super(new Identifier(MOD_ID, "geo/player_model.geo.json"));
    }

    @Override
    public Identifier getModelResource(ReplacedPlayerEntity object) {
        return new Identifier(MOD_ID, "geo/player_model.geo.json");
    }

    @Override
    public Identifier getTextureResource(ReplacedPlayerEntity object) {
        PlayerEntity player = object;
        if (player instanceof AbstractClientPlayerEntity clientPlayer) {
            return clientPlayer.getSkinTexture();
        }
        return new Identifier(MOD_ID, "textures/entity/player.png"); // 默认皮肤
    }

    @Override
    public Identifier getAnimationResource(ReplacedPlayerEntity animatable) {
        return new Identifier(MOD_ID, "animations/player.animation.json");
    }
}
