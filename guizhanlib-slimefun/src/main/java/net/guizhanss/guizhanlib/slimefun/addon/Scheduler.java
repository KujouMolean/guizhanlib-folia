package net.guizhanss.guizhanlib.slimefun.addon;

import com.google.common.base.Preconditions;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.concurrent.TimeUnit;

/**
 * A class for scheduling tasks.
 * Modified from InfinityLib
 *
 * @author Mooy1
 * @author ybw0014
 */
@ParametersAreNonnullByDefault
@SuppressWarnings("ConstantConditions")
public final class Scheduler {

    private final Plugin plugin;

    public Scheduler(@Nonnull Plugin plugin) {
        Preconditions.checkArgument(plugin != null, "Plugin instance cannot be null");
        this.plugin = plugin;
    }

    public void run(@Nonnull Runnable runnable, SchedulerContext context) {
        context.runTask(plugin, runnable, 1);
    }

    public void runAsync(@Nonnull Runnable runnable) {
        Bukkit.getAsyncScheduler().runNow(plugin, scheduledTask -> runnable.run());
    }

    public void run(int delayTicks, @Nonnull Runnable runnable, SchedulerContext context) {
        context.runTask(plugin,runnable, delayTicks);
    }

    public void runAsync(int delayTicks, @Nonnull Runnable runnable) {
        Bukkit.getAsyncScheduler().runDelayed(plugin, scheduledTask -> runnable.run(), delayTicks * 50L, TimeUnit.MILLISECONDS);
    }

    public void repeat(int intervalTicks, @Nonnull Runnable runnable, SchedulerContext context) {
        repeat(intervalTicks, 1, runnable, context);
    }

    public void repeatAsync(int intervalTicks, @Nonnull Runnable runnable) {
        repeatAsync(intervalTicks, 1, runnable);
    }

    public void repeat(int intervalTicks, int delayTicks, @Nonnull Runnable runnable, SchedulerContext context) {
        context.repeat(plugin,runnable,delayTicks,Math.max(1, intervalTicks));
    }

    public void repeatAsync(int intervalTicks, int delayTicks, @Nonnull Runnable runnable) {
        Bukkit.getAsyncScheduler().runAtFixedRate(plugin, scheduledTask -> runnable.run(), delayTicks * 50L, Math.max(1, intervalTicks) * 50L, TimeUnit.MILLISECONDS);
    }
}
