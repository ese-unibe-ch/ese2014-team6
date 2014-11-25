<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="base" tagdir="/WEB-INF/tags" %>


<base:page title="Advert - ${(editable)?('edit'):(adForm.title)}">
	<jsp:attribute name="customHead">
		<script type="text/javascript"  src="js/adp_tabs.js"></script>
		<script type="text/javascript" src="http://maps.googleapis.com/maps/api/js?sensor=false"></script>
		<script type="text/javascript" src="js/map.js"></script>
		<script type="text/javascript"  src="js/adp_carousel.js"></script>
	</jsp:attribute>

	<jsp:attribute name="page_header">
		<c:if test="${editable}">
			<h1>Advert <small>edit mode</small></h1>
		</c:if>
		<c:if test="${editable == false}">
			<h1>${adForm.title}</h1>
		</c:if>
	</jsp:attribute>
		
	<jsp:body>
		<c:if test="${editable == true}">
			<div class="stepwizard">
				<div class="stepwizard-row setup-panel">
					<div class="stepwizard-step">
						<a href="#main" type="button" class="btn btn-primary btn-circle"> </a>
						<p>Info</p>
					</div>
					<div class="stepwizard-step">
						<a href="#map" type="button" class="btn btn-default btn-circle"> </a>
						<p>Map</p>
					</div>
					<div class="stepwizard-step">
						<a href="#pics" type="button" class="btn btn-default btn-circle" ${adForm.id == 0 ? 'disabled="disabled"':''}> </a>
						<p>Pictures</p>
					</div>
				</div>
			</div>
		</c:if>
		
		<form:form method="post" modelAttribute="adForm" action="advert" id="adForm" cssClass="form-horizontal"  enctype="multipart/form-data" autocomplete="off" role="form">
			
			<div class="row ${(editable)?('setup-content'):('') }" id="pics">
				<div class="col-xs-12">
					<div class="col-md-12">
						<%@include file="/WEB-INF/jspf/adpage_parts/adp_pictures.jspf"%>
						<c:if test="${editable == true}">
							<button class="btn btn-primary prevBtn btn-lg" type="button" >Previous</button>
						</c:if>
					</div>
				</div>
			</div>
			<div class="row ${(editable)?('setup-content'):('') }" id="main">
				<div class="col-xs-12">
					<div class="col-md-12">
						<%@include file="/WEB-INF/jspf/adpage_parts/adp_info.jspf"%>
						<c:if test="${editable == true}">
							<button class="btn btn-primary nextBtn btn-lg pull-right"  type="button" >Next</button>
						</c:if>
					</div>
				</div>
			</div>
			<div class="row ${(editable)?('setup-content'):('') }" id="map">
				<div class="col-xs-12">
					<div class="col-md-12">
						<%@include file="/WEB-INF/jspf/adpage_parts/adp_map.jspf"%>
						<c:if test="${editable == true}">
							<button class="btn btn-primary prevBtn btn-lg" type="button" >Previous</button>
							<button class="btn btn-primary nextBtn btn-lg pull-right" type="button" >Next</button>
						</c:if>
					</div>
				</div>
			</div>
			
			<div class="form-group hidden">
				<form:input type="hidden" path="id" id="field-id"/>
				<form:input type="hidden" path="ownerId" id="field-ownerId"/>
			</div>
			
			<div class="col-xs-12 advert-buttongroup">
				<div class="col-md-12 pull-right">
					<div class="form-group">
						<c:if test="${editable == false}">
							<a class="btn btn-primary btn-lg advert-buttongroupbutton" href="bookmark">Bookmark</a>
						</c:if>
						<c:if test="${editable == true}">
							<button type="submit" class="btn btn-primary btn-lg advert-buttongroupbutton" ${adForm.id == 0 ? 'disabled="disabled"':''}>Save</button>
						</c:if>
						<a class="btn btn-default btn-lg advert-buttongroupbutton" 
						href="${editable ? 'discard' : 'contact?id=${adForm.id}'}" ${adForm.id == 0 ? 'disabled="disabled"':''}>${editable ? 'Discard' : 'Contact'}</a>
					</div>
				</div>
			</div>
		</form:form>
	</jsp:body>
</base:page>
