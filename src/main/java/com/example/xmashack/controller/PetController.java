package com.example.xmashack.controller;

import com.example.xmashack.domain.Pet;
import com.example.xmashack.repository.PetRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/pets")
public class PetController {

    private final PetRepository petRepository;

    public PetController(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    @PostMapping
    public Pet createPet(@RequestBody Pet pet) {
        return petRepository.insertPet(pet);
    }

    @GetMapping("/{id}")
    public Pet getPet(@PathVariable String id) {
        return petRepository.getPetById(id);

    }

    @GetMapping
    public Collection<Pet> find(@RequestParam(required = false) String petOwnerId) {
        if (petOwnerId != null) {
            return petRepository.getAllPetsForOwnerId(petOwnerId);
        }
        return petRepository.getAllPets();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePet(@PathVariable String id) {
        petRepository.deletePet(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }


}
