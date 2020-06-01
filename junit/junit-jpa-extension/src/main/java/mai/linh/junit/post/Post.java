package mai.linh.junit.post;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "post")
@Cacheable(false)
public class Post {
 
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_post")
    @SequenceGenerator(name = "seq_post", initialValue = 100, allocationSize = 5)
    private Long id;
 
    private String title;
 
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<PostComment> comments = new ArrayList<PostComment>();

    public Post() {
    }

    public Post(String title) {
        this.title = title;
    }
 
    public void addComment(PostComment comment) {
        comments.add(comment);
        comment.setPost(this);
    }
 
    public void removeComment(PostComment comment) {
        comments.remove(comment);
        comment.setPost(null);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<PostComment> getComments() {
        return this.comments;
    }

    public void setComments(List<PostComment> comments) {
        this.comments.clear();
        this.comments.addAll(comments);
    }

    @Override
    public String toString() {
        return "Post [" +
            " id = " + id +  
            ", title = '" + title + "'" +   
            ", comments = { " + String.join("; ", comments.stream().map(PostComment::getReview).map(s -> "'" + s + "'").collect(Collectors.toList())) + " } " +  
            "]"; 
    }
}