package top.xcyyds.chineserpg.player;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class PlayerActionManager {
    private List<ActionType> actions;
    private static final int COMBINATION_LENGTH = 4;
    private boolean recording = false;
    private PlayerEntity player;

    public PlayerActionManager(PlayerEntity player) {
        this.actions = new ArrayList<>();
        this.player = player;
    }

    public void startRecording() {
        this.recording = true;
        this.actions.clear(); // 开始记录时清空之前的记录
    }

    public void stopRecording() {
        this.recording = false;
        checkCombination(); // 停止记录时检查组合
    }

    public void recordAction(ActionType action) {
        if (recording) {
            if (actions.size() >= COMBINATION_LENGTH) {
                actions.remove(0);
            }
            actions.add(action);
        }
    }

    private void checkCombination() {
        if (actions.size() < COMBINATION_LENGTH) {
            return;
        }

        // 示例: R-L-R-L
        if (actions.get(0) == ActionType.RIGHT_CLICK &&
                actions.get(1) == ActionType.LEFT_CLICK &&
                actions.get(2) == ActionType.RIGHT_CLICK &&
                actions.get(3) == ActionType.LEFT_CLICK) {
            triggerSkill("Piercing Thrust");
        }
        // 添加更多的组合判断
    }

    private void triggerSkill(String skillName) {
        ItemStack mainHandItem = player.getMainHandStack();
        if (mainHandItem.isIn(CAN_USE_SKILL_SWORDS)) {
            System.out.println("Triggered skill: " + skillName);
            // 在这里添加触发技能的逻辑，例如显示特效或计算伤害
        }
    }

    public static final TagKey<Item> CAN_USE_SKILL_SWORDS = TagKey.of(Registries.ITEM.getKey(), new Identifier("chineserpg", "can_use_skill_swords"));
}
