package com.example.xmashack.controller;

import com.example.xmashack.domain.Pet;
import com.example.xmashack.repository.PetRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;

@RestController
@RequestMapping("/api/pets")
@CrossOrigin(origins = "http://localhost:4200")
public class PetController {

    private final PetRepository petRepository;

    public PetController(PetRepository petRepository) {

        this.petRepository = petRepository;
    }

    @PostMapping
    public ResponseEntity<Pet> createPetWithImage(@RequestParam("name") String name,
                                                  @RequestParam("description") String description,
                                                  @RequestParam("type") String type,
                                                  @RequestParam("age") Integer age,
                                                  @RequestParam("petOwnerId") String petOwnerId,
                                                  @RequestParam("image") MultipartFile image) throws IOException {

        // Save the image and get the file path
        String imagePath = saveImage(image);

        // Create the Pet object with the image path
        Pet pet = new Pet(null, name, description, type, age, imagePath, petOwnerId);

        // Insert the pet record into the database
        Pet savedPet = petRepository.insertPet(pet);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedPet);
    }

    // Method to save the image to the server's file system
    private String saveImage(MultipartFile image) throws IOException {
        // Define the directory to save images (relative path, it will be inside the project's root)
        String uploadDir = "images/";

        // Ensure the directory exists
        Files.createDirectories(Paths.get(uploadDir));

        // Get the original filename and create a path to store the image
        String fileName = image.getOriginalFilename();
        Path imagePath = Paths.get(uploadDir + fileName);

        // Save the image to the directory
        Files.copy(image.getInputStream(), imagePath);

        // Return the relative path (you can store it in DB and use it to serve images later)
        return "/images/" + fileName;
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
