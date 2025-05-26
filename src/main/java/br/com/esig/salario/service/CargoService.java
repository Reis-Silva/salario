package br.com.esig.salario.service;

import br.com.esig.salario.model.Cargo;
import br.com.esig.salario.repository.CargoRepository;
import jakarta.inject.Inject;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CargoService {

    @Inject
    private CargoRepository cargoRepository;

    public List<Cargo> listarTodos() {
        return cargoRepository.findAll();
    }
}
