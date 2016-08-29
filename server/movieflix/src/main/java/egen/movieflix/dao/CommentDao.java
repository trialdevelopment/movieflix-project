package egen.movieflix.dao;

import java.util.List;

import egen.movieflix.entity.Comment;
import egen.movieflix.entity.Movie;

public interface CommentDao {

	List<Comment> findAllComments();
	Comment addComment(Comment comment);
	void deleteComment(Movie movie);
}