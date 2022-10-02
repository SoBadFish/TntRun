package org.sobadfish.tntrun.event;

import cn.nukkit.plugin.Plugin;
import org.sobadfish.tntrun.player.team.TeamInfo;
import org.sobadfish.tntrun.room.GameRoom;


/**
 * 队伍胜利事件
 * @author SoBadFish
 * 2022/1/15
 */
public class TeamVictoryEvent extends GameRoomEvent{

    private final TeamInfo teamInfo;

    public TeamVictoryEvent(TeamInfo teamInfo, GameRoom room, Plugin plugin) {
        super(room, plugin);
        this.teamInfo = teamInfo;
    }

    public TeamInfo getTeamInfo() {
        return teamInfo;
    }
}
