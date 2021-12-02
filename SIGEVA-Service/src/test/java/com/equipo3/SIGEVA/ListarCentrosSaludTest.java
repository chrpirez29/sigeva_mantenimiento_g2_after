package com.equipo3.SIGEVA;

import com.equipo3.SIGEVA.controller.CentroController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class ListarCentrosSaludTest {

    @Autowired
    private CentroController centroController;

    @Test
    void getTodosCentros(){
        assertNotNull(centroController.listarCentros());
    }
}
