package top.xcyyds.chineserpg.register;


import top.xcyyds.chineserpg.event.FabricEventManager;
import top.xcyyds.chineserpg.item.BooksItem;
import top.xcyyds.chineserpg.itemgroup.ChineseRPGItemGroup;

public class ChineseRPGRegister {
    public static void registryAllThings() {
        //注册物品
        BooksItem.registryItem();

        //注册物品组
        ChineseRPGItemGroup.registryItemGroup();

        //加入物品到物品组
        /* 有两种方式可以将物品放到物品组：
        一种是用putItemIntoItemGroup()加入原版物品组
        一种是在ChineseRPGItemGroup类中加入自定义物品组
         */

        //注册事件
        FabricEventManager.registryEvent();
    }
}
