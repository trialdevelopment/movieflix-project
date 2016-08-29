package egen.movieflix.service;

import java.util.List;

import egen.movieflix.entity.Comment;

public interface CommentService {

	List<Comment> findAllComments();
	Comment addComment(Comment comment);

}