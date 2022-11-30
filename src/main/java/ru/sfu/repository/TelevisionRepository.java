package ru.sfu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sfu.entity.Television;

import java.util.List;
import java.util.Optional;

/**
 * JPA Repository Interface for Television entities
 * @author Agapchenko V.V.
 */
@Repository
public interface TelevisionRepository extends JpaRepository<Television, Integer> {
    /**
     * Find Televisions by Width + Height
     * @param width Screen width
     * @param height Screen height
     * @return List of Televisions
     */
    List<Television> findByWidthAndHeight(
            Integer width,
            Integer height
    );

    /**
     * Find last Television
     * @return Optional Television
     */
    Optional<Television> findFirstByOrderByIdDesc();

    /**
     * Find sold or not sold Televisions
     * @param sold Sold or not
     * @return List of Televisions
     */
    List<Television> findBySold(boolean sold);
}
