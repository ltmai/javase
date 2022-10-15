package mai.linh.junit.article;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="ARTICLE_DESKTOP")
@DiscriminatorValue("DESKTOP")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Desktop extends Article {
   private String peripheral;

   public String getPeripheral() {
      return peripheral;
   }

   public void setPeripheral(String peripheral) {
      this.peripheral = peripheral;
   }
}
