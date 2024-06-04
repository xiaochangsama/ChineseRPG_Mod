package top.xcyyds.chineserpg.player;


public interface PlayerDataProvider {
    //通过这个函数，获取到玩家现存的动态数据
    PlayerData getPersistentData();
}
