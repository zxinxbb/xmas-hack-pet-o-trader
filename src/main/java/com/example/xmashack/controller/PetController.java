package com.example.xmashack.controller;

import com.example.xmashack.domain.Pet;
import com.example.xmashack.repository.PetRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collection;

@RestController
@RequestMapping("/api/pets")
//@CrossOrigin(origins = "http://localhost:4200")

public class PetController {

    private final PetRepository petRepository;

    public PetController(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    @PostMapping
    public ResponseEntity<Pet> createPet(
            @RequestBody Pet pet,
            @RequestParam MultipartFile image) throws IOException {

        Pet savedPet = petRepository.insertPet(pet, image);
        System.out.println(savedPet);

        return ResponseEntity.ok(savedPet);
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

