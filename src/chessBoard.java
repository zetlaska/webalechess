import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Chessboard implements Serializable, Cloneable {
    private Chessboard previousState = null;
    private Piece.Color turn;
    private List<Piece> pieces = new ArrayList<Piece>();
    private Piece inCheck = null;
    private Piece lastMoved = null;

    //if the Sun is in check, returns it
    public Piece getPieceInCheck() {return inCheck; }
    //returns the piece that last moved
    public Piece getLastMovedPiece() {return lastMoved;}

    //creates a new board object
    public Chessboard(boolean initPieces) {
        turn = Piece.Color.Red;

        if(initPieces) {
            //blue pieces
            pieces.add(new Arrow(new Point(0,1), Piece.Color.Blue));
            pieces.add(new Arrow(new Point(2,1), Piece.Color.Blue));
            pieces.add(new Arrow(new Point(4,1), Piece.Color.Blue));
            pieces.add(new Arrow(new Point(6,1), Piece.Color.Blue));

            pieces.add(new Plus(new Point(0,0), Piece.Color.Blue));
            pieces.add(new Triangle(new Point(1,0), Piece.Color.Blue));
            pieces.add(new Chevron(new Point(2,0), Piece.Color.Blue));
            pieces.add(new Sun(new Point(3,0), Piece.Color.Blue));
            pieces.add(new Chevron(new Point(4,0), Piece.Color.Blue));
            pieces.add(new Triangle(new Point(5,0), Piece.Color.Blue));
            pieces.add(new Plus(new Point(6,0), Piece.Color.Blue));

            //red pieces
            pieces.add(new Arrow(new Point(0,6), Piece.Color.Red));
            pieces.add(new Arrow(new Point(2,6), Piece.Color.Red));
            pieces.add(new Arrow(new Point(4,6), Piece.Color.Red));
            pieces.add(new Arrow(new Point(6,6), Piece.Color.Red));

            pieces.add(new Plus(new Point(0,7), Piece.Color.Red));
            pieces.add(new Triangle(new Point(1,7), Piece.Color.Red));
            pieces.add(new Chevron(new Point(2,7), Piece.Color.Red));
            pieces.add(new Sun(new Point(3,7), Piece.Color.Red));
            pieces.add(new Chevron(new Point(4,7), Piece.Color.Red));
            pieces.add(new Triangle(new Point(5,7), Piece.Color.Red));
            pieces.add(new Plus(new Point(6,7), Piece.Color.Red));

        }
    }

    //private constructor used to create a copy of the board
    private Chessboard(Piece.Color turn, Chessboard previousState, List<Piece> pieces, Piece lastMoved, Piece inCheck) {
        this.turn = turn;
        if (inCheck!= null)
            this.inCheck = inCheck.clone();
        if (lastMoved!=null)
            this.lastMoved = lastMoved.clone();
        this.previousState = previousState;
        for(Piece p : pieces) {
            this.pieces.add(p.clone());
        }
    }

    //returns the list of all the pieces on the board
    public List<Piece> getPieces() {return pieces;}
    //returns the piece in a specified location
    public Piece getPieceAt(Point p) {
        for(Piece pc : pieces) {
            if(pc.getLocation().x == p.x && pc.getLocation().y == p.y)
                return pc;
        }
        return null;
    }

    //removes the piece from the board
    public void removePiece(Piece p) {
        if (pieces.contains(p)) {
            pieces.remove(p);
            return;
        }
    }

    //adds a piece to the board
    public void addPiece(Piece p) {pieces.add(p);}
    public void removePieceAt(Point p) {
        Piece temp = null;
        for(Piece pc : pieces) {
            if (pc.getLocation().equals(p)) {
                temp=pc;
                break;
            }
        }
        if (temp != null)
            pieces.remove(temp);
    }

    //returns the color that can move next
    public Piece.Color getTurn() {return turn;}

    //performs a given move
    public void doMove(Movement m, boolean showDialog) {
        this.previousState = this.clone();

        if(m.getCaptured() != null);
        this.removePiece(m.getCaptured());
        m.getPiece().moveTo(m.getMoveTo());


        //checks once the arrow reaches the end of board
        checkArrowEndOfBoard(m.getPiece());
        //checkTriangleTurn(m.getPiece());
        //checkPlusTurn(m.getPiece());

        this.lastMoved = m.getPiece();
        this.inCheck = sunInCheck();

        //changes the color of pieces moving next
        turn = Piece.Color.values()[(turn.ordinal() + 1) % 2];
    }

    //reverses the arrow once arrow reaches the end of the board
    private void checkArrowEndOfBoard(Piece arrow) {
        if(arrow instanceof Arrow && (arrow.getLocation().y == 0 || arrow.getLocation(). y == 7)) {

            Piece reversed;
            reversed = new ArrowReversed(arrow.getLocation(), arrow.getColor());

            pieces.remove(arrow);
            pieces.add(reversed);
        }
    }

    /*private void checkTriangleTurn(Piece triangle) {
        if(triangle instanceof Triangle) {
            Piece triangleTransform;

            triangleTransform = new Plus(triangle.getLocation(), triangle.getColor());

            pieces.remove(triangle);
            pieces.add(triangleTransform);
        }
    }



    private void checkPlusTurn(Piece plus) {
        if(plus instanceof Plus) {
            Piece plusTransform;

            plusTransform = new Triangle(plus.getLocation(), plus.getColor());

            pieces.remove(plus);
            pieces.add(plusTransform);
        }
    }*/

    //returns a new board object with the executed move
    public Chessboard tryMove(Movement m) {
        //creates a copy of the board
        Chessboard helper = this.clone();
        //creates a copy of the move for the copied board
        Piece capture = null;
        if(m.getCaptured() != null)
            capture = helper.getPieceAt(m.getCaptured().getLocation());
        Piece moving = helper.getPieceAt(m.getPiece().getLocation());
        // performs the move on the copied board
        helper.doMove(new Movement(moving,
                m.getMoveTo(), capture), false);

        return helper;
    }

    public Piece sunInCheck() {
        //go through the pieces on the board
        for(Piece pc : pieces)
            //goes through list of valid moves for the pieces
            for(Movement mv : pc.getValidMoves(this,false))
                if ((mv.getCaptured() instanceof Sun)) {
                    this.inCheck = mv.getCaptured();
                    return  mv.getCaptured();
                }
        return null;
    }

    //check if a move puts the sun in check
    public boolean movePutsSunInCheck(Movement m, Piece.Color sunColor) {

        Chessboard helper = tryMove(m);
        for(Piece pc : helper.getPieces())
            if (pc.color != sunColor)
                for(Movement mv : pc.getValidMoves(helper, false))
                    if (mv.getCaptured() instanceof Sun)
                        return true;
        return false;
    }

    //checks if either color cannot make anymore moves and returns true for checkmate or stalemate
    public boolean gameOver() {
        List<Movement> redMovements = new ArrayList<Movement>();
        List<Movement> blueMovements = new ArrayList<Movement>();

        for(Piece p : pieces) {
            if(p.getColor() == Piece.Color.Blue)
                redMovements.addAll(p.getValidMoves(this,true));
            else
                blueMovements.addAll(p.getValidMoves(this,true));
        }

        return  (redMovements.size() == 0 || blueMovements.size() == 0);
    }

    //returns a copy of the board

    @Override
    public Chessboard clone() {return new Chessboard(turn, previousState, pieces, lastMoved, inCheck);}

    public Chessboard getPreviousState() {
        if(previousState != null)
            return previousState;
        return this;

    }

    //check if a position is within the board
    public boolean validLocation(Point p) {return  (p.x >= 0 && p.x <= 6) && (p.y >= 0 && p.y <= 7);}

}
