package xcyyds.chineserpg.register;


import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static xcyyds.chineserpg.itemgroup.ChineseRPGItemGroup.TEST_ITEM_GROUP;


public class ItemGroupRegister {
    public static void registryItemGroup(){
        Registry.register(Registries.ITEM_GROUP, new Identifier("tutorial", "test_group"), TEST_ITEM_GROUP);
    }
}
