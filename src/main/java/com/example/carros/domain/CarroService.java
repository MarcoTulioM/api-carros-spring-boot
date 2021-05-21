package com.example.carros.domain;

import com.example.carros.dto.CarroDTO;
import com.example.carros.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarroService {

    @Autowired
    private CarroRepository rep;

    public List<CarroDTO> getCarro () {

        return rep.findAll().stream().map(CarroDTO::create).collect(Collectors.toList());
    }

    public CarroDTO getCarroById(Long id) {
        Optional<Carro> carro = rep.findById(id);
        return carro.map(CarroDTO::create).orElseThrow(() -> new ObjectNotFoundException("Carro não encontrado"));
    }

    public List<CarroDTO> getCarroByTipo(String tipo) {

        return rep.findByTipo(tipo).stream().map(CarroDTO::create).collect(Collectors.toList());
    }

    public CarroDTO insert(Carro carro) {
        Assert.isNull(carro.getId(),"Não foi possível inserir o registro");

        return CarroDTO.create(rep.save(carro));
    }

    public CarroDTO update ( Long id, Carro carro) {
        Assert.notNull(id, "Não foi possível atualizar o registro");

        Optional<Carro> opt = rep.findById(id);
        if( opt.isPresent() ) {
            Carro db = opt.get();
            db.setNome(carro.getNome());
            db.setTipo(carro.getTipo());

            rep.save(db);

            return CarroDTO.create(db);
        } else {
            throw new RuntimeException("Erro");
        }
    }

    public void delete (Long id) {
        rep.deleteById(id);
    }

    // Without DataBase Access ------------------------------------------------

    /*public List<Carro> getCarroFake () {

        List<Carro> veiculos = new ArrayList<>();

        veiculos.add(new Carro(1L, "Nome do Carro"));
        veiculos.add(new Carro(2L, "Nome do Carro"));
        veiculos.add(new Carro(3L, "Nome do Carro"));

        return  veiculos;
    }*/
}
