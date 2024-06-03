package top.xcyyds.chineserpg.itemgroup;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import top.xcyyds.chineserpg.item.ChineseRPGItem;


//直接在这里创建物品组，之后去注册
public class ChineseRPGItemGroup {
    public static final ItemGroup KUNG_FU_ITEM_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(ChineseRPGItem.WATER_BOOK_LOW))
            .displayName(Text.translatable("itemGroup.chineserpg.kung_fu_item_group"))
            .entries((context, entries) -> {
                entries.add(ChineseRPGItem.WATER_BOOK_LOW);
            })
            .build();
}
