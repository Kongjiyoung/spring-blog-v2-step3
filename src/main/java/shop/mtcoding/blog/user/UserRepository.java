package shop.mtcoding.blog.user;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import shop.mtcoding.blog.board.Board;

@RequiredArgsConstructor
@Repository
public class UserRepository {

    private final EntityManager em;
    @Transactional
    public User updateById(int id, String password, String email){
        User user = findById(id);
        user.setPassword(password);
        user.setEmail(email);
//        user.update(password,email);
        return user;
    }
    public User findById(int id) {
        User user = em.find(User.class, id);
        return user;
    }

    @Transactional
    public User save(User user){
        em.persist(user);
        return user;
    }

    public User findByUsernameAndPassword(UserRequest.LoginDTO reqDTO){
        Query query = em.createQuery("select u from User u where u.username= :username and u.password= :password", User.class); //find못함 id로 찾을때만 가능
        query.setParameter("username", reqDTO.getUsername());
        query.setParameter("password", reqDTO.getPassword());

        return (User) query.getSingleResult();
    }
}
