package top.xcyyds.chineserpg.item;

import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;



//这个类继承mc原本的Item，以后用来拓展各种各样的新功能
public class BooksItem extends ChineseRPGItem {

    //创建物品实例，之后去注册
    public static Item WATER_BOOK_LOW = new BooksItem();
    public static Item EARTH_BOOK_LOW = new BooksItem();
    public static Item FIRE_BOOK_LOW = new BooksItem();
    public static Item GOLD_BOOK_LOW = new BooksItem();
    public static Item WOOD_BOOK_LOW = new BooksItem();

    //第一种构造函数
    public BooksItem() {
        super(new Settings()
                .maxCount(1)//用这种方式来向构造函数传入基本修改
        );
    }
    //用物品的实例注册
    public static void registryItem(){
        Registry.register(Registries.ITEM, "chineserpg:water_book_low", WATER_BOOK_LOW);
        Registry.register(Registries.ITEM, "chineserpg:earth_book_low", EARTH_BOOK_LOW);
        Registry.register(Registries.ITEM, "chineserpg:fire_book_low", FIRE_BOOK_LOW);
        Registry.register(Registries.ITEM, "chineserpg:gold_book_low", GOLD_BOOK_LOW);
        Registry.register(Registries.ITEM, "chineserpg:wood_book_low", WOOD_BOOK_LOW);
    }

    //在这里Override其他的功能，让物品可以实现更多如播放声音的功能


}
