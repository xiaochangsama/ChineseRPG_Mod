package xcyyds.chineserpg.item;

import net.minecraft.item.Item;

//这个类继承mc原本的Item，以后用来拓展各种各样的新功能
public class ChineseRPGItem extends Item {
    public ChineseRPGItem() {
        super(new Settings()
                .maxCount(16)//用这种方式来向构造函数传入基本修改
        );
        //在这里Override其他的功能，让物品可以实现更多如播放声音的功能
    }
}
