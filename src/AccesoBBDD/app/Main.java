package AccesoBBDD.app;

import AccesoBBDD.exception.LBException;
import AccesoBBDD.model.LeaderBoard;
import AccesoBBDD.mysql.LBHandleDB;

import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        LBHandleDB handler = null;
        try{
           handler = new LBHandleDB();
        }catch(LBException lb){
            System.out.println(lb.getMessage());
            System.exit(0);
        }
        while (true) {
            try {
                LeaderBoard lb = new LeaderBoard();
                System.out.println(
                        "\n-----Menú de la Bingo LeaderBoard-----" +
                                "\n\t1. Añadir un nuevo jugador a la tabla" +
                                "\n\t2. Modificar información de un jugador" +
                                "\n\t3. Eliminar a un jugador de la tabla" +
                                "\n\t4. Obtener información de un jugador" +
                                "\n\t5. Obtener información de todos los jugadores" +
                                "\n\t6. Actualizar posiciones en la tabla" +
                                "\n\t7. Mostrar pódium de mejores jugadores" +
                                "\n\t8. Salir del programa"
                );
                System.out.println("");
                System.out.print("Escoja una opción: ");
                if (!sc.hasNextInt())
                    throw new LBException(11, "");
                switch (sc.nextInt()){
                    case 1:
                        System.out.println("");
                        System.out.print("Escriba el nombre del nuevo Jugador (máximo 10 caracteres): ");
                        sc.nextLine();
                        lb.setPlayerName(sc.nextLine());
                        if(lb.getPlayerName().length() > 10)
                            throw new LBException(17, "");
                        System.out.print("Escriba el número de Líneas Cantadas por el Jugador: ");
                        if(!sc.hasNextInt())
                            throw new LBException(12, "");
                        lb.setLinesCalled(sc.nextInt());
                        System.out.print("Escriba el número de Bingos Cantados por el Jugador: ");
                        if(!sc.hasNextInt())
                            throw new LBException(12, "");
                        lb.setBingosCalled(sc.nextInt());
                        handler.addPlayer(lb);
                        break;
                    case 2:
                        System.out.println("");
                        System.out.print("Escriba el nombre del Jugador cuyo registro desea modificar: ");
                        sc.nextLine();
                        lb.setPlayerName(sc.nextLine());
                        System.out.print("Escriba el nuevo número de Lineas Cantadas por el Jugador: ");
                        if(!sc.hasNextInt())
                            throw new LBException(12, "");
                        lb.setLinesCalled(sc.nextInt());
                        System.out.print("Escriba el nuevo número de Bingos Cantados por el Jugador: ");
                        if(!sc.hasNextInt())
                            throw new LBException(12, "");
                        lb.setBingosCalled(sc.nextInt());
                        handler.updatePlayer(lb);
                        break;
                    case 3:
                        System.out.println("");
                        System.out.print("Escriba el nombre del Jugador que desea eliminar: ");
                        sc.nextLine();
                        handler.deletePlayer(sc.nextLine());
                        break;
                    case 4:
                        System.out.println("");
                        sc.nextLine();
                        if(!handler.getTablaActualizada()) {
                            System.out.println("Se ha detectado que las posiciones en el ranking no están actualizadas.");
                            System.out.println("La información obtenida por esta operación podría no resultar verídica.");
                            System.out.println("Está seguro de continuar? (S/N)");
                            switch(sc.nextLine()){
                                case "S", "s" -> {
                                    System.out.print("Escriba el nombre del Jugador cuyo registro desea obtener: ");
                                    handler.getPlayer(sc.nextLine());}
                                case "N", "n" -> System.out.println("Operación cancelada");
                                default -> throw new LBException(5, "");
                            }
                        }else {
                            System.out.print("Escriba el nombre del Jugador cuyo registro desea obtener: ");
                            handler.getPlayer(sc.nextLine());
                        }
                        break;
                    case 5:
                        if(!handler.getTablaActualizada()) {
                            System.out.println("Se ha detectado que las posiciones en el ranking no están actualizadas.");
                            System.out.println("La información obtenida por esta operación podría no resultar verídica.");
                            System.out.println("Está seguro de continuar? (S/N)");
                            sc.nextLine();
                            switch(sc.nextLine()){
                                case "S", "s" -> handler.getPlayers();
                                case "N", "n" -> System.out.println("Operación cancelada");
                                default -> throw new LBException(5, "");
                            }
                        }else
                            handler.getPlayers();
                        break;
                    case 6:
                        handler.updateLeaderBoard();
                        break;
                    case 7:
                        if(!handler.getTablaActualizada()) {
                            System.out.println("Se ha detectado que las posiciones en el ranking no están actualizadas.");
                            System.out.println("La información obtenida por esta operación podría no resultar verídica.");
                            System.out.println("Está seguro de continuar? (S/N)");
                            sc.nextLine();
                            switch(sc.nextLine()){
                                case "S", "s" -> handler.getPodium();
                                case "N", "n" -> System.out.println("Operación cancelada");
                                default -> throw new LBException(5, "");
                            }
                        }else
                            handler.getPodium();
                        break;
                    case 8:
                        handler.closeConnection();
                        System.exit(0);
                    default:
                        throw new LBException(11, "");
                }
            } catch (LBException lb) {
                System.out.println(lb.getMessage());
                if(!lb.getComplexMessage().isEmpty()) {
                    System.out.println("Desea obtener el mensaje detallado del fallo? (S/N)");
                    try {
                        sc.nextLine();
                        switch(sc.nextLine()){
                            case "S", "s" -> System.out.println(lb.getComplexMessage());
                            case "N", "n" -> System.out.print("");
                            default -> throw new LBException(5, "");
                        }
                    } catch (LBException lb2) {
                        System.out.println(lb2.getMessage());
                    }
                }
                if(lb.getErrorCode() < 5)
                    System.exit(0);
            }
        }
    }
}
