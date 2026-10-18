package game;

class Rock extends Hand {
    public Rock() {
        this.name = "Rock";
    }

    @Override
    public String interact(Hand other) {
        if (other instanceof Rock) {
            return "It's a tie!";
        } else if (other instanceof Scissors) {
            return "Rock breaks Scissors! You WIN!";
        } else {
            return "Paper covers Rock! You LOSE!";
        }
    }
}
