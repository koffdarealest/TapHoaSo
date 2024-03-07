package scheduler;

import dao.PostDAO;

import model.Post;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import java.util.Date;
import java.util.List;


public class PostConfirmScheduler implements Job {


    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        List<Post> listUnconfirmedPosts = getUnconfirmedPosts();
        for (Post post : listUnconfirmedPosts) {
            if (isOverdue(post)) {
                confirmReceive(post);
                PaymentToSeller paymentToSeller = new PaymentToSeller();
                paymentToSeller.init();
                paymentToSeller.payMoneyToSeller(post);
            }
        }
    }

    private List<Post> getUnconfirmedPosts() {
        PostDAO postDAO = new PostDAO();
        List<Post> listUnconfirmedPosts = postDAO.getUnconfirmedPost();
        return listUnconfirmedPosts;
    }

    private boolean isOverdue(Post post) {
        Date now = new Date();
        return post.getUpdatedAt().getTime() + 1 * 60 * 1000 < now.getTime();
    }

    private void confirmReceive(Post post) {
        try {
            PostDAO postDAO = new PostDAO();
            postDAO.confirmReceivePost(post);
            postDAO.updatePost(post);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
