package mai.linh.junit;

import java.util.Optional;
import java.util.stream.Stream;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceUnit;

import mai.linh.junit.post.Post;

public class PostRepository {

    @PersistenceUnit(name = "Production")
	private EntityManager em;

    public PostRepository(EntityManager em) {
        this.em = em;
	}

	public Optional<Post> findPostById(long id) {
        return Optional.ofNullable(em.find(Post.class, id));
	}

	public void persist(Object post) {
        em.persist(post);
	}

	public Stream<Post> findPostByTitle(String title) {
        return em.createQuery("SELECT p FROM Post p WHERE p.title LIKE '%" + title + "%'", Post.class)
                 .getResultStream();
	}
    
}