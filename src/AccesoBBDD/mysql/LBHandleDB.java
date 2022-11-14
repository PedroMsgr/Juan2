package AccesoBBDD.mysql;

import AccesoBBDD.exception.LBException;
import AccesoBBDD.model.LeaderBoard;
import AccesoBBDD.util.DatabaseConnection;

import javax.swing.*;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

public class LBHandleDB {
    private static Connection con;
    private static Properties lb;
    private static Scanner sc = new Scanner(System.in);
    private boolean tablaActualizada;
    public boolean getTablaActualizada(){return tablaActualizada;}
    public LBHandleDB() throws LBException {
        con = DatabaseConnection.getConnection();
        lb = DatabaseConnection.getLB();
        tablaActualizada = Boolean.getBoolean(lb.getProperty("tablaActualizada"));
        int intentosInicio = 3;
        while (intentosInicio > 0) {
            System.out.println("-----Iniciar Sesión en el programa-----");
            System.out.print("\t Introduzca su Usuario: ");
            String username = sc.nextLine();
            System.out.print("\t Introduzca su Contraseña: ");
            String password = sc.nextLine();
            String query = "select * from logininlb where username = \""+username+"\" and passwd = \""+password+'"';
            try(Statement statement = con.createStatement()) {
                ResultSet comprobar = statement.executeQuery(query);
                if(comprobar.next())
                    return;
                else
                    throw new SQLException();
            }catch (SQLException se){
                intentosInicio--;
                if(intentosInicio == 1){
                    System.out.println("Le queda 1 intento para Iniciar Sesión.");
                    System.out.println("ADVERTENCIA: Este es su último intento, si vuelve a fallar todos los datos de la tabla serán eliminados.");
                }else
                    System.out.println("Le quedan "+intentosInicio+" intentos para Iniciar Sesión.");
            }
        }
        clearAll();
        throw new LBException(0, "");
    }

    public void clearAll() throws LBException{
        String query = "delete from leaderboard";
        try(Statement statement = con.createStatement()){
            statement.execute(query);
        }catch (SQLException se){
            throw new LBException(14, se.getMessage());
        }
    }

    public void closeConnection() throws LBException {
        try {
            con.close();
            lb.setProperty("tablaActualizada", String.valueOf(tablaActualizada));
            lb.store(new FileWriter(".\\LB.properties"), "Last Modified:");
        }catch (SQLException se){
            throw new LBException(4, se.getMessage());
        }catch (IOException e){
            throw new LBException(2, e.getMessage());
        }
    }

    public void updateLeaderBoard() throws LBException{
        System.out.println("Actulizando ranking...");
        List<LeaderBoard> allPlayers = new ArrayList<>();
        String query = "select * from leaderboard order by posInBoard";
        try(Statement statement = con.createStatement()){
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                LeaderBoard lb = new LeaderBoard(
                        resultSet.getInt(1),
                        resultSet.getString("playerName"),
                        resultSet.getInt("linesCalled"),
                        resultSet.getInt("bingosCalled")
                );
                allPlayers.add(lb);
            }
            clearAll();
            for(int pos = 0;pos < allPlayers.size();pos++){
                for(int pos2 = pos+1;pos2 < allPlayers.size();pos2++){
                    LeaderBoard lb = allPlayers.get(pos);
                    LeaderBoard lb2 = allPlayers.get(pos2);
                    int puntajelb = (lb.getLinesCalled()*35) + (lb.getBingosCalled()*100);
                    int puntajelb2 = (lb2.getLinesCalled()*35) + (lb2.getBingosCalled()*100);
                    if(puntajelb < puntajelb2) {
                        int save = lb.getPosInBoard();
                        allPlayers.get(pos).setPosInBoard(lb2.getPosInBoard());
                        allPlayers.get(pos2).setPosInBoard(save);
                        allPlayers.remove(pos2);
                        allPlayers.remove(pos);
                        allPlayers.add(pos, lb2);
                        allPlayers.add(pos2, lb);
                    }
                }
            }
            for(LeaderBoard lb:allPlayers){
                addPlayerBackGround(lb);
            }
            /*resultSet = statement.executeQuery(query);
            allPlayers = new ArrayList<>();
            while(resultSet.next()) {
                LeaderBoard lb = new LeaderBoard(
                        resultSet.getInt(1),
                        resultSet.getString("playerName"),
                        resultSet.getInt("linesCalled"),
                        resultSet.getInt("bingosCalled")
                );
                allPlayers.add(lb);
            }
            clearAll();
            for(LeaderBoard lb:allPlayers){
                addPlayerBackGround(lb);
            }*/
        }catch (SQLException se){
            throw new LBException(16, se.getMessage());
        }
        System.out.println("Ranking Actualizado!");
        tablaActualizada = true;
    }

    public void getPodium() throws LBException{
        String query = "select playerName from leaderboard order by posInBoard limit 3";
        String primero;
        String segundo;
        String tercero;
        try(Statement statement = con.createStatement()){
            ResultSet resultSet = statement.executeQuery(query);
            if(resultSet.next()){
                primero = resultSet.getString("playerName");
                while(primero.length() < 10)
                    primero = primero+" ";
                resultSet.next();
                segundo = resultSet.getString("playerName");
                while(segundo.length() < 10)
                    segundo = segundo+" ";
                resultSet.next();
                tercero = resultSet.getString("playerName");
                while(tercero.length() < 10)
                    tercero = tercero+" ";
                System.out.println(
                    "             "+primero+
                    "\n            |¯¯¯¯¯¯¯¯¯¯¯|"+
                    "\n            |  🥇1st🥇\t|"+
                    "\n "+segundo+" |           |"+
                    "\n|¯¯¯¯¯¯¯¯¯¯¯¯           |"+
                    "\n|  🥈2nd🥈\t            |"+
                    "\n|                       | "+tercero+
                    "\n|                       ¯¯¯¯¯¯¯¯¯¯¯¯|"+
                    "\n|                         🥉3rd🥉\t|"+
                    "\n|                                   |"+
                    "\n|                                   |"+
                    "\n|___________________________________|");
            }else
                throw new LBException(10, "");
        }catch (SQLException se){
            throw new LBException(10, se.getMessage());
        }
    }

    public void addPlayer(LeaderBoard lb) throws LBException{
        String maxPlayers = "select count(playerName) from leaderboard";
        try (Statement statement = con.createStatement()){
            ResultSet resultSet = statement.executeQuery(maxPlayers);
            resultSet.next();
            if(resultSet.getInt(1) > 100)
                throw new LBException(15, "");
            String query = "insert into leaderboard values("+(resultSet.getInt(1)+1)+", \""+lb.getPlayerName()+"\"," +
                    ""+lb.getLinesCalled()+", "+lb.getBingosCalled()+")";
            statement.execute(query);
            System.out.println("Nuevo Jugador creado con éxito.");
            System.out.println("Desea actualizar las posiciones en la tabla? (S/N)");
            switch(sc.nextLine()){
                case "S", "s" -> updateLeaderBoard();
                case "N", "n" -> tablaActualizada = false;
                default -> throw new LBException(5, "");
            }
        }catch (SQLException se){
            throw new LBException(6, se.getMessage());
        }
    }

    public void addPlayerBackGround(LeaderBoard lb) throws LBException{
        String query = "insert into leaderboard values("+lb.getPosInBoard()+", \""+lb.getPlayerName()+"\"," +
                ""+lb.getLinesCalled()+", "+lb.getBingosCalled()+")";
        try (Statement statement = con.createStatement()){
            statement.execute(query);
        }catch (SQLException se){
            throw new LBException(6, se.getMessage());
        }
    }

    public void updatePlayer(LeaderBoard lb) throws LBException{
        String query = "update leaderboard set linesCalled = "+lb.getLinesCalled()+
                ", bingosCalled = "+lb.getBingosCalled()+" where playerName = \""+lb.getPlayerName()+'"';
        String check = "select * from leaderboard where playerName = \""+lb.getPlayerName()+'"';
        try(Statement statement = con.createStatement()) {
            ResultSet comprobar = statement.executeQuery(check);
            if(comprobar.next()){
                statement.execute(query);
                System.out.println("Información del jugador actualizada con éxito.");
                System.out.println("Desea actualizar las posiciones en la tabla? (S/N)");
                switch(sc.nextLine()) {
                    case "S", "s" -> updateLeaderBoard();
                    case "N", "n" -> tablaActualizada = false;
                    default -> throw new LBException(5, "");
                }
            }else
                throw new LBException(7, "");
        }catch (SQLException se){
            throw new LBException(7,se.getMessage());
        }
    }

    public void deletePlayer(String playerName) throws LBException{
        String query = "delete from leaderboard where playerName = \""+playerName+'"';
        String check = "select * from leaderboard where playerName = \""+playerName+'"';
        try(Statement statement = con.createStatement()) {
            ResultSet comprobar = statement.executeQuery(check);
            if(comprobar.next()){
                statement.execute(query);
                System.out.println("Jugador eliminado con éxito.");
                System.out.println("Desea actualizar las posiciones en la tabla? (S/N)");
                switch(sc.nextLine()) {
                    case "S", "s" -> updateLeaderBoard();
                    case "N", "n" -> tablaActualizada = false;
                    default -> throw new LBException(5, "");
                }
            }else
                throw new LBException(8, "");
        }catch (SQLException se){
            throw new LBException(8, se.getMessage());
        }
    }

    public void getPlayer(String playerName) throws LBException{
        String query = "select * from leaderboard where playerName = \""+playerName+'"';
        try (Statement statement = con.createStatement()){
            ResultSet resultSet = statement.executeQuery(query);
            if(!resultSet.next())
                throw new LBException(9, "");
            LeaderBoard leaderBoard = new LeaderBoard();
            leaderBoard.setPosInBoard(resultSet.getInt(1));
            leaderBoard.setPlayerName(resultSet.getString("playerName"));
            leaderBoard.setLinesCalled(resultSet.getInt("linesCalled"));
            leaderBoard.setBingosCalled(resultSet.getInt("bingosCalled"));
            System.out.println(leaderBoard);
        }catch (SQLException se){
            throw new LBException(9,se.getMessage());
        }
    }

    public void getPlayers() throws LBException {
        String query = "select * from leaderboard order by posInBoard";
        try (Statement statement = con.createStatement()){
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                LeaderBoard leaderBoard = new LeaderBoard();
                leaderBoard.setPosInBoard(resultSet.getInt(1));
                leaderBoard.setPlayerName(resultSet.getString("playerName"));
                leaderBoard.setLinesCalled(resultSet.getInt("linesCalled"));
                leaderBoard.setBingosCalled(resultSet.getInt("bingosCalled"));
                System.out.println(leaderBoard);
            }
        }catch (SQLException se){
            throw new LBException(10, se.getMessage());
        }
    }
}

