package net.guizhanss.guizhanlib.slimefun.addon;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.Plugin;

public class SchedulerContext {
    private Entity entity;
    private World world;
    private int chunkX;
    private int chunkZ;

    public void setLocation(Location location) {
        this.world = location.getWorld();
        this.chunkX = location.getBlockX() >> 4;
        this.chunkZ = location.getBlockZ() >> 4;
    }

    public void setChunk(World world, int chunkX, int chunkZ) {
        this.world = world;
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    public void setBlock(Block block) {
        setLocation(block.getLocation());
    }

    public void runTask(Plugin plugin, Runnable runnable, int delay) {
        if (entity != null) {
            Location location = entity.getLocation();
            entity.getScheduler().run(plugin, scheduledTask -> runnable.run(), () -> {
                Bukkit.getRegionScheduler().runDelayed(plugin, location, scheduledTask -> runnable.run(), Math.max(1, delay));
            });
            return;
        } else if (world != null) {
            Bukkit.getRegionScheduler().runDelayed(plugin, world, chunkX, chunkZ, scheduledTask -> runnable.run(), Math.max(1, delay));
        }
        throw new RuntimeException("No enough context");
    }

    public void repeat(Plugin plugin, Runnable runnable, int delayTicks, int interval) {
        if (entity != null) {
            Location location = entity.getLocation();
            entity.getScheduler().runAtFixedRate(plugin, scheduledTask -> runnable.run(), () -> {
                Bukkit.getRegionScheduler().runAtFixedRate(plugin, location, scheduledTask -> runnable.run(), delayTicks, interval);
            }, delayTicks, interval);
        } else if (world != null) {
            Bukkit.getRegionScheduler().runAtFixedRate(plugin, world, chunkX, chunkZ, scheduledTask -> runnable.run(), delayTicks, interval);
        }
        throw new RuntimeException("No enough context");
    }

    public static SchedulerContext of(Entity entity) {
        SchedulerContext schedulerContext = new SchedulerContext();
        schedulerContext.setEntity(entity);
        return schedulerContext;
    }

    public static SchedulerContext of(Location entity) {
        SchedulerContext schedulerContext = new SchedulerContext();
        schedulerContext.setLocation(entity);
        return schedulerContext;
    }

    public static SchedulerContext of(Block entity) {
        SchedulerContext schedulerContext = new SchedulerContext();
        schedulerContext.setBlock(entity);
        return schedulerContext;
    }
}
