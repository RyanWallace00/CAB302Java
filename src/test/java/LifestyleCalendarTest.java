package com.example.cab302javaproject;

import javafx.application.Platform;
import javafx.stage.Stage;
import org.junit.jupiter.api.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LifestyleCalendarTest {
    private LifestyleCalendar lifestyleCalendar;

    public LifestyleCalendarTest() {
        // Initialize the JavaFX toolkit
        Platform.startup(() -> {});
    }

    @BeforeEach
    void setUp() {
        lifestyleCalendar = new LifestyleCalendar();
    }

//    @Test
//    void testStart() throws InterruptedException {
//        CountDownLatch latch = new CountDownLatch(1);
//        Platform.runLater(() -> {
//            try {
//                Stage stage = new Stage();
//                lifestyleCalendar.start(stage);
//                stage.close();
//            } catch (Exception e) {
//                e.printStackTrace();
//            } finally {
//                latch.countDown();
//            }
//        });
//        latch.await(10, TimeUnit.SECONDS);
//    }

//    @Test
//    void testMain() throws InterruptedException {
//        // This test ensures that the main method runs without throwing an exception
//        CountDownLatch latch = new CountDownLatch(1);
//        Platform.runLater(() -> {
//            try {
//                LifestyleCalendar.main(new String[]{});
//            } catch (Exception e) {
//                e.printStackTrace();
//            } finally {
//                // Exit the JavaFX application
//                Platform.exit();
//                latch.countDown();
//            }
//        });
//        latch.await(10, TimeUnit.SECONDS);
//    }
}