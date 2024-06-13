package top.xcyyds.chineserpg.renderer;

import software.bernie.geckolib.renderer.GeoItemRenderer;
import top.xcyyds.chineserpg.item.ChineseRPGJianItem;
import top.xcyyds.chineserpg.model.IronJianModel;

public class IronJianRenderer extends GeoItemRenderer<ChineseRPGJianItem> {

    public static final IronJianRenderer INSTANCE = new IronJianRenderer();

    public IronJianRenderer() {
        super(new IronJianModel());
    }
}
