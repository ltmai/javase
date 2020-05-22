package mai.linh.junit.one_to_many.journal;

import java.io.Serializable;

public class JournalParamPk implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Long journalId;

    private Long position;

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
}