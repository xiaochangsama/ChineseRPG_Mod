package top.xcyyds.chineserpg.itemgroup;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import top.xcyyds.chineserpg.item.BooksItem;
import top.xcyyds.chineserpg.item.ChineseRPGJianItem;

import static top.xcyyds.chineserpg.ChineseRPG.MOD_ID;

        /* 有两种方式可以将物品放到物品组：
        一种是用putItemIntoItemGroup()加入原版物品组
        一种是在ChineseRPGItemGroup类中加入自定义物品组
         */

//直接在这里创建物品组，之后去注册
public class ChineseRPGItemGroup {
    public static final ItemGroup KUNG_FU_ITEM_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(BooksItem.LIGHT_SKILL_BOOK))
            .displayName(Text.translatable("itemGroup.chineserpg.kung_fu_item_group"))
            .entries((context, entries) -> {
                entries.add(ChineseRPGJianItem.IRON_JIAN);
                entries.add(BooksItem.LIGHT_SKILL_BOOK);
                entries.add(BooksItem.OUTER_SKILL_BOOK);
            })
            .build();
    public static void registryItemGroup(){
        Registry.register(Registries.ITEM_GROUP, new Identifier(MOD_ID, "kung_fu_item_group"), KUNG_FU_ITEM_GROUP);
    }
}
