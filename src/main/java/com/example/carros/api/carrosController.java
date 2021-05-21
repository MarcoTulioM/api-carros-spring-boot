package com.example.carros.api;

import com.example.carros.domain.Carro;
import com.example.carros.domain.CarroService;
import com.example.carros.dto.CarroDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/carros")
public class carrosController {

    @Autowired
    private CarroService service;

    @GetMapping()
    public ResponseEntity<List<CarroDTO>> get () {
        //return new ResponseEntity<>(service.getCarro(), HttpStatus.OK);
        return ResponseEntity.ok(service.getCarro());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarroDTO> getId (@PathVariable("id") Long id ) {
        CarroDTO carro = service.getCarroById(id);

        return ResponseEntity.ok(carro);

        /*return carro.map(c -> ResponseEntity.ok(c))
                .orElse(ResponseEntity.notFound().build());*/
        /*return carro.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());*/
        //________________________________________________
        /*
        return carro.isPresent() ?
                ResponseEntity.ok(carro.get()) :
                ResponseEntity.notFound().build();
        */
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<CarroDTO>> getTipo (@PathVariable("tipo") String tipo ) {
        List<CarroDTO> listaDeCarros = service.getCarroByTipo(tipo);

        return listaDeCarros.isEmpty() ?
                ResponseEntity.noContent().build() :
                ResponseEntity.ok(listaDeCarros);
    }

    @PostMapping
    public ResponseEntity post(@RequestBody Carro carro) {

        CarroDTO c = service.insert(carro);

        URI location = getUri(c.getId());
        return ResponseEntity.created(location).build();
    }
    public URI getUri (Long id) {
        return ServletUriComponentsBuilder.fromCurrentRequest().path("{id}").buildAndExpand(id).toUri();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateCarro ( @PathVariable("id") Long id, @RequestBody Carro carro) {
        CarroDTO c = service.update(id, carro);

        return (c != null) ?
                ResponseEntity.ok(c) :
                ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCarro (@PathVariable("id") Long id) {
        service.delete(id);

        return ResponseEntity.ok().build();
    }
}
