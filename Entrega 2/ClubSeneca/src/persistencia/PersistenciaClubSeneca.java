package persistencia;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import logica.ClubSeneca;

/** Guarda y recupera el estado completo del club y sus relaciones. */
public final class PersistenciaClubSeneca {
    private PersistenciaClubSeneca() { }

    public static void guardar(ClubSeneca club, Path archivo) throws IOException {
        Objects.requireNonNull(club, "El club no puede ser null");
        Objects.requireNonNull(archivo, "La ruta no puede ser null");
        Path absoluto = archivo.toAbsolutePath();
        if (absoluto.getParent() != null) Files.createDirectories(absoluto.getParent());
        try (ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream(absoluto.toFile()))) {
            salida.writeObject(club);
        }
    }

    public static ClubSeneca cargar(Path archivo) throws IOException, ClassNotFoundException {
        Objects.requireNonNull(archivo, "La ruta no puede ser null");
        try (ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(archivo.toFile()))) {
            Object objeto = entrada.readObject();
            if (!(objeto instanceof ClubSeneca club)) throw new IOException("El archivo no contiene un ClubSeneca válido");
            return club;
        }
    }
}
