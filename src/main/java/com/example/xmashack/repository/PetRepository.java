package com.example.xmashack.repository;

import com.example.xmashack.domain.Pet;
import com.example.xmashack.domain.PetOwner;
import com.example.xmashack.exception.ResourceNotFoundException;
import com.example.xmashack.generated.tables.records.PetOwnerRecord;
import com.example.xmashack.generated.tables.records.PetRecord;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.SelectJoinStep;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static com.example.xmashack.generated.Tables.PET_OWNER;
import static com.example.xmashack.generated.tables.Pet.PET;

@Repository
public class PetRepository {
    private final DSLContext dslContext;


    public PetRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    public Pet insertPet(Pet pet) {
        PetRecord petRecord = new PetRecord();
        if (pet.getId() == null) {
            petRecord.setId(UUID.randomUUID().toString().replace("-", ""));
        } else {
            petRecord.setId(pet.getId());
        }
        petRecord.setName(pet.getName());
        petRecord.setDescription(pet.getDescription());
        petRecord.setType(pet.getType());
        petRecord.setAge(pet.getAge());
        petRecord.setImagepath(pet.getImagePath());
        petRecord.setPetownerid(pet.getPetOwnerId());

       PetRecord returnedPetRecord = dslContext.insertInto(PET).set(petRecord).returning().fetchOne();

       return new Pet (returnedPetRecord.getId(),returnedPetRecord.getName()
               ,returnedPetRecord.getDescription(),returnedPetRecord.getType()
               ,returnedPetRecord.getAge(),returnedPetRecord.getImagepath()
               ,returnedPetRecord.getPetownerid());
    }

    public Pet getPetById(String id) {
        PetRecord petRecord = dslContext.selectFrom(PET).where(PET.ID.eq(id)).fetchOne();
        if (petRecord != null) {
            return new Pet(petRecord.getId(), petRecord.getName(), petRecord.getDescription(), petRecord.getType(), petRecord.getAge(), petRecord.getImagepath(), petRecord.getPetownerid());
        } else {
            throw new ResourceNotFoundException("pet with id " + id + " not found");
        }
    }

    public Collection<Pet> getAllPetsForOwnerId (String PetOwnerId){
        SelectJoinStep<Record> select = dslContext.select(PET.fields()).from(PET);

        return select
                .where(PET.PETOWNERID.eq(PetOwnerId))
                .orderBy(PET.NAME.asc()).fetchInto(Pet.class);
    }

    public Collection<Pet> getAllPets() {
        SelectJoinStep<Record> select = dslContext.select(PET.fields()).from(PET);
        Set<Condition> conditions = new HashSet<>();

        return select
                .where(conditions)
                .orderBy(PET.NAME.asc()).fetchInto(Pet.class);

    }


}
