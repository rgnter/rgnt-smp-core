package xyz.rgnt.smp;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.rgnt.smp.abstracted.IMechanic;
import xyz.rgnt.smp.abstracted.IService;
import xyz.rgnt.smp.controllers.ConfigurationController;
import xyz.rgnt.smp.controllers.Controller;
import xyz.rgnt.smp.mechanics.CombatMechanic;
import xyz.rgnt.smp.mechanics.CoordsMechanic;
import xyz.rgnt.smp.service.StatusService;


@Log4j2(topic = "rgnt's smp")
public class Plugin
    extends JavaPlugin {

    public static Plugin instance;

    @Getter
    private final Controller<IService> serviceController
             = new Controller<>();
    @Getter
    private final Controller<IMechanic> mechanicController
            = new Controller<>();


    @Override
    public void onLoad() {
        this.serviceController.register(new StatusService(this));
        this.mechanicController.register(
                new CoordsMechanic(this),
                new CombatMechanic(this)
        );

        Plugin.getProvidingPlugin(Plugin.class);
    }

    @Override
    public void onEnable() {
        this.serviceController.initializeAll();
        this.mechanicController.initializeAll();
    }

    @Override
    public void onDisable() {
        this.mechanicController.terminateAll();
        this.serviceController.terminateAll();
    }

}
