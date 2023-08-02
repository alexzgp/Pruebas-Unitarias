package com.api.rest.app.services.impl;

import com.api.rest.app.exception.ResourceNotFoundException;
import com.api.rest.app.model.Empleado;
import com.api.rest.app.repository.EmpleadoRepository;
import com.api.rest.app.services.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class EmpleadoServiceImpl implements EmpleadoService {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Override
    public Empleado saveEmpleado(Empleado empleado) {
        Optional<Empleado> empleadoGuardado = empleadoRepository.findByEmail(empleado.getEmail());
        if (empleadoGuardado.isPresent()){
            throw new ResourceNotFoundException("El empleado con ese email ya existe: "
                                                + empleado.getEmail());
        }
        return empleadoRepository.save(empleado);
    }

    @Override
    public List<Empleado> getAllEmpleados() {
        return empleadoRepository.findAll();
    }

    @Override
    public Optional<Empleado> getEmpleadoById(Long id) {
        return empleadoRepository.findById(id);
    }

    @Override
    public Empleado updateEmpleado(Empleado empleadoActualizado) {
        return empleadoRepository.save(empleadoActualizado);
    }

    @Override
    public void deleteEmpleado(Long id) {
        empleadoRepository.deleteById(id);
    }
}
