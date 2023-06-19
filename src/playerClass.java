import org.json.JSONObject;

import java.util.ArrayList;

public class playerClass {
    private String name;
    private String subclass;
    private int level;
    private ArrayList<Stat> stats = new ArrayList<Stat>();
    private ArrayList<Skill> skill = new ArrayList<Skill>();
    private ArrayList<Item> inventory = new ArrayList<Item>();

//    public static playerClass builderFromJSONObj(String name, int level, String className) {
//
//    }

//    public static playerClass playerClassBuilderFromJSONObj(JSONObject obj) {
//
//    }

    public static void main(String[] args) {
        System.out.println(statNumberGenerator());
        System.out.println(statGenerator(statNumberGenerator()));
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

    public ArrayList<Skill> getSkill() {
        return skill;
    }

    public void setSkill(ArrayList<Skill> skill) {
        this.skill = skill;
    }

    public ArrayList<Item> getInventory() {
        return inventory;
    }

    public void setInventory(ArrayList<Item> inventory) {
        this.inventory = inventory;
    }



}
