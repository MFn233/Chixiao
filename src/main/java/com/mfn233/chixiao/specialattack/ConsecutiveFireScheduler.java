package com.mfn233.chixiao.specialattack;

import com.mfn233.chixiao.entity.ChixiaoDrive;
import mods.flammpfeil.slashblade.entity.EntitySummonedSwordBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Spawns the phantom blades / swords of a special attack one after another
 * (every FIRE_INTERVAL ticks), so the volley is released in a continuous succession.
 */
public class ConsecutiveFireScheduler {

    private static final int FIRE_INTERVAL = 4;

    private static final class SingletonHolder {
        private static final ConsecutiveFireScheduler INSTANCE = new ConsecutiveFireScheduler();
    }

    public static ConsecutiveFireScheduler getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private ConsecutiveFireScheduler() {
    }

    private final Deque<ShotTask> queue = new ArrayDeque<ShotTask>();

    public static void schedule(EntityPlayer player, int count, float[] damage, int[] colors, boolean bladeQi) {
        getInstance().queue.add(new ShotTask(player, count, damage, colors, bladeQi));
    }

    @SubscribeEvent
    public void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }
        if (queue.isEmpty()) {
            return;
        }

        ShotTask task = queue.peek();
        if (task.player == null || task.player.isDead || task.player.world == null) {
            queue.poll();
            return;
        }

        if (task.tick++ % FIRE_INTERVAL == 0) {
            fire(task);
            if (++task.index >= task.count) {
                queue.poll();
            }
        }
    }

    private static void fire(ShotTask task) {
        EntityPlayer player = task.player;
        float damage = task.damage[task.index];
        int color = task.colors[task.index];

        if (task.bladeQi) {
            ChixiaoDrive blade = new ChixiaoDrive(player.world, player, damage, false, 0.0f);
            blade.setColor(color);
            blade.setInitialSpeed(1.5f);
            blade.setLifeTime(40);
            player.world.spawnEntity(blade);
        } else {
            EntitySummonedSwordBase sword = new EntitySummonedSwordBase(player.world, player, damage, 0.0f);
            sword.setColor(color);
            sword.setLifeTime(60);
            sword.setInterval(5);
            sword.setDriveVector(2.0f);
            player.world.spawnEntity(sword);
        }
    }

    private static final class ShotTask {
        final EntityPlayer player;
        final int count;
        final float[] damage;
        final int[] colors;
        final boolean bladeQi;
        int index;
        int tick;

        ShotTask(EntityPlayer player, int count, float[] damage, int[] colors, boolean bladeQi) {
            this.player = player;
            this.count = count;
            this.damage = damage;
            this.colors = colors;
            this.bladeQi = bladeQi;
        }
    }
}