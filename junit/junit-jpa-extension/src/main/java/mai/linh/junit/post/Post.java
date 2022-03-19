package mai.linh.junit.post;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
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
 
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OrderBy("time")
    private List<Comment> comments = new ArrayList<Comment>();

    public Post() {
    }

    public Post(String title) {
        this.title = title;
    }
 
    public void addComment(Comment... comments) {
        for (Comment comment : comments) {
            this.comments.add(comment);
            comment.setPost(this);
        }
    }
 
    public void removeComment(Comment comment) {
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

    public List<Comment> getComments() {
        return this.comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments.clear();
        this.comments.addAll(comments);
    }

    @Override
    public String toString() {
        return "Post [" +
            " id = " + id +  
            ", title = '" + title + "'" +   
            ", comments = { " + String.join("; ", comments.stream().map(Comment::getReview).map(s -> "'" + s + "'").collect(Collectors.toList())) + " } " +  
            "]"; 
    }
}