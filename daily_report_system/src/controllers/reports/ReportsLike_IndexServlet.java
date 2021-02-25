package controllers.reports;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Liked_list;
import models.Report;
import utils.DBUtil;

/**
 * Servlet implementation class ReportsLike_ShowServlet
 */
@WebServlet("/reports/like_index")
public class ReportsLike_IndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportsLike_IndexServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    //各レポートのいいね記録を一覧表示
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //データベース接続
        EntityManager em = DBUtil.createEntityManager();
        Report r = em.find(Report.class, Integer.parseInt(request.getParameter("id")));

        //ページ設定
        int page;
        try{
            page = Integer.parseInt(request.getParameter("page"));
        } catch(Exception e) {
            page = 1;
        }

        //全いいね記録のデータ取得
        List<Liked_list> liked_lists = em.createNamedQuery("getAllLiked_lists", Liked_list.class)
                                  .setParameter("report", r)
                                  .setFirstResult(15 * (page - 1))
                                  .setMaxResults(15)
                                  .getResultList();

        //いいねの合計数を取得
        long like_count = (long)em.createNamedQuery("getLike_Count", Long.class)
                                  .setParameter("report", r)
                                  .getSingleResult();

        em.close();

        //各取得データのセット
        request.setAttribute("liked_lists", liked_lists);   //いいね記録のデータリスト
        request.setAttribute("page", page);     //件数表示に必要
        request.setAttribute("like_count", like_count);     //ページに必要
        request.setAttribute("id", request.getParameter("id"));     //いいね詳細のページバック

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/reports/liked_index.jsp");
        rd.forward(request, response);
    }

}
