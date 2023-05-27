package mai.linh.junit;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import mai.linh.junit.extension.JpaJUnitEm;
import mai.linh.junit.extension.JpaJUnitExtension;
import mai.linh.junit.post.Comment;
import mai.linh.junit.post.Post;

@ExtendWith(JpaJUnitExtension.class)
@ExtendWith(MockitoExtension.class)
public class PostTest {
    
    private static final String NEW_POST_TITLE = "New post";

    @JpaJUnitEm
    public EntityManager em;

    private PostRepository postRepository;

    @BeforeEach

    public void beforeEach() {
        postRepository = new PostRepository(em);
    }

    @Test
    public void whenPostExists_thenItCanBeFound() {
        // given
        // when
        Optional<Post> post = postRepository.findPostById(1L);
        // then
        assertAll(
            ()->assertTrue(post.isPresent()),
            ()->assertEquals("First Post", post.get().getTitle()),
            ()->assertEquals(3, post.get().getComments().size())
        );
    }

    @Test
    public void whenAddPost_thenNewPostCreated() {
        // given
        Post post = new Post(NEW_POST_TITLE);
        Comment comment1 = new Comment("First review");
        Comment comment2 = new Comment("Second review");
        Comment comment3 = new Comment("Third review");
        post.addComment(comment1, comment2, comment3);
        // when
        postRepository.persist(post);
        // then
        Optional<Post> dbPost = postRepository.findPostByTitle("New").findFirst();
        assertAll(
            ()->assertTrue(dbPost.isPresent()),
            ()->assertEquals(NEW_POST_TITLE, dbPost.get().getTitle()),
            ()->assertEquals(3, dbPost.get().getComments().size()),
            ()->assertTrue(dbPost.get().getComments().contains(comment1)),
            ()->assertTrue(dbPost.get().getComments().contains(comment2)),
            ()->assertTrue(dbPost.get().getComments().contains(comment3))
        );
    }

    @Test
    public void whenRemoveComment_thenCommentRemoved()
    {
        Optional<Post> post = postRepository.findPostById(1L);
        post.ifPresent(p -> {
            Comment c = p.getComments().get(0);
            p.removeComment(c); 
            em.merge(p);
        }); 

        Optional<Post> dbPost = postRepository.findPostById(1L);
        assertTrue(dbPost.isPresent());
        assertEquals(dbPost.get().getComments().size(), 2);
    }
}