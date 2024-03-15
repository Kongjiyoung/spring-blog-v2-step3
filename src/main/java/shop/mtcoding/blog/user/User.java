package shop.mtcoding.blog.user;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import shop.mtcoding.blog.util.MyDateUtil;

import java.sql.Timestamp;

//setter는 나중에 값이 바뀔때 하는 거 변경할 애들만 하는거

@NoArgsConstructor //빈 생성자를 자동으로 만들어줌
@Data
@Entity
@Table(name="user_tb")
public class User { //모델링 : 데이터베이스세상과 자바의세상이 다르기 때문에 가져오기 위해 만들어줘야함
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true)
    private String username;
    private String password;
    private String email;

    @CreationTimestamp //pc -> db (날짜 주입)
    private Timestamp createdAt;

    @Builder //몇개쓸지 생성자를 만들어줘야함 초기화할때 쓸 수 있는게 정해져있음 // 이렇게 하나만 만들어줘도 골라서 쓸 수 있음
    public User(Integer id, String username, String password, String email, Timestamp createdAt) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.createdAt = createdAt;
    }
    public void update(String password, String email){
        this.password = password;
        this.email = email;
    }
}
