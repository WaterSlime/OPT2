import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class playerClass {
    private static ArrayList<String> skillList = new ArrayList<String>(
            Arrays.asList("Athletics", "Acrobatics", "Sleight of Hand", "Stealth", "Arcana", "History", "Investigation", "Nature", "Religion", "Animal Handling",
            "Insight", "Medicine", "Perception", "Survival", "Deception", "Intimidation", "Performance", "Persuasion"));
    private String name;
    private String subclass;
    private int level;
    private ArrayList<Stat> stats = new ArrayList<Stat>();
    private ArrayList<Skill> skills = new ArrayList<Skill>();
    private ArrayList<String> proficiencies = new ArrayList<String>();
    private ArrayList<Feature> features = new ArrayList<Feature>();
    private ArrayList<Item> inventory = new ArrayList<Item>();

    public playerClass(String name, String subclass, int level, ArrayList<Stat> stats, ArrayList<Skill> skills, ArrayList<Feature> features, ArrayList<Item> inventory) {
        this.name = name;
        this.subclass = subclass;
        this.level = level;
        this.stats = stats;
        this.skills = skills;
        this.features = features;
        this.inventory = inventory;
    }


    public static void main(String[] args) {
        System.out.println(statNumberGenerator());
        System.out.println(statGenerator(statNumberGenerator()));
    }

    public static playerClass playerClassBuilderFromJSONObj(String name, int level, String className, String species, JSONObject obj) {
        String subclass = obj.getJSONArray("subclasses").getJSONObject(1).get("name").toString();
        ArrayList<Stat> stats = statGenerator(statNumberGenerator());
        ArrayList<Skill> skills = skillGenerator(stats);


        return new playerClass("Bob", subclass, level, stats, new ArrayList<Skill>(), new ArrayList<Feature>(), new ArrayList<Item>());
    }

    public static ArrayList<Integer> statNumberGenerator() {
        ArrayList<Integer> numbers = new ArrayList<Integer>();
        for (int i = 0; i < 30; i++) {
            int[] rolls = new int[4];
            for (int j = 0; j < 4; j++) {
                rolls[j] = (int) (Math.random() * 5 + 2);
            }
            int min = 7;
            int number = 0;
            for (int k = 0; k < 4; k++) {
                min = (rolls[k] < min) ? rolls[k] : min;
            }
            for (int l = 0; l < 4; l++) {
                number += rolls[l];
            }
            number -= min;
            numbers.add(number);
        }

        return numbers;
    }

    public static ArrayList<Stat> statGenerator(ArrayList<Integer> statNumbers) {
        ArrayList<Stat> statList = new ArrayList<Stat>();
        statList.add(new Stat("Strength", statNumbers.get(0), true));
        statList.add(new Stat("Dexterity", statNumbers.get(1), false));
        statList.add(new Stat("Constitution", statNumbers.get(2), false));
        statList.add(new Stat("Intelligence", statNumbers.get(3), true));
        statList.add(new Stat("Wisdom", statNumbers.get(4), false));
        statList.add(new Stat("Charisma", statNumbers.get(5), false));
        return statList;
    }

    private static ArrayList<Skill> skillGenerator(ArrayList<Stat> stats) {
        ArrayList<Skill> skills = new ArrayList<Skill>();
        skills.add(new Skill(skillList.get(0), 0, false, stats.get(0)));
        skills.add(new Skill(skillList.get(1), 0, false, stats.get(1)));
        skills.add(new Skill(skillList.get(2), 0, false, stats.get(1)));
        skills.add(new Skill(skillList.get(3), 0, false, stats.get(1)));
        skills.add(new Skill(skillList.get(4), 0, false, stats.get(3)));
        skills.add(new Skill(skillList.get(5), 0, false, stats.get(3)));
        skills.add(new Skill(skillList.get(6), 0, false, stats.get(3)));
        skills.add(new Skill(skillList.get(7), 0, false, stats.get(3)));
        skills.add(new Skill(skillList.get(8), 0, false, stats.get(3)));
        skills.add(new Skill(skillList.get(9), 0, false, stats.get(4)));
        skills.add(new Skill(skillList.get(10), 0, false, stats.get(4)));
        skills.add(new Skill(skillList.get(11), 0, false, stats.get(4)));
        skills.add(new Skill(skillList.get(12), 0, false, stats.get(4)));
        skills.add(new Skill(skillList.get(13), 0, false, stats.get(4)));
        skills.add(new Skill(skillList.get(14), 0, false, stats.get(5)));
        skills.add(new Skill(skillList.get(15), 0, false, stats.get(5)));
        skills.add(new Skill(skillList.get(16), 0, false, stats.get(5)));
        skills.add(new Skill(skillList.get(17), 0, false, stats.get(5)));
        return skills;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubclass() {
        return subclass;
    }

    public void setSubclass(String subclass) {
        this.subclass = subclass;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public ArrayList<Stat> getStats() {
        return stats;
    }

    public void setStats(ArrayList<Stat> stats) {
        this.stats = stats;
    }

    public ArrayList<Skill> getSkills() {
        return skills;
    }

    public void setSkills(ArrayList<Skill> skills) {
        this.skills = skills;
    }

    public ArrayList<Feature> getFeatures() {
        return features;
    }

    public void setFeatures(ArrayList<Feature> features) {
        this.features = features;
    }

    public ArrayList<Item> getInventory() {
        return inventory;
    }

    public void setInventory(ArrayList<Item> inventory) {
        this.inventory = inventory;
    }



}
