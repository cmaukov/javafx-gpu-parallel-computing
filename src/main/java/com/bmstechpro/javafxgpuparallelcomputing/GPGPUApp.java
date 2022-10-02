package com.bmstechpro.javafxgpuparallelcomputing;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.PixelFormat;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Random;
import java.util.stream.IntStream;

public class GPGPUApp extends Application {
    private static final int W = 1600;
    private static final int H = 900;
    private static final int ARRAY_LENGTH = W * H;
    private static final int[] array1 = new int[ARRAY_LENGTH];
    private static final int[] array2 = new int[ARRAY_LENGTH];
    private static final int[] result = new int[ARRAY_LENGTH];

    private Canvas canvas;

    @Override
    public void start(Stage stage) throws IOException {

        stage.setScene(new Scene(createContent()));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    private Parent createContent() {
        // color argb = 32 bits (alpha = 8 bits, red = 8 bits, green = 8bits, blue = 8 bits) https://en.wikipedia.org/wiki/RGBA_color_model
        int colorRed = 255 << 24 | 255 << 16;
        Random random = new Random();
        for (int i = 0; i < ARRAY_LENGTH; i++) {
            int randomValue = random.nextInt(100_000);

            array1[i] = randomValue;
            array2[i] = colorRed - randomValue;
        }
        Pane root = new Pane();
        root.setPrefSize(W, H);
        canvas = new Canvas(W, H);
        root.getChildren().add(canvas);
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                onUpdate();
            }
        };
        timer.start();

        return root;
    }

    private void onUpdate() {
        long start = System.nanoTime();
        IntStream.range(0, ARRAY_LENGTH)
                .parallel()
                .forEach(index -> {
                    result[index] = array1[index] + array2[index];
                });
        long end = System.nanoTime() - start;
        System.out.println("Time: " + end / 1_000_000.0 +" ms");
        canvas.getGraphicsContext2D()
                .getPixelWriter()
                .setPixels(0, 0, W, H, PixelFormat.getIntArgbPreInstance(), result, 0, W);
    }
}