package ru.sfu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sfu.entity.Alert;
import ru.sfu.entity.Television;
import ru.sfu.repository.TelevisionRepository;

import java.util.List;
import java.util.Optional;

/**
 * Television Service
 * @author Agapchenko V.V.
 */
@Service
public class TelevisionServiceImpl implements TelevisionService {

    private final TelevisionRepository rep;
    private final AlertService alert;

    /**
     * Constructor
     * @param televisionRepository Television Repository
     * @param alertService Alert Service
     */
    @Autowired
    TelevisionServiceImpl(
            TelevisionRepository televisionRepository,
            AlertService alertService
    ) {
        rep = televisionRepository;
        alert = alertService;
    }

    /**
     * Get All Televisions
     * @return List of Televisions
     */
    @Override
    public List<Television> getAll() {
        return rep.findAll();
    }

    /**
     * Get Television by ID
     * @param id Identification Number
     * @return Television or null
     */
    @Override
    public Television getById(int id) {
        return rep.findById(id).map(tv -> {
            if (tv.isSold())
                return null;
            return tv;
        }).orElse(null);
    }

    /**
     * Get last ID
     * @return Last ID
     */
    @Override
    public int getLastId() {
        Optional<Television> lastTv = rep.findFirstByOrderByIdDesc();
        return lastTv.map(tv -> tv.getId() + 1).orElse(1);
    }

    /**
     * Get Televisions by Width + Height
     * @param width Screen width
     * @param height Screen height
     * @return List of Televisions
     */
    @Override
    public List<Television> getByWidthAndHeight(int width, int height) {
        return rep.findByWidthAndHeight(width, height);
    }

    /**
     * Create new Television
     * @param tv New Television
     * @return Operation result
     */
    @Override
    public boolean create(Television tv) {
        alert.sendAlert(new Alert("create", tv.toString()));

        if (rep.findById(tv.getId()).isPresent()) {
            return false;
        }

        rep.save(tv);
        return true;
    }

    /**
     * Update Television
     * @param tv Existing Television
     * @return Operation result
     */
    @Override
    public boolean update(Television tv) {
        alert.sendAlert(new Alert("update", tv.toString()));

        if (rep.findById(tv.getId()).isEmpty()) {
            return false;
        }

        rep.save(tv);
        return true;
    }

    /**
     * Delete Television by ID
     * @param id Identification Number
     * @return Operation result
     */
    @Override
    public boolean deleteById(int id) {
        boolean result = false;
        String message = String.valueOf(id);
        Optional<Television> optTv = rep.findById(id);

        if (optTv.isPresent()) {
            message = optTv.get().toString();
            rep.deleteById(id);
            result = true;
        }

        alert.sendAlert(new Alert("delete", message));
        return result;
    }

    /**
     * Get Not Sold Televisions
     * @return List of Televisions
     */
    @Override
    public List<Television> getNotSold() {
        return rep.findBySold(false);
    }

    /**
     * Mark Television as Sold by ID
     * @param id Identification Number
     * @return Operation result
     */
    @Override
    public boolean sell(int id) {
        boolean result = false;
        String message = String.valueOf(id);
        Optional<Television> optTv = rep.findById(id);

        if (optTv.isPresent()) {
            Television tv = optTv.get();

            tv.setSold(true);
            rep.save(tv);

            message = tv.toString();
            result = true;
        }

        alert.sendAlert(new Alert("sell", message));
        return result;
    }
}
