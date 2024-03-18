//package shop.mtcoding.blog.board;
//
//import jakarta.persistence.*;
//import lombok.Builder;
//import lombok.Data;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import org.hibernate.annotations.CreationTimestamp;
//import shop.mtcoding.blog.user.User;
//import shop.mtcoding.blog.util.MyDateUtil;
//
//import java.sql.Timestamp;
//
////setter는 나중에 값이 바뀔때 하는 거 변경할 애들만 하는거
//@NoArgsConstructor
//@Data //추천하지않음 getter만
//@Entity
//@Table(name="board_tb")
//public class Board { //모델링 : 데이터베이스세상과 자바의세상이 다르기 때문에 가져오기 위해 만들어줘야함
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer id;
//    private String title;
//    private String content;
//
//
//    //@joinColumn(name="userId") //따로 pk가져온 폴링키 이름을 지정할 수 있음
//    @ManyToOne(fetch = FetchType.LAZY)
//    private User user; //user_id 자동으로 pk가져와 user_id로 연결함 //폴링키가 자동으로 제약조건으로 걸림
//
//    @CreationTimestamp //pc -> db (날짜 주입)
//    private Timestamp createdAt;
//
//    @Builder
//    public Board(Integer id, String title, String content, User user, Timestamp createdAt) {
//        this.id = id;
//        this.title = title;
//        this.content = content;
//        this.user = user;
//        this.createdAt = createdAt;
//    }
//
//}
package shop.mtcoding.blog.board;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import shop.mtcoding.blog.user.User;

import java.sql.Timestamp;

@NoArgsConstructor
@Data
@Table(name = "board_tb")
@Entity
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String content;

    //@JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user; // db -> user_id

    @CreationTimestamp // pc -> db (날짜주입)
    private Timestamp createdAt;

    @Transient // 테이블 생성이 안됨
    private boolean isOwner;


    @Builder
    public Board(Integer id, String title, String content, User user, Timestamp createdAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.user = user;
        this.createdAt = createdAt;
    }
}