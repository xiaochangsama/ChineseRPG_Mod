package top.xcyyds.chineserpg.martialart;

import com.google.gson.Gson;
import net.fabricmc.loader.api.FabricLoader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MartialArtLoader {
    private static final Gson GSON = new Gson();
    private static final String MARTIAL_ARTS_PATH = "assets/chineserpg/martial_arts/";

    public static List<MartialArt> loadMartialArts() {
        List<MartialArt> martialArts = new ArrayList<>();
        try {
            List<Path> files = getFilesFromResource(MARTIAL_ARTS_PATH);
            for (Path file : files) {
                martialArts.add(loadMartialArtFromFile(file));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return martialArts;
    }

    private static List<Path> getFilesFromResource(String folderPath) throws IOException {
        return FabricLoader.getInstance()
                .getModContainer("chineserpg")
                .orElseThrow(() -> new IOException("Mod container not found"))
                .findPath(folderPath)
                .map(path -> {
                    try {
                        return java.nio.file.Files.walk(path)
                                .filter(java.nio.file.Files::isRegularFile)
                                .collect(Collectors.toList());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .orElseThrow(() -> new IOException("Resource folder not found"));
    }

    private static MartialArt loadMartialArtFromFile(Path filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(java.nio.file.Files.newInputStream(filePath), StandardCharsets.UTF_8))) {
            return GSON.fromJson(reader, MartialArt.class);
        }
    }
}
