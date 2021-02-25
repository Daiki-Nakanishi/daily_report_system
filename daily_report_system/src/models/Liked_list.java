package models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Table(name = "liked_lists")
@NamedQueries({
    //レポートに対するいいね記録の取得（いいね一覧ページで使用）
    @NamedQuery(
        name = "getAllLiked_lists",
        query = "SELECT r FROM Liked_list AS r WHERE r.report = :report ORDER BY r.id DESC"
    ),
    //いいねの件数取得（いいね一覧ページで使用）
    @NamedQuery(
        name = "getLike_Count",
        query = "SELECT COUNT(r) FROM Liked_list AS r WHERE r.report = :report"
    ),
    //その日報へいいね済かチェック（レポート詳細ページで使用）
    @NamedQuery(
        name = "getIsLiked",
        query = "SELECT COUNT(r) FROM Liked_list AS r WHERE r.report = :report AND r.employee = :employee"
    )
})
@Entity
public class Liked_list {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "report_id", nullable = false)
    private Report report;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Column(name = "created_at", nullable = false)
    private Timestamp created_at;

    @Column(name = "updated_at", nullable = false)
    private Timestamp updated_at;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }

}