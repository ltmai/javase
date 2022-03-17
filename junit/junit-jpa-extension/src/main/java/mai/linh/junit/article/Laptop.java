package mai.linh.junit.article;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="ARTICLE_LAPTOP")
@DiscriminatorValue("LAPTOP")
@Getter
@Setter
public class Laptop extends Article {
   private String battery; 
}
