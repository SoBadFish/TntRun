package org.sobadfish.tntrun.variable;

import cn.nukkit.Player;
import com.smallaswater.npc.data.RsNpcConfig;
import com.smallaswater.npc.variable.BaseVariableV2;
import com.smallaswater.npc.variable.VariableManage;

import org.sobadfish.tntrun.manager.TotalManager;
import org.sobadfish.tntrun.room.GameRoom;
import org.sobadfish.tntrun.room.WorldRoom;
import org.sobadfish.tntrun.room.config.GameRoomConfig;

import java.util.Map;

/**
 * 对接RSNPC
 * @author Sobadfish
 *  2023/2/20
 */
public class GameNpcVariable extends BaseVariableV2 {


    public static void init() {
        VariableManage.addVariableV2("skywar", GameNpcVariable.class);
    }

    @Override
    public void onUpdate(Player player, RsNpcConfig rsNpcConfig) {
        initVariable();
    }


    private void initVariable(){

        for(GameRoomConfig roomConfig: TotalManager.getRoomManager().getRoomConfigs()){
            addRoomVariable(roomConfig);
        }
        for(Map.Entry<String, WorldRoom> worldRoomEntry: TotalManager.getMenuRoomManager().getWorldRoomLinkedHashMap().entrySet()){
            WorldRoom worldRoom = worldRoomEntry.getValue();
            int p = 0;
            for(GameRoomConfig roomConfig: worldRoom.getRoomConfigs()){
                GameRoom room = TotalManager.getRoomManager().getRoom(roomConfig.name);
                if(room != null){
                    p+= room.getPlayerInfos().size();
                }
            }
            addVariable("%tr-"+worldRoom.getName()+"-player%",p+"");

        }
        int game = 0;
        for(GameRoom gameRoom: TotalManager.getRoomManager().getRooms().values()){
            game += gameRoom.getPlayerInfos().size();
        }
        addVariable("%tr-all-player%",game+"");

    }

    public void addRoomVariable(GameRoomConfig roomConfig){
        int p = 0;
        int mp = roomConfig.getMaxPlayerSize();
        String status = "&a等待中";
        GameRoom room = TotalManager.getRoomManager().getRoom(roomConfig.name);
        if(room != null){
            p = room.getPlayerInfos().size();
            switch (room.getType()){
                case START:
                    status = "&c游戏中";
                    break;
                case END:
                case CLOSE:
                    status =  "&e结算中";
                    break;
                default:break;
            }
        }
        addVariable("%tr-"+roomConfig.getName()+"-player%",p+"");
        addVariable("%tr-"+roomConfig.getName()+"-maxplayer%",mp+"");
        addVariable("%tr-"+roomConfig.getName()+"-status%",status);
    }
}