package shop.mtcoding.blog.board;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class BoardPersistRepository {
    private final EntityManager em;
    @Transactional
    public void updateById(int id, BoardRequest.updateDTO reqDTO) {
        Board board = findById(id); //영속화된 객체의 상태를 변경하고 트랜잭션이 종료되면 업데이트된다.
        board.update(reqDTO);
    } //더티체킹
    public void deleteById2(int id) {
        Board board =findById(id);
        em.remove(board); //PC에 객체를 지우고, 트랜잭션이 종료되면 객체를 지운다
    }
    public void deleteById(int id) {
//        em.createQuery("delete from Board b where b.id = :id")
//                .setParameter("id",id)
//                .executeUpdate();
    }
    public Board findById(int id) {
        Board board = em.find(Board.class,id); //(받을 클래스, 프라이머리키),
        return board;
    }

    public List<Board> findAll() {
        Query query = em.createQuery("select b from Board b order by b.id desc"); //조회할때는 다 쿼리를 적어야함
        return query.getResultList();
    }
    @Transactional
    public Board save(Board board) {

        //1. 비영속 객체
        em.persist(board);
        //2. board -> 영속 객체
        return board; //return 필요없음

    }


}
