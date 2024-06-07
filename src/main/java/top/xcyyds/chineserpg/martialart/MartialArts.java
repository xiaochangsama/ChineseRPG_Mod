package top.xcyyds.chineserpg.martialart;

import top.xcyyds.chineserpg.player.jump.PlayerJumpHandler;
import top.xcyyds.chineserpg.registry.MartialArtRegistry;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class MartialArts {
    private static MartialArt jianghuLightSkill;

    public static void registerAll() {
        jianghuLightSkill = createJianghuLightSkill();
        MartialArtRegistry.registerMartialArt(jianghuLightSkill);
    }

    private static MartialArt createJianghuLightSkill() {
        List<String> description = Arrays.asList(
                "不知是何人所创的轻功，广为流传。", // 描述的第一行
                "该轻功能够使人如履平地，飞檐走壁。", // 描述的第二行
                "轻功之妙，妙在一个“轻”字。", // 描述的第三行
                "习之者可轻盈跃起，浮空而行。", // 描述的第四行
                "——此功由江湖人士创作，流传甚广。" // 描述的第五行
        );
        MartialArt jianghuLightSkill = new MartialArt(
                "江湖轻功", // 名称
                "轻功", // 类型
                1, // 等级
                100.0f, // 完整度
                description, // 描述
                "江湖人士", // 作者
                UUID.fromString("56A528D5-CC8A-64A0-4EC8-79B7B8F20D9E")
        );

        // 创建武功词条
        MartialArtEntry entry = new MartialArtEntry(
                "左脚蹬右脚", // 名称,可以生成很多种不同的预设名称
                1, // 等级
                PlayerJumpHandler.Multi_JUMP, // 跳跃类型
                3, // 跳跃次数
                10.0f, // 内力消耗
                0.5f, // 跳跃力度
                1, // 气团数量
                0.0f, // 减伤高度
                0.0f, // 减伤比例
                0.0f // 闪避率
        );

        jianghuLightSkill.addEntry(entry);
        return jianghuLightSkill;
    }

    public static MartialArt getJianghuLightSkill() {
        if (jianghuLightSkill == null) {
            jianghuLightSkill = createJianghuLightSkill();
            MartialArtRegistry.registerMartialArt(jianghuLightSkill);
        }
        return jianghuLightSkill;
    }
}
