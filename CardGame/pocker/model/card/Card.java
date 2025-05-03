package CardGame.pocker.model.card;

public class Card {
    private final CardNumber number;
    private final CardShape shape;

    Card(CardNumber number, CardShape shape) {
        this.number = number;
        this.shape = shape;
    }

    public CardNumber getNumber() {
        return number;
    }

    public CardShape getShape() {
        return shape;
    }

    public String getNumberName() {
        return number.getName();
    }
    public int getNumberScore() {
        return number.getScore();
    }

    public String getShapeName() {
        return shape.getName();
    }

    public int getShapeScore() {
        return shape.getScroe();
    }

    @Override
    public String toString() {
        return this.shape.getName() + this.number.getName();
    }
}