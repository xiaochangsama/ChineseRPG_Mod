package top.xcyyds.chineserpg.item;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import top.xcyyds.chineserpg.martialart.MartialArts;
import top.xcyyds.chineserpg.martialart.skill.MartialArt;

public class OuterSkillBook extends BooksItem {

    public OuterSkillBook() {
        super();
    }

    // 创建随机的外功书本
    public static ItemStack createRandomOuterSkillBook() {
        ItemStack itemStack = new ItemStack(BooksItem.OUTER_SKILL_BOOK);

        // 获取随机的外功武功实例
        MartialArt randomOuterSkill = MartialArts.getRandomOuterSkill();

        // 将武功数据存入书籍的 NBT
        NbtCompound nbt = new NbtCompound();
        randomOuterSkill.writeToNbt(nbt);
        itemStack.setNbt(nbt);

        // 设置物品的名称为武功的名称，并且为金色
        itemStack.setCustomName(Text.literal(randomOuterSkill.getName()).formatted(Formatting.GOLD, Formatting.BOLD));

        return itemStack;
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);
        if (!stack.hasNbt() && stack.getItem() instanceof OuterSkillBook) {
            // 初始化外功书本数据
            ItemStack initializedStack = OuterSkillBook.createRandomOuterSkillBook();
            stack.setNbt(initializedStack.getNbt());
        }
    }

    // 用物品的实例注册
    public static void registryItem() {
        Registry.register(Registries.ITEM, "chineserpg:outer_skill_book", BooksItem.OUTER_SKILL_BOOK);
    }
}
