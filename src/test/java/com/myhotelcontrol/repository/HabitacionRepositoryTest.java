package com.myhotelcontrol.repository;

import com.myhotelcontrol.domain.habitaciones.DetalleCamas;
import com.myhotelcontrol.domain.habitaciones.Habitacion;
import com.myhotelcontrol.enums.TamanoCama;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@ActiveProfiles("test")
class HabitacionRepositoryTest {

    @Autowired
    private HabitacionRepository habitacionRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    @DisplayName("Debería persistir una habitación con sus camas correctamente")
    void persistirHabitacionConCamas(){

        var habitacion = new Habitacion();
        habitacion.setNombre("Habitacion");
        habitacion.setNumero(202);
        habitacion.setPrecio(new BigDecimal("2500.00"));

        // agregar una cama
        var cama = new DetalleCamas(TamanoCama.KING_SIZE, 1);
        habitacion.getConfiguracionCamas().add(cama);

        var guardada = habitacionRepository.save(habitacion);

        assertNotNull(guardada.getId());
        assertEquals(1,guardada.getConfiguracionCamas().size());
        assertEquals(TamanoCama.KING_SIZE, guardada.getConfiguracionCamas().get(0).getTamanoCama());
    }
}