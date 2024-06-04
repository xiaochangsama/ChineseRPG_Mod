package top.xcyyds.chineserpg;


public interface PlayerPersistentDataProvider {
    //通过这个函数，获取到玩家现存的动态数据
    PlayerPersistentData getPersistentData();
}
