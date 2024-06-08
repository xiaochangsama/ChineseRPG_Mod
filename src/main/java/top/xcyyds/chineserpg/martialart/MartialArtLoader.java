package top.xcyyds.chineserpg.martialart;

import com.google.gson.*;
import net.fabricmc.loader.api.FabricLoader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class MartialArtLoader {
    private static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(MartialArt.class, new MartialArtDeserializer())
            .registerTypeAdapter(MartialArtEntry.class, new MartialArtEntryDeserializer())
            .create();
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

    static class MartialArtDeserializer implements JsonDeserializer<MartialArt> {
        @Override
        public MartialArt deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject jsonObject = json.getAsJsonObject();
            String name = getString(jsonObject, "name");
            String type = getString(jsonObject, "type");
            int level = getInt(jsonObject, "level");
            float completeness = getFloat(jsonObject, "completeness");
            List<String> description = getStringList(jsonObject, "description");
            String author = getString(jsonObject, "author", "江湖人士");
            UUID uuid = UUID.fromString(getString(jsonObject, "uuid"));
            List<MartialArtEntry> entries = getEntries(jsonObject, "entries", context);

            MartialArt martialArt = new MartialArt(name, type, level, completeness, description, author, uuid);
            entries.forEach(martialArt::addEntry);
            return martialArt;
        }

        private String getString(JsonObject jsonObject, String memberName) {
            return jsonObject.has(memberName) ? jsonObject.get(memberName).getAsString() : "";
        }

        private String getString(JsonObject jsonObject, String memberName, String defaultValue) {
            return jsonObject.has(memberName) ? jsonObject.get(memberName).getAsString() : defaultValue;
        }

        private int getInt(JsonObject jsonObject, String memberName) {
            return jsonObject.has(memberName) ? jsonObject.get(memberName).getAsInt() : 0;
        }

        private float getFloat(JsonObject jsonObject, String memberName) {
            return jsonObject.has(memberName) ? jsonObject.get(memberName).getAsFloat() : 0.0f;
        }

        private List<String> getStringList(JsonObject jsonObject, String memberName) {
            List<String> list = new ArrayList<>();
            if (jsonObject.has(memberName)) {
                JsonArray jsonArray = jsonObject.getAsJsonArray(memberName);
                for (JsonElement element : jsonArray) {
                    list.add(element.getAsString());
                }
            }
            return list;
        }

        private List<MartialArtEntry> getEntries(JsonObject jsonObject, String memberName, JsonDeserializationContext context) {
            List<MartialArtEntry> entries = new ArrayList<>();
            if (jsonObject.has(memberName)) {
                JsonArray jsonArray = jsonObject.getAsJsonArray(memberName);
                for (JsonElement element : jsonArray) {
                    entries.add(context.deserialize(element, MartialArtEntry.class));
                }
            }
            return entries;
        }
    }

    static class MartialArtEntryDeserializer implements JsonDeserializer<MartialArtEntry> {
        @Override
        public MartialArtEntry deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject jsonObject = json.getAsJsonObject();
            String name = getString(jsonObject, "name");
            int level = getInt(jsonObject, "level");
            String jumpType = getString(jsonObject, "jumpType");
            int jumpCount = getInt(jsonObject, "jumpCount");
            float innerPowerConsumption = getFloat(jsonObject, "innerPowerConsumption");
            double velocityYIncrease = getDouble(jsonObject, "velocityYIncrease");
            int particleCount = getInt(jsonObject, "particleCount");
            float damageReductionHeight = getFloat(jsonObject, "damageReductionHeight");
            float damageReductionPercentage = getFloat(jsonObject, "damageReductionPercentage");
            float dodgeRate = getFloat(jsonObject, "dodgeRate");
            double directionalVelocity = getDouble(jsonObject, "directionalVelocity");

            return new LightSkillEntry(name, level, jumpType, jumpCount, innerPowerConsumption, velocityYIncrease, particleCount, damageReductionHeight, damageReductionPercentage, dodgeRate, directionalVelocity);
        }

        private String getString(JsonObject jsonObject, String memberName) {
            return jsonObject.has(memberName) ? jsonObject.get(memberName).getAsString() : "";
        }

        private int getInt(JsonObject jsonObject, String memberName) {
            return jsonObject.has(memberName) ? jsonObject.get(memberName).getAsInt() : 0;
        }

        private float getFloat(JsonObject jsonObject, String memberName) {
            return jsonObject.has(memberName) ? jsonObject.get(memberName).getAsFloat() : 0.0f;
        }

        private double getDouble(JsonObject jsonObject, String memberName) {
            return jsonObject.has(memberName) ? jsonObject.get(memberName).getAsDouble() : 0.0;
        }
    }
}
