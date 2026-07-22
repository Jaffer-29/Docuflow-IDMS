package ds.comsats.idms.idms;

/**
 * Plain launcher entry point used when the application is packaged as a native
 * executable (jpackage). A class that does NOT extend javafx.application.Application
 * is required as the main class so the JavaFX runtime can start correctly when the
 * modules are loaded from the classpath instead of the module path.
 */
public class Launcher {
    public static void main(String[] args) {
        MainFrame.main(args);
    }
}
