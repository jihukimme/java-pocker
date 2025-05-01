package pocker;

class Card {
    private final int number;
    private final char shape;

    Card(int number, char shape) {
        this.number = number;
        this.shape = shape;
    }

    public int getNumber(){
        return this.number;
    }

    public char getShape() {
        return this.shape;
    }

    private String numberToString() {
        switch (number) {
            case 1: return "A";
            case 11: return "J";
            case 12: return "Q";
            case 13: return "K";
            default: return String.valueOf(number);
        }
    }

    @Override
    public String toString() {
        return this.shape + numberToString();
    }
}