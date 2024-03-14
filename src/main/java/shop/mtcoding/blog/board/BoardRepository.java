package shop.mtcoding.blog.board;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import shop.mtcoding.blog.user.User;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class BoardRepository {
    private final EntityManager em;

    public List<Board> findAllV2() {
        String q1 = "select b from Board b order by b.id desc";
        List<Board> boardList = em.createQuery(q1, Board.class).getResultList();
        int[] idx = boardList.stream().mapToInt(board -> board.getUser().getId()).distinct().toArray();

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
            String id = "id";
            query.setParameter(id + i, idx[i]);
        }
        List<User> userList = query.getResultList();

//        for (Board board : boardList) {
//            for (User user : userList) {
//                if (board.getUser().getId()==user.getId()) {
//                    board.setUser(user);
//                }
//            }
//        }

        List<Board> newBoardList=boardList.stream().filter(board -> {
            for (User user : userList) {
                if (board.getUser().getId() == user.getId()) {
                    board.setUser(user);
                    return true;
                }
            }
            return false;
        }).toList();

//        List<Board> newBoardList2=boardList.stream().filter(board -> {
//            for (User user : userList) {
//                if (board.getUser().getId() == user.getId()) {
//                    board.setUser(user);
//                }
//            }
//            return false;
//        }).toList();

        return newBoardList;
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
