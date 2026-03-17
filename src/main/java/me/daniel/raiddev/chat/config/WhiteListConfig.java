package me.daniel.raiddev.chat.config;
import net.fabricmc.loader.api.FabricLoader;
import java.nio.file.*;
import java.util.*;
public class WhiteListConfig {
    private static final Path DIR = FabricLoader.getInstance().getGameDir().resolve("Daniel_Mods/Chat_Helper");
    private static final Path FILE = DIR.resolve("white_list.txt");
    private static List<String> words = new ArrayList<>();
    private static long lastTime = -1;
    public static void load() {
        try {
            if (Files.notExists(DIR)) Files.createDirectories(DIR);
            if (Files.notExists(FILE)) {
                words = new ArrayList<>(List.of("хуй"));
                save();
            } else {
                long modTime = Files.getLastModifiedTime(FILE).toMillis();
                if (modTime > lastTime) {
                    words = Files.readAllLines(FILE);
                    words.removeIf(String::isEmpty);
                    lastTime = modTime;
                }
            }
        } catch (Exception ignored) {}
    }
    public static void save() {
        try {
            Files.write(FILE, words);
        } catch (Exception ignored) {}
    }
    public static List<String> getWords() {
        if (System.currentTimeMillis() % 1000 < 20) load();
        return words;
    }
}