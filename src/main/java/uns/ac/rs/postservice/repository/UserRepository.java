package uns.ac.rs.postservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import uns.ac.rs.postservice.domain.User;

public interface UserRepository extends JpaRepository<User, Long>{
	User findByEmail(String email);
	User findByUsername(String username);
	List<User> findByCanBeTaggedTrue();
	List<User> findByIsPrivateFalse();
}
