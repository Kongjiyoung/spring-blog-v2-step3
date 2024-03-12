package shop.mtcoding.blog.board;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import shop.mtcoding.blog.util.MyDateUtil;

import java.sql.Timestamp;

//setter는 나중에 값이 바뀔때 하는 거 변경할 애들만 하는거
@NoArgsConstructor
@Data
@Entity
@Table(name="board_tb")
public class Board { //모델링 : 데이터베이스세상과 자바의세상이 다르기 때문에 가져오기 위해 만들어줘야함
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String content;
    private String username;

    @CreationTimestamp //pc -> db (날짜 주입)
    private Timestamp createdAt;

    public Board(String title, String content, String username) {
        this.title = title;
        this.content = content;
        this.username = username;
    }
}
