package game;

abstract class Hand {
    String name;
    
    public abstract String interact(Hand other);
    
    public String getName() {
        return name;
    }
}
