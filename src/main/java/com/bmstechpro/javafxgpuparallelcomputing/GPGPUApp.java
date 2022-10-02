package com.bmstechpro.javafxgpuparallelcomputing;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class GPGPUApp extends Application {
    private static final int W = 1600;
    private static final int H = 900;

    @Override
    public void start(Stage stage) throws IOException {

        stage.setScene(new Scene(createContent()));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    private Parent createContent() {
        Pane root = new Pane();
        root.setPrefSize(W,H);

        return root;
    }
}