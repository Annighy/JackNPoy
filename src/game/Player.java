package game;
public class Player {
    private Hand choices;  
    private String name;

    public Player(String name) {
        this.name = name;
    }

    public void setChoice(Hand hand) {
        this.choices = hand;  
    }

    public Hand getChoice() {
        return choices; 
    }

    public String getName() {
        return name;
    }
}