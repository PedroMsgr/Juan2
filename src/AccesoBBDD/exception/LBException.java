package AccesoBBDD.exception;

public class LBException extends Exception{
    private int errorCode;
    private String basicMessage;
    private String complexMessage;

    public LBException(int errorCode, String complexMessage) {
        this.errorCode = errorCode;
        switch (errorCode) {
            case 0 -> this.basicMessage = "FATAL_ERROR: Intentos de inicio de sesión al programa agotados. Se han borrado todos los registros de la tabla.";
            case 1 -> this.basicMessage = "FATAL_ERROR: No ha sido posible conectar con la Base de Datos";
            case 2 -> this.basicMessage = "FATAL_ERROR: No se ha podido cargar el archivo \"LB.properties\".";
            case 3 -> this.basicMessage = "FATAL_ERROR: No se ha encontrado el archivo \"LB.properties\".";
            case 4 -> this.basicMessage = "FATAL_ERROR: No se ha podido cerrar la conexión con la Base de Datos.";
            case 5 -> this.basicMessage = "Error, la opción introducida no es válida. Se considerará que respondió con \"N\"";
            case 6 -> this.basicMessage = "Error, no se ha podido crear un nuevo jugador. Posible causa: El nombre del Jugador ya existe en la tabla.";
            case 7 -> this.basicMessage = "Error, no se ha podido actualizar la información del jugador. Posible causa: El nombre del Jugador no existe en la tabla.";
            case 8 -> this.basicMessage = "Error, no se ha podido eliminar al jugador. Posible causa: El nombre del Jugador no existe en la tabla.";
            case 9 -> this.basicMessage = "Error, no se ha podido obtener la información del jugador. Posible causa: El nombre del Jugador no existe en la tabla.";
            case 10 -> this.basicMessage = "Error, no se han podido obtener los registros solicitados de la tabla.";
            case 11 -> this.basicMessage = "Error, la opción introducida no es válida. Por favor, vuelva a intentarlo.";
            case 12 -> this.basicMessage = "Error, la información introducida no es válida. La operación se ha cancelado.";
            case 13 -> this.basicMessage = "La operación se ha cancelado.";
            case 14 -> this.basicMessage = "Error, no se han podido eliminar los registros de la tabla.";
            case 15 -> this.basicMessage = "Error, la tabla de jugadores está llena. Modifique o elimine a algún jugador ya existente.";
            case 16 -> this.basicMessage = "Error, no se han podido actualizar las posiciones del ranking.";
            case 17 -> this.basicMessage = "Error, nombre de jugador muy largo.";
        }
        this.complexMessage = complexMessage;
    }

    @Override
    public String getMessage() {
        return basicMessage;
    }

    public String getComplexMessage() {
        return complexMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }
}

