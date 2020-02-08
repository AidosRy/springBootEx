package kz.iitu.swd.group34.FirstSpringBootIITU.repositories;

import kz.iitu.swd.group34.FirstSpringBootIITU.entities.Courses;
import kz.iitu.swd.group34.FirstSpringBootIITU.entities.Groups;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GroupsRepository extends JpaRepository<Groups, Long> {
    List<Groups> findAll();
    Optional<Groups> findById(Long id);
}
