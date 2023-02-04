package xyz.rgnt.smp.abstracted;

import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.Nullable;

import java.io.File;

/**
 * Represents any class that is configurable.
 */
public interface IConfigurable {

    /**
     * Handle provided configuration.
     *
     * @param section Configuration section.
     */
    default void handleConfiguration(@Nullable ConfigurationSection section) {

    }

    /**
     * @return Configuration file. Returning null should default to global configuration.
     */
    default @Nullable File configurationFile() {
        return null;
    }

    /**
     * @return Unique configuration section id, by which can be configuration for such class identified.
     */
    default @Nullable String configurationSectionId() {
        return null;
    }

}
