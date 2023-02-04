package xyz.rgnt.smp.mechanics;

import com.destroystokyo.paper.event.server.ServerTickEndEvent;
import io.papermc.paper.adventure.AdventureComponent;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.TranslatableComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.minecraft.network.protocol.game.ClientboundSetActionBarTextPacket;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.jetbrains.annotations.Nullable;
import xyz.rgnt.smp.Plugin;
import xyz.rgnt.smp.abstracted.IMechanic;

@RequiredArgsConstructor
@Log4j2(topic = "rgnt's smp")
public class CoordsMechanic
        implements IMechanic {

    private final Plugin plugin;

    @Getter
    @Setter
    private Configuration configuration
            = new Configuration("<gray>{x} {y} {z} <dark_gray>(<gray>{target}<dark_gray>)");

    @Override
    public void initialize() {
        this.registerListener(plugin);
    }

    @Override
    public void terminate() {
    }

    @Override
    public @Nullable String configurationSectionId() {
        return "coords_mechanic";
    }

    @Override
    public void handleConfiguration(@Nullable ConfigurationSection section) {
        if (section == null)
            return;
        final var configurationBuilder
                = Configuration.builder();
        if (!section.contains("format"))
            return;

        configurationBuilder.formatComponent(section.getString("format"));
        this.configuration = configurationBuilder.build();
    }


    @EventHandler
    private void handlePlayerMove(final PlayerMoveEvent event) {
        final var player = event.getPlayer();
        final var location = player.getLocation();

        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            final var block = player.getTargetBlock(5);
            final var entity = player.getTargetEntity(120, false);

            final String targetName;
            if (entity instanceof Player target)
                targetName = String.format("<italic><lang:%s> <red>♥%.2f", target.getName(), target.getHealth());
            else if (entity instanceof LivingEntity living)
                targetName = String.format("<lang:%s> <red>♥%.0f", entity.getType().translationKey(), living.getHealth());
            else if (block != null && block.getType() != Material.AIR)
                targetName = String.format("<lang:%s>", block.translationKey());
            else
                targetName = "nothing";

            final var format = this.configuration.formatComponent
                    .replaceAll("\\{x}", String.valueOf(location.getBlockX()))
                    .replaceAll("\\{y}", String.valueOf(location.getBlockY()))
                    .replaceAll("\\{z}", String.valueOf(location.getBlockZ()))
                    .replaceAll("\\{target}", String.format("%s", targetName));
            player.sendActionBar(MiniMessage.miniMessage().deserialize(format));
        });
    }

    /**
     * Configuration
     *
     * @param formatComponent
     */
    @Builder
    public record Configuration(
            String formatComponent
    ) {
    }
}
