package top.xcyyds.chineserpg.itemgroup;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import top.xcyyds.chineserpg.item.BooksItem;
import top.xcyyds.chineserpg.martialart.MartialArtLoader;
import top.xcyyds.chineserpg.martialart.skill.MartialArt;

public class OuterSkillItemGroup {
    public static final ItemGroup OUTER_SKILL_ITEM_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(BooksItem.OUTER_SKILL_BOOK))
            .displayName(Text.translatable("itemGroup.chineserpg.outer_skill_item_group"))
            .entries((context, entries) -> {
                // 加载 JSON 文件中的武功书籍
                for (MartialArt martialArt : MartialArtLoader.loadMartialArts()) {
                    if ("外功".equals(martialArt.getType())) {
                        ItemStack itemStack = BooksItem.createOuterSkillBook(martialArt);
                        entries.add(itemStack);
                    }
                }
            })
            .build();

    public static void registerItemGroup() {
        Registry.register(Registries.ITEM_GROUP, new Identifier("chineserpg", "outer_skill_item_group"), OUTER_SKILL_ITEM_GROUP);
    }
}
