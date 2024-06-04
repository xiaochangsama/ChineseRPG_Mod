package top.xcyyds.chineserpg.martialart;

import top.xcyyds.chineserpg.registry.MartialArtRegistry;

import java.util.UUID;

public class MartialArts {
    public static final MartialArt JIANGHU_LIGHT_SKILL;

    static {
        JIANGHU_LIGHT_SKILL = new MartialArt(
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

        JIANGHU_LIGHT_SKILL.addEntry(entry);
    }

    public static void registerAll() {
        MartialArtRegistry.registerMartialArt(JIANGHU_LIGHT_SKILL);
    }
}
