package top.xcyyds.chineserpg.item;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import top.xcyyds.chineserpg.player.IPlayerDataProvider;
import top.xcyyds.chineserpg.player.PlayerData;
import top.xcyyds.chineserpg.martialart.MartialArt;
import top.xcyyds.chineserpg.registry.MartialArtRegistry;

import java.util.List;
import java.util.UUID;

public class BooksItem extends ChineseRPGItem {

    // 创建物品实例，之后去注册
    public static final Item WATER_BOOK_LOW = new BooksItem();
    public static final Item EARTH_BOOK_LOW = new BooksItem();
    public static final Item FIRE_BOOK_LOW = new BooksItem();
    public static final Item GOLD_BOOK_LOW = new BooksItem();
    public static final Item WOOD_BOOK_LOW = new BooksItem();
    public static final Item JIANGHU_LIGHT_SKILL_BOOK = new JianghuLightSkillBook();

    // 第一种构造函数
    public BooksItem() {
        super(new Settings().maxCount(1)); // 用这种方式来向构造函数传入基本修改
    }

    // 用物品的实例注册
    public static void registryItem() {
        Registry.register(Registries.ITEM, "chineserpg:water_book_low", WATER_BOOK_LOW);
        Registry.register(Registries.ITEM, "chineserpg:earth_book_low", EARTH_BOOK_LOW);
        Registry.register(Registries.ITEM, "chineserpg:fire_book_low", FIRE_BOOK_LOW);
        Registry.register(Registries.ITEM, "chineserpg:gold_book_low", GOLD_BOOK_LOW);
        Registry.register(Registries.ITEM, "chineserpg:wood_book_low", WOOD_BOOK_LOW);
        JianghuLightSkillBook.registryItem();
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    // 当书被右键时，学习或装备武功
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        if (!world.isClient && stack.hasNbt()) {
            NbtCompound nbt = stack.getNbt();
            MartialArt martialArt = MartialArt.readFromNbt(nbt);

            // 将武功存储到PlayerData中
            PlayerData playerData = ((IPlayerDataProvider) user).getPlayerData();

            if (playerData.addSkill(martialArt.getUuid())) {
                user.sendMessage(Text.literal("你学会了 " + martialArt.getName() + "！").formatted(Formatting.GOLD, Formatting.BOLD), true);
                return new TypedActionResult<>(ActionResult.SUCCESS, stack);
            } else if (!martialArt.getUuid().equals(playerData.getEquippedSkill())) {
                playerData.equipSkill(martialArt.getUuid());
                user.sendMessage(Text.literal("你装备了 " + martialArt.getName() + "！").formatted(Formatting.GOLD, Formatting.BOLD), true);
                return new TypedActionResult<>(ActionResult.SUCCESS, stack);
            }else{
                user.sendMessage(Text.literal("你已经装备了 " + martialArt.getName() + "！").formatted(Formatting.DARK_RED, Formatting.BOLD), true);
            }
        }
        return new TypedActionResult<>(ActionResult.PASS, stack);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if (stack.hasNbt()) {
            NbtCompound nbt = stack.getNbt();
            MartialArt martialArt = MartialArt.readFromNbt(nbt);

            //类型
            tooltip.add(createTooltipText("§7[类型：" +martialArt.getType()+"§7]", Formatting.GOLD));
            //名称，作者
            tooltip.add(createTooltipText("§8[封面]", Formatting.DARK_GRAY));
            tooltip.add(createTooltipText("§7 《" + martialArt.getName()+"》", Formatting.GOLD));
            tooltip.add(createTooltipText("           §7——"+ martialArt.getAuthor()+" 著", Formatting.GOLD));
            //品质，完整度
            tooltip.add(createTooltipText("§8[外观]", Formatting.DARK_GRAY));
            tooltip.add(createTooltipText("§7[品质：" + getDisplayLevelName(martialArt.getLevel(),martialArt.getCompleteness())+"§7] "+"  §7[完整度：" + getCompletenessDescription(martialArt.getCompleteness())+"§7] ", getDisplayLevelColor(martialArt.getLevel())));
            //内容
            tooltip.add(createTooltipText("§8[内容]", Formatting.DARK_GRAY));
            tooltip.add(createTooltipText("§7" + martialArt.getDescription(), Formatting.WHITE));
            //使用方法
            tooltip.add(createTooltipText("§8[使用方法]", Formatting.DARK_GRAY));
            tooltip.add(createTooltipText("学习/装备："+"§7右键", Formatting.GOLD));
        }
    }

    private Text createTooltipText(String text, Formatting color) {
        return Text.literal(text).formatted(color);
    }

    private int calculateDisplayLevel(int level, float completeness) {
        if (completeness < 20) return level - 5;
        if (completeness < 40) return level - 4;
        if (completeness < 60) return level - 3;
        if (completeness < 80) return level - 2;
        if (completeness < 100) return level - 1;
        return level;
    }

    private String getDisplayLevelName(int level, float completeness) {
        int _level = calculateDisplayLevel(level, completeness);
        if (_level <= 0) return "tooltip.chineserpg.scrap";
        if (_level <= 3) return "tooltip.chineserpg.basic_skill";
        if (_level <= 6) return "tooltip.chineserpg.third_rate_skill";
        if (_level <= 9) return "tooltip.chineserpg.second_rate_skill";
        if (_level <= 12) return "tooltip.chineserpg.first_rate_skill";
        if (_level <= 15) return "tooltip.chineserpg.top_skill";
        if (_level <= 18) return "tooltip.chineserpg.epic_skill";
        if (_level <= 20) return "tooltip.chineserpg.semi_immortal_skill";
        return "tooltip.chineserpg.unknown_level";
    }

    private Formatting getDisplayLevelColor(int level) {
        if (level <= 0) return Formatting.DARK_GRAY;
        if (level <= 3) return Formatting.WHITE;
        if (level <= 6) return Formatting.GREEN;
        if (level <= 9) return Formatting.AQUA;
        if (level <= 12) return Formatting.DARK_BLUE;
        if (level <= 15) return Formatting.DARK_PURPLE;
        if (level <= 18) return Formatting.RED;
        if (level <= 20) return Formatting.DARK_RED;
        return Formatting.GOLD;
    }

    private String getCompletenessDescription(float completeness) {
        if (completeness < 20) return "tooltip.chineserpg.tattered_pages";
        if (completeness < 40) return "tooltip.chineserpg.scatter_fragments";
        if (completeness < 60) return "tooltip.chineserpg.normal_fragment";
        if (completeness < 80) return "tooltip.chineserpg.slightly_damaged";
        if (completeness < 100) return "tooltip.chineserpg.slightly_worn";
        return "tooltip.chineserpg.intact";
    }
}
