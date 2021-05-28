package de.IchMagOmasKekse;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Chat {

    public static String current_time = "", current_date = "";
    public static int current_hours = 0, current_minutes = 0, current_seconds = 0;

    public Chat() {
        getCurrentDate();
        getCurrentTime();
        getCurrentHours();
        getCurrentMinutes();
        getCurrentSeconds();
    }

    public static void sendConsoleMessage(String... lines) {
        for(String line : lines) System.out.println("[" + current_date + "][" + current_time + "] " + line);
    }

    private static long last_time = -1l;
    public static void tick() {
        long now = System.currentTimeMillis();
        long delta = (now - last_time);
        if(last_time == -1 || delta >= 1000) {
            last_time = now;
            getCurrentDate();
            getCurrentTime();
            getCurrentHours();
            getCurrentMinutes();
            getCurrentSeconds();
        }
    }

    /**
     * Gibt die Aktuelle Uhrzeit in einem String zurück
     * @return
     */
    public static String getCurrentTime() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        current_time = dtf.format(now);
        return dtf.format(now);
    }
    public static String getCurrentSeconds() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("ss");
        LocalDateTime now = LocalDateTime.now();
        current_seconds = Integer.parseInt(dtf.format(now));
        return dtf.format(now);
    }
    public static String getCurrentMinutes() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("mm");
        LocalDateTime now = LocalDateTime.now();
        current_minutes = Integer.parseInt(dtf.format(now));
        return dtf.format(now);
    }
    public static String getCurrentHours() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH");
        LocalDateTime now = LocalDateTime.now();
        current_hours = Integer.parseInt(dtf.format(now));
        return dtf.format(now);
    }

    /**
     * Gibt das Aktuelle Datum in einem String zurück
     * @return
     */
    public static String getCurrentDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDateTime now = LocalDateTime.now();
        current_date = dtf.format(now);
        return dtf.format(now);
    }

}
