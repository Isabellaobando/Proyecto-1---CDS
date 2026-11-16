# Club Deportivo Séneca - versión completada

## Requisitos
- JDK 21
- Eclipse IDE con integración Maven (m2e)
- JUnit Jupiter 5.10.2 (resuelto por Maven)

## Cambio de validación de credenciales
El método `Usuario.validarCredenciales(login, password)` compara el contenido de los textos con `equals()` y rechaza valores de entrada nulos. Esto evita el error de comparar cadenas con `==`. Se agregó una prueba para credenciales con cadenas distintas en memoria pero con el mismo contenido.

## Importar en Eclipse
1. Descomprimir el proyecto.
2. En Eclipse: File > Import > Existing Maven Projects.
3. Seleccionar la carpeta `ClubSenecaFinal_Actualizado` y finalizar.
4. Ejecutar `consola.ConsolaClubSeneca`.

## Menú principal
La consola incluye gestión de empleados, jugadores, equipos, deportes individuales, cafetería, torneos, instalaciones y entrenamientos; consultas; reportes; guardar y cargar.

## Persistencia
Las opciones 11 y 12 guardan/cargan el objeto completo `ClubSeneca` con sus relaciones mediante serialización. El archivo predeterminado es `datos/club-seneca.dat` relativo al directorio de ejecución. La clase `persistencia.PersistenciaClubSeneca` también permite indicar otra ruta.

Las clases del modelo que forman parte del estado del club implementan `Serializable`.

## Pruebas
Las pruebas están en `test/`, organizadas por paquete. Cada tipo Java de `src/` (excepto `module-info.java`) tiene una clase de prueba homónima terminada en `Test`. Se añadieron pruebas de comportamiento para los componentes nuevos y pruebas específicas de persistencia y consola.

Para ejecutarlas: `mvn test` o, en Eclipse, Run As > JUnit Test sobre la carpeta `test`.

La compilación de producción con `javac --release 21` fue verificada. La suite JUnit debe ejecutarse en el equipo con Maven/JUnit disponibles.

## Inicio de sesión por roles

Al iniciar la aplicación, el sistema carga automáticamente los datos guardados en `datos/club-seneca.dat` cuando ese archivo existe. Si todavía no hay una cuenta de administrador, solicita crear la cuenta del administrador inicial.

Después, solicita usuario y contraseña y muestra el menú correspondiente al tipo de cuenta:
- Administrador: gestión general del club y reportes.
- Entrenador: entrenamientos, consulta de información y cafetería.
- Fisioterapeuta: consulta de jugadores, información del club y cafetería.
- Jugador: consulta de información y sesiones, y cafetería.
- Empleado general: consulta de información y cafetería.

La opción **Cerrar sesión** vuelve a la pantalla de inicio de sesión. En el campo de usuario se puede escribir `SALIR` para terminar la aplicación.

Los nuevos empleados se registran seleccionando su tipo (empleado general, administrador, entrenador o fisioterapeuta). Los jugadores conservan el acceso con el usuario y contraseña capturados al registrarlos.
