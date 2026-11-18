package com.covoiturage.service.impl;

import com.covoiturage.dto.request.DriverRequest;
import com.covoiturage.dto.response.DriverResponse;
import com.covoiturage.entity.Driver;
import com.covoiturage.exception.BusinessException;
import com.covoiturage.exception.ResourceNotFoundException;
import com.covoiturage.mapper.DriverMapper;
import com.covoiturage.repository.DriverRepository;
import com.covoiturage.service.DriverService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DriverServiceImpl implements DriverService {

    private final DriverRepository driverRepository;
    private final DriverMapper driverMapper;

    @Override
    @Transactional
    public DriverResponse createDriver(DriverRequest request) {
        log.info("creation d'1 nv conducteur avec l'email : {}", request.email());

        if (driverRepository.existsByEmail(request.email())) {
            throw new BusinessException("cet email est deja utilise par 1 autre conducteur");
        }

        if (driverRepository.existsByLicenseNumber(request.licenseNumber())) {
            throw new BusinessException("Ce numéro de permis est deja utilisa.");
        }

        Driver driver = driverMapper.toEntity(request);
        Driver savedDriver = driverRepository.save(driver);

        log.info("Conducteur cree avec succes. ID : {}", savedDriver.getId());

        return driverMapper.toResponse(savedDriver);
    }

    @Override
    @Transactional(readOnly = true)
    public DriverResponse getDriverById(Long id) {
        log.info("Recherche du conducteur avec l'ID : {}", id);

        Driver driver = driverRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("conducteur intrv avec l'id : " + id));

        return driverMapper.toResponse(driver);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DriverResponse> getAllDrivers() {
        log.info("Recupération de la liste de tous les conducteur");
        List<Driver> drivers = driverRepository.findAll();
        return driverMapper.toResponseList(drivers);
    }

    @Override
    @Transactional
    public DriverResponse updateDriver(Long id, DriverRequest request) {
        log.info("mise a jour du conducteur avec l'ID : {}", id);

        Driver existingDriver = driverRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("conducteur intrv avec l'id : " + id));


        if (!existingDriver.getEmail().equals(request.email())
                && driverRepository.existsByEmail(request.email())) {
            throw new BusinessException("cet email est deja utilise par 1 autre conducteur");
        }


        if (!existingDriver.getLicenseNumber().equals(request.licenseNumber())
                && driverRepository.existsByLicenseNumber(request.licenseNumber())) {
            throw new BusinessException("ce numero de permis est deja utilise");
        }

        driverMapper.updateEntityFromRequest(request, existingDriver);
        Driver updatedDriver = driverRepository.save(existingDriver);
        log.info("conducteur mis à jour avec succes ID : {}", updatedDriver.getId());

        return driverMapper.toResponse(updatedDriver);
    }

    @Override
    @Transactional(readOnly = true)
    public DriverResponse getDriverByEmail(String email) {
        log.info("Recherche du conducteur par email : {}", email);

        Driver driver = driverRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("conducteur intrv avec l'email : " + email));

        return driverMapper.toResponse(driver);
    }
}