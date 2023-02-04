package xyz.rgnt.smp.abstracted;

import lombok.NonNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents any class controlling controllables.
 * @param <T>
 */
public interface IController<T extends IControllable> {

    /**
     * Register controllables to be controlled.
     * @param controllable Controllables.
     */
    default void register(@NonNull T ... controllable) {
        for (T t : controllable) {
            register(t);
        }
    }

    /**
     * Register controllable to be controlled.
     * @param controllable Controllable instance.
     */
    void register(@NonNull T controllable);

    /**
     * @param clazz Class.
     * @return Any controllable instance with matching class.
     */
    @Nullable T get(@NonNull Class<T> clazz);


    /**
     * Initialize all controllables.
     */
    void initializeAll();

    /**
     * Terminate all controllables.
     */
    void terminateAll();


}
