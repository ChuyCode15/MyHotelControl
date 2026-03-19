package com.myhotelcontrol.domain.habitaciones;

import com.myhotelcontrol.enums.TamanoCama;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Embeddable
@Data
public class DetalleCamas {

    @Enumerated(EnumType.STRING)
    private TamanoCama tamanoCama;

    private Integer cantidad;

    public DetalleCamas() {
    }

    public DetalleCamas(TamanoCama tamanoCama, Integer cantidad) {
        this.tamanoCama = tamanoCama;
        this.cantidad = cantidad;

    }

}
