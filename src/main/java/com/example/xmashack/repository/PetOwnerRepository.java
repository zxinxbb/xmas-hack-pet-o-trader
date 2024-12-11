package com.example.xmashack.repository;

import org.jooq.Record;
import com.example.xmashack.domain.PetOwner;
import com.example.xmashack.exception.ResourceNotFoundException;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.SelectJoinStep;
import org.springframework.stereotype.Repository;
import com.example.xmashack.generated.tables.records.PetOwnerRecord;

import java.util.*;

import static com.example.xmashack.generated.Tables.PET_OWNER;

@Repository
public class PetOwnerRepository {
    private final DSLContext dslContext;

    public PetOwnerRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    public PetOwner insertPetOwner(PetOwner petOwner) {
        PetOwnerRecord petOwnerRecord = new PetOwnerRecord();
        if (petOwner.getId() == null) {
            petOwnerRecord.setId(UUID.randomUUID().toString().replace("-", ""));//gen id
        } else {
            petOwnerRecord.setId(petOwner.getId());
        }
        petOwnerRecord.setName(petOwner.getName());
       PetOwnerRecord  returnedPetOwnerRecord = dslContext.insertInto(PET_OWNER).set(petOwnerRecord).returning().fetchOne();//translate into sql and put into table

       return new PetOwner(returnedPetOwnerRecord.getId(),returnedPetOwnerRecord.getName());


    }

    public PetOwner getPetOwnerById(String id) {
        PetOwnerRecord petOwnerRecord = dslContext.selectFrom(PET_OWNER).where(PET_OWNER.ID.eq(id)).fetchOne();// gets id from db
        if (petOwnerRecord != null) {
            return new PetOwner(petOwnerRecord.getId(), petOwnerRecord.getName());
        } else {
            throw new ResourceNotFoundException("pet owner with id " + id + " not found");
        }
    }

    public Collection<PetOwner> getAllPetOwners() {
        SelectJoinStep<Record> select = dslContext.select(PET_OWNER.fields()).from(PET_OWNER);
        Set<Condition> conditions = new HashSet<>();

        return select
                .where(conditions)
                .orderBy(PET_OWNER.NAME.asc()).fetchInto(PetOwner.class);

    }
}
