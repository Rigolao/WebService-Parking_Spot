package com.spring_curso.curso_spring.controllers;

import com.spring_curso.curso_spring.dtos.ParkingSpotDTO;
import com.spring_curso.curso_spring.models.ParkingSpotModel;
import com.spring_curso.curso_spring.services.ParkingSpotService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/parking-spot")
public class ParkingSpotController {

    private final ParkingSpotService parkingSpotService;

    public ParkingSpotController(ParkingSpotService parkingSpotService) {
        this.parkingSpotService = parkingSpotService;
    }

    @PostMapping
    //@Valid faz as anotações do DTO funcionarem
    public ResponseEntity<Object> saveParkingSpot(@RequestBody @Valid ParkingSpotDTO parkingSpotDTO) {
        var parkingSpotModel = new ParkingSpotModel();
        //Esse metodo recebe o que vai ser convertido e no que ele vai virar, como a função from do VO
        BeanUtils.copyProperties(parkingSpotDTO, parkingSpotModel);
        parkingSpotModel.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));
        return ResponseEntity.status(HttpStatus.CREATED).body(parkingSpotService.save(parkingSpotModel));
    }

    @GetMapping
    public ResponseEntity<Page<ParkingSpotModel>> getAllParkingSpots(
            @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(parkingSpotService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneParkingSpot(@PathVariable(value = "id") UUID id) {
        Optional<ParkingSpotModel> parkingSpotModelOptional = parkingSpotService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(parkingSpotModelOptional);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteParkingSpot(@PathVariable(value = "id") UUID id) {
        Optional<ParkingSpotModel> parkingSpotModelOptional = parkingSpotService.findById(id);
        //metodo get sendo verificado dentro do service
        parkingSpotService.delete(parkingSpotModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Parking Spot deleted sucessfully.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateParkingSpot(@PathVariable(value = "id") UUID id,
                                                    @RequestBody @Valid ParkingSpotDTO parkingSpotDTO) {
        Optional<ParkingSpotModel> parkingSpotModelOptional = parkingSpotService.findById(id);
        var parkingSpotModel = parkingSpotModelOptional.get();

        //Forma 1:
//        parkingSpotModel.setParkingSpotNumber(parkingSpotDTO.getParkingSpotNumber());
//        parkingSpotModel.setLicensePlateCar(parkingSpotDTO.getLicensePlateCar());
//        parkingSpotModel.setModelCar(parkingSpotDTO.getModelCar());
//        parkingSpotModel.setBrandCar(parkingSpotDTO.getBrandCar());
//        parkingSpotModel.setColorCar(parkingSpotDTO.getColorCar());
//        parkingSpotModel.setResponsibleName(parkingSpotDTO.getResponsibleName());
//        parkingSpotModel.setApartment(parkingSpotDTO.getApartment());
//        parkingSpotModel.setBlock(parkingSpotDTO.getBlock());

        //Forma 2:
        BeanUtils.copyProperties(parkingSpotDTO, parkingSpotModel);
        parkingSpotModel.setId(parkingSpotModelOptional.get().getId());
        parkingSpotModel.setRegistrationDate(parkingSpotModelOptional.get().getRegistrationDate());
        return ResponseEntity.status(HttpStatus.OK).body(parkingSpotService.edit(parkingSpotModel));
    }
}
