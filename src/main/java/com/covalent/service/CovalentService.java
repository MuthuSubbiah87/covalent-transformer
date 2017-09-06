package com.covalent.service;

import java.util.List;

import com.covalent.models.CovalentConfig;
import com.covalent.models.FileModel;

/**
 * This interface declares the methods that provides CRUD operations for
 * {@link com.ooyala.demo.model.User} objects.
 * @author Muthu Subbiah G
 */

public interface CovalentService {
	/**
     * Creates a new user entry.
     * @param todo  The information of the created todo entry.
     * @return      The information of the created todo entry.
     */
    FileModel create(FileModel fileModel);

    /**
     * Deletes a user entry.
     * @param id    The id of the deleted user entry.
     * @return      THe information of the deleted user entry.
     * @throws com.ooyala.demo.service.userNotFoundException if no user entry is found.
     */
    FileModel delete(String id);

    /**
     * Finds all user entries.
     * @return The information of all user entries.
     */
    List<FileModel> findAll();

    /**
     * Finds a single user entry.
     * @param id    The id of the requested user entry.
     * @return      The information of the requested user entry.
     * @throws com.ooyala.demo.service.userNotFoundException if no user entry is found.
     */
    FileModel findById(String id);

    /**
     * Updates the information of a user entry.
     * @param todo  The information of the updated user entry.
     * @return      The information of the updated user entry.
     * @throws com.javaadvent.bootrest.todo.TodoNotFoundException if no user entry is found.
     */
    FileModel update(FileModel user);

	String updateCovalentProperties();

	CovalentConfig getCovalentConfig();
}
