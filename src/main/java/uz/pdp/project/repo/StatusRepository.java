package uz.pdp.project.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.project.entity.Status;

import java.util.List;
import java.util.Optional;

public interface StatusRepository extends JpaRepository<Status, Integer> {

    List<Status> findAllByActiveTrueOrderByPositionNumber();

    Optional<Status> findByName(String name);

    @Query("SELECT MAX(s.positionNumber) FROM Status s")
    Optional<Integer> findMaxPositionNumber();
}
