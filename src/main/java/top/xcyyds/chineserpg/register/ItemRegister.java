package top.xcyyds.chineserpg.register;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import static top.xcyyds.chineserpg.item.ChineseRPGItem.WATER_BOOK_LOW;

public class ItemRegister {


    //用物品的实例注册
    public static void registryItem(){
        Registry.register(Registries.ITEM, "chineserpg:water_book_low", WATER_BOOK_LOW);
    }

//    //将注册的物品添加到原版MC的物品组
//    public static void putItemsIntoItemGroup(){
//        ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register(content -> {
//            content.add(WATER_BOOK_LOW);
//        });
//    }

//    // 使用lambda表达式创建物品实例并包装注册方法
//    public static final Item TEST_ITEM = Registry.register(
//            Registries.ITEM,
//            new Identifier("chineserpg", "test_item"),
//            new ChineseRPGItem()
//    );

}
