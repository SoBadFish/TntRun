package org.sobadfish.tntrun.thread;

import cn.nukkit.block.BlockAir;
import cn.nukkit.scheduler.PluginTask;
import org.sobadfish.tntrun.TntRunMain;
import org.sobadfish.tntrun.manager.TotalManager;
import org.sobadfish.tntrun.player.PlayerInfo;
import org.sobadfish.tntrun.room.GameRoom;

/**
 * @author Sobadfish
 * 18:24
 */
public class ChunkGroundRunnable extends PluginTask<TntRunMain> {
    public ChunkGroundRunnable(TntRunMain tntRunMain) {
        super(tntRunMain);
    }

    @Override
    public void onRun(int i) {
        for(GameRoom room: TotalManager.getRoomManager().getRooms().values()){
            for(PlayerInfo info: room.getLivePlayers()){
                if(info.isStart){
                    //TODO 清除方块
                    if(info.getPlayer().isOnGround()) {
                        info.getPlayer().getLevel().setBlock(info.getPlayer().getLevelBlock(), new BlockAir(), true);
                        info.getPlayer().getLevel().setBlock(info.getPlayer().getLevelBlock().add(0, -1, 0), new BlockAir(), true);
                    }
                    if(info.getPlayer().isInsideOfWater()){
                        info.death(null);
                    }
                }
            }
        }

    }
}
