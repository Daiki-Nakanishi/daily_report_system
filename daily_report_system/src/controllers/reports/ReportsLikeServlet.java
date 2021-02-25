package controllers.reports;

import java.io.IOException;
import java.sql.Timestamp;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import models.Liked_list;
import models.Report;
import utils.DBUtil;

/**
 * Servlet implementation class ReportsLikeServlet
 */
@WebServlet("/reports/like")
public class ReportsLikeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportsLikeServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    //該当レポートに対するいいね処理
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //データベース接続
        EntityManager em = DBUtil.createEntityManager();
        Report r = em.find(Report.class, Integer.parseInt(request.getParameter("id")));

        //新規いいね記録の作成・データのセット
        Liked_list l = new Liked_list();
        l.setReport(r);
        l.setEmployee((Employee)request.getSession().getAttribute("login_employee"));
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        l.setCreated_at(currentTime);
        l.setUpdated_at(currentTime);

        //データベースに登録
        em.getTransaction().begin();
        em.persist(l);
        em.getTransaction().commit();

        //改めて該当レポートへのいいね総数を取得
        long like_count = (long)em.createNamedQuery("getLike_Count", Long.class)
                .setParameter("report", r)
                .getSingleResult();
        r.setLike_count(like_count);

        //いいね総数を更新登録
        em.getTransaction().begin();
        em.getTransaction().commit();
        em.close();

        request.getSession().setAttribute("flush", "いいねしました");
        response.sendRedirect(request.getContextPath() + "/reports/index");
    }
}
