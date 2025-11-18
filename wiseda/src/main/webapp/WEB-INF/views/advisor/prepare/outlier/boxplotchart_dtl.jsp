<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@page import="kr.wise.commons.WiseMetaConfig"%>


<script type="text/javascript" src='<c:url value="/js/plotly/plotly-latest.min.js"/>'></script>


<script type="text/javascript">

$(document).ready(function() {
// 	Plotly.purge('boxplot_chart_div');
});

$(window).on('load',function() {
});


$(window).resize( function(){
});


    
function createBoxPlot(url, param) {
	$("div#chart_stit").text("Box Plot");
	Plotly.purge('boxplot_chart_div');
	Plotly.d3.json(url+"?"+param, function(resjson){
		if(resjson == null) return;
		
		var colors = ['rgba(93, 164, 214, 0.5)', 'rgba(255, 144, 14, 0.5)', 'rgba(44, 160, 101, 0.5)', 'rgba(255, 65, 54, 0.5)', 'rgba(207, 114, 255, 0.5)', 'rgba(127, 96, 0, 0.5)', 'rgba(255, 140, 184, 0.5)', 'rgba(79, 90, 117, 0.5)', 'rgba(222, 223, 0, 0.5)'];
		var data = [];
		for(i=0;i<resjson.length;i++) {
		  var result = {
		    type: 'box',
		    y: resjson[i].colVal,
		    name: resjson[i].colNm,
// 		    boxpoints: 'all',
	 	    boxpoints: 'Outliers',
		    jitter: 0.5,
		    whiskerwidth: 0.5,
		    fillcolor: 'cls',
		    marker: {
		      size: 5
		    },
		    line: {
		      width: 1
		    }
		  };
		  data.push(result);
			
		};


		var layout = {
// 			    title: 'Points Scored by the Top 9 Scoring NBA Players in 2012',
			    yaxis: {
			        autorange: true,
			        showgrid: true,
			        zeroline: true,
// 			        dtick: 5,
			        gridcolor: 'rgb(255, 255, 255)',
// 			        gridwidth: 1,
			        zerolinecolor: 'rgb(255, 255, 255)',
			        zerolinewidth: 2
			    },
			    margin: {
			        l: 40,
			        r: 30,
			        b: 80,
			        t: 20
			    },
			    paper_bgcolor: 'rgb(243, 243, 243)',
			    plot_bgcolor: 'rgb(243, 243, 243)',
			    showlegend: false
			};
		
		Plotly.newPlot('boxplot_chart_div', data, layout);
	}); 
}	

function createScatter(url, param) {
	
	Plotly.purge('boxplot_chart_div');
	$("div#chart_stit").text("Outlier Chart(Scatter Plot)");
	Plotly.d3.json(url+"?"+param, function(resjson){
		if(resjson == null) return;
		
		var colors = ['rgba(93, 164, 214, 0.5)', 'rgba(255, 144, 14, 0.5)', 'rgba(44, 160, 101, 0.5)', 'rgba(255, 65, 54, 0.5)', 'rgba(207, 114, 255, 0.5)', 'rgba(127, 96, 0, 0.5)', 'rgba(255, 140, 184, 0.5)', 'rgba(79, 90, 117, 0.5)', 'rgba(222, 223, 0, 0.5)'];
		var data = [];
		var inlier = {
				  x: resjson.inlier[0],
				  y: resjson.inlier[1],
				  mode: 'markers',
				  type: 'scatter',
				  name: 'Inlier',
				  /* text: ['A-1', 'A-2', 'A-3', 'A-4', 'A-5'],
				  textposition: 'top center',
				  textfont: {
				    family:  'Raleway, sans-serif'
				  }, */
				  marker: { size: 7 }
				};

		var outlier = {
				x: resjson.outlier[0],
				y: resjson.outlier[1],
			  mode: 'markers',
			  type: 'scatter',
			  name: 'Outlier',
			  marker: { size: 7 }
			};

				
		var data = [ inlier, outlier ];

		var layout = {
// 			    title: 'Points Scored by the Top 9 Scoring NBA Players in 2012',
				xaxis: {
			        autorange: true,
			        showgrid: true,
				},
			    yaxis: {
			        autorange: true,
			        showgrid: true,
			        zeroline: true,
// 			        dtick: 5,
			        gridcolor: 'rgb(255, 255, 255)',
// 			        gridwidth: 1,
			        zerolinecolor: 'rgb(255, 255, 255)',
			        zerolinewidth: 2
			    },
			    margin: {
			        l: 40,
			        r: 30,
			        b: 80,
			        t: 20
			    },
			    legend: {
			        y: 0.5,
			        yref: 'paper',
			        font: {
// 			          family: 'Arial, sans-serif',
			          size: 20,
			          color: 'grey',
			        }
			      },
			    paper_bgcolor: 'rgb(243, 243, 243)',
			    plot_bgcolor: 'rgb(243, 243, 243)',
			    showlegend: true
			};
		
		Plotly.newPlot('boxplot_chart_div', data, layout);
	}); 
}	


</script>

	<div >
		<div id="chart_stit" class="stit">Outlier Chart</div><!--컬럼분석 상세정보-->

		<div style="clear:both; height:10px;"><span></span></div>
		
		<div id="boxplot_chart_div"></div>
		
		<div style="clear:both; height:10px;"><span></span></div>
		<!-- 그리드 입력 입력 -->
		
	</div>
	
