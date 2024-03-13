package shop.mtcoding.blog.board;

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
    @Test
    public void updateById_test(){
        // given
        int id = 1;
        String title = "제목수정1";
        String content = "내용수정1";
        String username = "bori";

        // when
        boardPersistRepository.updateById(id, title, content, username);

        // then
        Board board = boardPersistRepository.findById(id);
        System.out.println("updateById_test/board : "+board);
        assertThat(board.getTitle()).isEqualTo("제목수정1");
        assertThat(board.getContent()).isEqualTo("내용수정1");
        assertThat(board.getUsername()).isEqualTo("bori");
    }
    @Test
    public void deleteById_test(){
        // given
        int id=1;
        // when
        boardPersistRepository.deleteById(id);
        // then
        List<Board> boardList = boardPersistRepository.findAll();
        assertThat(boardList.size()).isEqualTo(4);
    }
    @Test
    public void findById_test(){
        // given
        int id=1;
        // when
        Board board = boardPersistRepository.findById(id);
        System.out.println("findById_test"+board);

        // then
        assertThat(board.getTitle()).isEqualTo("제목1");
        assertThat(board.getUsername()).isEqualTo("ssar");
    }

}