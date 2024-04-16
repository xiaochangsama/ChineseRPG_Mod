package xcyyds.chineserpg.register;

import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import xcyyds.chineserpg.item.ChineseRPGItem;

public class ChineseRPGRegister {
    public static void registryAllThings(){
        //注册物品
        Registry.register(Registries.ITEM, "chineserpg:test_item", new ChineseRPGItem());
    }
}
