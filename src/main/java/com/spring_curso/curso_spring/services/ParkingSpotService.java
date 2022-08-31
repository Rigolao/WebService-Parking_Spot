package com.spring_curso.curso_spring.services;

import com.spring_curso.curso_spring.exceptions.*;
import com.spring_curso.curso_spring.models.ParkingSpotModel;
import com.spring_curso.curso_spring.repositories.ParkingSpotRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ParkingSpotService {

    private final ParkingSpotRepository parkingSpotRepository;

    public ParkingSpotService(ParkingSpotRepository parkingSpotRepository) {
        this.parkingSpotRepository = parkingSpotRepository;
    }

    @Transactional //Garante um rollback caso de errado
    public ParkingSpotModel save(ParkingSpotModel parkingSpotModel) {
        if (existsByLicensePlateCar(parkingSpotModel.getLicensePlateCar())) {
            throw new InvalidLicensePlateCarException();
        }
        if (existsByParkingSpotNumber(parkingSpotModel.getParkingSpotNumber())) {
            throw new InvalidParkingSpotNumberException();
        }
        if (existsByApartmentAndBlock(parkingSpotModel.getApartment(), parkingSpotModel.getBlock())) {
            throw new InvalidApartmentAndBlockException();
        }
        return parkingSpotRepository.save(parkingSpotModel);
    }

    @Transactional
    public List<ParkingSpotModel> saveList(List<ParkingSpotModel> parkingSpotModelList){
        if(parkingSpotModelList.isEmpty()){
            throw new EmptyListException();
        }

        for(int i=0; i < parkingSpotModelList.size(); i++){
            for(int j = i; j < parkingSpotModelList.size(); j++){
                if(parkingSpotModelList.get(i).equals(parkingSpotModelList.get(j))){
                    throw new DuplicateItemException();
                }
            }
        }
        if(parkingSpotModelList.stream().anyMatch(
                p -> existsByParkingSpotNumber(p.getParkingSpotNumber()))){
            throw new InvalidParkingSpotNumberException();
        }
        if(parkingSpotModelList.stream().anyMatch(
                p -> existsByApartmentAndBlock(p.getApartment(), p.getBlock()))){
            throw new InvalidApartmentAndBlockException();
        }
        if(parkingSpotModelList.stream().anyMatch(
                p -> existsByLicensePlateCar(p.getLicensePlateCar()))){
            throw new InvalidLicensePlateCarException();
        }
        return parkingSpotRepository.saveAll(parkingSpotModelList);
    }

    public boolean existsByLicensePlateCar(String licensePlateCar) {
        return parkingSpotRepository.existsByLicensePlateCar(licensePlateCar);
    }

    public boolean existsByParkingSpotNumber(String parkingSpotNumber) {
        return parkingSpotRepository.existsByParkingSpotNumber(parkingSpotNumber);
    }

    public boolean existsByApartmentAndBlock(String apartment, String block) {
        return parkingSpotRepository.existsByApartmentAndBlock(apartment, block);
    }

    public Page<ParkingSpotModel> findAll(Pageable pageable) {
        return parkingSpotRepository.findAll(pageable);
    }

    public Optional<ParkingSpotModel> findById(UUID id) {
        var entity = parkingSpotRepository.findById(id);
        if (entity.isEmpty()) {
            throw new NotFoundException();
        }
        return entity;
    }

    @Transactional
    public void delete(ParkingSpotModel parkingSpotModel) {
        parkingSpotRepository.delete(parkingSpotModel);
    }

    @Transactional
    public ParkingSpotModel edit(ParkingSpotModel parkingSpotModel) {
        return parkingSpotRepository.save(parkingSpotModel);
    }
}
