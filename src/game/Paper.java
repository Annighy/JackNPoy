package game;

class Paper extends Hand {
    public Paper() {
        this.name = "Paper";
    }

    @Override
    public String interact(Hand other) {
        if (other instanceof Paper) {
            return "It's a tie!";
        } else if (other instanceof Rock) {
            return "Paper covers Rock! You WIN!";
        } else {
            return "Scissors cuts Paper! You LOSE!";
        }
    }
}