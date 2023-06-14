package com.tfg.eHealth.vo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Embeddable
@Getter
@Setter
public class Archivo {

    @Lob
    private byte[] archivo;
}
