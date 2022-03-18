package mai.linh.junit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.stream.Stream;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import mai.linh.junit.article.Article;
import mai.linh.junit.article.Desktop;
import mai.linh.junit.article.Laptop;
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

    /**
     * Change <provider> to EclipseLink for this test, because
     * Hibernate has a bug generating the SQL statements for H2
     * in this unit test.
     */
    @Test
    public void whenAddingArticle_thenArticleCreated() {
        // given
        // when
        Laptop laptop = new Laptop();
        laptop.setName("Bussiness Laptop");
        laptop.setBattery("Lithium 10000A");
        articleRepository.persist(laptop);

        Stream<Article> laptops = articleRepository.findArticleByType("LAPTOP");
        Laptop dbLaptop = (Laptop)laptops.findFirst().get();
        // then
        assertEquals("Bussiness Laptop", dbLaptop.getName());
    }
}
