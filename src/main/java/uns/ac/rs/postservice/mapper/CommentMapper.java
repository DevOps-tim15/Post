package uns.ac.rs.postservice.mapper;

import java.util.ArrayList;
import java.util.List;

import uns.ac.rs.postservice.domain.Comment;
import uns.ac.rs.postservice.dto.CommentDTO;

public class CommentMapper {
	
	public static CommentDTO fromEntity(Comment comment) {
		return new CommentDTO(comment.getPost().getId(), comment.getUser().getUsername(), comment.getText());
	}
	
	public static List<CommentDTO> fromEntityList(List<Comment> comments) {
		List<CommentDTO> commentDTO = new ArrayList<>();
		for(Comment comment:comments) {
			commentDTO.add(fromEntity(comment));
		}
		return commentDTO;	
	}
}
