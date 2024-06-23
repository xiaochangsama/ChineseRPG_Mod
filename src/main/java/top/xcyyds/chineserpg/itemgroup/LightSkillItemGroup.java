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

import static top.xcyyds.chineserpg.ChineseRPG.MOD_ID;

public class LightSkillItemGroup {
    public static final ItemGroup LIGHT_SKILL_ITEM_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(BooksItem.LIGHT_SKILL_BOOK))
            .displayName(Text.translatable("itemGroup.chineserpg.light_skill_item_group"))
            .entries((context, entries) -> {
                // 加载 JSON 文件中的武功书籍
                for (MartialArt martialArt : MartialArtLoader.loadMartialArts()) {
                    if ("轻功".equals(martialArt.getType())) {
                        ItemStack itemStack = BooksItem.createLightSkillBook(martialArt);
                        entries.add(itemStack);
                    }
                }
            })
            .build();

    public static void registerItemGroup() {
        Registry.register(Registries.ITEM_GROUP, new Identifier(MOD_ID, "light_skill_item_group"), LIGHT_SKILL_ITEM_GROUP);
    }
}
