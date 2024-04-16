package xcyyds.chineserpg.itemgroup;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

import static xcyyds.chineserpg.item.ChineseRPGItem.TEST_ITEM;


//直接在这里创建物品组，之后去注册
public class ChineseRPGItemGroup {
    public static final ItemGroup TEST_ITEM_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(TEST_ITEM))
            .displayName(Text.translatable("itemGroup.tutorial.test_group"))
            .entries((context, entries) -> {
                entries.add(TEST_ITEM);
            })
            .build();
}
