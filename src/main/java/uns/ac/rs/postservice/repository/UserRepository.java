package uns.ac.rs.postservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import uns.ac.rs.postservice.domain.User;

public interface UserRepository extends JpaRepository<User, Long>{
	User findByEmail(String email);
	User findByUsername(String username);
	List<User> findByCanBeTaggedTrue();
	List<User> findByCanBeTaggedTrueAndIsPrivateFalse();
	List<User> findByCanBeTaggedTrueAndIsPrivateTrue();

	List<User> findByIsPrivateFalse();
	List<User> findByIsPrivateTrue();
	
	@Query(value = "select * from user_t u where u.id in (select ua.user_id from user_authority ua where ua.authority_id = 1 "
			+ "or ua.authority_id = 2)", nativeQuery = true)
	List<User> findAllRegisteredUsers();
}
