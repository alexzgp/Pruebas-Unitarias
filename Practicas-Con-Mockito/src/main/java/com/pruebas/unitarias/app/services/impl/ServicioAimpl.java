package com.pruebas.unitarias.app.services.impl;

import com.pruebas.unitarias.app.services.ServicioA;

public class ServicioAimpl implements ServicioA {
    @Override
    public int sumar(int a, int b) {
        return a+b+1;
    }
}
