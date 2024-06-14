package top.xcyyds.chineserpg.player;

import java.util.ArrayList;
import java.util.List;

public class PlayerActionManager {
    private List<ActionType> actions;
    private static final int COMBINATION_LENGTH = 4;
    private boolean recording = false;

    public PlayerActionManager() {
        this.actions = new ArrayList<>();
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
        System.out.println("Triggered skill: " + skillName);
        // 在这里添加触发技能的逻辑，例如显示特效或计算伤害
    }
}
