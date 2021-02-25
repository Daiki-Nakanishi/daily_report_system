package controllers.reports;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import models.Report;
import utils.DBUtil;

/**
 * Servlet implementation class ReportsShowServlet
 */
@WebServlet("/reports/show")
public class ReportsShowServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportsShowServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    //レポート詳細画面の処理
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //データベース接続、データ取得(レポート情報・ログインID)
        EntityManager em = DBUtil.createEntityManager();
        Report r = em.find(Report.class, Integer.parseInt(request.getParameter("id")));
        Employee login_employee = (Employee)request.getSession().getAttribute("login_employee");

        //自分のレポート総数取得
        long reports_count = (long)em.createNamedQuery("getMyReportsCount", Long.class)
                .setParameter("employee", login_employee)
                .getSingleResult();

        //各レポートのいいね件数を取得
        long like_count = (long)em.createNamedQuery("getLike_Count", Long.class)
                .setParameter("report", r)
                .getSingleResult();

        //閲覧者が既にいいね済みか確認
        long like_validation = (long)em.createNamedQuery("getIsLiked", Long.class)
                .setParameter("report", r)
                .setParameter("employee", login_employee)
                .getSingleResult();

        //データの取得
        r.setLike_count(like_count);

        //記録のいいね総数が正しくなかった場合に備え、更新を行う
        em.getTransaction().begin();
        em.getTransaction().commit();
        em.close();

        //買う取得データをセット
        request.setAttribute("report", r);
        request.setAttribute("reports_count", reports_count);
        request.setAttribute("like_count", like_count);
        request.setAttribute("like_validation", like_validation);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/reports/show.jsp");
        rd.forward(request, response);
    }

}