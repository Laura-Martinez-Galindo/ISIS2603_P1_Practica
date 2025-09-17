package co.edu.uniandes.dse.parcial1.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import co.edu.uniandes.dse.parcial1.dto.EstadioDTO;
import co.edu.uniandes.dse.parcial1.entities.EstadioEntity;
import co.edu.uniandes.dse.parcial1.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcial1.services.EstadioService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/estadios")
public class EstadioController {
    @Autowired
    private EstadioService estadioService;

    @Autowired
    private ModelMapper modelMaper;

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public EstadioDTO create(@RequestBody EstadioDTO estadioDTO) throws IllegalOperationException{
        EstadioEntity estadio = estadioService.createEstadio(modelMaper.map(estadioDTO, EstadioEntity.class));
        return modelMaper.map(estadio, EstadioDTO.class);
    }
    
}
