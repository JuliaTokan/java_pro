package ua.kiev.prog.oauth2.loginviagoogle.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.kiev.prog.oauth2.loginviagoogle.model.Task;

import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByAccountEmail(String email, Pageable pageable);
    Long countByAccountEmail(String email);
    void deleteById(Long ID);
    void deleteByAccountEmailAndText(String email, String text);
}
