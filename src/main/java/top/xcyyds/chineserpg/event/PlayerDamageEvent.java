package top.xcyyds.chineserpg.event;

public class PlayerDamageEvent {
    public static void register() {
        PlayerDamageCallback.EVENT.register((player, source, amount) -> {




            return false; // 不取消伤害
        });
    }
}
