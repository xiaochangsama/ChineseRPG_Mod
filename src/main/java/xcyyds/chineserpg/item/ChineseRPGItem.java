package xcyyds.chineserpg.item;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;


//这个类继承mc原本的Item，以后用来拓展各种各样的新功能
public class ChineseRPGItem extends Item {

    //创建物品实例，之后去注册
    public static Item TEST_ITEM = new ChineseRPGItem();

    //第一种构造函数
    public ChineseRPGItem() {
        super(new Settings()
                .maxCount(16)//用这种方式来向构造函数传入基本修改
        );
    }

    //在这里Override其他的功能，让物品可以实现更多如播放声音的功能

    //物品提示，之后可以设计用按键来打开物品提示
    @Override
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
//        tooltip.add(Text.translatable("item.chineserpg.test_item.tooltip"));
        tooltip.add(Text.of("这个物品来自XiaoChangSAMA"));
    }
}
