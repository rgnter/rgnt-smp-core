package xyz.rgnt.smp.abstracted;

/**
 * Represents any controllable class by means of initialization and termination. For controllers see interface {@link IController}
 */
public interface IControllable {

    /**
     * Initialize.
     * @throws Exception Any exception.
     */
    void initialize() throws Exception;

    /**
     * Terminate
     * @throws Exception Any exception.
     */
    default void terminate() throws Exception {}

}
