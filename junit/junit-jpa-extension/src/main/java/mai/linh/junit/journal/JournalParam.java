package mai.linh.junit.journal;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Cacheable(false)
@Table(name="JOURNAL_PARAM")
@IdClass(JournalParamPk.class)
public class JournalParam {

    @Id
    @Column(name="JOURNAL_ID")
    private Long journalId;

    @Id
    private Long position;

    private String parameter;

    public Long getJournalId() {
        return journalId;
    }

    public void setJournalId(Long id) {
        this.journalId = id;
    }

    public Long getPosition() {
        return position;
    }

    public void setPosition(Long position) {
        this.position = position;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

}
