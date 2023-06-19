public class Skill {
    String name;
    int bonus;
    boolean proficient;
    Stat stat;

    public Skill(String name, int playerProficiency, boolean proficient, Stat stat) {
        this.name = name;
        this.bonus = proficient ? (playerProficiency + stat.getModifier()) : stat.getModifier();
        this.proficient = proficient;
        this.stat = stat;
    }

    @Override
    public String toString() {
        return "Skill{" +
                "name='" + name + '\'' +
                ", bonus=" + bonus +
                ", proficient=" + proficient +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBonus() {
        return bonus;
    }

    public void setBonus(int bonus) {
        this.bonus = bonus;
    }

    public boolean isProficient() {
        return proficient;
    }

    public void setProficient(boolean proficient) {
        this.proficient = proficient;
    }

    public Stat getStat() {
        return stat;
    }

    public void setStat(Stat stat) {
        this.stat = stat;
    }
}
