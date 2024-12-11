package com.example.xmashack.controller;

import com.example.xmashack.domain.PetOwner;
import com.example.xmashack.repository.PetOwnerRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/petowners")
public class PetOwnerController {

    private final PetOwnerRepository petOwnerRepository;

    public PetOwnerController(PetOwnerRepository petOwnerRepository) {
        this.petOwnerRepository = petOwnerRepository;
    }

    @PostMapping
    public PetOwner createPetOwner(@RequestBody PetOwner petOwner) {
        return petOwnerRepository.insertPetOwner(petOwner);

    }

    @GetMapping("/{id}")
    public PetOwner getPetOwner(@PathVariable String id) {
        return petOwnerRepository.getPetOwnerById(id);

    }

    @GetMapping
    public Collection<PetOwner> findAllPetOwners() {
        return petOwnerRepository.getAllPetOwners();
    }


}
