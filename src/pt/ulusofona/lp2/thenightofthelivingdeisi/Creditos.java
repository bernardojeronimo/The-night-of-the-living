/*package pt.ulusofona.lp2.thenightofthelivingdeisi;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import javax.swing.*;
import java.awt.event.HierarchyEvent;
import java.io.File;

public class Creditos {

    private MediaPlayer mediaPlayer;

    public Creditos() {
    }


    public JPanel getCreditsPanel() {
        JPanel panel = new JPanel();

        // Inicializa o JavaFX em um thread separado
        JFXPanel jfxPanel = new JFXPanel(); // Necessário para integrar JavaFX com Swing

        // Inicia a execução do JavaFX
        Platform.runLater(() -> {
            // Caminho para o seu vídeo
            String videoPath = "./creditos.mp4"; // Certifique-se de ter o caminho correto aqui
            Media media = new Media(new File(videoPath).toURI().toString());
            mediaPlayer = new MediaPlayer(media); // Salva o MediaPlayer para controlar mais tarde
            MediaView mediaView = new MediaView(mediaPlayer);

            // Configura o tamanho do vídeo (aumentando o tamanho dos créditos)
            mediaView.setFitWidth(550); // Aumentando a largura
            mediaView.setFitHeight(400); // Aumentando a altura

            // Use um StackPane para ser o root da cena
            StackPane root = new StackPane();
            root.getChildren().add(mediaView);

            // Cria a cena e adiciona ao JFXPanel
            Scene scene = new Scene(root, 800, 700); // Defina o tamanho da cena
            jfxPanel.setScene(scene);

            // Inicia a reprodução do vídeo
            mediaPlayer.play();
        });

        // Adiciona o JFXPanel ao JPanel
        panel.add(jfxPanel);

        // Registra um listener para parar o vídeo quando o painel for fechado
        panel.addHierarchyListener(e -> {
            if ((e.getChangeFlags() & HierarchyEvent.SHOWING_CHANGED) != 0 && !panel.isShowing()) {
                stopVideo(); // Chama o método para parar o vídeo
            }
        });

        return panel;
    }

    // Método para parar o vídeo
    private void stopVideo() {
        if (mediaPlayer != null) {
            mediaPlayer.stop(); // Para o vídeo
        }
    }
}
*/