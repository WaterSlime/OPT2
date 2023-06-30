import org.davidmoten.text.utils.WordWrap;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class playerClass {
    //prerequisite reference lists
    private static ArrayList<String> skillList = new ArrayList<String>(
            Arrays.asList("Athletics", "Acrobatics", "Sleight of Hand", "Stealth", "Arcana", "History", "Investigation", "Nature", "Religion", "Animal Handling",
            "Insight", "Medicine", "Perception", "Survival", "Deception", "Intimidation", "Performance", "Persuasion"));
    private static ArrayList<String> statAbbreviations = new ArrayList<String>(
            Arrays.asList("STR", "DEX", "CON", "INT", "WIS", "CHA"));
    private String name;
    private String subclass;
    private int level;
    private int proficiency;
    private String species;
    private ArrayList<Stat> stats = new ArrayList<Stat>();
    private ArrayList<Skill> skills = new ArrayList<Skill>();
    private ArrayList<String> proficiencies = new ArrayList<String>();
    private ArrayList<Feature> features = new ArrayList<Feature>();
    private ArrayList<Item> inventory = new ArrayList<Item>();

    public playerClass(String name, String subclass, int level, String species, ArrayList<Stat> stats, ArrayList<Skill> skills, ArrayList<String> proficiencies, ArrayList<Feature> features, ArrayList<Item> inventory) {
        this.name = name;
        this.subclass = subclass;
        this.level = level;
        this.proficiency = (int) Math.floor((level - 1) / 4) + 2;
        this.species = species;
        this.stats = stats;
        this.skills = skills;
        this.proficiencies = proficiencies;
        this.features = features;
        this.inventory = inventory;
    }

    //Takes the inputs given by a user and a JSON object and generates a full playerClass from it
    public static playerClass playerClassBuilderFromJSONObj(String name, String className, int level, String species, JSONObject obj) {
        String subclass = obj.getJSONArray("subclasses").getJSONObject(0).get("name").toString();
        ArrayList<Stat> stats = statGenerator(statNumberGenerator());
        ArrayList<Skill> skills = skillGenerator(stats);
        //get skill proficiencies
        ArrayList<String> proficiencies = new ArrayList<String>();
        JSONObject profChoices = obj.getJSONArray("proficiency_choices").getJSONObject(0);
        int choices = Integer.parseInt(profChoices.get("choose").toString());
        JSONArray options = profChoices.getJSONObject("from").getJSONArray("options");
        ArrayList<String> skillProfs = new ArrayList<String>();
        do {
            String prof = options.getJSONObject((int) (Math.random() * options.length())).getJSONObject("item").get("name").toString().split(" ")[1];
            if (!skillProfs.contains(prof)) {
                skillProfs.add(prof);
            }
        } while (skillProfs.size() < choices);
        for (int i = 0; i < skills.size(); i++) {
            for (int j = 0; j < skillProfs.size(); j++) {
                if (skills.get(i).equals(skillProfs.get(j))) {
                    skills.get(i).setProficient(true);
                }
            }
        }
        //get other proficiencies
        JSONArray profList = obj.getJSONArray("proficiencies");
        for (int i = 0; i < profList.length(); i++) {
            proficiencies.add(profList.getJSONObject(i).get("name").toString());
        }
        for (int i = 0; i < statAbbreviations.size(); i++) {
            if (statAbbreviations.get(i).equals(proficiencies.get(proficiencies.size() - 1).split(" ")[2]) || statAbbreviations.get(i).equals(proficiencies.get(proficiencies.size() - 2).split(" ") [2])) {
                stats.get(i).setProficientST(true);
            }
        }
        //get features from levels
        JSONArray levelsArray = APICommunication.APIRequest(APICommunication.APIRequestBuilder("levels", className), true);
        ArrayList<Feature> features = new ArrayList<Feature>();
        int current = 0;
        int k = 0;
        while (current < level) {
            current = Integer.parseInt(levelsArray.getJSONObject(k).get("level").toString());
            JSONArray currentFeatures = levelsArray.getJSONObject(k).getJSONArray("features");
            for (int i = 0; i < currentFeatures.length(); i++) {
                if (currentFeatures.getJSONObject(i).get("name").toString().equals("Ability Score Improvement")) {
                    Stat upgraded = stats.get((int) Math.random() * 6);
                    upgraded.setScore(upgraded.getScore() + 2);
                } else {
                    features.add(new Feature(currentFeatures.getJSONObject(i).get("name").toString(),
                            APICommunication.APIRequest(APICommunication.APIRequestBuilder("URL", currentFeatures.getJSONObject(i).get("url").toString())).get("desc").toString()));
                }
            }
            k++;
        }
        //generate inventory from character generator
        ArrayList<Item> inventory = new ArrayList<Item>();
        inventory.add(Weapon.weaponBuilderFromJSONObj(APICommunication.APIRequest(APICommunication.APIRequestBuilder("equipment", "longsword"))));
        inventory.add(Weapon.weaponBuilderFromJSONObj(APICommunication.APIRequest(APICommunication.APIRequestBuilder("equipment", "blowgun"))));
        inventory.add(Armour.armourBuilderFromJSONObj(APICommunication.APIRequest(APICommunication.APIRequestBuilder("equipment", "chain-mail"))));
        Item abacus = Item.itemBuilderFromJSONObj(APICommunication.APIRequest(APICommunication.APIRequestBuilder("equipment", "abacus")));
        abacus.setDescription("A counting frame, consisting of a wooden frame with spokes and beads, used for calculations. ");
        inventory.add(abacus);
        return new playerClass(name, subclass, level, species, stats, skills, proficiencies, features, inventory);
    }

    //Generates an arraylist of 6 random stats within the range of 6-18, using a common DnD stat method, rolling 4 6-sided dice and keeping the 3 highest.
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

    //Populates an arraylist of stats with a given list of numbers
    public static ArrayList<Stat> statGenerator(ArrayList<Integer> statNumbers) {
        ArrayList<Stat> statList = new ArrayList<Stat>();
        statList.add(new Stat("Strength", statNumbers.get(0), false));
        statList.add(new Stat("Dexterity", statNumbers.get(1), false));
        statList.add(new Stat("Constitution", statNumbers.get(2), false));
        statList.add(new Stat("Intelligence", statNumbers.get(3), false));
        statList.add(new Stat("Wisdom", statNumbers.get(4), false));
        statList.add(new Stat("Charisma", statNumbers.get(5), false));
        return statList;
    }

    //Populates an arraylist of skill with a given list of stats
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

    //Prints out the contents of the playerClass in a clean overview
    public void printString() {
        System.out.println(String.format("%-20s%-20s%-20s%-20s\n", "Name:", this.getName(), "Subclass:", this.getSubclass()) +
                String.format("%-20s%-20d%-20s%-20s\n", "Level:", this.getLevel(), "Species:", this.getSpecies()) +
                String.format("%-20s%-20s%-20s%-20s\n", this.stats.get(0).getStatName() + ":", this.stats.get(0).getModifier(), this.skills.get(0).getName() + ":", this.skills.get(0).getBonus()) +
                String.format("%-20s%-20s%-20s%-20s\n", "Saving throw:", (this.stats.get(0).isProficientST()) ? this.stats.get(0).getModifier() + this.getProficiency() : this.stats.get(0).getModifier(), "", "") +
                String.format("%-20s%-20s%-20s%-20s\n", this.stats.get(1).getStatName() + ":", this.stats.get(1).getModifier(), this.skills.get(1).getName() + ":", this.skills.get(1).getBonus()) +
                String.format("%-20s%-20s%-20s%-20s\n", "Saving throw:", (this.stats.get(1).isProficientST()) ? this.stats.get(1).getModifier() + this.getProficiency() : this.stats.get(1).getModifier(), this.skills.get(2).getName() + ":", this.skills.get(2).getBonus()) +
                String.format("%-20s%-20s%-20s%-20s\n", "", "", this.skills.get(3).getName() + ":", this.skills.get(3).getBonus()) +
                String.format("%-20s%-20s%-20s%-20s\n", this.stats.get(2).getStatName() + ":", this.stats.get(2).getModifier(), "", "") +
                String.format("%-20s%-20s%-20s%-20s\n", "Saving throw:", (this.stats.get(2).isProficientST()) ? this.stats.get(2).getModifier() + this.getProficiency() : this.stats.get(2).getModifier(), "", "") +
                String.format("%-20s%-20s%-20s%-20s\n", this.stats.get(3).getStatName() + ":", this.stats.get(3).getModifier(), this.skills.get(4).getName() + ":", this.skills.get(4).getBonus()) +
                String.format("%-20s%-20s%-20s%-20s\n", "Saving throw:", (this.stats.get(3).isProficientST()) ? this.stats.get(3).getModifier() + this.getProficiency() : this.stats.get(3).getModifier(), this.skills.get(5).getName() + ":", this.skills.get(5).getBonus()) +
                String.format("%-20s%-20s%-20s%-20s\n", "", "", this.skills.get(6).getName() + ":", this.skills.get(6).getBonus()) +
                String.format("%-20s%-20s%-20s%-20s\n", "", "", this.skills.get(7).getName() + ":", this.skills.get(7).getBonus()) +
                String.format("%-20s%-20s%-20s%-20s\n", "", "", this.skills.get(8).getName() + ":", this.skills.get(8).getBonus()) +
                String.format("%-20s%-20s%-20s%-20s\n", this.stats.get(4).getStatName() + ":", this.stats.get(4).getModifier(), this.skills.get(9).getName() + ":", this.skills.get(9).getBonus()) +
                String.format("%-20s%-20s%-20s%-20s\n", "Saving throw:", (this.stats.get(4).isProficientST()) ? this.stats.get(4).getModifier() + this.getProficiency() : this.stats.get(4).getModifier(), this.skills.get(10).getName() + ":", this.skills.get(10).getBonus()) +
                String.format("%-20s%-20s%-20s%-20s\n", "", "", this.skills.get(11).getName() + ":", this.skills.get(11).getBonus()) +
                String.format("%-20s%-20s%-20s%-20s\n", "", "", this.skills.get(12).getName() + ":", this.skills.get(12).getBonus()) +
                String.format("%-20s%-20s%-20s%-20s\n", "", "", this.skills.get(13).getName() + ":", this.skills.get(13).getBonus()) +
                String.format("%-20s%-20s%-20s%-20s\n", this.stats.get(5).getStatName() + ":", this.stats.get(5).getModifier(), this.skills.get(14).getName() + ":", this.skills.get(14).getBonus()) +
                String.format("%-20s%-20s%-20s%-20s\n", "Saving throw:", (this.stats.get(5).isProficientST()) ? this.stats.get(5).getModifier() + this.getProficiency() : this.stats.get(5).getModifier(), this.skills.get(15).getName() + ":", this.skills.get(15).getBonus()) +
                String.format("%-20s%-20s%-20s%-20s\n", "", "", this.skills.get(16).getName() + ":", this.skills.get(16).getBonus()) +
                String.format("%-20s%-20s%-20s%-20s\n", "", "", this.skills.get(17).getName() + ":", this.skills.get(17).getBonus()));
                for (int i = 0; i < this.features.size(); i++) {
                    System.out.println(this.features.get(i).getName());
                    System.out.println(WordWrap.from(this.features.get(i).getDescription().substring(2, this.features.get(i).getDescription().length() - 2)).maxWidth(200).insertHyphens(true).wrap() + "\n");
                }
                for (Item i : this.getInventory()) {
                    System.out.println(i.toString());
        }
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

    public int getProficiency() {
        return proficiency;
    }

    public void setProficiency(int proficiency) {
        this.proficiency = proficiency;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
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
