package com.tfg.eHealth.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@ToString
public class ArchivoOutDto {

    private String nombreArchivo;
    private String tipoContenido;
    private byte[] bytes;
}
