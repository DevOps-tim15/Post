package uns.ac.rs.postservice.repository;

import java.util.List;
import java.util.Set;
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
	
	@Query(value = "select * from post p where p.id in (select l.post_id from post_liked_by l where l.user_id = ?1) or "
			+ "p.id in (select l.post_id from post_disliked_by l where l.user_id = ?1)", nativeQuery = true)
	List<Post> findAllLikedOrDisliked(Long userId);
	
	@Query(value = "select * from post p where p.id in (select s.post_id from post_saved_by s where s.user_id = ?1)", nativeQuery = true)
	List<Post> findAllSaved(Long userId);
	
	@Query(value = "select * from post p where p.id in (select r.post_id from post_reported_by r)", nativeQuery = true)
	Set<Post> findAllReported();
	
	@Query(value = "select * from post p where p.id in (select t.post_id from post_tagged_user t where t.user_id = ?1)", nativeQuery = true)
	Set<Post> findAllTagged(Long userId);

	@Query(value = "select * from post p where p.id in (select s.post_id from post_tagged_user s where s.user_id = ?1)", nativeQuery = true)
	List<Post> getAllTaggedByUser(Long userId);
	
}
