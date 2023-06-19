public class Stat {
    String statName;
    int score;
    int modifier;
    boolean proficientST;

    public Stat (String name, int score, boolean proficient) {
        this.statName = name;
        this.score = score;
        this.modifier = (int) ((score < 10) ? Math.floor((score - 11) / 2) : Math.floor((score - 10) / 2));
        this.proficientST = proficient;
    }

    @Override
    public String toString() {
        return "Stat{" +
                "statName='" + statName + '\'' +
                ", score=" + score +
                ", modifier=" + modifier +
                ", proficientST=" + proficientST +
                '}';
    }

    public String getStatName() {
        return statName;
    }

    public void setStatName(String statName) {
        this.statName = statName;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
        this.modifier = (int) ((score < 10) ? Math.floor((score - 11) / 2) : Math.floor((score - 10) / 2));
    }

    public int getModifier() {
        return modifier;
    }

    public boolean isProficientST() {
        return proficientST;
    }

    public void setProficientST(boolean proficient) {
        this.proficientST = proficient;
    }
}
