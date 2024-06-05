package top.xcyyds.chineserpg.martialart;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MartialArt {
    private UUID uuid;
    private String name;
    private String type;
    private int level;
    private float completeness;
    private List<String> description;
    private String author;
    private List<MartialArtEntry> entries;

    public MartialArt(String name, String type, int level, float completeness, List<String> description, String author) {
        this.uuid = UUID.randomUUID();
        this.name = name;
        this.type = type;
        this.level = level;
        this.completeness = completeness;
        this.description = description;
        this.author = author != null ? author : "江湖人士";
        this.entries = new ArrayList<>();
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public void addEntry(MartialArtEntry entry) {
        this.entries.add(entry);
    }

    public void writeToNbt(NbtCompound nbt) {
        nbt.putUuid("UUID", uuid);
        nbt.putString("Name", name);
        nbt.putString("Type", type);
        nbt.putInt("Level", level);
        nbt.putFloat("Completeness", completeness);

        NbtList descriptionNbt = new NbtList();
        for (String line : description) {
            descriptionNbt.add(NbtString.of(line));
        }
        nbt.put("Description", descriptionNbt);

        nbt.putString("Author", author);

        NbtList entriesNbt = new NbtList();
        for (MartialArtEntry entry : entries) {
            NbtCompound entryNbt = new NbtCompound();
            entry.writeToNbt(entryNbt);
            entriesNbt.add(entryNbt);
        }
        nbt.put("Entries", entriesNbt);
    }

    public static MartialArt readFromNbt(NbtCompound nbt) {
        UUID uuid = nbt.getUuid("UUID");
        String name = nbt.getString("Name");
        String type = nbt.getString("Type");
        int level = nbt.getInt("Level");
        float completeness = nbt.getFloat("Completeness");

        NbtList descriptionNbt = nbt.getList("Description", 8);
        List<String> description = new ArrayList<>();
        for (int i = 0; i < descriptionNbt.size(); i++) {
            description.add(descriptionNbt.getString(i));
        }

        String author = nbt.getString("Author");

        MartialArt martialArt = new MartialArt(name, type, level, completeness, description, author);
        martialArt.setUuid(uuid);

        NbtList entriesNbt = nbt.getList("Entries", 10);
        for (int i = 0; i < entriesNbt.size(); i++) {
            NbtCompound entryNbt = entriesNbt.getCompound(i);
            martialArt.addEntry(MartialArtEntry.readFromNbt(entryNbt));
        }
        return martialArt;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public float getCompleteness() {
        return completeness;
    }

    public void setCompleteness(float completeness) {
        this.completeness = completeness;
    }

    public List<String> getDescription() {
        return description;
    }

    public void setDescription(List<String> description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public List<MartialArtEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<MartialArtEntry> entries) {
        this.entries = entries;
    }
}
