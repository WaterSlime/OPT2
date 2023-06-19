import java.util.*;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    private static ArrayList<String> classes = new ArrayList<String>(
            Arrays.asList("barbarian", "bard", "cleric", "druid", "fighter", "monk", "paladin", "ranger", "rogue", "sorcerer", "warlock", "wizard"));
    private static ArrayList<String> species = new ArrayList<>(
            Arrays.asList("dragonborn", "dwarf", "elf", "gnome", "half-elf", "half-orc", "halfling", "human", "tiefling"));

    public static void main(String[] args) {
        String menuOption = "0";
        Scanner choose = new Scanner(System.in);


        do {
            System.out.println("Please pick an action from the menu below: \n1: Create new character\n9: Exit");
            menuOption = choose.next();

            switch (menuOption) {
                case "1":
                    newCharDialogue(choose);
                    break;
                case "9":
                    break;
                default:
                    System.out.println("Not a valid input, please input a single numerical value corresponding to one of the options listed. ");
            }

        } while (!"9".equals(menuOption));
    }

    public static void newCharDialogue(Scanner scanner) {
        String classString = "";
        int level = 0;
        String speciesString = "";
        String name = "";
        do {
            System.out.println("What class would you like the character to be:" +
                    "\nbarbarian, bard, cleric, druid, fighter, monk, paladin, ranger, rogue, sorcerer, warlock, wizard");
            classString = scanner.next();
            if (!classes.contains(classString)) {
                System.out.println("Incorrect input, please try again");
            }
        } while (!classes.contains(classString));
        do {
            System.out.println("What level should the character be?");
            if (scanner.hasNextInt()) {
                level = Math.min(20, scanner.nextInt());
            } else {
                System.out.println("please insert a numerical value ");
            }
        } while (level == 0);
        do {
            System.out.println("What species would you like the character to be:" +
                    "\ndragonborn, dwarf, elf, gnome, half-elf, half-orc, halfling, human, tiefling");
            speciesString = scanner.next();
            if (!species.contains(speciesString)) {
                System.out.println("Incorrect input, please try again");
            }
        } while (!species.contains(species));
        System.out.println("Give your character a name: ");
        name = scanner.next();
    }
}