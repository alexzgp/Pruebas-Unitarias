package com.api.rest.app.services;


import com.api.rest.app.exception.ResourceNotFoundException;
import com.api.rest.app.model.Empleado;
import com.api.rest.app.repository.EmpleadoRepository;
import com.api.rest.app.services.impl.EmpleadoServiceImpl;
import org.assertj.core.api.Assertions;


import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.BitSet;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class EmpleadoServiceTests {

    // Creamos un simulacro con Mock(Dependencia)
    @Mock
    private EmpleadoRepository empleadoRepository;

    // Injectamos la dependencia en el servicio
    @InjectMocks
    private EmpleadoServiceImpl empleadoService;

    private Empleado empleado;

    @BeforeEach
    public void setup(){
        empleado = Empleado.builder()
                .id(1L)
                .nombre("Cristian")
                .apellido("Ramirez")
                .email("c1@gmail.com")
                .build();
    }

    @DisplayName("Test para guardar un empleado")
    @Test
    public void testGuardarEmpleado(){
        //Given
        BDDMockito.given(empleadoRepository.findByEmail(empleado.getEmail()))
                .willReturn(Optional.empty());
        BDDMockito.given(empleadoRepository.save(empleado)).willReturn(empleado);

        //When
        Empleado empleadoGuardado = empleadoService.saveEmpleado(empleado);

        //Then
        Assertions.assertThat(empleadoGuardado).isNotNull();
    }


    @DisplayName("Test para guardar un empleado con Throw Exeption")
    @Test
    public void testGuardarEmpleadoConThrowException(){
        //Given
        BDDMockito.given(empleadoRepository.findByEmail(empleado.getEmail()))
                .willReturn(Optional.of(empleado));

        //When
        assertThrows(ResourceNotFoundException.class, () -> {
            empleadoService.saveEmpleado(empleado);
        });

        //Then
        Mockito.verify(empleadoRepository, Mockito.never()).save(any(Empleado.class));
    }

    @DisplayName("Test para listar a los empleados")
    @Test
    public void testListarEmpleados(){
        //Given
        Empleado empleado1 = Empleado.builder()
                .id(1L)
                .nombre("Julen")
                .apellido("Oliva")
                .email("j2@gmail.com")
                .build();
        BDDMockito.given(empleadoRepository.findAll()).willReturn(List.of(empleado,empleado1));

        //When
        List<Empleado> empleados = empleadoService.getAllEmpleados();

        //Then
        Assertions.assertThat(empleados).isNotNull();
        Assertions.assertThat(empleados.size()).isEqualTo(2);
    }

    @DisplayName("Test retornar lista vacia")
    @Test
    public void testListarColeccionEmpleadosVacia(){
        //Given
        Empleado empleado1 = Empleado.builder()
                .id(2L)
                .nombre("Julen")
                .apellido("Oliva")
                .email("j2@gmail.com")
                .build();
        BDDMockito.given(empleadoRepository.findAll()).willReturn(Collections.emptyList());

        //When
        List<Empleado> empleados = empleadoService.getAllEmpleados();

        //Then
        Assertions.assertThat(empleados).isEmpty();
        Assertions.assertThat(empleados.size()).isEqualTo(0);
    }

    @DisplayName("Test obtener empleado por ID")
    @Test
    public void testObtenerEmpleadoPorId(){
        //Given - Primero siempre hay que indicar el comportamiento de la dependencia
        BDDMockito.given(empleadoRepository.findById(1L)).willReturn(Optional.of(empleado));

        //When
        Empleado empleadoGuardado = empleadoService.getEmpleadoById(empleado.getId()).get();

        //Then
        Assertions.assertThat(empleadoGuardado).isNotNull();
    }

    @DisplayName("Test para actualizar empleado")
    @Test
    public void testActualizarEmpleado(){
        //Given
        BDDMockito.given(empleadoRepository.save(empleado)).willReturn(empleado);
        empleado.setEmail("cr7@gmail.com");
        empleado.setNombre("Cristian Raul");

        //When
        Empleado empleadoActualizado = empleadoService.updateEmpleado(empleado);

        //Then
        Assertions.assertThat(empleadoActualizado.getNombre()).isEqualTo("Cristian Raul");
        Assertions.assertThat(empleadoActualizado.getEmail()).isEqualTo("cr7@gmail.com");
    }

    @DisplayName("Test para eliminar un empleado")
    @Test
    public void testEliminarEmpleado(){
        //Given
        Long empleadoId = 1L;
        //WillDoNothing es porque no vamos a retornar nada ya que estamos eliminando
        BDDMockito.willDoNothing().given(empleadoRepository).deleteById(empleadoId);

        //When
        empleadoService.deleteEmpleado(empleadoId);

        //Then
        BDDMockito.verify(empleadoRepository, Mockito.times(1)).deleteById(empleadoId);
    }
}
