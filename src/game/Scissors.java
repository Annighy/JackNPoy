package game;

class Scissors extends Hand {
    public Scissors() {
        this.name = "Scissors";
    }

    @Override
    public String interact(Hand other) {
        if (other instanceof Scissors) {
            return "It's a tie!";
        } else if (other instanceof Paper) {
            return "Scissors cuts Paper! You WIN!";
        } else {
            return "Rock breaks Scissors! You LOSE!";
        }
    }
}
