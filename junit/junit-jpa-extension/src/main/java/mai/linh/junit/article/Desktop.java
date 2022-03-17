package mai.linh.junit.article;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="ARTICLE_DESKTOP")
@DiscriminatorValue("DESKTOP")
public class Desktop extends Article {
   private String peripheral;

   public String getPeripheral() {
      return peripheral;
   }

   public void setPeripheral(String peripheral) {
      this.peripheral = peripheral;
   }
}
