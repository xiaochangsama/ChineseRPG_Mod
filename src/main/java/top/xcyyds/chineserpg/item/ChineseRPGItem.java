package top.xcyyds.chineserpg.item;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;

import java.util.List;

//这个类继承mc原本的Item，以后用来拓展各种各样的新功能
public abstract class ChineseRPGItem extends Item {

    //第一种构造函数
    public ChineseRPGItem(Settings settings) {
        super(settings);
    }

    //在这里Override其他的功能，让物品可以实现更多如播放声音的功能
    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    //物品提示，之后可以设计用按键来打开物品提示
    @Override
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        tooltip.add(Text.of("这个物品来自XiaoChangSAMA"));
    }
}
