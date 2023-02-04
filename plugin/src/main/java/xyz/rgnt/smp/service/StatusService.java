package xyz.rgnt.smp.service;

import com.destroystokyo.paper.event.server.PaperServerListPingEvent;
import com.destroystokyo.paper.profile.CraftPlayerProfile;
import com.destroystokyo.paper.profile.PlayerProfile;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import xyz.rgnt.smp.Plugin;
import xyz.rgnt.smp.abstracted.IService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Log4j2
@RequiredArgsConstructor
public class StatusService
        implements IService {

    private final Plugin plugin;
    private Configuration configuration = Configuration.builder()
            .tightPlayerCount(true)
            .sample(true)
            .samplePrivacy(true)
            .sampleFormat("<white>{name} <red>❤ {health}")
            .build();

    @Override
    public void initialize() {
        registerListener(this.plugin);
    }

    @Override
    public void terminate() {
        unregisterListener();
    }


    @EventHandler
    private void handleStatusQuery(PaperServerListPingEvent event) {
        final var onlinePlayers = Bukkit.getOnlinePlayers();
        if (this.configuration.tightPlayerCount) {
            event.setNumPlayers(onlinePlayers.size());
            event.setMaxPlayers(event.getNumPlayers() + 1);
        }

        if(this.configuration.sample) {
            final var sample = event.getPlayerSample();
            sample.clear();

            for (final var onlinePlayer : onlinePlayers) {
                final var pos = onlinePlayer.getLocation();
                final var sampleFormat = this.configuration.sampleFormat
                        .replaceAll("\\{name}", onlinePlayer.getName())
                        .replaceAll("\\{health}", String.format("%.0f", onlinePlayer.getHealth()))
                        .replaceAll("\\{x}", String.valueOf(pos.getBlockX()))
                        .replaceAll("\\{y}", String.valueOf(pos.getBlockY()))
                        .replaceAll("\\{z}", String.valueOf(pos.getBlockZ()));
                final var displayComponent = MiniMessage.miniMessage()
                        .deserialize(sampleFormat);
                final var display = LegacyComponentSerializer.legacySection()
                        .serialize(displayComponent);

                sample.add(new CraftPlayerProfile(this.configuration.samplePrivacy ? UUID.randomUUID() : onlinePlayer.getUniqueId(), display));
            }
        }
    }


    @Builder
    record Configuration(boolean tightPlayerCount,

                         boolean sample,
                         boolean samplePrivacy,
                         String sampleFormat) {

    }
}
