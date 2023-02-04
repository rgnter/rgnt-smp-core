package xyz.rgnt.smp.abstracted;

import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Represents any class that can be bukkit listener.
 */
public interface IListener
        extends Listener {

    /**
     * Registers bukkit listener bound to plugin.
     *
     * @param plugin Plugin instance.
     */
    default void registerListener(@NonNull JavaPlugin plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    /**
     * Unregisters bukkit listener.
     */
    default void unregisterListener() {
        HandlerList.unregisterAll(this);
    }


}
