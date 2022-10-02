package org.sobadfish.tntrun.thread;

import cn.nukkit.Server;
import cn.nukkit.block.Block;
import cn.nukkit.block.BlockAir;
import cn.nukkit.level.Level;
import cn.nukkit.level.Position;
import cn.nukkit.math.Vector3;
import cn.nukkit.math.Vector3f;
import cn.nukkit.scheduler.AsyncTask;
import cn.nukkit.scheduler.PluginTask;
import cn.nukkit.scheduler.Task;
import org.sobadfish.tntrun.TntRunMain;
import org.sobadfish.tntrun.manager.TotalManager;
import org.sobadfish.tntrun.player.PlayerInfo;
import org.sobadfish.tntrun.room.GameRoom;

import java.util.ArrayList;

/**
 * @author Sobadfish
 * 18:24
 */
public class ChunkGroundRunnable extends PluginTask<TntRunMain> {
    public ChunkGroundRunnable(TntRunMain tntRunMain) {
        super(tntRunMain);
    }
    public static ArrayList<Vector3> positions = new ArrayList<>();

    @Override
    public void onRun(int i) {
        for(GameRoom room: TotalManager.getRoomManager().getRooms().values()){
            if(room.getType() == GameRoom.GameType.START) {
                for (PlayerInfo info : room.getLivePlayers()) {
                    if (info.isStart) {
                        //TODO 清除方块
                        if (info.getPlayer().isOnGround()) {

                            double x =  info.getPlayer().getX();
                            double y = info.getPlayer().getFloorY();
                            double z =  info.getPlayer().getZ();
                            ArrayList<Vector3> blocks = new ArrayList<>();
                            blocks.add(new Vector3(x,y,z));
                            blocks.add(new Vector3(x + 1,y,z));
                            blocks.add(new Vector3(x,y,Math.ceil(z)));
                            Server.getInstance().getScheduler().scheduleAsyncTask(TotalManager.getPlugin(), new BlockSetRunnable(info.player.getLevel(),blocks));

                        }

                    }
                }
            }
        }

    }

    private class BlockSetRunnable extends AsyncTask{

        private ArrayList<Vector3> v3;
        private Level level;
        BlockSetRunnable(Level level, ArrayList<Vector3> v3){
            this.v3 = v3;
            this.level = level;
        }

        @Override
        public void onRun() {
            for(Vector3 v3: v3){
                level.setBlock(v3.add(0,-1),new BlockAir());
                level.setBlock(v3.add(0,-2),new BlockAir());
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
