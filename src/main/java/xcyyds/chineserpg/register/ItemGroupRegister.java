package xcyyds.chineserpg.register;


import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static xcyyds.chineserpg.itemgroup.ChineseRPGItemGroup.KUNG_FU_ITEM_GROUP;


public class ItemGroupRegister {
    public static void registryItemGroup(){
        Registry.register(Registries.ITEM_GROUP, new Identifier("chineserpg", "kung_fu_item_group"), KUNG_FU_ITEM_GROUP);
    }
}
