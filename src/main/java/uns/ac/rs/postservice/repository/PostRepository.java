package uns.ac.rs.postservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import uns.ac.rs.postservice.domain.Post;
import uns.ac.rs.postservice.domain.User;

public interface PostRepository extends JpaRepository<Post, Long> {
	
	@Query(value = "select * from post p where p.user_id = ?1", nativeQuery = true)
	List<Post> findAllByUserId(Long userId);
	
	@Query(value = "select * from post p where p.user_id in(select u.id from user_t u where u.is_private = false)", nativeQuery = true)
	List<Post> findAllPostsByPublicUsers();
	
	@Query(value = "select * from post p where p.user_id in ?1 or p.user_id in(select u.id from user_t u where u.is_private = false)", nativeQuery = true)
	List<Post> findAllByFollowingUsers(List<Long> usersIds);
	
}
