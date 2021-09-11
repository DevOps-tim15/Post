package uns.ac.rs.postservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import uns.ac.rs.postservice.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
