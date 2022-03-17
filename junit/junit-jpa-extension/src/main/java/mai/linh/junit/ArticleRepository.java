package mai.linh.junit;

import java.util.Optional;
import java.util.stream.Stream;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceUnit;

import mai.linh.junit.article.Article;
import mai.linh.junit.post.Post;

public class ArticleRepository {

	@PersistenceUnit(name = "Production")
	private EntityManager em;

	public ArticleRepository(EntityManager em) {
		this.em = em;
	}

	public Optional<Post> findArticleById(long id) {
		return Optional.ofNullable(em.find(Post.class, id));
	}

	public void persist(Object post) {
		em.persist(post);
	}

	public Stream<Article> findArticleByType(String type) {
        return em.createQuery("SELECT p FROM Article p WHERE p.type = :type", Article.class)
                 .setParameter("type", type)
                 .getResultList().stream();
	}
}