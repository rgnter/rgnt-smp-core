package xyz.rgnt.smp.controllers;

import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.Nullable;
import xyz.rgnt.smp.abstracted.IControllable;
import xyz.rgnt.smp.abstracted.IController;

import java.util.function.Consumer;

/**
 * Controls any controllable.
 * @param <T> Type of controllable
 */
@Log4j2(topic = "rgnt's smp")
public class Controller<T extends IControllable>
    implements IController<T> {

    private final Object2ObjectArrayMap<Class<T>, T> controllableMap
            = new Object2ObjectArrayMap<>();

    @Override
    public void register(@NonNull T controllable) {
        this.controllableMap.put((Class<T>) controllable.getClass(),controllable);
    }

    @Override
    public @Nullable T get(@NonNull Class<T> clazz) {
        return this.controllableMap.get(clazz);
    }

    /**
     * Initialize all controllables registered.
     */
    @Override
    public void initializeAll() {
        this.controllableMap.forEach((clazz, controllable) -> {
            try {
                // initialize controllable
                controllable.initialize();
            } catch (Exception e) {
                log.error("Couldn't initialize controllable {}.",
                        controllable.getClass().getSimpleName(), e);
            }
        });
    }

    /**
     * Terminate all controllables registered.
     */
    @Override
    public void terminateAll() {
        this.controllableMap.forEach((clazz, controllable) -> {
            try {
                controllable.terminate();
            } catch (Exception e) {
                log.error("Couldn't terminate controllable {}({})",
                        controllable.getClass().getSimpleName(), controllable.getClass().getName(), e);
            }
        });
    }
}
