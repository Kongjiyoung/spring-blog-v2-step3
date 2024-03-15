package shop.mtcoding.blog.board;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import shop.mtcoding.blog.user.User;

import java.util.List;

@Import(BoardRepository.class)
@DataJpaTest
public class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private EntityManager em;

    @Test
    public void updateById_test(){
        // given
        int id=1;
        String title="제목수정";
        String content="내용수정";
        // when
        boardRepository.updateById(id, title, content);
        em.flush(); //실제코드는 작성할 필요가 없다. 트랜잭션이 종료되기 때문에
        // then
        System.out.println("updateById_test : " + boardRepository.findById(1));

    }
    @Test
    public void delete_test(){
        // given
        int id =1;

        // when
        boardRepository.deleteById(id);

        // then
        System.out.println("deleteById_test : " + boardRepository.findAll().size());
    }
    @Test
    public void findAllV2_test(){
        //given

        //when
        List<Board> boardList=boardRepository.findAllV2();
        //then
        for (Board board : boardList)
        System.out.println(board);

    }

//    @Test
//    public void findUser_test(){
//        //given
//
//        //when
//        List<User> userList=boardRepository.findAllV2();
//        //then
//
//        System.out.println(userList);
//
//    }
    @Test
    public void findUserid_test(){
        //given

        //when
        List<Board> boardList=boardRepository.findAll();
        int[] id=boardList.stream().mapToInt(board->board.getUser().getId()).distinct().toArray();
        //then
        for (int i : id){
            System.out.println(i);
        }

        // select * from user_tb where id in (3,2,1,1);
        // select * from user_tb where id in (3,2,1);
    }
    @Test
    public void query_test(){
        int[] idx= {1,2,3};

        String q="select u from User u where u.id in (";
        for(int i=0; i<idx.length;i++){
            if (i==idx.length-1){
                q = q + ":id"+i+")";
            }
            else {
                q = q + ":id"+i+",";
            }
        }

        System.out.println(q);
    }

    @Test
    public void findAll_lazyLoading_test(){
        // given

        // when
        List<Board> boardList=boardRepository.findAll();
        boardList.forEach(board -> System.out.println(board.getUser().getUsername()));

        // then
    }

    @Test
    public void findAll_test(){
        //given

        //when
        boardRepository.findAll();
        //then

    }
    @Test
    public void findById_test2(){
        int id=1;
        System.out.println("start - 1");
        Board board=boardRepository.findById(id);
        System.out.println("start - 2");
        System.out.println(board.getUser().getId());
        System.out.println("start - 3");
        System.out.println(board.getUser().getUsername());
    }

    @Test
    public void findById_test(){
        int id =1;

        boardRepository.findById(id);
    }
}
