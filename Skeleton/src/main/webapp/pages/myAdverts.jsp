<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="base" tagdir="/WEB-INF/tags" %>


<base:page title="Profile">
	<jsp:attribute name="page_header">
		<h1>${user.userName}'s adverts</h1>
	</jsp:attribute>

	<jsp:body>
		<c:if test="${not empty ads}">
			<table class="table table-striped table-hover" id="accordion">
				<tr><th>Title</th><th>Price</th><th></th><th></th></tr>
				<c:forEach var="ad" items="${ads}">
					<tr>
						<td><a data-toggle="collapse" data-parent="#accordion" href="#collapse${ad.advId}"><c:out value="${ad.title}"/></a></td>
						<td><a data-toggle="collapse" data-parent="#accordion" href="#collapse${ad.advId}"><c:out value="${ad.price}"/></a></td>
						<td><a class="btn btn-dafault" data-toggle="collapse" data-parent="#accordion" href="#collapse${ad.advId}">Show Interestees</a></td>
						<td><a class="btn btn-dafault" href="advert?id=${ad.advId}">Open Ad</a></td>
					</tr>
					<tr></tr>
					<tr id="collapse${ad.advId}" class="collapse"><td colspan="5">
						<c:if test="${not empty ad.bookmarks}">
							<span class="glyphicon glyphicon-chevron-right"></span> Interestees:
							<table class="table table-striped table-hover">
								<c:forEach var="bm" items="${ad.bookmarks}">
									<c:if test="${bm.interested == true}">
										<tr>
											<td><a><c:out value="${bm.user.userName}"/></a></td>
											<td><a><c:out value="${bm.user.email}"/></a></td>
											<td><a class="btn btn-dafault" href="invite?usrId=${bm.user.usrId}&adId=${ad.advId}">Invite to event</a></td>
											<td><a class="btn btn-dafault" href="contact?usrId=${bm.user.usrId}">Contact</a></td>
										</tr>
									</c:if>
								</c:forEach>
							</table>
						</c:if>
						<c:if test="${empty ad.bookmarks}">
							<span class="glyphicon glyphicon-chevron-right"></span> No Interestees.
						</c:if>
					</td></tr>
				</c:forEach>
			</table>
		</c:if>
		<c:if test="${empty ads}">
			No Ads available
		</c:if>
	</jsp:body>
</base:page>
