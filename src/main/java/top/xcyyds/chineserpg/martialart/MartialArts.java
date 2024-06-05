package top.xcyyds.chineserpg.martialart;

import top.xcyyds.chineserpg.registry.MartialArtRegistry;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class MartialArts {
    public static final MartialArt JIANGHU_LIGHT_SKILL;

    static {
        List<String> description = Arrays.asList(
                "不知是何人所创的轻功，广为流传。", // 描述的第一行
                "该轻功能够使人如履平地，飞檐走壁。", // 描述的第二行
                "轻功之妙，妙在一个“轻”字。", // 描述的第三行
                "习之者可轻盈跃起，浮空而行。", // 描述的第四行
                "——此功由江湖人士创作，流传甚广。" // 描述的第五行
        );
        JIANGHU_LIGHT_SKILL = new MartialArt(
                "江湖轻功", // 名称
                "轻功", // 类型
                1, // 等级
                100.0f, // 完整度
                description, // 描述
                "江湖人士" // 作者
        );

        // 创建武功词条
        MartialArtEntry entry = new MartialArtEntry(
                "左脚蹬右脚", // 名称
                1, // 等级
                "二段跳", // 跳跃类型
                1, // 跳跃次数
                0.0f, // 内力消耗
                0.0f, // 减伤高度
                0.0f, // 减伤比例
                0.0f // 闪避率
        );

        JIANGHU_LIGHT_SKILL.addEntry(entry);
    }

    public static void registerAll() {
        MartialArtRegistry.registerMartialArt(JIANGHU_LIGHT_SKILL);
    }
}
