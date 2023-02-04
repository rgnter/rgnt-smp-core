package xyz.rgnt.smp.mechanics;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.rgnt.smp.abstracted.IMechanic;

@Log4j2(topic = "rgnt's smp")
@RequiredArgsConstructor
public class CombatMechanic
    implements IMechanic {

    private final JavaPlugin plugin;

    @Override
    public void initialize() {
        registerListener(this.plugin);
    }

    @EventHandler
    private void handleEntityDamageEntity(final EntityDamageByEntityEvent event) {
        final var damagee = event.getEntity();
        final var damager = event.getDamager();
        final var damage = event.getDamage();

        // native
        {
            final var mojangDamagee = ((CraftEntity) damagee).getHandle();
            final var mojangWorld = mojangDamagee.getLevel();
        }
    }

    @EventHandler
    private void handlePlayerDeath(final PlayerDeathEvent event) {
        final var player = event.getPlayer();
        final var location = player.getLocation();
        log.info("Player '{}' died at {} {} {} ({})", player.getName(),
                location.getBlockX(), location.getBlockY(), location.getBlockZ(), location.getWorld().getName());
    }
}
