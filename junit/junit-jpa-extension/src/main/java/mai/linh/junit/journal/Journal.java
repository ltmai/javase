package mai.linh.junit.journal;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Cacheable;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyColumn;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Cacheable(false)
@Table(name="JOURNAL")
public class Journal {
    /**
     * Generate ID values from sequence SEQ_JOURNAL (created by JPA) which does not 
     * start from 1 to avoid clashing with the entries created in init-data.sql on startup
     */
    @Id
    @SequenceGenerator(name = "JournalSequenceGenerator", sequenceName = "SEQ_JOURNAL", initialValue = 99, allocationSize = 10)
    @GeneratedValue(generator = "JournalSequenceGenerator")
    private Long id;

    private String category;

    private String message;
    
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "JournalParam", joinColumns = {@JoinColumn(name = "JOURNAL_ID", referencedColumnName = "ID")})
    @MapKeyColumn(name = "POSITION")
    @Column(name = "PARAMETER")
    private Map<Integer, String> parameters = new HashMap<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<Integer, String> getParameters() {
        return parameters;
    }

    public void setParameters(Map<Integer, String> parameters) {
        this.parameters.clear();
        this.parameters.putAll(parameters);
    }

    @Override
    public String toString() {
        String result = this.message;
        for (java.util.Map.Entry<Integer, String> entry : parameters.entrySet()) {
            result = result.replace("%" + entry.getKey(), entry.getValue());
        }
        return result;
    }

}