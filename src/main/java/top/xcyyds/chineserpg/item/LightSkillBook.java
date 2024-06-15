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

public class LightSkillBook extends BooksItem {

    public LightSkillBook() {
        super();
    }

    // 创建随机的江湖轻功
    public static ItemStack createRandomLightSkillBook() {
        ItemStack itemStack = new ItemStack(BooksItem.LIGHT_SKILL_BOOK);

//        // 获取预定义的江湖轻功武功实例
//        MartialArt jianghuLightSkill = MartialArts.getJianghuLightSkill();
        //原本是上面那玩意
        MartialArt randomLightSkill = MartialArts.getRandomLightSkill();

        // 将武功数据存入书籍的 NBT
        NbtCompound nbt = new NbtCompound();
        if (randomLightSkill != null) {
            randomLightSkill.writeToNbt(nbt);
        }
        itemStack.setNbt(nbt);

        // 设置物品的名称为武功的名称，并且为金色
        itemStack.setCustomName(Text.literal(randomLightSkill.getName()).formatted(Formatting.GOLD, Formatting.BOLD));

        return itemStack;
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);
        if (!stack.hasNbt() && stack.getItem() instanceof LightSkillBook) {
            // 初始化江湖轻功书籍数据
            ItemStack initializedStack = LightSkillBook.createRandomLightSkillBook();
            stack.setNbt(initializedStack.getNbt());
        }
    }

    // 用物品的实例注册
    public static void registryItem() {
        Registry.register(Registries.ITEM, "chineserpg:light_skill_book", BooksItem.LIGHT_SKILL_BOOK);
    }
}
