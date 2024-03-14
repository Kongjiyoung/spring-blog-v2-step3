package shop.mtcoding.blog.board;

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
//    public void findAllV2_test(){
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
    public void findAll1_test(){
        //given

        //when
        List<Board> boardList=boardRepository.findAll();
        int[] id=boardList.stream().mapToInt(board->board.getUser().getId()).distinct().toArray();
        //then

        System.out.println(id);


    }
    @Test
    public void findAll2_test(){
        //given

        //when
        List<Board> boardList=boardRepository.findAll();
        int[] id=boardList.stream().mapToInt(board->board.getUser().getId()).distinct().toArray();
        //then

        System.out.println(id);


    }
    @Test
    public void randomquery_test(){
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
    public void custom_test(){
        List<Board> boardList=boardRepository.findAll();

        int[] userIds = boardList.stream().mapToInt(board->board.getUser().getId()).distinct().toArray(); //mapToInt int형식으로 모으기, distinct [3,2,1,1]->[3,2,1]로 찌그러트리기
        for (int i : userIds){
            System.out.println(i);
        }

        // select * from user_tb where id in (3,2,1,1);
        // select * from user_tb where id in (3,2,1);
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
