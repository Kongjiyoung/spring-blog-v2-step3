package shop.mtcoding.blog.board;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import shop.mtcoding.blog.user.User;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@DataJpaTest
public class BoardJAPRepositoryTest {
    @Autowired
    private BoardJPARepository boardJPARepository;
    @Autowired
    private EntityManager em;


    //save
    @Test
    public void save_test(){
        // given
        User sessionUser= User.builder().id(1).build();
        Board board = Board.builder()
                .title("제목5")
                .content("내용5")
                .user(sessionUser)
                .build();

        // when
        boardJPARepository.save(board);

        // then
        System.out.println("save_test id : "+board.getId());
    }
    //findById
    @Test
    public void findById_test(){
        // given
        int id=1;

        // when
        Optional<Board> boardOP = boardJPARepository.findById(id);

        if(boardOP.isPresent()){
            Board board = boardOP.get();
            System.out.println("findById_test :" + board.getId());
        }

        // then
        //눈으로 결과를 보고 나서
        //무조건 assertion으로 검증하기
    }
    //findAll(sort)
    @Test
    public void findAll_test(){
        // given
        int id=1;

        // when
        List<Board> boardList = boardJPARepository.findAll();


        // then
        System.out.println("findAll_test : "+boardList);

    }

    //deleteById
    @Test
    public void deleteById_test(){
        // given
        int id = 1;

        // when
        boardJPARepository.deleteById(id);
        em.flush();
        // then
    }

}
