package shop.mtcoding.blog.board;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Import(BoardPersistRepository.class)
@DataJpaTest
public class BoardPersistRepositoryTest {

    @Autowired // DI
    private BoardPersistRepository boardPersistRepository;
    @Autowired // DI
    private EntityManager em;

    @Test
    public void updateById_test(){
        // given
        int id=1;
        String title = "제목 수정1";

        // when
        Board board = boardPersistRepository.findById(id); //조회해서 수정하는 걸 더티체킹
        board.setTitle("id");
        em.flush();
        // then

    }
    @Test
    public void deleteById2_test(){
        // given
        int id=1;
        // when
        //아이디 있는지 확인
        Board board1 = boardPersistRepository.findById(id);
        System.out.println("findById_test : "+board1);
        //삭제
        boardPersistRepository.deleteById2(id);
        //슬립을걸어 데이터 베이스에서 변경됐는지 확인할 수 있음
        try {
            // 3초 동안 스레드를 일시적으로 정지시킵니다.
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        board1 = boardPersistRepository.findById(id);
        System.out.println("findById_test : "+board1);
        List<Board> boardList = boardPersistRepository.findAll();
        System.out.println(boardList);
//        em.flush();
//        List<Board> boardList = boardPersistRepository.findAll();
//        assertThat(boardList.size()).isEqualTo(4);
        // then
        //버퍼에 지고 있는 쿼리를 즉시 전송
//        em.flush(); //강제로 플러시 -> 강제로 버퍼를 흘러보내 삭제를 해줌
    }
    @Test
    public void deleteById_test(){
        // given
        int id=1;
        // when
        boardPersistRepository.deleteById2(id);
        // then
        List<Board> boardList = boardPersistRepository.findAll();
        assertThat(boardList.size()).isEqualTo(4);
    }
    @Test
    public void findById_test(){ //같은 데이터를 조회할때 PC에서 캐싱하는지 확인
        // given
        int id=1;
        // when
        Board board1 = boardPersistRepository.findById(1);
        System.out.println("findById_test"+board1);
        Board board2 = boardPersistRepository.findById(2);
        System.out.println("findById_test"+board2);

        // then
//        assertThat(board1.getTitle()).isEqualTo("제목1");
//        assertThat(board2.getTitle()).isEqualTo("제목1");
//        assertThat(board2.getUsername()).isEqualTo("ssar");
    }
    @Test
    public void findAll_test(){
        // given

        // when
        List<Board> boardList = boardPersistRepository.findAll();

        // then
        System.out.println("findAll_test/size : "+boardList.size());
        System.out.println("findAll_test/username : "+boardList.get(2).getUsername());

        //org.assertj.core.api
        assertThat(boardList.size()).isEqualTo(4);
        assertThat(boardList.get(3).getUsername()).isEqualTo("ssar");
    }
    @Test
    public void seve_test(){
        // given
//        String title="제목5";
//        String content="내용5";
//        String username="ssar";
        Board board = new Board("제목5", "내용5", "ssar");

        // when
//        Board board=boardPersistRepository.save(title,content,username);
        boardPersistRepository.save(board);
        System.out.println("save_test : "+board);
        // then
    }




}