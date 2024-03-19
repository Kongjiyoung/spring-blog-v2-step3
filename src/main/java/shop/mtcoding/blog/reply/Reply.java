package shop.mtcoding.blog.reply;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import shop.mtcoding.blog.board.Board;
import shop.mtcoding.blog.user.User;

import java.sql.Time;
import java.sql.Timestamp;

@NoArgsConstructor
@Data
@Table(name = "reply_tb")
@Entity
public class Reply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String comment;

    //@JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user; // db -> user_id

    @ManyToOne(fetch = FetchType.LAZY)
    private Board board; // db -> user_id

    @CreationTimestamp // pc -> db (날짜주입)
    private Timestamp createdAt;

    @Transient
    private boolean isReplyOwner;

    @Builder
    public Reply(Integer id, String comment, User user, Board board,Timestamp createdAt) {
        this.id = id;
        this.comment = comment;
        this.user = user;
        this.board = board;
        this.createdAt = createdAt;
    }
}