package com.covalent.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.covalent.models.FileModel;

import java.util.List;


/**
 * This repository provides CRUD operations for {@link com.ooyala.demo.model.User}
 * objects.
 * @author Muthu Subbiah G
 */
@Repository
public interface CovalentRepository extends MongoRepository<FileModel, String> {

    /**
     * Deletes a Employee entry from the database.
     * @param deleted   The deleted Employee entry.
     */
    void delete(FileModel fileModel);

    /**
     * Finds all Employee entries from the database.
     * @return  The information of all Employee entries that are found from the database.
     */
    List<FileModel> findAll();

    /**
     * Finds the information of a single Employee entry.
     * @param id    The id of the requested Employee entry.
     * @return      The information of the found Employee entry. If no Employee entry
     *              is found, this method returns an empty {@link java.util.Optional} object.
     */
    FileModel findOne(String id);

    /**
     * Saves a new Employee entry to the database.
     * @param saved The information of the saved Employee entry.
     * @return      The information of the saved Employee entry.
     */
    FileModel save(FileModel fileModel);
}
