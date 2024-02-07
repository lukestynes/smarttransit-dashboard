package seng202.team5;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import seng202.team5.gui.MainWindow;

/**
 * Default entry point of the application
 *
 * @author Luke Stynes
 */
public class App {
    private static final Logger log = LogManager.getLogger(App.class);

    /**
     * Entry point of SmartTransit Dashboard. Generates the main window of the application.
     *
     * @param args program arguments from command line
     */
    public static void main(String[] args) {
        log.info("Starting SmartTransit Dashboard...");
        MainWindow.main(args);
    }
}
