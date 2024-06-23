package top.xcyyds.chineserpg.martialart;

import com.google.gson.*;
import net.fabricmc.loader.api.FabricLoader;
import top.xcyyds.chineserpg.martialart.artentry.DamageControl;
import top.xcyyds.chineserpg.martialart.artentry.LightSkillEntry;
import top.xcyyds.chineserpg.martialart.artentry.MartialArtEntry;
import top.xcyyds.chineserpg.martialart.artentry.OuterSkillEntry;
import top.xcyyds.chineserpg.martialart.skill.MartialArt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static top.xcyyds.chineserpg.ChineseRPG.MOD_ID;

public class MartialArtLoader {
    private static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(MartialArt.class, new MartialArtDeserializer())
            .registerTypeAdapter(MartialArtEntry.class, new MartialArtEntryDeserializer())
            .create();
    private static final String LIGHT_SKILLS_PATH = "assets/chineserpg/martial_arts/light_skills/";
    private static final String OUTER_SKILLS_PATH = "assets/chineserpg/martial_arts/outer_skills/";

    public static List<MartialArt> loadMartialArts() {
        List<MartialArt> martialArts = new ArrayList<>();
        try {
            martialArts.addAll(loadMartialArtsFromPath(LIGHT_SKILLS_PATH));
            martialArts.addAll(loadMartialArtsFromPath(OUTER_SKILLS_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return martialArts;
    }

    private static List<MartialArt> loadMartialArtsFromPath(String folderPath) throws IOException {
        List<MartialArt> martialArts = new ArrayList<>();
        List<Path> files = getFilesFromResource(folderPath);
        for (Path file : files) {
            martialArts.add(loadMartialArtFromFile(file));
        }
        return martialArts;
    }

    private static List<Path> getFilesFromResource(String folderPath) throws IOException {
        return FabricLoader.getInstance()
                .getModContainer(MOD_ID)
                .orElseThrow(() -> new IOException("Mod container not found"))
                .findPath(folderPath)
                .map(path -> {
                    try {
                        return Files.walk(path)
                                .filter(Files::isRegularFile)
                                .collect(Collectors.toList());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .orElseThrow(() -> new IOException("Resource folder not found"));
    }

    private static MartialArt loadMartialArtFromFile(Path filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(Files.newInputStream(filePath), StandardCharsets.UTF_8))) {
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
            String entryType = getString(jsonObject, "type");

            return switch (entryType) {
                case "轻功" -> new LightSkillEntry(name, level,
                        getString(jsonObject, "jumpType"),
                        getInt(jsonObject, "jumpCount"),
                        getFloat(jsonObject, "innerPowerConsumption"),
                        getDouble(jsonObject, "velocityYIncrease"),
                        getInt(jsonObject, "particleCount"),
                        getFloat(jsonObject, "damageReductionHeight"),
                        getFloat(jsonObject, "damageReductionPercentage"),
                        getFloat(jsonObject, "dodgeRate"),
                        getDouble(jsonObject, "directionalVelocity"));
                case "外功" -> new OuterSkillEntry(name, level,
                        getFloat(jsonObject, "damage"),
                        getInt(jsonObject, "cooldown"),
                        getFloat(jsonObject, "range"),
                        context.deserialize(jsonObject.get("damageControl"), DamageControl.class),
                        getFloat(jsonObject, "maxInnerPowerMultiplier"),
                        getFloat(jsonObject, "currentInnerPowerMultiplier"),
                        getFloat(jsonObject, "manualPowerMultiplier"),
                        getFloat(jsonObject, "maxInnerPowerConsumption"),
                        getFloat(jsonObject, "minInnerPowerConsumption"),
                        getString(jsonObject, "outerType"),
                        getString(jsonObject, "releaseMethod"),
                        getString(jsonObject,"animationName"));
                default -> throw new IllegalStateException("Unexpected value: " + entryType);
            };
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
