package top.xcyyds.chineserpg.registry;

import java.util.HashMap;
import java.util.Map;

public class MartialArtRegistry {
    private static final Map<String, top.xcyyds.chineserpg.registry.MartialArt> registry = new HashMap<>();

    public static void registerMartialArt(top.xcyyds.chineserpg.registry.MartialArt martialArt) {
        registry.put(martialArt.getName(), martialArt);
    }

    public static top.xcyyds.chineserpg.registry.MartialArt getMartialArt(String name) {
        return registry.get(name);
    }

    public static void initializeRegistry() {
        // 初始化时将江湖轻功写入注册表
        registerMartialArt(new top.xcyyds.chineserpg.registry.MartialArt("江湖轻功", "轻功", 10, 100, "江湖中流传的轻功绝学", "某位大师"));
    }
}
