package mai.linh.junit.post;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "post_comment")
@Cacheable(false)
public class PostComment {
 
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_post_comment")
    @SequenceGenerator(name = "seq_post_comment", initialValue = 100, allocationSize = 5)
    private Long id;
 
    private String review;
 
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    public PostComment() {
    }

    public PostComment(String review) {
        this.review = review;
    }
 
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((post == null) ? 0 : post.hashCode());
        result = prime * result + ((review == null) ? 0 : review.hashCode());
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
        PostComment other = (PostComment) obj;
        if (post == null) {
            if (other.post != null)
                return false;
        } else if (!post.equals(other.post))
            return false;
        if (review == null) {
            if (other.review != null)
                return false;
        } else if (!review.equals(other.review))
            return false;
        return true;
    }
}