package mai.linh.junit.article;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="ARTICLE_LAPTOP")
@DiscriminatorValue("LAPTOP")
public class Laptop extends Article {
   private String battery;

   public String getBattery() {
      return battery;
   }

   public void setBattery(String battery) {
      this.battery = battery;
   } 
}
