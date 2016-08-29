package egen.movieflix.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import egen.movieflix.dao.CommentDao;
import egen.movieflix.entity.Comment;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	CommentDao commentDao;

	@Override
	public List<Comment> findAllComments() {
		return commentDao.findAllComments();
	}

	@Override
	public Comment addComment(Comment comment) {
		return commentDao.addComment(comment);
	}

}