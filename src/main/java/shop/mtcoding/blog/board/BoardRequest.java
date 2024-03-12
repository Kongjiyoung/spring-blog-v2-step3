package shop.mtcoding.blog.board;

public class BoardRequest {

    public static class SaveDTO{
        private String title;
        private String content;
        private String username;

        public Board toEntity(){
            return new Board(title, content, username);
        }
    }
}
