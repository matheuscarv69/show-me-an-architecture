package src.infrastructure.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import src.infrastructure.model.UserModel;

public interface UserRepositoryJPA extends JpaRepository<UserModel, Long> {
}
