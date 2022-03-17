package mai.linh.junit.article;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="ARTICLE")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name="type")
@Getter
@Setter
public class Article {
   @Id
   @SequenceGenerator(name = "ArticleSequenceGenerator", sequenceName = "SEQ_ARTICLE", initialValue = 99, allocationSize = 10)
   @GeneratedValue(generator = "ArticleSequenceGenerator")
   private long id;

   private String type;

   private String name;

   public String getType() {
      return type;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public void setType(String type) {
      this.type = type;
   }
}
