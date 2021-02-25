<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <h2>いいね　一覧</h2>
        <table id="report_liked">
            <tbody>
                <tr>
                    <th class="liked_list_name">氏名</th>
                    <th class="liked_list_date">日付</th>
                </tr>
                <c:forEach var="liked_list" items="${liked_lists}" varStatus="status">
                    <tr class="row${status.count % 2}">
                        <td class="liked_list_name"><c:out value="${liked_list.employee.name}" /></td>
                        <td class="liked_list_date"><fmt:formatDate value='${liked_list.updated_at}' pattern='yyyy-MM-dd HH:mm:ss' /></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <div id="pagination">
            （全 ${like_count} 件）<br />
            <c:forEach var="i" begin="1" end="${((like_count - 1) / 15) + 1}" step="1">
                <c:choose>
                    <c:when test="${i == page}">
                        <c:out value="${i}" />&nbsp;
                    </c:when>
                    <c:otherwise>
                        <a href="<c:url value='/reports/like_index?page=${i}' />"><c:out value="${i}" /></a>&nbsp;
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>
        <p><a href="<c:url value='/reports/show?id=${id}' />">日報詳細へ戻る</a></p>
    </c:param>
</c:import>



