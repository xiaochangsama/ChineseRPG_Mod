package top.xcyyds.chineserpg.register;


import top.xcyyds.chineserpg.event.FabricEventManager;

public class ChineseRPGRegister {
    public static void registryAllThings() {
        //注册物品
        ItemRegister.registryItem();
        //注册物品组
        ItemGroupRegister.registryItemGroup();
        //加入物品到物品组
        /* 有两种方式可以将物品放到物品组：
        一种是用putItemIntoItemGroup()加入原版物品组
        一种是在ChineseRPGItemGroup类中加入自定义物品组
         */

        //注册事件
        FabricEventManager.registryEvent();
    }
}
