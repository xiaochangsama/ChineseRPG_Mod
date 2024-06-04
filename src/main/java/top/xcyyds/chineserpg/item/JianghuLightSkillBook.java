package top.xcyyds.chineserpg.item;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import top.xcyyds.chineserpg.registry.MartialArt;

public class JianghuLightSkillBook extends BooksItem {

    public JianghuLightSkillBook() {
        super();
    }

    public static ItemStack createJianghuLightSkillBook() {
        ItemStack itemStack = new ItemStack(BooksItem.JIANGHU_LIGHT_SKILL_BOOK);

        // 初始化武功数据
        MartialArt jianghuLightSkill = new MartialArt(
                "江湖轻功", // 名称
                "轻功", // 类型
                1, // 等级
                100.0f, // 完整度
                "不知是何人所创的轻功，广为流传。", // 描述
                "江湖人士" // 作者
        );

        // 创建武功词条
        MartialArtEntry entry = new MartialArtEntry(
                "左脚蹬右脚", // 名称
                1, // 等级
                "二段跳", // 主属性类型
                "二段跳", // 跳跃类型
                1, // 跳跃次数
                0.0f, // 内力消耗
                0.0f, // 减伤高度
                0.0f, // 减伤比例
                0.0f // 闪避率
        );

        jianghuLightSkill.addEntry(entry);

        // 将武功数据存入书籍的 NBT
        NbtCompound nbt = new NbtCompound();
        jianghuLightSkill.writeToNbt(nbt);
        itemStack.setNbt(nbt);

        // 设置物品的名称为武功的名称，并且为金色
        itemStack.setCustomName(Text.literal(jianghuLightSkill.getName()).formatted(Formatting.GOLD,Formatting.BOLD));


        return itemStack;
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);
        if (!stack.hasNbt() && stack.getItem() instanceof JianghuLightSkillBook) {
            // 初始化江湖轻功书籍数据
            ItemStack initializedStack = JianghuLightSkillBook.createJianghuLightSkillBook();
            stack.setNbt(initializedStack.getNbt());
        }
    }

    // 用物品的实例注册
    public static void registryItem() {
        Registry.register(Registries.ITEM, "chineserpg:jianghu_light_skill", BooksItem.JIANGHU_LIGHT_SKILL_BOOK);
    }
}
