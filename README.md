<pre>
TEMÁTICA DEL PROGRAMA: Tabla clasificatoria de puntuaciones del bingo

ANTES DE USAR EL PROGRAMA:
    1. Crear la base de datos con el archivo "CrearBBDD.sql" para que el
programa funcione correctamente.
    2. Revisar las propiedades del archivo porperties para asegurar que
la conexión a la BBDD se realiza correctamente.
    3. No cambiar de sitio ningún archivo. De hacerlo puede que el
programa no funcione como debería.

INSTRUCCIONES DE USO:
#NOTA: Existen 2 tipos de errores que saltan en el programa:
    - FATAL_ERROR: Este error afecta al funcionamiento del programa y
por lo tanto provoca la salida de este.
    - Error: Este error solo afecta a la operación realizada en ese
momento por lo cuál no fuerza la salida del programa.

Al arrancar el programa, si todo ha ido bien, deberá salirte un mensaje
diciendo "Conexión Correcta". Después del susodicho mensaje, saldrá otro
pidiendote que inicies sesión. Las credenciales necesarias se encuentran
en la tabla "loginInLB" creada a partir del archivo "CrearBBDD.sql".
También encontrarás las credenciales por defecto en el archivo
"LB.properties" como un comentario al final del fichero.

ADVERTENCIA: Si fallas en el logueo 3 veces seguidas el programa borrará
el registro de la tabla principal y se cerrará.

Si nos hemos logueado correctamente, deberá salirnos un menú con 8 
opciones:
    1. Añadir un nuevo jugador a la tabla: Con esta opción podremos
crear un nuevo jugador y añadirlo a la tabla mientras el nombre no sea
idéntico al de un jugador ya existente o no existan 100 jugadores en la
tabla.
    2. Modificar información de un jugador: Con esta opción podremos
modificar la puntuación de un jugador cuyo nombre exista en la tabla.
    3. Eliminar a un jugador de la tabla: Con esta opción podremos eliminar
el registro de un jugador cuyo nombre exista en la tabla.

#NOTA: Te habrás fijado que al ejecutar cualquiera de las 3 opciones
antes mencionadas sale una opción que nos consulta si queremos
actualizar el ranking. Aunque respondamos que no, luego desde el menú
principal tenemos una opción para actualizar el ranking.

    4. Obtener información de un jugador: Usando el nombre de un
Jugador ya existente en la tabla podremos sacar su información. En el
caso de no haber actualizado el ranking de posiciones cuando se pidió,
nos llegará una advertencia sobre la vericidad de la información que 
obtendremos.
    5. Obtener información de todos los jugadores: Usando esta
opción, obtendremos la información de todos los jugadores de la tabla.
En el caso de no haber actualizado el ranking de posiciones cuando se
pidió, nos llegará una advertencia sobre la vericidad de la información
que obtendremos.
    6. Actualizar posiciones en la tabla: Con esta opción podremos
actualizar las posiciones del ranking en la tabla para reclasificar a
los jugadores en caso de haber hecho alguna modificación.
    7. Mostrar pódium de mejores jugadores: Con esta opción se
mostrará por pantalla un podio con los 3 primeros puestos ocupados por
los 3 mejores jugadores de la tabla. En el caso de no haber actualizado
el ranking de posiciones cuando se pidió, nos llegará una advertencia
sobre la vericidad de la información queobtendremos.
    8. Salir del programa: Con esta opción cerraremos el programa
cerrando también la conexión y guardando un registro de la última vez
que se usó el programa en el archivo "LB.properties".

Y eso sería todo, disfruta del programa!
</pre>