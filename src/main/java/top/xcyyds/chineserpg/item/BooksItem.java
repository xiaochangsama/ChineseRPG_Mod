package top.xcyyds.chineserpg.item;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import top.xcyyds.chineserpg.martialart.skill.MartialArt;
import top.xcyyds.chineserpg.player.data.IPlayerDataProvider;
import top.xcyyds.chineserpg.player.data.PlayerData;

import java.util.List;

import static top.xcyyds.chineserpg.item.Tooltips.*;
import static top.xcyyds.chineserpg.martialart.PlayerMartialArtHandler.*;

public class BooksItem extends ChineseRPGItem {
    // 创建物品实例，之后去注册
    public static final Item LIGHT_SKILL_BOOK = new LightSkillBook();
    public static final Item OUTER_SKILL_BOOK = new OuterSkillBook();

    // 第一种构造函数
    public BooksItem() {
        super(new Settings().maxCount(1)); // 用这种方式来向构造函数传入基本修改
    }

    public static ItemStack createLightSkillBook(MartialArt martialArt) {
        ItemStack itemStack = new ItemStack(BooksItem.LIGHT_SKILL_BOOK);
        NbtCompound nbt = new NbtCompound();
        martialArt.writeToNbt(nbt);
        itemStack.setNbt(nbt);
        itemStack.setCustomName(Text.literal(martialArt.getName()).formatted(Formatting.GOLD, Formatting.BOLD));
        return itemStack;
    }

    public static ItemStack createOuterSkillBook(MartialArt martialArt) {
        ItemStack itemStack = new ItemStack(BooksItem.OUTER_SKILL_BOOK);
        NbtCompound nbt = new NbtCompound();
        martialArt.writeToNbt(nbt);
        itemStack.setNbt(nbt);
        itemStack.setCustomName(Text.literal(martialArt.getName()).formatted(Formatting.GOLD, Formatting.BOLD));
        return itemStack;
    }

    // 用物品的实例注册
    public static void registryItem() {
        LightSkillBook.registryItem();
        OuterSkillBook.registryItem();
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

            if (martialArt.getType().equals("轻功")) {
                if (learnLightSkill(playerData,martialArt, user)) {
                    user.sendMessage(Text.translatable("message.chineserpg.learned_skill", martialArt.getName()).formatted(Formatting.AQUA, Formatting.BOLD), true);
                } else if (playerData.getEquippedLightSkillUUID() == null || !playerData.getEquippedLightSkillUUID().equals(martialArt.getUuid())) {
                    equipLightSkill(playerData,martialArt, user);
                    user.sendMessage(Text.translatable("message.chineserpg.equipped_skill", martialArt.getName()).formatted(Formatting.GOLD, Formatting.BOLD), true);
                } else {
                    user.sendMessage(Text.translatable("message.chineserpg.already_equipped_skill", martialArt.getName()).formatted(Formatting.DARK_RED, Formatting.BOLD), true);
                }
                return new TypedActionResult<>(ActionResult.SUCCESS, stack);
            } else if (martialArt.getType().equals("外功")) {
                if (learnOuterSkill(playerData,martialArt, user)) {
                    user.sendMessage(Text.translatable("message.chineserpg.learned_skill", martialArt.getName()).formatted(Formatting.AQUA, Formatting.BOLD), true);
                } else if (playerData.getEquippedJianSkill() == null || !playerData.getEquippedJianSkill().equals(martialArt.getUuid())) {
                    equipOuterSkill(playerData,martialArt, user);
                    user.sendMessage(Text.translatable("message.chineserpg.equipped_skill", martialArt.getName()).formatted(Formatting.GOLD, Formatting.BOLD), true);
                } else {
                    user.sendMessage(Text.translatable("message.chineserpg.already_equipped_skill", martialArt.getName()).formatted(Formatting.DARK_RED, Formatting.BOLD), true);
                }
                return new TypedActionResult<>(ActionResult.SUCCESS, stack);
            }
        }
        return new TypedActionResult<>(ActionResult.PASS, stack);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if (stack.hasNbt()) {
            NbtCompound nbt = stack.getNbt();
            MartialArt martialArt = MartialArt.readFromNbt(nbt);

            // 类型
            tooltip.add(createTooltipText("§7[类型：" + martialArt.getType() + "§7]", Formatting.GOLD));
            // 名称，作者
            tooltip.add(createTooltipText("§8[封面]", Formatting.DARK_GRAY));
            tooltip.add(createTooltipText("§7 《" + martialArt.getName() + "》", Formatting.GOLD));
            tooltip.add(createTooltipText("           §7——" + martialArt.getAuthor() + " 著", Formatting.GOLD));
            // 品质，完整度
            Text quality = Text.translatable(getDisplayLevelName(martialArt.getLevel(), martialArt.getCompleteness())).formatted(getDisplayLevelColor(martialArt.getLevel()));
            Text completeness = Text.translatable(getCompletenessDescription(martialArt.getCompleteness())).formatted(Formatting.GRAY);
            tooltip.add(createTooltipText("§8[外观]", Formatting.DARK_GRAY));
            tooltip.add(Text.literal("§7[品质：").formatted(Formatting.GRAY)
                    .append(quality)
                    .append("§7] §7[完整度：")
                    .append(completeness)
                    .append("§7]"));
            // 内容
            tooltip.add(createTooltipText("§8[内容]", Formatting.DARK_GRAY));
            for (int i = 0; i < Math.min(martialArt.getDescription().size(), 8); i++) {
                tooltip.add(createTooltipText("§7" + martialArt.getDescription().get(i), Formatting.WHITE));
            }
            // 使用方法
            tooltip.add(createTooltipText("§8[使用方法]", Formatting.DARK_GRAY));
            tooltip.add(createTooltipText("学习/装备：" + "§7右键", Formatting.GOLD));
        }
    }
}
