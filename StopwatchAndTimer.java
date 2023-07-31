import java.util.Scanner;

public class StopwatchAndTimer {
    private static boolean swRunning;
    private static long swStartTime;
    private static long swStopTime;
    private static long tDuration;
    private static long rTime;
    private static boolean tRunning;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. To Start Stopwatch");
            System.out.println("2. To Stop Stopwatch ");
            System.out.println("3. To Reset Stopwatch ");
            System.out.println("4. To Set Duration Timer ");
            System.out.println("5. To Start Timer ");
            System.out.println("6. To StopTimer ");
            System.out.println("7. To  Reset Timer ");
            System.out.println("8. To Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    ToStartStopwatch();
                    break;
                case 2:
                    ToStopStopwatch();
                    break;
                case 3:
                    ToResetStopwatch();
                    break;
                case 4:
                    ToSetTimerDuration(scanner);
                    break;
                case 5:
                    ToStartTimer();
                    break;
                case 6:
                    ToStopTimer();
                    break;
                case 7:
                    ToResetTimer();
                    break;
                case 8:
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid");
            }
        }
    }

    private static void ToStartStopwatch() {
        if (!swRunning) {
            swStartTime = System.currentTimeMillis();
            System.out.println("Stopwatch started.");
            swRunning = true;
        } else {
            System.out.println("Stopwatch is already running.");
        }
    }
    private static void ToResetStopwatch() {
        swRunning = false;
        System.out.println("Stopwatch reset.");
    }

    private static void ToStopStopwatch() {
        if (swRunning) {
            swStopTime = System.currentTimeMillis();
            long elapsedTime = swStopTime - swStartTime;
            System.out.println("Stopwatch stopped. Elapsed time: " + formatElapsedTime(elapsedTime));
            swRunning = false;
        } else {
            System.out.println("Stopwatch is not running.");
        }
    }



    private static void ToSetTimerDuration(Scanner scanner) {
        System.out.print("Enter the duration of timer in sec: ");
        tDuration = scanner.nextLong() * 1000; 
        System.out.println("Timer duration set: " + formatRemainingTime(tDuration));
    }

    private static void ToStartTimer() {
        if (!tRunning) {
            rTime = tDuration;
            tRunning = true;

            Thread timerThread = new Thread(() -> {
                while (rTime > 0) {
                    try {
                        Thread.sleep(1000); 
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    rTime -= 1000; 
                    System.out.println("Timer: " + formatRemainingTime(rTime));
                }
                System.out.println("Timer expired!");
                tRunning = false;
            });
            timerThread.start();
        } else {
            System.out.println("Timer is already running.");
        }
    }

    private static void ToStopTimer() {
        if (tRunning) {
            tRunning = false;
            System.out.println("Timer paused. Remaining time: " + formatRemainingTime(rTime));
        } else {
            System.out.println("Timer is not running.");
        }
    }

    private static void ToResetTimer() {
        tRunning = false;
        rTime = 0;
        System.out.println("Timer reset.");
    }

    private static String formatElapsedTime(long elapsedTime) {
        long millisec = elapsedTime % 1000;
        long sec = (elapsedTime / 1000) % 60;
        long min = (elapsedTime / (1000 * 60)) % 60;
        long hr = (elapsedTime / (1000 * 60 * 60)) % 24;
        return String.format("%02d:%02d:%02d.%03d", hr, min, sec, millisec);
    }

    private static String formatRemainingTime(long remainingTime) {
        long hrs = (remainingTime / (1000 * 60 * 60)) % 24;
        long min = (remainingTime / (1000 * 60)) % 60;
        long sec = (remainingTime / 1000) % 60;
        return String.format("%02d:%02d:%02d", hrs, min, sec);
    }
}
