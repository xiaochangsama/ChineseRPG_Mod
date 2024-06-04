package top.xcyyds.chineserpg.item;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import top.xcyyds.chineserpg.martialart.MartialArt;
import top.xcyyds.chineserpg.martialart.MartialArts;

public class JianghuLightSkillBook extends BooksItem {

    public JianghuLightSkillBook() {
        super();
    }

    public static ItemStack createJianghuLightSkillBook() {
        ItemStack itemStack = new ItemStack(BooksItem.JIANGHU_LIGHT_SKILL_BOOK);

        // 获取预定义的江湖轻功武功实例
        MartialArt jianghuLightSkill = MartialArts.JIANGHU_LIGHT_SKILL;

        // 将武功数据存入书籍的 NBT
        NbtCompound nbt = new NbtCompound();
        jianghuLightSkill.writeToNbt(nbt);
        itemStack.setNbt(nbt);

        // 设置物品的名称为武功的名称，并且为金色
        itemStack.setCustomName(Text.literal(jianghuLightSkill.getName()).formatted(Formatting.GOLD, Formatting.BOLD));

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
