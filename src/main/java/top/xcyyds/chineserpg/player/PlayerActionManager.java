package top.xcyyds.chineserpg.player;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import top.xcyyds.chineserpg.martialart.artentry.OuterSkillEntry;
import top.xcyyds.chineserpg.network.AnimationSyncHandler;
import top.xcyyds.chineserpg.player.Jian.JianSkillHandler;
import top.xcyyds.chineserpg.player.data.IPlayerDataProvider;
import top.xcyyds.chineserpg.player.data.PlayerData;

import java.util.ArrayList;
import java.util.List;

public class PlayerActionManager {
    private final List<ActionType> actions;
    private static final int COMBINATION_LENGTH = 4;
    private boolean recording = false;
    private final PlayerEntity player;

    public PlayerActionManager(PlayerEntity player) {
        this.actions = new ArrayList<>();
        this.player = player;
    }

    public void startRecording() {
        this.recording = true;
        this.actions.clear(); // 开始记录时清空之前的记录
        player.sendMessage(Text.literal(getActionSequence()).formatted(Formatting.GOLD), true);
    }

    public void stopRecording() {
        this.recording = false;
        player.sendMessage(Text.literal(getActionSequence()).formatted(Formatting.GOLD), true);
        checkCombination(); // 停止记录时检查组合
    }

    public void recordAction(ActionType action) {
        if (recording && actions.size() < COMBINATION_LENGTH) {
            actions.add(action);
            player.sendMessage(Text.literal(getActionSequence()).formatted(Formatting.GOLD), true);
        }
    }

    private String getActionSequence() {
        StringBuilder sequence = new StringBuilder();
        sequence.append("- ");
        for (ActionType action : actions) {
            sequence.append(action == ActionType.RIGHT_CLICK ? "R" : "L").append(" - ");
        }
        return sequence.toString();
    }

    private void checkCombination() {
        String releaseMethod = getReleaseMethod();
        if (releaseMethod != null) {
            // 从玩家装备的外功中读取 outerType 和 releaseMethod
            PlayerData playerData = ((IPlayerDataProvider) player).getPlayerData();
            ItemStack mainHandItem = player.getMainHandStack();
            // 遍历玩家装备的所有外功，包括剑法、拳法等
            for (OuterSkillEntry skillEntry : playerData.getEquippedOuterSkills())
            {
                if(skillEntry.getOuterType().equals("剑法")
                        && mainHandItem.isIn(CAN_USE_SKILL_SWORDS)){
                    if (skillEntry.getReleaseMethod().equals(releaseMethod)) {
                        triggerJianSkill(skillEntry);
                        break;
                    }
                } else if (skillEntry.getOuterType().equals("拳法")) {

                }

            }
        }
    }

    private String getReleaseMethod() {
        if (actions.size() == 2) {
            if (actions.get(0) == ActionType.LEFT_CLICK && actions.get(1) == ActionType.LEFT_CLICK) {
                return "LL";
            } else if (actions.get(0) == ActionType.RIGHT_CLICK && actions.get(1) == ActionType.RIGHT_CLICK) {
                return "RR";
            } else if (actions.get(0) == ActionType.LEFT_CLICK && actions.get(1) == ActionType.RIGHT_CLICK) {
                return "LR";
            } else if (actions.get(0) == ActionType.RIGHT_CLICK && actions.get(1) == ActionType.LEFT_CLICK) {
                return "RL";
            }
        } else if (actions.size() == 3) {
            if (actions.get(0) == ActionType.LEFT_CLICK && actions.get(1) == ActionType.LEFT_CLICK && actions.get(2) == ActionType.LEFT_CLICK) {
                return "LLL";
            } else if (actions.get(0) == ActionType.RIGHT_CLICK && actions.get(1) == ActionType.RIGHT_CLICK && actions.get(2) == ActionType.RIGHT_CLICK) {
                return "RRR";
            } else if (actions.get(0) == ActionType.LEFT_CLICK && actions.get(1) == ActionType.LEFT_CLICK && actions.get(2) == ActionType.RIGHT_CLICK) {
                return "LLR";
            } else if (actions.get(0) == ActionType.RIGHT_CLICK && actions.get(1) == ActionType.RIGHT_CLICK && actions.get(2) == ActionType.LEFT_CLICK) {
                return "RRL";
            } else if (actions.get(0) == ActionType.LEFT_CLICK && actions.get(1) == ActionType.RIGHT_CLICK && actions.get(2) == ActionType.LEFT_CLICK) {
                return "LRL";
            } else if (actions.get(0) == ActionType.RIGHT_CLICK && actions.get(1) == ActionType.LEFT_CLICK && actions.get(2) == ActionType.RIGHT_CLICK) {
                return "RLR";
            } else if (actions.get(0) == ActionType.LEFT_CLICK && actions.get(1) == ActionType.RIGHT_CLICK && actions.get(2) == ActionType.RIGHT_CLICK) {
                return "LRR";
            } else if (actions.get(0) == ActionType.RIGHT_CLICK && actions.get(1) == ActionType.LEFT_CLICK && actions.get(2) == ActionType.LEFT_CLICK) {
                return "RLL";
            }
        } else if (actions.size() == 4) {
            if (actions.get(0) == ActionType.LEFT_CLICK && actions.get(1) == ActionType.LEFT_CLICK && actions.get(2) == ActionType.LEFT_CLICK && actions.get(3) == ActionType.LEFT_CLICK) {
                return "LLLL";
            } else if (actions.get(0) == ActionType.RIGHT_CLICK && actions.get(1) == ActionType.RIGHT_CLICK && actions.get(2) == ActionType.RIGHT_CLICK && actions.get(3) == ActionType.RIGHT_CLICK) {
                return "RRRR";
            } else if (actions.get(0) == ActionType.LEFT_CLICK && actions.get(1) == ActionType.LEFT_CLICK && actions.get(2) == ActionType.LEFT_CLICK && actions.get(3) == ActionType.RIGHT_CLICK) {
                return "LLLR";
            } else if (actions.get(0) == ActionType.RIGHT_CLICK && actions.get(1) == ActionType.RIGHT_CLICK && actions.get(2) == ActionType.RIGHT_CLICK && actions.get(3) == ActionType.LEFT_CLICK) {
                return "RRRL";
            } else if (actions.get(0) == ActionType.LEFT_CLICK && actions.get(1) == ActionType.LEFT_CLICK && actions.get(2) == ActionType.RIGHT_CLICK && actions.get(3) == ActionType.RIGHT_CLICK) {
                return "LLRR";
            } else if (actions.get(0) == ActionType.RIGHT_CLICK && actions.get(1) == ActionType.RIGHT_CLICK && actions.get(2) == ActionType.LEFT_CLICK && actions.get(3) == ActionType.LEFT_CLICK) {
                return "RRLL";
            } else if (actions.get(0) == ActionType.LEFT_CLICK && actions.get(1) == ActionType.RIGHT_CLICK && actions.get(2) == ActionType.LEFT_CLICK && actions.get(3) == ActionType.RIGHT_CLICK) {
                return "LRLR";
            } else if (actions.get(0) == ActionType.RIGHT_CLICK && actions.get(1) == ActionType.LEFT_CLICK && actions.get(2) == ActionType.RIGHT_CLICK && actions.get(3) == ActionType.LEFT_CLICK) {
                return "RLRL";
            } else if (actions.get(0) == ActionType.LEFT_CLICK && actions.get(1) == ActionType.RIGHT_CLICK && actions.get(2) == ActionType.RIGHT_CLICK && actions.get(3) == ActionType.LEFT_CLICK) {
                return "LRRL";
            } else if (actions.get(0) == ActionType.RIGHT_CLICK && actions.get(1) == ActionType.LEFT_CLICK && actions.get(2) == ActionType.LEFT_CLICK && actions.get(3) == ActionType.RIGHT_CLICK) {
                return "RLLR";
            } else if (actions.get(0) == ActionType.LEFT_CLICK && actions.get(1) == ActionType.LEFT_CLICK && actions.get(2) == ActionType.RIGHT_CLICK && actions.get(3) == ActionType.LEFT_CLICK) {
                return "LLRL";
            } else if (actions.get(0) == ActionType.RIGHT_CLICK && actions.get(1) == ActionType.RIGHT_CLICK && actions.get(2) == ActionType.LEFT_CLICK && actions.get(3) == ActionType.RIGHT_CLICK) {
                return "RRLR";
            }
        }
        // 可以根据需要添加更多组合逻辑
        return null;
    }


    private void triggerJianSkill(OuterSkillEntry skillEntry) {
            //这一句仅用来测试，之后会替换成其他表现方式
            player.sendMessage(Text.literal("释放 " + skillEntry.getName()).formatted(Formatting.GOLD), true);

            if (player instanceof ServerPlayerEntity serverPlayer) {
                // 发送数据包到客户端，通知播放指定的动画
                AnimationSyncHandler.sendAnimationPacket(serverPlayer, new Identifier("chineserpg", skillEntry.getAnimationName()));
            }

            //根据技能词条释放技能
            JianSkillHandler.useJianSkill(player, skillEntry);

    }

    public static final TagKey<Item> CAN_USE_SKILL_SWORDS = TagKey.of(Registries.ITEM.getKey(), new Identifier("chineserpg", "can_use_skill_swords"));

    public boolean isRecording() {
        return recording;
    }
}
