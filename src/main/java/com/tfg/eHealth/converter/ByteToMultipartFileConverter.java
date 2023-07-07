package com.tfg.eHealth.converter;

import com.tfg.eHealth.vo.Archivo;
import org.apache.tika.Tika;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Component
public class ByteToMultipartFileConverter {

    private final Tika TIKA = new Tika();

    public MultipartFile convert(Archivo archivo, String nombreArchivo) throws IOException {
        byte[] bytes = archivo.getArchivo();
        String tipoContenido = TIKA.detect(bytes);
        String extension = obtenerExtension(nombreArchivo);

        if (esTipoImagen(tipoContenido, extension)) {
            return new MockMultipartFile(nombreArchivo, nombreArchivo, tipoContenido, bytes);
        } else if (esTipoVideo(tipoContenido, extension)) {
            Path tempFile = Files.createTempFile("temp-video", extension);
            Files.copy(new ByteArrayInputStream(bytes), tempFile, StandardCopyOption.REPLACE_EXISTING);

            return new MockMultipartFile(nombreArchivo, nombreArchivo, tipoContenido, Files.newInputStream(tempFile));
        }

        throw new IllegalArgumentException("El tipo de archivo no es válido: " + tipoContenido);
    }

    private String obtenerExtension(String nombreArchivo) {
        int indicePunto = nombreArchivo.lastIndexOf(".");
        if (indicePunto != -1) {
            return nombreArchivo.substring(indicePunto);
        }
        return "";
    }

    private boolean esTipoImagen(String tipoContenido, String extension) {
        return tipoContenido.startsWith("image/") || extension.equalsIgnoreCase(".jpg") ||
                extension.equalsIgnoreCase(".jpeg") || extension.equalsIgnoreCase(".png") ||
                extension.equalsIgnoreCase(".gif");
    }

    private boolean esTipoVideo(String tipoContenido, String extension) {
        return tipoContenido.startsWith("video/") || extension.equalsIgnoreCase(".mp4") ||
                extension.equalsIgnoreCase(".avi") || extension.equalsIgnoreCase(".mov") ||
                extension.equalsIgnoreCase(".mkv");
    }
}
