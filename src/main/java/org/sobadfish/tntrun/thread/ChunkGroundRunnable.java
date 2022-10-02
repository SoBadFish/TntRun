package org.sobadfish.tntrun.thread;

import cn.nukkit.block.BlockAir;
import cn.nukkit.math.Vector3;
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
            if(room.getType() == GameRoom.GameType.START) {
                for (PlayerInfo info : room.getLivePlayers()) {
                    if (info.isStart) {
                        //TODO 清除方块
                        if (info.getPlayer().isOnGround()) {
                            double x = info.getPlayer().getX();
                            double y = info.getPlayer().getFloorY();
                            double z = info.getPlayer().getZ();
                            info.getPlayer().getLevel().setBlock(new Vector3(Math.ceil(x), y, Math.ceil(z)), new BlockAir());
                            info.getPlayer().getLevel().setBlock(new Vector3(x, y, z), new BlockAir());
                            info.getPlayer().getLevel().setBlock(new Vector3(Math.ceil(x), y - 2, Math.ceil(z)), new BlockAir());
                            info.getPlayer().getLevel().setBlock(new Vector3(x, y - 2, z), new BlockAir());
                        }

                    }
                }
            }
        }

    }
}
