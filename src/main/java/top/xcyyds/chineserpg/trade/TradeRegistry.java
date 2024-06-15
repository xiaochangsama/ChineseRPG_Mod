package top.xcyyds.chineserpg.trade;

import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.VillagerProfession;
import top.xcyyds.chineserpg.item.LightSkillBook;

public class TradeRegistry {
    public static void registerTrades() {
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.LIBRARIAN, 1, factories -> factories.add((entity, random) -> new TradeOffer(
                new ItemStack(Items.EMERALD, 50), // 50绿宝石
                LightSkillBook.createRandomLightSkillBook(),
                1, // 最大交易次数
                2, // 经验值
                0.1f // 价格乘数
        )));
    }
}
