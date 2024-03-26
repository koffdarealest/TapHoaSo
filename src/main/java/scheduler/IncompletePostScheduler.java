package scheduler;

import dao.PostDAO;

import model.Post;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import java.util.Date;
import java.util.List;


public class IncompletePostScheduler implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        List<Post> listIncompletePosts = getIncompletePosts();
        for (Post post : listIncompletePosts) {
            if (isOverdue(post)) {
                String status = post.getStatus();
                PaymentScheduler payment = new PaymentScheduler();
                payment.init();
                if(status.equals("buyerChecking") || status.equals("sellerDeniedComplain") || status.equals("buyerCanceledComplain")) {
                    confirmReceive(post);
                    payment.payMoneyToSeller(post);
                }
                if(status.equals("buyerComplaining")) {
                    cancelPost(post);
                    payment.refundToBuyer(post);
                }
            }
        }
    }

    private List<Post> getIncompletePosts() {
        PostDAO postDAO = new PostDAO();
        List<Post> listUnconfirmedPosts = postDAO.getIncompletePost();
        return listUnconfirmedPosts;
    }

    private boolean isOverdue(Post post) {
        Date now = new Date();
        return post.getUpdatedAt().getTime() + 2 * 60 * 1000 < now.getTime();
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

    private void cancelPost(Post post) {
        try {
            PostDAO postDAO = new PostDAO();
            post.setStatus("cancelled");
            postDAO.updatePost(post);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
