package shop.mtcoding.blog.board;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import shop.mtcoding.blog.user.User;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class BoardRepository {
    private final EntityManager em;




    //update board_tb set title =? where id =?
    //update board_tb set content =? where id =?
    //update board_tb set title =? where id =? 쿼리로 짜면 3가지경우의수를 다짜서 만들어야함
    @Transactional
    public void updateById(int id, String title, String content){
        Board board = findById(id);
        board.setTitle(title);
        board.setContent(content);
    }//더티체킹

    @Transactional
    public void deleteById(int id){
        Query query = em.createQuery("delete from Board b where b.id=:id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Transactional
    public Board save(Board board){
        em.persist(board);
        return board;
    }
    public List<Board> findAllV2() {
        String q1 = "select b from Board b order by b.id desc";
        List<Board> boardList = em.createQuery(q1, Board.class).getResultList();
        int[] idx = boardList.stream().mapToInt(board -> board.getUser().getId()).distinct().toArray();
//        List<Inteager> idx = boardList.stream().mapToInt(board -> board.getUser().getId()).distinct().boxed().toArray();

        String q = "select u from User u where u.id in (";
        for (int i = 0; i < idx.length; i++) {
            if (i == idx.length - 1) {
                q = q + ":id" + i + ")";
            } else {
                q = q + ":id" + i + ",";
            }
        }
        Query query = em.createQuery(q, User.class);
        for (int i = 0; i < idx.length; i++) {

            query.setParameter("id" + i, idx[i]);
        }
        List<User> userList = query.getResultList();

//        for (Board board : boardList) {
//            for (User user : userList) {
//                if (board.getUser().getId()==user.getId()) {
//                    board.setUser(user);
//                }
//            }
//        }

        boardList.stream().forEach(b->{
            User user=userList.stream().filter(u->
                    u.getId()==b.getId()).findFirst().get();
            b.setUser(user);
        });
        boardList.stream().forEach(b -> {
            User user = userList.stream().filter(u -> u.getId() == b.getUser().getId()).findFirst().get();
            b.setUser(user);
        });

//        List<Board> newBoardList2=boardList.stream().filter(board -> {
//            for (User user : userList) {
//                if (board.getUser().getId() == user.getId()) {
//                    board.setUser(user);
//                }
//            }
//            return false;
//        }).toList();

        return boardList;
    }

    public List<Board> findAll() {
        Query query = em.createQuery("select b from Board b order by b.id desc", Board.class);
        return query.getResultList();
    }

    public Board findByIdJoinUser(int id) {
        Query query = em.createQuery("select b from Board b join fetch b.user u where b.id= :id", Board.class);
        query.setParameter("id", id);

        return (Board) query.getSingleResult();
    }

    public Board findById(int id) {
        //id, title, content, user_id(이질감), created_at
        Board board = em.find(Board.class, id);
        return board;
    }
}
