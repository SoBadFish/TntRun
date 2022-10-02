package org.sobadfish.tntrun.event;

import cn.nukkit.plugin.Plugin;
import org.sobadfish.tntrun.player.PlayerInfo;
import org.sobadfish.tntrun.room.GameRoom;


/**
 * 玩家在游戏中死亡的事件
 * @author SoBadFish
 * 2022/1/15
 */
public class PlayerGameDeathEvent extends PlayerRoomInfoEvent{

    public PlayerGameDeathEvent(PlayerInfo playerInfo, GameRoom room, Plugin plugin) {
        super(playerInfo,room, plugin);
    }
}
