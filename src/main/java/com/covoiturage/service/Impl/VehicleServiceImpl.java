package com.covoiturage.service.Impl;

import com.covoiturage.dto.request.VehicleRequest;
import com.covoiturage.dto.response.VehicleResponse;
import com.covoiturage.entity.Driver;
import com.covoiturage.entity.Vehicle;
import com.covoiturage.exception.BusinessException;
import com.covoiturage.exception.ResourceNotFoundException;
import com.covoiturage.mapper.VehicleMapper;
import com.covoiturage.repository.DriverRepository;
import com.covoiturage.repository.VehicleRepository;
import com.covoiturage.service.VehicleService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;
    private final DriverRepository driverRepository;
    private final VehicleMapper vehicleMapper;

    @Transactional
    public VehicleResponse createVehicle(VehicleRequest request) {

        log.info("creation d'1 nv vehicule pour le conducteur ID : {}", request.driverId());

        if (vehicleRepository.existsByLicensePlate(request.licensePlate())) {
            throw new BusinessException("cet matricul est deja utilise");
        }

        Driver driver = driverRepository.findById(request.driverId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("condct intrv avec l'id : " + request.driverId()));

        Vehicle vehicle = vehicleMapper.toEntity(request);

        vehicle.setDriver(driver);

        Vehicle savedVehicle = vehicleRepository.save(vehicle);
        return vehicleMapper.toResponse(savedVehicle);

    }


    @Transactional
    public VehicleResponse getVehicleById(Long id) {
        log.info("recherche du vehicule avec l'ID : {}", id);

        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("vehicle intrv avec id : " + id));

        return vehicleMapper.toResponse(vehicle);
    }

    @Transactional
    public List<VehicleResponse> getVehiclesByDriverId(Long driverId) {
        log.info("recherche des vehicules du conducteur ID : {}", driverId);

        if (!driverRepository.existsById(driverId))
            throw new ResourceNotFoundException("conducteur intrv avec id : " + driverId);

        List<Vehicle> vehicles = vehicleRepository.findByDriverId(driverId);

        return vehicleMapper.toResponseList(vehicles);

    }

    @Transactional
    public VehicleResponse updateVehicle(Long id, VehicleRequest req) {
        log.info("mise a jour du vehicule avec l'ID : {}", id);

        Vehicle existingVehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("vehicule intrv avec id : " + id));

        Driver driver = driverRepository.findById(req.driverId())
                .orElseThrow(() -> new ResourceNotFoundException("conduct ontrv avec id : " + req.driverId()));

        if (vehicleRepository.existsByLicensePlate(req.licensePlate()) && !existingVehicle.getLicensePlate().equals(req.licensePlate()))
            throw new BusinessException("cet matricule est deja utilise par 1 autre vehicule");

        vehicleMapper.updateEntityFromRequest(req, existingVehicle);
        existingVehicle.setDriver(driver);

        Vehicle updatedVehicle = vehicleRepository.save(existingVehicle);
        return vehicleMapper.toResponse(updatedVehicle);

    }

}
