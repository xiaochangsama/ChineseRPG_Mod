package top.xcyyds.chineserpg.registry;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import top.xcyyds.chineserpg.item.MartialArtEntry;

import java.util.ArrayList;
import java.util.List;

public class MartialArt {
    private String name;
    private String type;
    private int level;
    private float completeness;
    private String description;
    private String author;
    private List<MartialArtEntry> entries;

    public MartialArt(String name, String type, int level, float completeness, String description, String author) {
        this.name = name;
        this.type = type;
        this.level = level;
        this.completeness = completeness;
        this.description = description;
        this.author = author != null ? author : "江湖人士";
        this.entries = new ArrayList<>();
    }

    public void addEntry(MartialArtEntry entry) {
        this.entries.add(entry);
    }

    public void writeToNbt(NbtCompound nbt) {
        nbt.putString("Name", name);
        nbt.putString("Type", type);
        nbt.putInt("Level", level);
        nbt.putFloat("Completeness", completeness);
        nbt.putString("Description", description);
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
        String name = nbt.getString("Name");
        String type = nbt.getString("Type");
        int level = nbt.getInt("Level");
        float completeness = nbt.getFloat("Completeness");
        String description = nbt.getString("Description");
        String author = nbt.getString("Author");

        MartialArt martialArt = new MartialArt(name, type, level, completeness, description, author);

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
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
