package mai.linh.junit.journal;

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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((journalId == null) ? 0 : journalId.hashCode());
        result = prime * result + ((position == null) ? 0 : position.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        JournalParamPk other = (JournalParamPk) obj;
        if (journalId == null) {
            if (other.journalId != null)
                return false;
        } else if (!journalId.equals(other.journalId))
            return false;
        if (position == null) {
            if (other.position != null)
                return false;
        } else if (!position.equals(other.position))
            return false;
        return true;
    }
}