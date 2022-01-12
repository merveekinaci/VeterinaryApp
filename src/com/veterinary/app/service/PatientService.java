package com.veterinary.app.service;

import java.util.List;
import java.util.Optional;

import com.veterinary.app.model.Patient;
import com.veterinary.app.model.User;
import com.veterinary.app.dto.PatientRegistrationDto;

public interface PatientService  {
    List <Patient> getPatients();

    Patient findByName_AndOwner(String name, User owner);

    Optional <Patient> findById(Long id);

    Patient save(PatientRegistrationDto registration, User owner);

    void update(PatientRegistrationDto registration, Patient patient);

    void deleteById(Long id);

    List<Patient> findByNameContainingIgnoreCase(String name);
}
