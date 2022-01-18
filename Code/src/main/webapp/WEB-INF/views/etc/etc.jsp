<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Code</title>
<%@ include file="/WEB-INF/views/inc/asset.jsp" %>
<script src="/code/asset/js/highcharts.js"></script>
<style>
	
</style>
</head>
<body>
	<!-- etc/etc.jsp -->
	<main class="main">
		<%@include file="/WEB-INF/views/inc/header.jsp" %>
		<section class="content">
			
			<h3>유저 활동 현황 <small>표</small></h3>
			
			<table class="table table-bordered">
				<tr>
					<th>등급</th>
					<th>이름</th>
					<th>아이디</th>
					<th>게시물</th>
					<th>댓글</th>
				</tr>
				<c:forEach items="${list}" var="dto">
				<tr>
					<td>${dto.lv}</td>
					<td>${dto.name}</td>
					<td>${dto.id}</td>
					<td>${dto.count}</td>
					<td>${dto.ccount}</td>
				</tr>
				</c:forEach>
			</table>
			
			<h3>유저 활동 현황 <small>차트</small></h3>
			
			<table>
				<tr>
					<td><div id="chart1"></div></td>
				</tr>
				<tr>
					<td><div id="chart2"></div></td>
				</tr>
			</table>
			
			
		</section>
		<%@include file="/WEB-INF/views/inc/footer.jsp" %>
	</main>
	
	<script>
	Highcharts.chart('chart1', {
	    chart: {
	        plotBackgroundColor: null,
	        plotBorderWidth: null,
	        plotShadow: false,
	        type: 'pie'
	    },
	    title: {
	        text: '유저별 게시물수'
	    },
	    tooltip: {
	        pointFormat: '{point.name}: <b>{point.y}개</b>'
	    },
	    accessibility: {
	        point: {
	            valueSuffix: '%'
	        }
	    },
	    plotOptions: {
	        pie: {
	            allowPointSelect: true,
	            cursor: 'pointer',
	            dataLabels: {
	                enabled: true,
	                format: '<b>{point.name}</b>: {point.y}개'
	            }
	        }
	    },
	    series: [{
	        name: 'Brands',
	        colorByPoint: true,
	        data: [
	        	<c:forEach items="${list}" var="dto">
	        {
	            name: '${dto.name}(${dto.id})',
	            y: ${dto.count}
	        }
	        ,
	        </c:forEach>
	        ]
	    }]
	});
	
	
	Highcharts.chart('chart2', {
	    chart: {
	        plotBackgroundColor: null,
	        plotBorderWidth: null,
	        plotShadow: false,
	        type: 'pie'
	    },
	    title: {
	        text: '유저별 댓글수'
	    },
	    tooltip: {
	        pointFormat: '{point.name}: <b>{point.y}개</b>'
	    },
	    accessibility: {
	        point: {
	            valueSuffix: '%'
	        }
	    },
	    plotOptions: {
	        pie: {
	            allowPointSelect: true,
	            cursor: 'pointer',
	            dataLabels: {
	                enabled: true,
	                format: '<b>{point.name}</b>: {point.y}개'
	            }
	        }
	    },
	    series: [{
	        name: 'Brands',
	        colorByPoint: true,
	        data: [
	        	<c:forEach items="${list}" var="dto">
	        {
	            name: '${dto.name}(${dto.id})',
	            y: ${dto.ccount}
	        }
	        ,
	        </c:forEach>
	        ]
	    }]
	});
	</script>
</body>
</html>







