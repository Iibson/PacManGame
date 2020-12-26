package PacManGame.gameObject.data;

public class Position {

    public final double x;
    public final double y;

    public Position(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        if (this.x != ((Position) obj).x)
            return false;
        return this.y == ((Position) obj).y;
    }
}
