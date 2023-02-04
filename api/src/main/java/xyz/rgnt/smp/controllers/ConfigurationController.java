package xyz.rgnt.smp.controllers;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.Nullable;
import xyz.rgnt.smp.abstracted.IControllable;

import java.io.*;
import java.util.function.Supplier;

/**
 * Provides configuration.
 */
@Log4j2(topic = "rgnt's smp")
public class ConfigurationController
        implements IControllable {

    @Getter
    private final @NonNull File file;

    @Getter
    private ConfigurationSection root;

    @Getter
    @Setter
    private @Nullable Supplier<InputStream> defaultConfiguration = null;


    public ConfigurationController(@NonNull File file) {
        this.file = file;
    }

    @Override
    public void initialize() {
        // if file doesn't exist, create its parent and create new file.
        if (!file.exists())
            if (file.getParentFile().exists()) {
                if (file.getParentFile().mkdirs()) {
                    try {
                        boolean created = !file.createNewFile();
                        // provide default contents
                        if (created && this.defaultConfiguration != null) {
                            try (final var input = this.defaultConfiguration.get();
                                 final var output = new FileOutputStream(file)) {
                                output.write(input.readAllBytes());
                            } catch (Exception x) {
                                log.error("Couldn't write default configuration file '{}'", file.getPath(), x);
                            }
                        }
                    } catch (IOException e) {
                        log.error("Couldn't create configuration file at '{}'", file.getPath(), e);
                    }
                }
            }

        final var yaml = new YamlConfiguration();
        try {
            yaml.load(file);
            this.root = yaml.getRoot();
        } catch (Exception e) {
            log.error("Couldn't load configuration", e);
        }
    }

    @Override
    public void terminate() {

    }
}
