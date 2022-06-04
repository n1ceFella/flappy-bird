package screens;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javax.swing.*;
import java.awt.*;

public class MainWindow extends Application
{



    @Override
    public void start(Stage primaryStage) throws Exception{}

    public MainWindow()
    {

    }

    public static void main(String[] args)
    {
        Platform.runLater(() -> {
            JFrame window = new JFrame();
            GameCanvas panel = new GameCanvas();
            window.setTitle("Flappy Bird");
            window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            window.setSize(new Dimension(288, 512));
            window.setLocation(300, 300);
            panel.addPaneltoFrame(window.getContentPane());
            window.setResizable(false);
            window.setVisible(true);
        });
    }
}
