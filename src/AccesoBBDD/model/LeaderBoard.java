package AccesoBBDD.model;

public class LeaderBoard {
    private int posInBoard;
    private String playerName;
    private int linesCalled;
    private int bingosCalled;

    public LeaderBoard(){

    }

    public LeaderBoard(int posInBoard, String playerName, int linesCalled, int bingosCalled) {
        this.posInBoard = posInBoard;
        this.playerName = playerName;
        this.linesCalled = linesCalled;
        this.bingosCalled = bingosCalled;
    }

    @Override
    public String toString() {
        return "\nNombre de Jugador: "+playerName
                +"\n\tPosición en el ranking: "+getRanking(posInBoard)
                +"\n\tLíneas Cantadas: "+linesCalled
                +"\n\tBingos Cantados: "+bingosCalled;
    }

    public static String getRanking(int posInBoard){
        int pos;
        if(posInBoard > 20)
            pos = posInBoard%10;
        else
            pos = posInBoard;
        String rank;
        switch (pos) {
            case 1:
                rank = posInBoard + "st";
                break;
            case 2:
                rank = posInBoard + "nd";
                break;
            case 3:
                rank = posInBoard + "rd";
                break;
            default:
                rank = posInBoard + "th";
        }
        return rank;
    }


    public void setPosInBoard(int posInBoard) {
        this.posInBoard = posInBoard;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public void setLinesCalled(int linesCalled) {
        this.linesCalled = linesCalled;
    }

    public void setBingosCalled(int bingosCalled) {
        this.bingosCalled = bingosCalled;
    }

    public int getPosInBoard() {
        return posInBoard;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getLinesCalled() {
        return linesCalled;
    }

    public int getBingosCalled() {
        return bingosCalled;
    }
}
