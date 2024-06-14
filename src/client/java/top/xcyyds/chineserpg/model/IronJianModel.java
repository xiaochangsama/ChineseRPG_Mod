package top.xcyyds.chineserpg.model;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;
import top.xcyyds.chineserpg.item.ChineseRPGJianItem;

import static top.xcyyds.chineserpg.ChineseRPG.MOD_ID;

public class IronJianModel extends GeoModel<ChineseRPGJianItem> {

    @Override
    public Identifier getModelResource(ChineseRPGJianItem object) {
        return new Identifier(MOD_ID, "geo/iron_jian.geo.json");
    }

    @Override
    public Identifier getTextureResource(ChineseRPGJianItem object) {
        return new Identifier(MOD_ID, "textures/item/iron_jian.png");
    }

    @Override
    public Identifier getAnimationResource(ChineseRPGJianItem object) {
        return new Identifier(MOD_ID, "animations/iron_jian.animation.json");
    }
}

