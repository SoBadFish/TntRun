package org.sobadfish.tntrun.thread;

import cn.nukkit.Server;
import cn.nukkit.block.Block;
import cn.nukkit.block.BlockAir;
import cn.nukkit.block.BlockID;
import cn.nukkit.level.Location;
import cn.nukkit.level.ParticleEffect;
import cn.nukkit.level.Position;
import cn.nukkit.level.particle.DestroyBlockParticle;
import cn.nukkit.math.Vector3;
import cn.nukkit.scheduler.PluginTask;
import org.sobadfish.tntrun.TntRunMain;
import org.sobadfish.tntrun.manager.TotalManager;
import org.sobadfish.tntrun.player.PlayerInfo;
import org.sobadfish.tntrun.room.GameRoom;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 重写方块清除算法
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
        for (GameRoom room : TotalManager.getRoomManager().getRooms().values()) {
            if (room.getType() == GameRoom.GameType.START) {
                Server.getInstance().getScheduler().scheduleDelayedTask(TotalManager.getPlugin()
                        , new BlockSetRunnable((TntRunMain) TotalManager.getPlugin(),
                                room),1);
            }
        }
    }


    //边缘检测
    private static final double PLAYER_BOUNDINGBOX_ADD = 0.3;
//
//    //深度
//    private static final int SCAN_DEPTH = 1;

    private static class BlockSetRunnable extends PluginTask<TntRunMain>{

        private final GameRoom room;

        BlockSetRunnable(TntRunMain plugin, GameRoom room){
            super(plugin);
            this.room = room;
        }


        private Block getBlockUnderPlayer(int y, Location location) {
            Position loc = new Position(location.getX(), y, location.getZ(),location.level);
            Block b11 = loc.add(+PLAYER_BOUNDINGBOX_ADD, -PLAYER_BOUNDINGBOX_ADD).getLevelBlock();
            if (b11.getId() != BlockID.AIR) {
                return b11;
            }
            Block b12 = loc.add(-PLAYER_BOUNDINGBOX_ADD, +PLAYER_BOUNDINGBOX_ADD).getLevelBlock();
            if (b12.getId() != BlockID.AIR) {
                return b12;
            }
            Block b21 = loc.add(+PLAYER_BOUNDINGBOX_ADD, +PLAYER_BOUNDINGBOX_ADD).getLevelBlock();
            if (b21.getId() != BlockID.AIR) {
                return b21;
            }
            Block b22 = loc.add(-PLAYER_BOUNDINGBOX_ADD, -PLAYER_BOUNDINGBOX_ADD).getLevelBlock();
            if (b22.getId() != BlockID.AIR) {
                return b22;
            }

            return null;
        }


        @Override
        public void onRun(int i) {
            for(PlayerInfo info: room.getLivePlayers()){
                if (info.isStart) {
                    Location location = info.getLocation();
                    int y = location.getLevelBlock().getFloorY();
                    Block block = null;
                    for (int l = 0; l <= room.getRoomConfig().scanDepth; l++) {
                        block = getBlockUnderPlayer(y, location);
                        y--;
                        if (block != null) {
                            break;
                        }
                    }
                    if (block != null) {

                        if(!room.worldInfo.getBlockDestroyable().contains(block)) {
                            room.worldInfo.getBlockDestroyable().add(block);
                        }
                        Server.getInstance().getScheduler().scheduleDelayedTask(TotalManager.getPlugin(), new BlockBreakThread(block),10);
                    }
                }
            }
        }

        private class BlockBreakThread implements Runnable{
            private final Block block;
            public BlockBreakThread(Block block) {
                this.block = block;
            }

            @Override
            public void run() {
                if(room.getType() == GameRoom.GameType.START){
                    room.worldInfo.getBlockDestroyable().remove(block);
                    block.level.addParticle(new DestroyBlockParticle(block,block));
                    block.level.setBlock(block,new BlockAir());
                }
            }
        }
    }
}
