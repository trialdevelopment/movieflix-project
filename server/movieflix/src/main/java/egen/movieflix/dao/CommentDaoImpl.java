package egen.movieflix.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import egen.movieflix.entity.Comment;
import egen.movieflix.entity.Movie;

/**
 ********************************************************
 * CommentDaoImpl 
 ******************************************************** 
 * 
 * This CommentDaoImpl is the implementation of CommentDao Interface.
 * The CommentDaoImpl class contains the following methods: 
 * 1.findAllComments() - This method is used to get a list of all the users who commented on a particular movie. 
 * 2.addComment() - This method adds a comment for a particular movie by a particular user.
 * 3.deleteComment() - The method deletes a particular comment.
 * @author Jay Nagda.
 * @category REST API Backend- Egen Project
 */


@Repository
public class CommentDaoImpl implements CommentDao{

	@PersistenceContext
	EntityManager em;

	@Override
	public List<Comment> findAllComments() {
		TypedQuery<Comment> query = em.createQuery("select c from Comment c", Comment.class);
		List<Comment> comments = query.getResultList();

		return comments;
	}

	@Override
	@Transactional
	public Comment addComment(Comment comment) {
		em.persist(comment);
		return null;
	}

	@Override
	@Transactional
	public void deleteComment(Movie movie) {
		Query query = em.createQuery("DELETE FROM Comment c WHERE c.movie = :pMovie");
		query.setParameter("pMovie", movie).executeUpdate();
	}


}
