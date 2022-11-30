package ru.sfu.service;

import ru.sfu.entity.Television;

import java.util.List;

/**
 * Television Service Interface
 * @author Agapchenko V.V.
 */
public interface TelevisionService {
    /**
     * Get All Televisions
     * @return List of Televisions
     */
    List<Television> getAll();

    /**
     * Get Television by ID
     * @param id Identification Number
     * @return Television or null
     */
    Television getById(int id);

    /**
     * Get last ID
     * @return Last ID
     */
    int getLastId();

    /**
     * Get Televisions by Width + Height
     * @param width Screen width
     * @param height Screen height
     * @return List of Televisions
     */
    List<Television> getByWidthAndHeight(int width, int height);

    /**
     * Create new Television
     * @param tv New Television
     * @return Operation result
     */
    boolean create(Television tv);

    /**
     * Update Television
     * @param tv Existing Television
     * @return Operation result
     */
    boolean update(Television tv);

    /**
     * Delete Television by ID
     * @param id Identification Number
     * @return Operation result
     */
    boolean deleteById(int id);

    /**
     * Get Not Sold Televisions
     * @return List of Televisions
     */
    List<Television> getNotSold();

    /**
     * Mark Television as Sold by ID
     * @param id Identification Number
     * @return Operation result
     */
    boolean sell(int id);
}
