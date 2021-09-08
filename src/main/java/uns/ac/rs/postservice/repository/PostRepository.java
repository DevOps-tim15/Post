package uns.ac.rs.postservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import uns.ac.rs.postservice.domain.Post;
import uns.ac.rs.postservice.domain.User;

public interface PostRepository extends JpaRepository<Post, Long> {
	List<Post> findAllByUser(User user);
}
