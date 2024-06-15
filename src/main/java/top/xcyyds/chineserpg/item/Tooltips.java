package top.xcyyds.chineserpg.item;

import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class Tooltips {
    public static Text createTooltipText(String text, Formatting color) {
        return Text.literal(text).formatted(color);
    }

    public static int calculateDisplayLevel(int level, float completeness) {
        if (completeness < 20) return level - 5;
        if (completeness < 40) return level - 4;
        if (completeness < 60) return level - 3;
        if (completeness < 80) return level - 2;
        if (completeness < 100) return level - 1;
        return level;
    }

    public static String getDisplayLevelName(int level, float completeness) {
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

    public static Formatting getDisplayLevelColor(int level) {
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

    public static String getCompletenessDescription(float completeness) {
        if (completeness < 20) return "tooltip.chineserpg.tattered_pages";
        if (completeness < 40) return "tooltip.chineserpg.scatter_fragments";
        if (completeness < 60) return "tooltip.chineserpg.normal_fragment";
        if (completeness < 80) return "tooltip.chineserpg.slightly_damaged";
        if (completeness < 100) return "tooltip.chineserpg.slightly_worn";
        return "tooltip.chineserpg.intact";
    }
}
