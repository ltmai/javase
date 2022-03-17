package mai.linh.junit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import mai.linh.junit.article.Article;
import mai.linh.junit.article.Desktop;
import mai.linh.junit.extension.JpaJUnitEm;
import mai.linh.junit.extension.JpaJUnitExtension;

@ExtendWith(JpaJUnitExtension.class)
@ExtendWith(MockitoExtension.class)
public class ArticleTest {
    @JpaJUnitEm
    public EntityManager em;

    private ArticleRepository articleRepository;

    @BeforeEach
    public void beforeEach() {
        articleRepository = new ArticleRepository(em);
    }

    @Test
    public void whenArticleExists_thenItCanBeFound() {
        // given
        // when
        Stream<Article> desktops = articleRepository.findArticleByType("DESKTOP");
        Desktop desktop = (Desktop)desktops.findFirst().get();
        // then
        assertEquals("Logitec Mouse+Keyboard", desktop.getPeripheral());
        assertEquals("Gamer Desktop", desktop.getName());
    }
}
