<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@page import="kr.wise.commons.WiseMetaConfig"%>

<html> 
<head>

<script type="text/javascript" src='<c:url value="/js/gojs/go.js"/>'></script>


<title><s:message code="SYS.TRRT.MNG" /></title> <!-- 시스템영역관리 -->

<script type="text/javascript">

var connTrgSchJson = ${codeMap.connTrgSch} ;

var interval = "";

var nodeVal = '';
var compVal = '';

$(document).ready(function() {
	$("#btnList").click(function() {
		
		var url = "<c:url value='/advisor/prepare/udefoutlier/udefoutlier_lst.do' />";
		
		location.href = url;
	});
	
	$("#btnPyScrt").click(function(){

		var url = "<c:url value='/advisor/prepare/udefoutlier/popup/python_scrt.do' />";
		
		var param = "?" + $("#frmUod #udfOtlDtcId").serialize();

		//openLayerPop(url, 500,  400, param) ;

		var vPop = OpenWindow(url+param, "py", 500, 400);
	});   	
   	
	$("#btnResult").click(function(){

		var url = "<c:url value='/advisor/prepare/udefoutlier/popup/result_view.do' />";
		
		var param = "?" + $("#frmUod").serialize();

		var vPop = OpenWindow(url+param, "", 800, 600);
	});
	
	$("#btnNewPyScrt").click(function(){
		var param = $("#frmUod").serialize();
		var urls = '<c:url value="/advisor/prepare/udefoutlier/script/scriptFileCreOne.do"/>';
		
		$.ajax({
	  		url: urls,
	  		async: false,
	  		type: "POST",
	  		data: replacerXssParam(param),
	  		dataType: 'json',
	  		success: function (res) {
	  			showMsgBox("INF", res.RESULT.MESSAGE);
	  		},
	  		error: function (jqXHR, textStatus, errorThrown) {
	  			showMsgBox("ERR", "Error");
	  		}
	  	});
	});
});

$(window).on('load',function() {

	
	init(); 

	$("#btnTblImpSave").show();  

	<c:if test="${wadUod.ibsStatus eq 'U' }" >

		$("#udfOtlDtcNm").val("${wadUod.udfOtlDtcNm}");

		myDiagram.model = go.Model.fromJson(${wadUod.mdlJsonInf}); 
	</c:if>

	
});


$(window).resize(function(){
    
});



var myDiagram;

function init() {
   // if (window.goSamples) goSamples();  // init for these samples -- you don't need to call this
    var $ = go.GraphObject.make;  // for conciseness in defining templates

    myDiagram =
      $(go.Diagram, "myDiagramDiv",  // must name or refer to the DIV HTML element
        {
          initialContentAlignment: go.Spot.Center,
          allowDrop: true,  // must be true to accept drops from the Palette
          "LinkDrawn": showLinkLabel,  // this DiagramEvent listener is defined below
          "LinkRelinked": showLinkLabel,
          scrollsPageOnFocus: false,
          "undoManager.isEnabled": true,  // enable undo & redo
          click: function(e){

				
           }
          
        });

   
    // when the document is modified, add a "*" to the title and enable the "Save" button
    myDiagram.addDiagramListener("Modified", function(e) {

      var button = document.getElementById("SaveButton");
      if (button) button.disabled = !myDiagram.isModified;
      var idx = document.title.indexOf("*");
      if (myDiagram.isModified) {
        if (idx < 0) document.title += "*";
      } else {
        if (idx >= 0) document.title = document.title.substr(0, idx);
      }
    });

    // helper definitions for node templates

    function nodeStyle() {
      return [
        // The Node.location comes from the "loc" property of the node data,
        // converted by the Point.parse static method.
        // If the Node.location is changed, it updates the "loc" property of the node data,
        // converting back using the Point.stringify static method.
        new go.Binding("location", "loc", go.Point.parse).makeTwoWay(go.Point.stringify),
        {
          // the Node.location is at the center of each node
          locationSpot: go.Spot.Center,
          //isShadowed: true,"D:/doc/BDQ/site/samples/flowchart.html"
          //shadowColor: "#888",
          // handle mouse enter/leave events to show/hide the ports
          mouseEnter: function (e, obj) { showPorts(obj.part, true); },
          mouseLeave: function (e, obj) { showPorts(obj.part, false); }
        }
      ];
    }

    // Make all ports on a node visible when the mouse is over the node
    function showPorts(node, show) {
      var diagram = node.diagram;
      if (!diagram || diagram.isReadOnly || !diagram.allowLink) return;
      node.ports.each(function(port) {
          port.stroke = (show ? "white" : null);
        });
    }

    // Define a function for creating a "port" that is normally transparent.
    // The "name" is used as the GraphObject.portId, the "spot" is used to control how links connect
    // and where the port is positioned on the node, and the boolean "output" and "input" arguments
    // control whether the user can draw links from or to the port.
    function makePort(name, spot, output, input) {
      // the port is basically just a small circle that has a white stroke when it is made visible
      return $(go.Shape, "Circle",
               {
                  fill: "transparent",
                  stroke: null,  // this is changed to "white" in the showPorts function
                  desiredSize: new go.Size(8, 8),
                  alignment: spot, alignmentFocus: spot,  // align the port on the main Shape
                  portId: name,  // declare this object to be a "port"
                  fromSpot: spot, toSpot: spot,  // declare where links may connect at this port
                  fromLinkable: output, toLinkable: input,  // declare whether the user may draw links to/from here
                  cursor: "pointer"  // show a different cursor to indicate potential link point
               });
    }

    // define the Node templates for regular nodes

    var lightText = 'whitesmoke';

    var vColorDP = "#2E9AFE";
    var vColorOD = "#04B486"; 
    var vColorMD = "#FF8000";

    myDiagram.nodeTemplateMap.add("",  // the default category
      $(go.Node, "Spot", nodeStyle(),
        // the main object is a Panel that surrounds a TextBlock with a rectangular Shape
        $(go.Panel, "Auto",
          $(go.Shape, "Rectangle",
            { minSize: new go.Size(150,28), fill: "#00A9C9", stroke: null },
            new go.Binding("figure", "figure")),
          $(go.TextBlock,
            {
              font: "bold 11pt Helvetica, Arial, sans-serif",
              stroke: lightText,
              margin: 8,
              maxSize: new go.Size(160, NaN),
              wrap: go.TextBlock.WrapFit,
              editable: true
            },
            new go.Binding("text").makeTwoWay())
            
        ),
        // four named ports, one on each side:
        makePort("T", go.Spot.Top, false, true),
        makePort("L", go.Spot.Left, true, true),
        makePort("R", go.Spot.Right, true, true),
        makePort("B", go.Spot.Bottom, true, false)
      ));

    myDiagram.nodeTemplateMap.add("DataImp",
      $(go.Node, "Spot", nodeStyle(),
        $(go.Panel, "Auto",
          $(go.Shape, "Rectangle",
            { minSize: new go.Size(150,28), fill: "#79C900", stroke: null }),
          $(go.TextBlock,
            { font: "bold 11pt Helvetica, Arial, sans-serif",textAlign: "center", stroke: lightText },  
            new go.Binding("text"))
        ),
        { // second arg will be this GraphObject, which in this case is the Node itself:
		    click: function(e, node) {

		      var locLeft = node.data.loc.split(" ");
		     
			  if(locLeft[1] > 0) return;
	              
			  nodeVal = node;
			  compVal = 'DataImp';
			  
		      compDataImp(node);
		    }
        },        
        // three named ports, one on each side except the top, all output only:
        makePort("L", go.Spot.Left, true, false),
        makePort("R", go.Spot.Right, true, false),
        makePort("B", go.Spot.Bottom, true, false)
      ));

 

    myDiagram.nodeTemplateMap.add("SubsetCol",
	  $(go.Node, "Spot", nodeStyle(),
	    $(go.Panel, "Auto",
	      $(go.Shape, "Rectangle",
	        { minSize: new go.Size(150,28), fill: vColorDP, stroke: null }), 
	      $(go.TextBlock, "SubsetCol",
	        { font: "bold 11pt Helvetica, Arial, sans-serif",textAlign: "center", stroke: lightText },  
	        new go.Binding("text"))
	    ),
        { // second arg will be this GraphObject, which in this case is the Node itself:
		    click: function(e, node) {
				
		    	var locLeft = node.data.loc.split(" ");

		    	//alert(locLeft[1]); 
		    			    	
				if(locLeft[1] > 0) return;

				nodeVal = node;
				compVal = 'SubsetCol';
				  
				compColDaseDv(node);   
		    }
        },
	    // three named ports, one on each side except the top, all output only:
	    makePort("T", go.Spot.Top, false, true),
	    makePort("L", go.Spot.Left, true, false),
	    makePort("R", go.Spot.Right, true, false),
	    makePort("B", go.Spot.Bottom, true, false)
	  ));

    myDiagram.nodeTemplateMap.add("SubsetRow",
       $(go.Node, "Spot", nodeStyle(),
         $(go.Panel, "Auto",
           $(go.Shape, "Rectangle",
             { minSize: new go.Size(150,28), fill: vColorDP, stroke: null }), 
           $(go.TextBlock, "SubsetRow",
             { font: "bold 11pt Helvetica, Arial, sans-serif",textAlign: "center", stroke: lightText },  
             new go.Binding("text"))
         ),
         { // second arg will be this GraphObject, which in this case is the Node itself:
 		    click: function(e, node) {

 		    	var locLeft = node.data.loc.split(" ");

				if(locLeft[1] > 0) return;

				nodeVal = node;
				compVal = 'SubsetRow';
				
				compRowDaseDv(node);   
 		    }
         },
         // three named ports, one on each side except the top, all output only:
         makePort("T", go.Spot.Top, false, true),
         makePort("L", go.Spot.Left, true, false),
         makePort("R", go.Spot.Right, true, false),
         makePort("B", go.Spot.Bottom, true, false)
       ));

    myDiagram.nodeTemplateMap.add("Join",
 	   $(go.Node, "Spot", nodeStyle(),
 	     $(go.Panel, "Auto",
 	       $(go.Shape, "Rectangle",
 	         { minSize: new go.Size(150,28), fill: vColorDP, stroke: null }), 
 	       $(go.TextBlock, "Join",
 	         { font: "bold 11pt Helvetica, Arial, sans-serif",textAlign: "center", stroke: lightText },  
 	         new go.Binding("text"))
 	     ),
         { // second arg will be this GraphObject, which in this case is the Node itself:
 		    click: function(e, node) {
 		    	var locLeft = node.data.loc.split(" ");

				//if(locLeft[0] > 0) return;
				
				nodeVal = node;
				compVal = 'Join';
				
				compJoin(node);
 		    }
         },
 	     // three named ports, one on each side except the top, all output only:
 	     makePort("T", go.Spot.Top, false, true),
 	     makePort("L", go.Spot.Left, true, false),
 	     makePort("R", go.Spot.Right, true, false),
 	     makePort("B", go.Spot.Bottom, true, false)
 	   ));

    myDiagram.nodeTemplateMap.add("Filter",
        $(go.Node, "Spot", nodeStyle(),
          $(go.Panel, "Auto",
            $(go.Shape, "Rectangle",
              { minSize: new go.Size(150,28), fill: vColorDP, stroke: null }), 
            $(go.TextBlock, "Filter",
              { font: "bold 11pt Helvetica, Arial, sans-serif",textAlign: "center", stroke: lightText },  
              new go.Binding("text"))
          ),
          { // second arg will be this GraphObject, which in this case is the Node itself:
  		    click: function(e, node) {

  		    	var locLeft = node.data.loc.split(" ");

		    	//alert(locLeft[1]); 
		    			    	
				if(locLeft[1] > 0) return;

				nodeVal = node;
				compVal = 'Filter';
				
				compDataFilter(node);   
  		    }
          },
          // three named ports, one on each side except the top, all output only:
          makePort("T", go.Spot.Top, false, true),
          makePort("L", go.Spot.Left, true, false),
          makePort("R", go.Spot.Right, true, false),
          makePort("B", go.Spot.Bottom, true, false)
        ));
    	    
    myDiagram.nodeTemplateMap.add("Cleansing",
        $(go.Node, "Spot", nodeStyle(),
          $(go.Panel, "Auto",
            $(go.Shape, "Rectangle",
              { minSize: new go.Size(150,28), fill: vColorDP, stroke: null }), 
            $(go.TextBlock, "Cleansing",
              { font: "bold 11pt Helvetica, Arial, sans-serif",textAlign: "center", stroke: lightText },  
              new go.Binding("text"))
          ),
          { // second arg will be this GraphObject, which in this case is the Node itself:
  		    click: function(e, node) {

  		    	var locLeft = node.data.loc.split(" ");

				//if(locLeft[0] > 0) return;
				
				nodeVal = node;
				compVal = 'Cleansing';
				
				compDataCln(node);
  		    }
          },
          // three named ports, one on each side except the top, all output only:
          makePort("T", go.Spot.Top, false, true),
          makePort("L", go.Spot.Left, true, false),
          makePort("R", go.Spot.Right, true, false),
          makePort("B", go.Spot.Bottom, true, false)
        ));

    myDiagram.nodeTemplateMap.add("ODBP",
        $(go.Node, "Spot", nodeStyle(),
          $(go.Panel, "Auto",
            $(go.Shape, "Rectangle",
              { minSize: new go.Size(150,28), fill: vColorOD, stroke: null }), 
            $(go.TextBlock, "ODBP",
              { font: "bold 11pt Helvetica, Arial, sans-serif",textAlign: "center", stroke: lightText },  
              new go.Binding("text"))
          ),
          { // second arg will be this GraphObject, which in this case is the Node itself:
  		    click: function(e, node) {
  		    	var locLeft = node.data.loc.split(" ");

				//if(locLeft[0] > 0) return;
				
				nodeVal = node;
				compVal = 'ODBP';
				
  		      	compBoxplot(node);
  		    }
          },
          // three named ports, one on each side except the top, all output only:
          makePort("T", go.Spot.Top, false, true),
          makePort("L", go.Spot.Left, true, false),
          makePort("R", go.Spot.Right, true, false),
          makePort("B", go.Spot.Bottom, true, false)
        )); 

    myDiagram.nodeTemplateMap.add("ODIsFr",
        $(go.Node, "Spot", nodeStyle(),
          $(go.Panel, "Auto",
            $(go.Shape, "Rectangle",
              { minSize: new go.Size(150,28), fill: vColorOD, stroke: null }), 
            $(go.TextBlock, "ODIsFr",
              { font: "bold 11pt Helvetica, Arial, sans-serif",textAlign: "center", stroke: lightText },  
              new go.Binding("text"))
          ),
          { // second arg will be this GraphObject, which in this case is the Node itself:
  		    click: function(e, node) {
  		    	var locLeft = node.data.loc.split(" ");

				//if(locLeft[0] > 0) return;
				
				nodeVal = node;
				compVal = 'ODIsFr';
				
  		      	compIsFr(node);
  		    }
          },
          // three named ports, one on each side except the top, all output only:
          makePort("T", go.Spot.Top, false, true),
          makePort("L", go.Spot.Left, true, false),
          makePort("R", go.Spot.Right, true, false),
          makePort("B", go.Spot.Bottom, true, false)
        ));

    myDiagram.nodeTemplateMap.add("ODLOF",
        $(go.Node, "Spot", nodeStyle(),
          $(go.Panel, "Auto",
            $(go.Shape, "Rectangle",
              { minSize: new go.Size(150,28), fill: vColorOD, stroke: null }), 
            $(go.TextBlock, "ODLOF",
              { font: "bold 11pt Helvetica, Arial, sans-serif",textAlign: "center", stroke: lightText },  
              new go.Binding("text"))
          ),
          { // second arg will be this GraphObject, which in this case is the Node itself:
  		    click: function(e, node) {
  		    	var locLeft = node.data.loc.split(" ");

				//if(locLeft[0] > 0) return;  
				
				nodeVal = node;
				compVal = 'ODLOF'; 
				
  		      	compLof(node); 
  		    }
          },
          // three named ports, one on each side except the top, all output only:
          makePort("T", go.Spot.Top, false, true),
          makePort("L", go.Spot.Left, true, false),
          makePort("R", go.Spot.Right, true, false),
          makePort("B", go.Spot.Bottom, true, false)
        ));

    myDiagram.nodeTemplateMap.add("ODOCV",
        $(go.Node, "Spot", nodeStyle(),
          $(go.Panel, "Auto",
            $(go.Shape, "Rectangle",
              { minSize: new go.Size(150,28), fill: vColorOD, stroke: null }), 
            $(go.TextBlock, "ODOCV",
              { font: "bold 11pt Helvetica, Arial, sans-serif",textAlign: "center", stroke: lightText },  
              new go.Binding("text"))
          ),
          { // second arg will be this GraphObject, which in this case is the Node itself:
  		    click: function(e, node) {
  		    	var locLeft = node.data.loc.split(" ");

				//if(locLeft[0] > 0) return;
				
				nodeVal = node;
				compVal = 'ODOCV';
				
  		      	compOcv(node);
  		    }
          },
          // three named ports, one on each side except the top, all output only:
          makePort("T", go.Spot.Top, false, true),
          makePort("L", go.Spot.Left, true, false),
          makePort("R", go.Spot.Right, true, false),
          makePort("B", go.Spot.Bottom, true, false)
        ));

    myDiagram.nodeTemplateMap.add("ODELEV",
        $(go.Node, "Spot", nodeStyle(),
          $(go.Panel, "Auto",
            $(go.Shape, "Rectangle",
              { minSize: new go.Size(150,28), fill: vColorOD, stroke: null }), 
            $(go.TextBlock, "ODELEV",
              { font: "bold 11pt Helvetica, Arial, sans-serif",textAlign: "center", stroke: lightText },  
              new go.Binding("text"))
          ),
          { // second arg will be this GraphObject, which in this case is the Node itself:
  		    click: function(e, node) {
  		    	var locLeft = node.data.loc.split(" ");

				//if(locLeft[0] > 0) return;
				
				nodeVal = node;
				compVal = 'ODELEV';
				
  		      	compElev(node);
  		    }
          },
          // three named ports, one on each side except the top, all output only:
          makePort("T", go.Spot.Top, false, true),
          makePort("L", go.Spot.Left, true, false),
          makePort("R", go.Spot.Right, true, false),
          makePort("B", go.Spot.Bottom, true, false)
        ));
    
    myDiagram.nodeTemplateMap.add("RRGR",
            $(go.Node, "Spot", nodeStyle(),
              $(go.Panel, "Auto",
                $(go.Shape, "Rectangle",
                  { minSize: new go.Size(150,28), fill: vColorOD, stroke: null }), 
                $(go.TextBlock, "RRGR",
                  { font: "bold 11pt Helvetica, Arial, sans-serif",textAlign: "center", stroke: lightText },  
                  new go.Binding("text"))
              ),
              { // second arg will be this GraphObject, which in this case is the Node itself:
      		    click: function(e, node) {
      		    	var locLeft = node.data.loc.split(" ");

    				//if(locLeft[0] > 0) return;
    				
    				nodeVal = node;
    				compVal = 'RRGR';
    				
      		      	compRrgr(node);
      		    }
              },
              // three named ports, one on each side except the top, all output only:
              makePort("T", go.Spot.Top, false, true),
              makePort("L", go.Spot.Left, true, false),
              makePort("R", go.Spot.Right, true, false),
              makePort("B", go.Spot.Bottom, true, false)
            ));
    
    myDiagram.nodeTemplateMap.add("MAD",
            $(go.Node, "Spot", nodeStyle(),
              $(go.Panel, "Auto",
                $(go.Shape, "Rectangle",
                  { minSize: new go.Size(150,28), fill: vColorOD, stroke: null }), 
                $(go.TextBlock, "MAD",
                  { font: "bold 11pt Helvetica, Arial, sans-serif",textAlign: "center", stroke: lightText },  
                  new go.Binding("text"))
              ),
              { // second arg will be this GraphObject, which in this case is the Node itself:
      		    click: function(e, node) {
      		    	var locLeft = node.data.loc.split(" ");

    				//if(locLeft[0] > 0) return;
    				
    				nodeVal = node;
    				compVal = 'MAD';
    				
      		      	compMad(node);
      		    }
              },
              // three named ports, one on each side except the top, all output only:
              makePort("T", go.Spot.Top, false, true),
              makePort("L", go.Spot.Left, true, false),
              makePort("R", go.Spot.Right, true, false),
              makePort("B", go.Spot.Bottom, true, false)
            ));
    myDiagram.nodeTemplateMap.add("CBLOF",
            $(go.Node, "Spot", nodeStyle(),
              $(go.Panel, "Auto",
                $(go.Shape, "Rectangle",
                  { minSize: new go.Size(150,28), fill: vColorOD, stroke: null }), 
                $(go.TextBlock, "CBLOF",
                  { font: "bold 11pt Helvetica, Arial, sans-serif",textAlign: "center", stroke: lightText },  
                  new go.Binding("text"))
              ),
              { // second arg will be this GraphObject, which in this case is the Node itself:
      		    click: function(e, node) {
      		    	var locLeft = node.data.loc.split(" ");

    				//if(locLeft[0] > 0) return;
    				
    				nodeVal = node;
    				compVal = 'CBLOF';
    				
      		      	compCblof(node);
      		    }
              },
              // three named ports, one on each side except the top, all output only:
              makePort("T", go.Spot.Top, false, true),
              makePort("L", go.Spot.Left, true, false),
              makePort("R", go.Spot.Right, true, false),
              makePort("B", go.Spot.Bottom, true, false)
            ));
    myDiagram.nodeTemplateMap.add("KNN",
            $(go.Node, "Spot", nodeStyle(),
              $(go.Panel, "Auto",
                $(go.Shape, "Rectangle",
                  { minSize: new go.Size(150,28), fill: vColorOD, stroke: null }), 
                $(go.TextBlock, "KNN",
                  { font: "bold 11pt Helvetica, Arial, sans-serif",textAlign: "center", stroke: lightText },  
                  new go.Binding("text"))
              ),
              { // second arg will be this GraphObject, which in this case is the Node itself:
      		    click: function(e, node) {
      		    	var locLeft = node.data.loc.split(" ");

    				//if(locLeft[0] > 0) return;
    				
    				nodeVal = node;
    				compVal = 'KNN';
    				
      		      	compKnn(node);
      		    }
              },
              // three named ports, one on each side except the top, all output only:
              makePort("T", go.Spot.Top, false, true),
              makePort("L", go.Spot.Left, true, false),
              makePort("R", go.Spot.Right, true, false),
              makePort("B", go.Spot.Bottom, true, false)
            ));
    myDiagram.nodeTemplateMap.add("AKNN",
            $(go.Node, "Spot", nodeStyle(),
              $(go.Panel, "Auto",
                $(go.Shape, "Rectangle",
                  { minSize: new go.Size(150,28), fill: vColorOD, stroke: null }), 
                $(go.TextBlock, "AKNN",
                  { font: "bold 11pt Helvetica, Arial, sans-serif",textAlign: "center", stroke: lightText },  
                  new go.Binding("text"))
              ),
              { // second arg will be this GraphObject, which in this case is the Node itself:
      		    click: function(e, node) {
      		    	var locLeft = node.data.loc.split(" ");

    				//if(locLeft[0] > 0) return;
    				
    				nodeVal = node;
    				compVal = 'AKNN';
    				
      		      	compAknn(node);
      		    }
              },
              // three named ports, one on each side except the top, all output only:
              makePort("T", go.Spot.Top, false, true),
              makePort("L", go.Spot.Left, true, false),
              makePort("R", go.Spot.Right, true, false),
              makePort("B", go.Spot.Bottom, true, false)
            ));
    myDiagram.nodeTemplateMap.add("FBOD",
            $(go.Node, "Spot", nodeStyle(),
              $(go.Panel, "Auto",
                $(go.Shape, "Rectangle",
                  { minSize: new go.Size(150,28), fill: vColorOD, stroke: null }), 
                $(go.TextBlock, "FBOD",
                  { font: "bold 11pt Helvetica, Arial, sans-serif",textAlign: "center", stroke: lightText },  
                  new go.Binding("text"))
              ),
              { // second arg will be this GraphObject, which in this case is the Node itself:
      		    click: function(e, node) {
      		    	var locLeft = node.data.loc.split(" ");

    				//if(locLeft[0] > 0) return;
    				
    				nodeVal = node;
    				compVal = 'FBOD';
    				
      		      	compFbod(node);
      		    }
              },
              // three named ports, one on each side except the top, all output only:
              makePort("T", go.Spot.Top, false, true),
              makePort("L", go.Spot.Left, true, false),
              makePort("R", go.Spot.Right, true, false),
              makePort("B", go.Spot.Bottom, true, false)
            ));
    myDiagram.nodeTemplateMap.add("HBOD",
            $(go.Node, "Spot", nodeStyle(),
              $(go.Panel, "Auto",
                $(go.Shape, "Rectangle",
                  { minSize: new go.Size(150,28), fill: vColorOD, stroke: null }), 
                $(go.TextBlock, "HBOD",
                  { font: "bold 11pt Helvetica, Arial, sans-serif",textAlign: "center", stroke: lightText },  
                  new go.Binding("text"))
              ),
              { // second arg will be this GraphObject, which in this case is the Node itself:
      		    click: function(e, node) {
      		    	var locLeft = node.data.loc.split(" ");

    				//if(locLeft[0] > 0) return;
    				
    				nodeVal = node;
    				compVal = 'HBOD';
    				
      		      	compHbod(node);
      		    }
              },
              // three named ports, one on each side except the top, all output only:
              makePort("T", go.Spot.Top, false, true),
              makePort("L", go.Spot.Left, true, false),
              makePort("R", go.Spot.Right, true, false),
              makePort("B", go.Spot.Bottom, true, false)
            ));
    myDiagram.nodeTemplateMap.add("ABOD",
            $(go.Node, "Spot", nodeStyle(),
              $(go.Panel, "Auto",
                $(go.Shape, "Rectangle",
                  { minSize: new go.Size(150,28), fill: vColorOD, stroke: null }), 
                $(go.TextBlock, "ABOD",
                  { font: "bold 11pt Helvetica, Arial, sans-serif",textAlign: "center", stroke: lightText },  
                  new go.Binding("text"))
              ),
              { // second arg will be this GraphObject, which in this case is the Node itself:
      		    click: function(e, node) {
      		    	var locLeft = node.data.loc.split(" ");

    				//if(locLeft[0] > 0) return;
    				
    				nodeVal = node;
    				compVal = 'ABOD';
    				
      		      	compAbod(node);
      		    }
              },
              // three named ports, one on each side except the top, all output only:
              makePort("T", go.Spot.Top, false, true),
              makePort("L", go.Spot.Left, true, false),
              makePort("R", go.Spot.Right, true, false),
              makePort("B", go.Spot.Bottom, true, false)
            ));

    /* 2018.04.23 사용하지 않는 컴포넌트 주석 처리
    myDiagram.nodeTemplateMap.add("MDLCre",
        $(go.Node, "Spot", nodeStyle(),
          $(go.Panel, "Auto",
            $(go.Shape, "Rectangle",
              { minSize: new go.Size(150,28), fill: vColorMD, stroke: null }), 
            $(go.TextBlock, "MDLCre",
              { font: "bold 11pt Helvetica, Arial, sans-serif",textAlign: "center", stroke: lightText },  
              new go.Binding("text"))
          ),
          { // second arg will be this GraphObject, which in this case is the Node itself:
  		    click: function(e, node) {
  		        alert(node.data.key);
  		    }
          },
          // three named ports, one on each side except the top, all output only:
          makePort("T", go.Spot.Top, false, true),
          makePort("L", go.Spot.Left, true, false),
          makePort("R", go.Spot.Right, true, false),
          makePort("B", go.Spot.Bottom, true, false)
        ));  

    myDiagram.nodeTemplateMap.add("MDLRnFr",
        $(go.Node, "Spot", nodeStyle(),
          $(go.Panel, "Auto",
            $(go.Shape, "Rectangle",
              { minSize: new go.Size(150,28), fill: vColorMD, stroke: null }), 
            $(go.TextBlock, "MDLRnFr",
              { font: "bold 11pt Helvetica, Arial, sans-serif",textAlign: "center", stroke: lightText },  
              new go.Binding("text"))
          ),
          { // second arg will be this GraphObject, which in this case is the Node itself:
  		    click: function(e, node) {
  		        alert(node.data.key);
  		    }
          },
          // three named ports, one on each side except the top, all output only:
          makePort("T", go.Spot.Top, false, true),
          makePort("L", go.Spot.Left, true, false),
          makePort("R", go.Spot.Right, true, false),
          makePort("B", go.Spot.Bottom, true, false)
        ));

    myDiagram.nodeTemplateMap.add("MDLDsTr", 
        $(go.Node, "Spot", nodeStyle(),
          $(go.Panel, "Auto",
            $(go.Shape, "Rectangle",
              { minSize: new go.Size(150,28), fill: vColorMD, stroke: null }), 
            $(go.TextBlock, "MDLDsTr",
              { font: "bold 11pt Helvetica, Arial, sans-serif",textAlign: "center", stroke: lightText },  
              new go.Binding("text"))
          ),
          { // second arg will be this GraphObject, which in this case is the Node itself:
  		    click: function(e, node) {
  		        alert(node.data.key);
  		    }
          },
          // three named ports, one on each side except the top, all output only:
          makePort("T", go.Spot.Top, false, true),
          makePort("L", go.Spot.Left, true, false),
          makePort("R", go.Spot.Right, true, false),
          makePort("B", go.Spot.Bottom, true, false)
        ));

    myDiagram.nodeTemplateMap.add("MDLXg", 
        $(go.Node, "Spot", nodeStyle(),
          $(go.Panel, "Auto",
            $(go.Shape, "Rectangle",
              { minSize: new go.Size(150,28), fill: vColorMD, stroke: null }), 
            $(go.TextBlock, "MDLXg",
              { font: "bold 11pt Helvetica, Arial, sans-serif",textAlign: "center", stroke: lightText },  
              new go.Binding("text"))
          ),
          { // second arg will be this GraphObject, which in this case is the Node itself:
  		    click: function(e, node) {
  		        alert(node.data.key);
  		    }
          },
          // three named ports, one on each side except the top, all output only:
          makePort("T", go.Spot.Top, false, true),
          makePort("L", go.Spot.Left, true, false),
          makePort("R", go.Spot.Right, true, false),
          makePort("B", go.Spot.Bottom, true, false)
        ));

    myDiagram.nodeTemplateMap.add("MDLKm",
        $(go.Node, "Spot", nodeStyle(),
          $(go.Panel, "Auto",
            $(go.Shape, "Rectangle",
              { minSize: new go.Size(150,28), fill: vColorMD, stroke: null }), 
            $(go.TextBlock, "MDLKm",
              { font: "bold 11pt Helvetica, Arial, sans-serif",textAlign: "center", stroke: lightText },  
              new go.Binding("text"))
          ),
          { // second arg will be this GraphObject, which in this case is the Node itself:
  		    click: function(e, node) {
  		        alert(node.data.key);
  		    }
          },
          // three named ports, one on each side except the top, all output only:
          makePort("T", go.Spot.Top, false, true),
          makePort("L", go.Spot.Left, true, false),
          makePort("R", go.Spot.Right, true, false),
          makePort("B", go.Spot.Bottom, true, false)
        ));         
     
    myDiagram.nodeTemplateMap.add("MDLNb",
        $(go.Node, "Spot", nodeStyle(),
          $(go.Panel, "Auto",
            $(go.Shape, "Rectangle",
              { minSize: new go.Size(150,28), fill: vColorMD, stroke: null }), 
            $(go.TextBlock, "MDLNb",
              { font: "bold 11pt Helvetica, Arial, sans-serif",textAlign: "center", stroke: lightText },  
              new go.Binding("text"))
          ),
          { // second arg will be this GraphObject, which in this case is the Node itself:
  		    click: function(e, node) {
  		        alert(node.data.key);
  		    }
          },
          // three named ports, one on each side except the top, all output only:
          makePort("T", go.Spot.Top, false, true),
          makePort("L", go.Spot.Left, true, false),
          makePort("R", go.Spot.Right, true, false),
          makePort("B", go.Spot.Bottom, true, false)
        ));         

    
    myDiagram.nodeTemplateMap.add("MdlPdt",
        $(go.Node, "Spot", nodeStyle(),
          $(go.Panel, "Auto",
            $(go.Shape, "Rectangle",
              { minSize: new go.Size(150,28), fill: "#79C900", stroke: null }), 
            $(go.TextBlock, "MdlPdt",
              { font: "bold 11pt Helvetica, Arial, sans-serif",textAlign: "center", stroke: lightText },  
              new go.Binding("text"))
          ),
          { // second arg will be this GraphObject, which in this case is the Node itself:
  		    click: function(e, node) {
  		        alert(node.data.key);
  		    }
          },
          // three named ports, one on each side except the top, all output only:
          makePort("T", go.Spot.Top, false, true),
          makePort("L", go.Spot.Left, true, false),
          makePort("R", go.Spot.Right, true, false),
          makePort("B", go.Spot.Bottom, true, false)
        ));

    myDiagram.nodeTemplateMap.add("MDLPdtDsTr",
        $(go.Node, "Spot", nodeStyle(),
          $(go.Panel, "Auto",
            $(go.Shape, "Rectangle",
              { minSize: new go.Size(150,28), fill: "#79C900", stroke: null }), 
            $(go.TextBlock, "MDLPdtDsTr",
              { font: "bold 11pt Helvetica, Arial, sans-serif",textAlign: "center", stroke: lightText },  
              new go.Binding("text"))
          ),
          { // second arg will be this GraphObject, which in this case is the Node itself:
  		    click: function(e, node) {
  		        alert(node.data.key);
  		    }
          },
          // three named ports, one on each side except the top, all output only:
          makePort("T", go.Spot.Top, false, true),
          makePort("L", go.Spot.Left, true, false),
          makePort("R", go.Spot.Right, true, false), 
          makePort("B", go.Spot.Bottom, true, false)
        ));      

    myDiagram.nodeTemplateMap.add("MDLPdtRnFr",
        $(go.Node, "Spot", nodeStyle(),
          $(go.Panel, "Auto",
            $(go.Shape, "Rectangle",
              { minSize: new go.Size(150,28), fill: "#79C900", stroke: null }), 
            $(go.TextBlock, "MDLPdtRnFr",
              { font: "bold 11pt Helvetica, Arial, sans-serif",textAlign: "center", stroke: lightText },  
              new go.Binding("text"))
          ),
          { // second arg will be this GraphObject, which in this case is the Node itself:
  		    click: function(e, node) {
  		        alert(node.data.key);
  		    }
          },
          // three named ports, one on each side except the top, all output only:
          makePort("T", go.Spot.Top, false, true),
          makePort("L", go.Spot.Left, true, false),
          makePort("R", go.Spot.Right, true, false), 
          makePort("B", go.Spot.Bottom, true, false)
        ));      

    myDiagram.nodeTemplateMap.add("MDLPdtXg",
        $(go.Node, "Spot", nodeStyle(),
          $(go.Panel, "Auto",
            $(go.Shape, "Rectangle",
              { minSize: new go.Size(150,28), fill: "#79C900", stroke: null }), 
            $(go.TextBlock, "MDLPdtXg",
              { font: "bold 11pt Helvetica, Arial, sans-serif",textAlign: "center", stroke: lightText },  
              new go.Binding("text"))
          ),
          { // second arg will be this GraphObject, which in this case is the Node itself:
  		    click: function(e, node) {
  		        alert(node.data.key);
  		    }
          },
          // three named ports, one on each side except the top, all output only:
          makePort("T", go.Spot.Top, false, true),
          makePort("L", go.Spot.Left, true, false),
          makePort("R", go.Spot.Right, true, false), 
          makePort("B", go.Spot.Bottom, true, false)
        )); */      

    
	/*
    myDiagram.nodeTemplateMap.add("UsrDef",
        $(go.Node, "Spot", nodeStyle(),
          $(go.Panel, "Auto",
            $(go.Shape, "Rectangle",
              { minSize: new go.Size(150,28), fill: "#79C900", stroke: null }), 
            $(go.TextBlock, "UsrDef",
              { font: "bold 11pt Helvetica, Arial, sans-serif",textAlign: "center", stroke: lightText },  
              new go.Binding("text"))
          ),
          { // second arg will be this GraphObject, which in this case is the Node itself:
  		    click: function(e, node) {
  		    	var locLeft = node.data.loc.split(" ");

				////if(locLeft[0] > 0) return;
				
				nodeVal = node;
				compVal = 'UsrDef';
				
				compUsrDef(node);
  		    }
          },
          // three named ports, one on each side except the top, all output only:
          makePort("T", go.Spot.Top, false, true),
          makePort("L", go.Spot.Left, true, false),
          makePort("R", go.Spot.Right, true, false),
          makePort("B", go.Spot.Bottom, true, false)
        ));
     */

    myDiagram.nodeTemplateMap.add("SaveRes",
        $(go.Node, "Spot", nodeStyle(),
          $(go.Panel, "Auto",
            $(go.Shape, "Rectangle",
              { minSize: new go.Size(150,28), fill: "#FF4000", stroke: null }), 
            $(go.TextBlock, "SaveRes",
              { font: "bold 11pt Helvetica, Arial, sans-serif",textAlign: "center", stroke: lightText },  
              new go.Binding("text"))
          ),
          { // second arg will be this GraphObject, which in this case is the Node itself:
  		    click: function(e, node) {
  		    	var locLeft = node.data.loc.split(" ");

				//if(locLeft[0] > 0) return;

				nodeVal = node;
				compVal = 'SaveRes';
				
				compSaveResult(node);
  		    }
          },
          // three named ports, one on each side except the top, all output only:
          makePort("T", go.Spot.Top, false, true),
          makePort("L", go.Spot.Left, true, false),
          makePort("R", go.Spot.Right, true, false),
          makePort("B", go.Spot.Bottom, true, false)
        ));

    
    /* 
    myDiagram.nodeTemplateMap.add("End",  
      $(go.Node, "Spot", nodeStyle(), 
        $(go.Panel, "Auto",
          $(go.Shape, "Circle",
            { minSize: new go.Size(150,28), fill: "#DC3C00", stroke: null }),
          $(go.TextBlock, "End",
            { font: "bold 11pt Helvetica, Arial, sans-serif", stroke: lightText },
            new go.Binding("text"))
        ),
        // three named ports, one on each side except the bottom, all input only:
        makePort("T", go.Spot.Top, false, true),
        makePort("L", go.Spot.Left, false, true),
        makePort("R", go.Spot.Right, false, true)
      ));

    myDiagram.nodeTemplateMap.add("Comment",
      $(go.Node, "Auto", nodeStyle(),
        $(go.Shape, "File",          
           { fill: "#EFFAB4", stroke: null }),
        $(go.TextBlock,
          {
            margin: 5,
            maxSize: new go.Size(200, NaN),
            wrap: go.TextBlock.WrapFit,
            textAlign: "center",
            editable: true,
            font: "bold 12pt Helvetica, Arial, sans-serif",            
            stroke: '#454545'
          },
          new go.Binding("text").makeTwoWay())
        // no ports, because no links are allowed to connect with a comment
      ));
    */


    // replace the default Link template in the linkTemplateMap
    myDiagram.linkTemplate =
      $(go.Link,  // the whole link panel
        {
          routing: go.Link.AvoidsNodes,
          curve: go.Link.JumpOver,
          corner: 5, toShortLength: 4,
          relinkableFrom: true,
          relinkableTo: true,
          reshapable: true,
          resegmentable: true,
          // mouse-overs subtly highlight links:
          mouseEnter: function(e, link) { link.findObject("HIGHLIGHT").stroke = "rgba(30,144,255,0.2)"; },
          mouseLeave: function(e, link) { link.findObject("HIGHLIGHT").stroke = "transparent"; }
        },
        new go.Binding("points").makeTwoWay(),
        $(go.Shape,  // the highlight shape, normally transparent
          { isPanelMain: true, strokeWidth: 8, stroke: "transparent", name: "HIGHLIGHT" }),
        $(go.Shape,  // the link path shape
          { isPanelMain: true, stroke: "gray", strokeWidth: 2 }),
        $(go.Shape,  // the arrowhead
          { toArrow: "standard", stroke: null, fill: "gray"}),
        $(go.Panel, "Auto",  // the link label, normally not visible
          { visible: false, name: "LABEL", segmentIndex: 2, segmentFraction: 0.5},
          new go.Binding("visible", "visible").makeTwoWay(),
          $(go.Shape, "RoundedRectangle",  // the label shape
            { fill: "#F8F8F8", stroke: null }),
          $(go.TextBlock, "Yes",  // the label
            {
              textAlign: "center",
              font: "10pt helvetica, arial, sans-serif",
              stroke: "#333333",
              editable: true
            },
            new go.Binding("text").makeTwoWay())
        )
      );

   

    // Make link labels visible if coming out of a "conditional" node.
    // This listener is called by the "LinkDrawn" and "LinkRelinked" DiagramEvents.
    function showLinkLabel(e) {
      var label = e.subject.findObject("LABEL");
      if (label !== null) label.visible = (e.subject.fromNode.data.figure === "Diamond");
      /**
       	 링크 관련 이벤트 정의 하면 됨
      */
      //console.log(e);
      //console.log(e.subject.toPort);
      //console.log(e.subject.toNode);
      //console.log(e.subject.fromPort);
      //console.log(e.subject.fromNode);
      //console.log(e.subject.toNode.key);
      //console.log(e.subject.fromNode.key);
      
      /* if(!lnkSearch(e.subject.fromNode.key, e.subject.toNode.key)) {
    	  myDiagram.commandHandler.deleteSelection();
    	  return false;
      } */
      
      var udfotldtcidVal = '${wadUod.udfOtlDtcId}';
      updUod(udfotldtcidVal, 'n');
    }

    // temporary links used by LinkingTool and RelinkingTool are also orthogonal:
    myDiagram.toolManager.linkingTool.temporaryLink.routing = go.Link.Orthogonal;
    myDiagram.toolManager.relinkingTool.temporaryLink.routing = go.Link.Orthogonal;
    
    myDiagram.commandHandler.doKeyDown = function() {
        var e = myDiagram.lastInput;
        var cmd = myDiagram.commandHandler;

		//alert(e.key);
        
        if (e.key === "Del") {
        	/* console.log(myDiagram.selection.Ea.key.Zd);
        	console.log(myDiagram.selection.Ea.key.Zd.key); //cre_comp_id
        	console.log(myDiagram.selection.Ea.key.Zd.category); //category
        	console.log('${wadUod.udfOtlDtcId}'); */

        	var crecompidVal = myDiagram.selection.Ea.key.Zd.key;
        	var categoryVal = myDiagram.selection.Ea.key.Zd.category;
        	var udfotldtcidVal = '${wadUod.udfOtlDtcId}';

			
        	if(categoryVal == undefined) {
        		myDiagram.commandHandler.deleteSelection();
        		updUod(udfotldtcidVal, 'y');
        		return;
        	}
        	
        	delComp(udfotldtcidVal, crecompidVal, categoryVal);
          	
        }
      };
      
      myDiagram.addDiagramListener("BackgroundSingleClicked", function(e) { 
    	  hideDtl();
	  });

    //load();  // load an initial diagram from some JSON text
    
    // initialize the Palette that is on the left side of the page
    myPalette =
      $(go.Palette, "myPaletteDiv",  // must name or refer to the DIV HTML element
        {
          scrollsPageOnFocus: false,
          nodeTemplateMap: myDiagram.nodeTemplateMap,  // share the templates used by myDiagram
          layout: $(go.TreeLayout, { nodeSpacing: 5, layerSpacing: 30 })         
          //model: new go.GraphLinksModel(pData)
        });

   
    myPalette.nodeTemplate =
        $(go.Node, "Horizontal",
          { selectionChanged: nodeSelectionChanged },  // this event handler is defined below
          $(go.Panel, "Auto",
            $(go.Shape, { fill: "#1F4963", stroke: null }),
            $(go.TextBlock,
              { font: "bold 13px Helvetica, bold Arial, sans-serif",
                stroke: "white", margin: 3 },
              new go.Binding("text", "key"))
          ),
          $("TreeExpanderButton") 
        );
     
      
	/*
    myPalette.linkTemplate =
        $(go.Link, go.Link.Orthogonal,
          { deletable: false, corner: 10 },
          $(go.Shape,
            { strokeWidth: 2 }
          ),
          $(go.TextBlock, go.Link.OrientUpright,
            { background: "white",
              visible: false,  // unless the binding sets it to true for a non-empty string
              segmentIndex: -2,
              segmentOrientation: go.Link.None
            },
            new go.Binding("text", "answer"),
            // hide empty string;
            // if the "answer" property is undefined, visible is false due to above default setting
            new go.Binding("visible", "answer", function(a) { return (a ? true : false); })
          )
        );
      */

    
    myPalette.model =
            $(go.TreeModel, {
              isReadOnly: true  // don't allow the user to delete or copy nodes
              // build up the tree in an Array of node data
              //nodeDataArray: traverseDom(document.activeElement)
            });
    

    myPalette.linkTemplate =
        $(go.Link,
          { selectable: false },
          $(go.Shape));  // the link shape

    /*
    //var mdlCreData = { key:"MDLCre",    category: "MDLCre",    text: "Model Creation" };
    var mdlCreDsTr = { key:"MDLDsTr", name:"MDLDsTr",  category: "MDLDsTr",   text: "Decision Tree" };
    var mdlCreRnFr = { key:"MDLRnFr", namd:"MDLRnFr",  category: "MDLRnFr",   text: "Random Forest" };
   
    myPalette.model.nodeDataArray = [
    	    { key:"DataImp",   category: "DataImp",   text: "Data Import"}, 
	        { key:"SubsetCol", category: "SubsetCol", text: "Subset(Col)" }, 
	        { key:"SubsetRow", category: "SubsetRow", text: "Subset(Row)" }, 
	        { key:"Join",      category: "Join",      text: "Join" }, 
	        { key:"Filter",    category: "Filter",    text: "Filter" }, 
	        { key:"Cleansing", category: "Cleansing", text: "Cleansing" }, 
	        { key:"ODBP",      category: "ODBP",      text: "Boxplot" }, 
	        { key:"ODIsFr",    category: "ODIsFr",    text: "Isolation Forest" },  
	        { key:"ODLOF",     category: "ODLOF",     text: "LOF" }, 
	        { key:"ODOCV",     category: "ODOCV",     text: "One Class SVM" }, 
	        { key:"ODELEV",    category: "ODELEV",    text: "Elliptic Envelope" }, 
	        { key:"MDLCre",    name:"MDLCre", category: "MDLCre",    text: "Model Creation" },  
	        mdlCreDsTr, 
	        mdlCreRnFr, 
	        { key:"MDLXg",     category: "MDLXg",     text: "xgBoost" }, 
	        { key:"MDLKm",     category: "MDLKm",     text: "K-means" },
	        { key:"MDLNb",     category: "MDLNb",     text: "Naive Bayes" },
	        { key:"UsrDef",    category: "UsrDef",    text: "User Defined" },
	        { key:"SaveRes",   category: "SaveRes",   text: "Save Result" },
      ];

   mdlCreDsTr.parent = "MDLCre"; 
   mdlCreRnFr.parent = "MDLCre";   
   */

   myPalette.model.nodeDataArray = [
	    {  category: "DataImp",    text: "Data Import"}, 	   
        {  category: "SubsetCol",  text: "Subset(Col)" }, 
        {  category: "SubsetRow",  text: "Subset(Row)" }, 
        {  category: "Join",       text: "Join" }, 
        {  category: "Filter",     text: "Filter" }, 
        {  category: "Cleansing",  text: "Cleansing" }, 
        {  category: "ODBP",       text: "Boxplot" }, 
        {  category: "ODIsFr",     text: "Isolation Forest" },  
        {  category: "ODLOF",      text: "LOF" }, 
        {  category: "ODOCV",      text: "One Class SVM" }, 
        {  category: "ODELEV",     text: "Elliptic Envelope" },
        {  category: "RRGR",       text: "Robust Regression" },
        
        {  category: "MAD",      text: "MAD" }, 
        {  category: "CBLOF",    text: "Cluster-based LOF" },  
        {  category: "KNN",      text: "KNN" }, 
        {  category: "AKNN",     text: "Average KNN" }, 
        {  category: "FBOD",     text: "FBOD" },
        {  category: "HBOD",     text: "HBOD" },
        {  category: "ABOD",     text: "ABOD" }, 
        
//         {  category: "MAD",      text: "Median Absolute Deviation" }, 
//         {  category: "CBLOF",    text: "Cluster-based Local Outlier Factor" },  
//         {  category: "KNN",      text: "K-Nearest Neighbors" }, 
//         {  category: "AKNN",     text: "Average K-Nearest Neighbor" }, 
//         {  category: "FBOD",     text: "Feature Bagging Outlier Detection" },
//         {  category: "HBOD",     text: "Histogram Based Outlier Detection" },
//         {  category: "ABOD",     text: "Angle Based Outlier Detection" },
        
        /* 2018.04.23 사용하지 않는 컴포넌트 주석 처리
        {  category: "MDLCre",     text: "Model Creation" },  
        {  category: "MDLDsTr",    text: "Decision Tree" },
        {  category: "MDLRnFr",    text: "Random Forest" },
        {  category: "MDLXg",      text: "xgBoost" }, 
        {  category: "MDLKm",      text: "K-means" },
        {  category: "MDLNb",      text: "Naive Bayes" }, 
        {  category: "UsrDef",     text: "User Defined" }, 
        */
        {  category: "SaveRes",    text: "Save Result" },
    ];
   

    /*
    myPalette.model.linkDataArray = [
			    	 		{ from: "MDLCre", to: "MDLDsTr" } ,
			    	 		{ from: "MDLCre", to: "MDLRnFr" }  
			    	   ];
	   */

    //grid sort  
     //myPalette.layout.sorting = go.GridLayout.Forward;

    
  } 
  // end init
  
  // Walk the DOM, starting at document, and return an Array of node data objects representing the DOM tree
  // Typical usage: traverseDom(document.activeElement)
  // The second and third arguments are internal, used when recursing through the DOM
  function traverseDom(node, parentName, dataArray) {
    if (parentName === undefined) parentName = null;
    if (dataArray === undefined) dataArray = [];
    // skip everything but HTML Elements
    if (!(node instanceof Element)) return;
    // Ignore the navigation menus
    if (node.id === "navindex" || node.id === "navtop") return;
    // add this node to the nodeDataArray
    var name = getName(node);
    var data = { key: name, name: name };
    dataArray.push(data);
    // add a link to its parent
    if (parentName !== null) {
      data.parent = parentName;
    }
    // find all children
    var l = node.childNodes.length;
    for (var i = 0; i < l; i++) {
      traverseDom(node.childNodes[i], name, dataArray);
    }
    return dataArray;
  }

  // Give every node a unique name
  function getName(node) {
    var n = node.nodeName;
    if (node.id) n = n + " (" + node.id + ")";
    var namenum = n;  // make sure the name is unique
    var i = 1;
    while (names[namenum] !== undefined) {
      namenum = n + i;
      i++;
    }
    names[namenum] = node;
    return namenum;
  }

  // When a Node is selected, highlight the corresponding HTML element.
  function nodeSelectionChanged(node) {
    if (node.isSelected) {
      names[node.data.name].style.backgroundColor = "lightblue";
    } else {
      names[node.data.name].style.backgroundColor = "";
    }
  }
  
  function makeTree(level, count, max, nodeDataArray, parentdata) {
        var numchildren = Math.floor(Math.random() * 10);
        for (var i = 0; i < numchildren; i++) {
          if (count >= max) return count;
          count++;
          var childdata = { key: count, parent: parentdata.key };
          nodeDataArray.push(childdata);
          if (level > 0 && Math.random() > 0.5) {
            count = makeTree(level - 1, count, max, nodeDataArray, childdata);
          }
        }
        return count;
  }
  
  
  
  function load() {
	  
     myDiagram.model = go.Model.fromJson(document.getElementById("mySavedModel").value);
  }

  //콤포넌트별 div 토글
  function toggelDivComp(showDiv){

  	$.each($("div"), function(i,obj){   

  		var vId = obj.id;

  		var vCmp = obj.id.substring(0,7);

  		if(vCmp == "divComp") {

  			$("#" + vId).hide(); 						
  		}  		
  	});

  	$("#" + showDiv).show(); 	 	
  }

  /* myDiagram.commandHandler.doKeyDown = function() {
      var e = myDiagram.lastInput;

	  alert(e.key); 
      
      var cmd = myDiagram.commandHandler;
      if (e.key === "T") {  // could also check for e.control or e.shift
        if (cmd.canCollapseSubGraph()) {
          cmd.collapseSubGraph();
        } else if (cmd.canExpandSubGraph()) {
          cmd.expandSubGraph();
        }
      } else {
        // call base method with no arguments
        go.CommandHandler.prototype.doKeyDown.call(cmd);
      }
  }; */

  //콤보박스별 데이터셋 조회 
  function getAnaDaseId(obj, udfOtlDtcId, creCompId){ 

  	var url = "<c:url value='/advisor/prepare/udefoutlier/getAnaDaseId.do' />";

  	var param = new Object();

  	param.udfOtlDtcId = udfOtlDtcId;
  	param.creCompId = creCompId;
  	
  	//alert(param);

  	$.ajax({
  		url: url,
  		async: false,
  		type: "POST",
  		data: replacerXssParam(param),
  		dataType: 'json',
  		success: function (res) {
  			
  			//alert(JSON.stringify(res)); 
  			
  			//var obj = $("#frmColDaseDv #srcAnaDaseId");
  			
  			//removeOption(obj); 
  			
  			obj.html("");

  			$.each(res.DATA, function(i,map){ 

  				addOption(obj, map.anaDaseId, map.anaDaseNm); 
  			});
  					
  			
  		},
  		error: function (jqXHR, textStatus, errorThrown) {
  						
  		}
  	});
  	
  }
  
  function delComp(udfotldtcidVal, crecompidVal, categoryVal) {
	  var url = "<c:url value='/advisor/prepare/udefoutlier/delComp.do' />";
  	
    	var param = new Object();

    	param.udfOtlDtcId = udfotldtcidVal;
    	param.creCompId = crecompidVal;
    	param.creCompNm = categoryVal;

    	$.ajax({
    		url: url,
    		async: false,
    		type: "POST",
    		data: replacerXssParam(param),
    		dataType: 'json',
    		success: function (res) {
    			myDiagram.commandHandler.deleteSelection();
    			nodeVal = '';
    			compVal = '';
				updUod(udfotldtcidVal);
    		},
    		error: function (jqXHR, textStatus, errorThrown) {
    						
    		}
    	});
  }
  
  function updUod(udfOtlDtcId, delYn) {
	  $("#frmUod #mdlJsonInf").val(myDiagram.model.toJson());
	  
	  var url = "<c:url value='/advisor/prepare/udefoutlier/updUod.do' />";
	  	
  	var param = $("#frmUod").serialize();

  	$.ajax({
  		url: url,
  		async: false,
  		type: "POST",
  		data: param,
  		dataType: 'json',
  		success: function (res) {
  			//alert(res.RESULT.MESSAGE);
  			if(delYn == 'n') {
  				linkEvt();
  				//hideDtl();
  			} else if(delYn == 'y') {
  				linkEvt();
  				//hideDtl();
  			} else {
  				hideDtl();
  			}
  		},
  		error: function (jqXHR, textStatus, errorThrown) {
  						
  		}
  	});
  }
    
  function hideDtl() {
	  $("#divCompDataImp").css('display', 'none');
	  $("#divCompColDaseDv").css('display', 'none');
	  $("#divCompRowDaseDv").css('display', 'none');
	  $("#divCompDataFilter").css('display', 'none');
	  $("#divCompJoin").css('display', 'none');
	  $("#divCompDataCleansing").css('display', 'none');
	  $("#divCompBoxplot").css('display', 'none');
	  $("#divCompIsFr").css('display', 'none');
	  $("#divCompElev").css('display', 'none');
	  $("#divCompOcv").css('display', 'none');
	  $("#divCompLof").css('display', 'none');
	  $("#divCompRrgr").css('display', 'none');
	  $("#divCompSaveResult").css('display', 'none');
	  $("#divCompUsrDef").css('display', 'none');
	  
	  $("#divCompMad").css('display', 'none');
	  $("#divCompCblof").css('display', 'none');
	  $("#divCompKnn").css('display', 'none');
	  $("#divCompAknn").css('display', 'none');
	  $("#divCompFbod").css('display', 'none');
	  $("#divCompHbod").css('display', 'none');
	  $("#divCompAbod").css('display', 'none');
  }
  
  function linkEvt() {
	$("#frmUod #mdlJsonInf").val(myDiagram.model.toJson());
	  
	var url = "<c:url value='/advisor/prepare/udefoutlier/insertLink.do' />";
	  	
  	//var param = $("#frmUod").serialize();
  	var param = new Object();
  	param.udfOtlDtcId = '${wadUod.udfOtlDtcId}';

  	$.ajax({
  		url: url,
  		async: false,
  		type: "POST",
  		data: replacerXssParam(param),
  		dataType: 'json',
  		success: function (res) {
  			refreshComp();
  		},
  		error: function (jqXHR, textStatus, errorThrown) {
  			refreshComp();
  		}
  	});
  }
  
  function refreshComp() {
	  //alert(compVal + ", " + nodeVal);
	  switch(compVal) {
	  case "DataImp" :
		  compDataImp(nodeVal);
		  break;
	  case "SubsetCol" :
		  compColDaseDv(nodeVal);
		  break;
	  case "SubsetRow" :
		  compRowDaseDv(nodeVal);
		  break;
	  case "Join" :
		  compJoin(nodeVal);
		  break;
	  case "Filter" :
		  compDataFilter(nodeVal);
		  break;
	  case "Cleansing" :
		  compDataCln(nodeVal);
		  break;
	  case "ODBP" :
		  compBoxplot(nodeVal);
		  break;
	  case "ODIsFr" :
		  compIsFr(nodeVal);
		  break;
	  case "ODLOF" :
		  compLof(nodeVal);
		  break;
	  case "ODOCV" :
		  compOcv(nodeVal);
		  break;
	  case "ODELEV" :
		  compElev(nodeVal);
		  break;
	  case "RRGR" :
		  compRrgr(nodeVal);
		  break;
		  
	  case "MAD" :
		  compMad(nodeVal);
		  break;
	  case "CBLOF" :
		  compCblof(nodeVal);
		  break;
	  case "KNN" :
		  compKnn(nodeVal);
		  break;
	  case "AKNN" :
		  compAknn(nodeVal);
		  break;
	  case "FBOD" :
		  compFbod(nodeVal);
		  break;
	  case "HBOD" :
		  compHbod(nodeVal);
		  break;
	  case "ABOD" :
		  compAbod(nodeVal);
		  break;

	  case "UsrDef" :
		  compUsrDef(nodeVal);
		  break;
	  case "SaveRes" :
		  compSaveResult(nodeVal);
		  break;
	  }
  }
  
  function lnkSearch(from, to) {
	  //var jsonInf = JSON.parse($("#mdlJsonInf").val());
	  //var jsonInf = eval($("#frmUod #mdlJsonInf").val());
	  //console.log(jsonInf);
	  //console.log($("#frmUod #mdlJsonInf").val());
	  
	  return false;
	  
	  /* for(i=0; i<jsonInf.linkDataArray.length; i++) {
	  	console.log("from >> " + jsonInf.linkDataArray[i].from);
	  	console.log("to >> " + jsonInf.linkDataArray[i].to);
	  	if(jsonInf.linkDataArray[i].from == from || jsonInf.linkDataArray[i].to == to) {
	  		return false;
	  	}
	  }
	  
	  return true; */
  }


</script>

</head>

<body>
<!-- 메뉴 메인 제목 -->
<div class="menu_subject">
	<div class="tab">
	<div class="menu_title"><s:message code="SYS.MNG" /></div> <!-- 시스템 관리 -->
	
	    <div class="stit"><s:message code="SYS.TRRT.MNG" /></div> <!-- 시스템영역 관리 --> 
	
	</div>

</div>

<div class="divLstBtn" style="float:right;">  
    <div class="bt03">
    	 <!-- 기존소스_bak 181018 -->
    	<input type="button" name="btnList" id="btnList" class="btn_save" value="목록보기" />
    	<input type="button" name="btnResult" id="btnResult" class="btn_save" value="오류데이터" />
		<input type="button" name="btnPyScrt" id="btnPyScrt" class="btn_save" value="스크립트보기" />
		<input type="button" name="btnNewPyScrt" id="btnNewPyScrt" class="btn_save" value="스크립트생성" />
    	
    	<!-- <input type="button" name="btnList" id="btnList" class="btn_save" value="<s:message code='BTN.LST.VIEW' />" /> --><!-- 목록보기 코드값으로 수정 181018 -->
    	<!-- <input type="button" name="btnResult" id="btnResult" class="btn_save" value="<s:message code='EROR.DATA' />" /> --><!-- 오류데이터 코드값으로 수정 181018 -->
		<!-- <input type="button" name="btnPyScrt" id="btnPyScrt" class="btn_save" value="<s:message code='BTN.SCRIPT.VIEW' />" /> --><!-- 스크립트보기 코드값으로 수정 181018 -->
		<!-- <input type="button" name="btnNewPyScrt" id="btnNewPyScrt" class="btn_save" value="<s:message code='BTN.SCRIPT.CRTN' />" /> --><!-- 스크립트생성 코드값으로 수정 181018 -->
	</div>
</div>		

<!-- 메뉴 메인 제목 -->
<div style="clear:both; height:5px;"><span></span></div>

  <form id="frmUod" name="frmUod">
  	<input type="hidden" name="udfOtlDtcId" id="udfOtlDtcId" value="${wadUod.udfOtlDtcId}" />
  	<input type="hidden" name="ibsStatus"   id="ibsStatus" value="${wadUod.ibsStatus}" />  
  	<input type="hidden" name="mdlJsonInf"  id="mdlJsonInf"  />
  	    
  	    
  	    		
	  	<fieldset>   
		<div class="tb_basic" >
		    
			<table width="100%" border="0" cellspacing="0" cellpadding="0" >
			   <caption><s:message code='PROF.MNG'/></caption><!-- 프로파일 관리 -->
			   <colgroup>
				   <col style="width:15%;" />
				   <col style="width:*%;" />				       			 
			   </colgroup>
			   <tbody>   
			   		<tr>                               
			           <th scope="row"  class="th_require"><label for="udfOtlDtcNm">사용자정의이상값탐지명</label></th><!-- 사용자정의이상값탐지명 -->
			           <td>
			               <input type="text" name="udfOtlDtcNm" id="udfOtlDtcNm"  class="wd99p" />
			           </td> 			                     	         
			       </tr>    			       
			   </tbody>
		   </table>   
		  
	  </div>
	  
	  </fieldset>
  </form>
  
  
  
  <div style="clear:both; height:10px;"><span></span></div>
  
  <div id="divDiagram" style="width:70%; display: flex; justify-content: space-between;float:left;">
     <div id="myPaletteDiv" style="width: 200px; margin-right: 2px; background-color: whitesmoke; border: solid 1px black"></div>
     <div id="myDiagramDiv" style="flex-grow: 1; height: 770px; border: solid 1px black"></div>
  </div>
  
  <div style="width: 1%;float:left;">&nbsp;</div>
  
  <!-- DataImp  -->
  <div id="divCompDataImp" style="width:29%;float:left;display:none;" >  
  	
  	<%@include file="./dataimp_rqst_dtl.jsp" %>  
  </div> 
  <!-- DataImp end -->
  
  <!-- Col Dase dv  -->
  <div id="divCompColDaseDv" style="width:29%;float:left;display:none;" > 
  	
  	<%@include file="./coldasedv_rqst_dtl.jsp" %>  
  </div> 
  <!-- Col Dase dv end -->
  
  <!-- Row Dase dv  -->
  <div id="divCompRowDaseDv" style="width:29%;float:left;display:none;" > 
  	
  	<%@include file="./rowdasedv_rqst_dtl.jsp" %>  
  </div> 
  <!-- Row Dase dv end -->
  
  <!-- Data Filter dv  -->
  <div id="divCompDataFilter" style="width:29%;float:left;display:none;" > 
  	
  	<%@include file="./datafilter_rqst_dtl.jsp" %>  
  </div> 
  <!-- Data Filter dv end -->
  
  <!-- Data Join dv -->
  <div id="divCompJoin" style="width:29%;float:left;display:none;" >
  
  	<%@include file="./datajoin_rqst_dtl.jsp" %>
  </div>
  <!-- Data Join dv end -->
  
  <!-- Data Cleansing dv -->
  <div id="divCompDataCleansing" style="width:29%;float:left;display:none;">
  	
  	<%@include file="./datacleansing_rqst_dtl.jsp" %>
  </div>
  <!-- Data Cleansing dv end -->
  
  <!-- Boxplot dv -->
  <div id="divCompBoxplot" style="width:29%;float:left;display:none;">
  	
  	<%@include file="./boxplot_rqst_dtl.jsp" %>
  </div>
  <!-- Boxplot dv end -->
  
  <!-- IsFr dv -->
  <div id="divCompIsFr" style="width:29%;float:left;display:none;">
  	
  	<%@include file="./isfr_rqst_dtl.jsp" %>
  </div>
  <!-- IsFr dv end -->
  
  <!-- IsFr dv -->
  <div id="divCompElev" style="width:29%;float:left;display:none;">
  	
  	<%@include file="./elev_rqst_dtl.jsp" %>
  </div>
  <!-- IsFr dv end -->
  
  <!-- Ocv dv -->
  <div id="divCompOcv" style="width:29%;float:left;display:none;">
  	
  	<%@include file="./ocv_rqst_dtl.jsp" %>
  </div>
  <!-- Ocv dv end -->
  
  <!-- Lof dv -->
  <div id="divCompLof" style="width:29%;float:left;display:none;">
  	
  	<%@include file="./lof_rqst_dtl.jsp" %>
  </div>
  <!-- Lof dv end -->
  
  <!-- Rrgr dv -->
  <div id="divCompRrgr" style="width:29%;float:left;display:none;">
  	
  	<%@include file="./rrgr_rqst_dtl.jsp" %>
  </div>
  <!-- Rrgr dv end -->
  
  <!-- Rrgr dv -->
  <div id="divCompMad" style="width:29%;float:left;display:none;">
  	
  	<%@include file="./mad_rqst_dtl.jsp" %>
  </div>
  <!-- Rrgr dv end -->
  <!-- Rrgr dv -->
  <div id="divCompCblof" style="width:29%;float:left;display:none;">
  	
  	<%@include file="./cblof_rqst_dtl.jsp" %>
  </div>
  <!-- Rrgr dv end -->
  <!-- Rrgr dv -->
  <div id="divCompKnn" style="width:29%;float:left;display:none;">
  	
  	<%@include file="./knn_rqst_dtl.jsp" %>
  </div>
  <!-- Rrgr dv end -->
  <!-- Rrgr dv -->
  <div id="divCompAknn" style="width:29%;float:left;display:none;">
  	
  	<%@include file="./aknn_rqst_dtl.jsp" %>
  </div>
  <!-- Rrgr dv end -->
  <!-- Rrgr dv -->
  <div id="divCompFbod" style="width:29%;float:left;display:none;">
  	
  	<%@include file="./fbod_rqst_dtl.jsp" %>
  </div>
  <!-- Rrgr dv end -->
  <!-- Rrgr dv -->
  <div id="divCompHbod" style="width:29%;float:left;display:none;">
  	
  	<%@include file="./hbod_rqst_dtl.jsp" %>
  </div>
  <!-- Rrgr dv end -->
  <!-- Rrgr dv -->
  <div id="divCompAbod" style="width:29%;float:left;display:none;">
  	
  	<%@include file="./abod_rqst_dtl.jsp" %>
  </div>
  <!-- Rrgr dv end -->
  
  <!-- SaveResult dv -->
  <div id="divCompSaveResult" style="width:29%;float:left;display:none;">
  	
  	<%@include file="./saveres_rqst_dtl.jsp" %>
  </div>
  <!-- SaveResult dv end -->
  
  <!-- UsrDef dv -->
  <div id="divCompUsrDef" style="width:29%;float:left;display:none;">
  	
  	<%@include file="./usrdef_rqst_dtl.jsp" %>
  </div>
  <!-- UsrDef dv end -->
</body>
</html>
