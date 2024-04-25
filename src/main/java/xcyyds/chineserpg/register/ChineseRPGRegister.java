package xcyyds.chineserpg.register;


import xcyyds.chineserpg.event.FabricEventManager;

import static xcyyds.chineserpg.register.ItemGroupRegister.registryItemGroup;
import static xcyyds.chineserpg.register.ItemRegister.putItemsIntoItemGroup;
import static xcyyds.chineserpg.register.ItemRegister.registryItem;

public class ChineseRPGRegister {
    public static void registryAllThings() {
        //注册物品
        registryItem();
        //注册物品组
        registryItemGroup();
        //加入物品到物品组
        /* 有两种方式可以将物品放到物品组：
        一种是用putItemIntoItemGroup()加入原版物品组
        一种是在ChineseRPGItemGroup类中加入自定义物品组
         */
        putItemsIntoItemGroup();
        //注册事件
        FabricEventManager.registryEvent();
    }
}
