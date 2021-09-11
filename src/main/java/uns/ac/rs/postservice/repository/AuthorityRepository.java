package uns.ac.rs.postservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import uns.ac.rs.postservice.domain.Authority;
import uns.ac.rs.postservice.domain.UserType;

public interface AuthorityRepository extends JpaRepository<Authority, Integer> {
	
	Authority findAuthorityByUserType(UserType userType);
}