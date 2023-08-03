package com.api.rest.app.repository;

import com.api.rest.app.model.Empleado;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.sql.SQLOutput;
import java.util.List;
import java.util.Optional;


//Solo permite hacer test a las entidades y repositorios
@DataJpaTest
public class EmpleadoRepositoryTests {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    private Empleado empleado;

    @BeforeEach
    public void setup(){
        empleado = Empleado.builder()
                .nombre("Cristian")
                .apellido("Ramirez")
                .email("c1@gmail.com")
                .build();
    }


    @DisplayName("Test para guardar un empleado")
    @Test
    public void testGuardarEmpleado(){
        //Given -- Utilizamos lombock para construir un objeto (builder)
        Empleado empleado1 = Empleado.builder()
                .nombre("Pepe")
                .apellido("Lopez")
                .email("p12@gmail.com")
                .build();

        //When
        Empleado empleadoGuardado = empleadoRepository.save(empleado1);

        //Then
        Assertions.assertThat(empleadoGuardado).isNotNull();
        Assertions.assertThat(empleadoGuardado.getId()).isGreaterThan(0);
    }

    @DisplayName("Test para listar a los empleados")
    @Test
    public void testlistarEmpleados(){
        //Given
        Empleado empleado1 = Empleado.builder()
                .nombre("Julen")
                .apellido("Oliva")
                .email("j2@gmail.com")
                .build();

        empleadoRepository.save(empleado1);
        empleadoRepository.save(empleado);

        //When
        List<Empleado> listaEmpleados = empleadoRepository.findAll();

        //Then
        Assertions.assertThat(listaEmpleados.size()).isNotNull();
        Assertions.assertThat(listaEmpleados.size()).isEqualTo(2);
    }

    @DisplayName("Test para obtener un empleado por ID")
    @Test
    public void testObtenerEmpleadoPorId(){
        //Given
        empleadoRepository.save(empleado);

        //When
        Empleado empleadoDB = empleadoRepository.findById(empleado.getId()).get();

        //Then
        Assertions.assertThat(empleadoDB).isNotNull();
    }

    @DisplayName("Test para actualizar un empleado")
    @Test
    public void testActualizarEmpleado() {
        //Given
        empleadoRepository.save(empleado);

        //When
        Empleado empleadoGuardado = empleadoRepository.findById(empleado.getId()).get();
        empleadoGuardado.setEmail("c234@gmail.com");
        empleadoGuardado.setNombre("Cristian Raul");
        empleadoGuardado.setApellido("Ramirez Perez");
        Empleado empleadoActualizado = empleadoRepository.save(empleadoGuardado);

        //Then
        Assertions.assertThat(empleadoActualizado.getEmail()).isEqualTo("c234@gmail.com");
        Assertions.assertThat(empleadoActualizado.getNombre()).isEqualTo("Cristian Raul");
        Assertions.assertThat(empleadoActualizado.getApellido()).isEqualTo("Ramirez Perez");
    }

    @DisplayName("Test para eliminar a un empleado")
    @Test
    public void testEliminarEmpleado(){
        //Given
        empleadoRepository.save(empleado);

        //When
        empleadoRepository.deleteById(empleado.getId());
        // Optional retorna un true o false
        Optional<Empleado> empleadoOptional = empleadoRepository.findById(empleado.getId());

        //Then
        Assertions.assertThat(empleadoOptional).isEmpty();
    }
}
