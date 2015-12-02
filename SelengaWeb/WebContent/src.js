var radioBtn;
var checked=new Array();
function add_pokaz(elem,id)
{
	var numb = id;
	elem.bind('click',function(numb){
//		alert(numb);
//		if (elem.getAttribute('checked')==true)
		if($(this).is(":checked"))
		{
//			$('body').append('Selected '+id);
		    checked[Number(id)]=1;
		}
	else
		{
		  checked[Number(id)]=0;
		}
//		alert('faq');
		alert('id = '+Number(id)+' Length ='+checked.length);
	});
}

$('#load_data').bind('click',function() {
	alert(checked.length);
//	for ( var i in checked) {
//		if (!arr.hasOwnProperty(i)) continue;
//			  $('body').append('Selected '+Number(i)); 
//	}
	checked.forEach(function(item, i,arr) {
		$('body').append('i='+i+'  item='+item); 
//		  alert( i + ": " + item + " (массив:" + arr + ")" );
	});
//	alert(checked);
//	for (var int = 0; int < checked.length; int++) {
//		if (checked[int]!=undefined)
//			{
//			}		
//	}
});

		$('#by_year').bind('click',function() {
					$.get("getYears", function(responseJson) {                 // Execute Ajax GET request on URL of "someservlet" and execute the following function with Ajax response JSON..
						var $select = $("#years");                           // Locate HTML DOM element with ID "someselect".
				        $select.find("input").remove();   
				        $select.find("label").remove();   
				         $.each(responseJson, function(index, item) {               // Iterate over the JSON object.
				        	var container=$('#years');
					        $('<input />', { type: 'radio', name:'years', id: 'year'+index, value: item }).appendTo(container);
					        $('<label />', { 'for': 'year'+index, text: item }).appendTo(container);		
				        });
				    });  	
					$.get("getSp", function(responseJson) {                 // Execute Ajax GET request on URL of "someservlet" and execute the following function with Ajax response JSON...
				        var $select = $("#sp");                           // Locate HTML DOM element with ID "someselect".
				        $select.find("input").remove();
				        $select.find("label").remove();   // Find all child elements with tag name "option" and remove them (just to prevent duplicate options when button is pressed again).
				        $.each(responseJson, function(key, value) {               // Iterate over the JSON object.
					        var container=$('#sp');
					        $('<input />', { type: 'checkbox', id: 'sp'+key, value: value}).appendTo(container);
					        $('<label />', { 'for': 'sp'+key, text: value }).appendTo(container);		
				        });
				    });  	
					$.get("getGroupPokaz",{issp:'1'}, function(responseJson) {                 // Execute Ajax GET request on URL of "someservlet" and execute the following function with Ajax response JSON...
				        var gpcont = $("#gp");                           // Locate HTML DOM element with ID "someselect".
				        gpcont.find("input").remove();    
				        gpcont.find("label").remove();   // Find all child elements with tag name "option" and remove them (just to prevent duplicate options when button is pressed again).
				        gpcont.find("div").remove();   // Find all child elements with tag name "option" and remove them (just to prevent duplicate options when button is pressed again).
				        var i=0;
				        var divline=$('<div/>',{'class':'linediv'});
				        divline.appendTo(gpcont);
				        $.each(responseJson, function(key, value) {               // Iterate over the JSON object.
				        if ((i%3 == 0) && (i!=0))
				        {
				        	divline=$('<div/>',{'class':'linediv'});
				        	divline.appendTo(gpcont);
				        }
				        var celldiv=$('<div/>',{'class':'celldiv'});
				        celldiv.appendTo(divline);
				        var gps= $('<input />', { type: 'checkbox', id: 'gp'+key, value: value});
				        gps.appendTo(celldiv);
				        $('<label />', { 'for': 'gp'+key, text: value }).appendTo(celldiv);	
				        i++;
				        $(gps).bind('click',function(value){
//				        	    alert(value);
					        	$.get("getPokaz", {id:key}, function(responseJson){
						        		var pcont = $("#pokaz");                           // Locate HTML DOM element with ID "someselect".
						        		pcont.find("input").remove();
						        		pcont.find("label").remove();   // Find all child elements with tag name "option" and remove them (just to prevent duplicate options when button is pressed again).
						        		pcont.find("div").remove();   // Find all child elements with tag name "option" and remove them (just to prevent duplicate options when button is pressed again).
								        var i=0;
								        var divline=$('<div/>',{'class':'linediv'});
								        divline.appendTo(pcont);
								        $.each(responseJson, function(key, value) {               // Iterate over the JSON object.
							        	    if ((i%3 == 0) && (i!=0))
									        {
									        	divline=$('<div/>',{'class':'linediv'});
									        	divline.appendTo(pcont);
									        }
									        var celldiv=$('<div/>',{'class':'celldiv'});
									        celldiv.appendTo(divline);
									        var pokazbut = $('<input />', { type: 'checkbox', id: 'pokaz'+key, value: key});
									        pokazbut.appendTo(celldiv);
//									        pokazbut.bind('click',add_pokaz(key));
									        add_pokaz(pokazbut, key);
									        $('<label />', { 'for': 'pokaz'+key, text: value }).appendTo(celldiv);		
									        i++;
					        	});
					        });
				        });
				    });  	
	 			 });
					
		});			
		$('#by_sp').bind('click',function() {
		 {
			$.get("getYears", function(responseJson) {                 // Execute Ajax GET request on URL of "someservlet" and execute the following function with Ajax response JSON..
				var $select = $("#years");                           // Locate HTML DOM element with ID "someselect".
		        $select.find("input").remove();   
		        $select.find("label").remove();   
		         $.each(responseJson, function(index, item) {               // Iterate over the JSON object.
		        	var container=$('#years');
			        $('<input />', { type: 'checkbox', name:'years', id: 'year'+index, value: item }).appendTo(container);
			        $('<label />', { 'for': 'year'+index, text: item }).appendTo(container);		
		        });
		    });  	
			$.get("getSp", function(responseJson) {                 // Execute Ajax GET request on URL of "someservlet" and execute the following function with Ajax response JSON...
		        var $select = $("#sp");                           // Locate HTML DOM element with ID "someselect".
		        $select.find("input").remove();
		        $select.find("label").remove();   // Find all child elements with tag name "option" and remove them (just to prevent duplicate options when button is pressed again).
		        $.each(responseJson, function(key, value) {               // Iterate over the JSON object.
			        var container=$('#sp');
			        $('<input />', { type: 'radio',name:"sps", id: 'sp'+key, value: value}).appendTo(container);
			        $('<label />', { 'for': 'sp'+key, text: value }).appendTo(container);		
		        });
		    });  	
			$.get("getGroupPokaz",{issp:'1'}, function(responseJson) {                 // Execute Ajax GET request on URL of "someservlet" and execute the following function with Ajax response JSON...
		        var gpcont = $("#gp");                           // Locate HTML DOM element with ID "someselect".
		        gpcont.find("input").remove();    
		        gpcont.find("label").remove();   // Find all child elements with tag name "option" and remove them (just to prevent duplicate options when button is pressed again).
		        gpcont.find("div").remove();   // Find all child elements with tag name "option" and remove them (just to prevent duplicate options when button is pressed again).
		        var i=0;
		        var divline=$('<div/>',{'class':'linediv'});
		        divline.appendTo(gpcont);
		        $.each(responseJson, function(key, value) {               // Iterate over the JSON object.
		        if ((i%3 == 0) && (i!=0))
		        {
		        	divline=$('<div/>',{'class':'linediv'});
		        	divline.appendTo(gpcont);
		        }
		        var celldiv=$('<div/>',{'class':'celldiv'});
		        celldiv.appendTo(divline);
		        var gps= $('<input />', { type: 'checkbox', id: 'gp'+key, value: value});
		        gps.appendTo(celldiv);
		        $('<label />', { 'for': 'gp'+key, text: value }).appendTo(celldiv);	
		        i++;
		        $(gps).bind('click',function(value){
//		        	    alert(value);
			        	$.get("getPokaz", {id:key}, function(responseJson){
				        		var pcont = $("#pokaz");                           // Locate HTML DOM element with ID "someselect".
				        		pcont.find("input").remove();
				        		pcont.find("label").remove();   // Find all child elements with tag name "option" and remove them (just to prevent duplicate options when button is pressed again).
				        		pcont.find("div").remove();   // Find all child elements with tag name "option" and remove them (just to prevent duplicate options when button is pressed again).
						        var i=0;
						        var divline=$('<div/>',{'class':'linediv'});
						        divline.appendTo(pcont);
						        $.each(responseJson, function(key, value) {               // Iterate over the JSON object.
					        	    if ((i%3 == 0) && (i!=0))
							        {
							        	divline=$('<div/>',{'class':'linediv'});
							        	divline.appendTo(pcont);
							        }
							        var celldiv=$('<div/>',{'class':'celldiv'});
							        celldiv.appendTo(divline);
							        var pokazbut = $('<input />', { type: 'checkbox', id: 'pokaz'+key, value: key, 'name':'pokaz'});
							        pokazbut.appendTo(celldiv);
							        $('<label />', { 'for': 'pokaz'+key, text: value }).appendTo(celldiv);		
							        i++;
			        	});
			        });
		        });
		    });  	
		 });
		 };
		});
		$('#by_district').bind('click',function() {
			 {
				$.get("getYears", function(responseJson) {                 // Execute Ajax GET request on URL of "someservlet" and execute the following function with Ajax response JSON..
					var $select = $("#years");                           // Locate HTML DOM element with ID "someselect".
			        $select.find("input").remove();   
			        $select.find("label").remove();   
			        $select.find("div").remove();   
			    });  	
				$.get("getSp", function(responseJson) {                 // Execute Ajax GET request on URL of "someservlet" and execute the following function with Ajax response JSON...
			        var $select = $("#sp");                           // Locate HTML DOM element with ID "someselect".
			        $select.find("input").remove();
			        $select.find("label").remove();   // Find all child elements with tag name "option" and remove them (just to prevent duplicate options when button is pressed again).
			        $select.find("div").remove();   // Find all child elements with tag name "option" and remove them (just to prevent duplicate options when button is pressed again).
			    });  	
				$.get("getGroupPokaz",{issp:'0'}, function(responseJson) {                 // Execute Ajax GET request on URL of "someservlet" and execute the following function with Ajax response JSON...
			        var gpcont = $("#gp");                           // Locate HTML DOM element with ID "someselect".
			        gpcont.find("input").remove();    
			        gpcont.find("label").remove();   // Find all child elements with tag name "option" and remove them (just to prevent duplicate options when button is pressed again).
			        gpcont.find("div").remove();   // Find all child elements with tag name "option" and remove them (just to prevent duplicate options when button is pressed again).
			        var i=0;
			        var divline=$('<div/>',{'class':'linediv'});
			        divline.appendTo(gpcont);
			        $.each(responseJson, function(key, value) {               // Iterate over the JSON object.
			        if ((i%3 == 0) && (i!=0))
			        {
			        	divline=$('<div/>',{'class':'linediv'});
			        	divline.appendTo(gpcont);
			        }
			        var celldiv=$('<div/>',{'class':'celldiv'});
			        celldiv.appendTo(divline);
			        var gps= $('<input />', { type: 'checkbox', id: 'gp'+key, value: value});
			        gps.appendTo(celldiv);
			        $('<label />', { 'for': 'gp'+key, text: value }).appendTo(celldiv);	
			        i++;
			        $(gps).bind('click',function(value){
//			        	    alert(value);
				        	$.get("getPokaz", {id:key}, function(responseJson){
					        		var pcont = $("#pokaz");                           // Locate HTML DOM element with ID "someselect".
					        		pcont.find("input").remove();
					        		pcont.find("label").remove();   // Find all child elements with tag name "option" and remove them (just to prevent duplicate options when button is pressed again).
					        		pcont.find("div").remove();   // Find all child elements with tag name "option" and remove them (just to prevent duplicate options when button is pressed again).
							        var i=0;
							        var divline=$('<div/>',{'class':'linediv'});
							        divline.appendTo(pcont);
							        $.each(responseJson, function(key, value) {               // Iterate over the JSON object.
						        	    if ((i%3 == 0) && (i!=0))
								        {
								        	divline=$('<div/>',{'class':'linediv'});
								        	divline.appendTo(pcont);
								        }
								        var celldiv=$('<div/>',{'class':'celldiv'});
								        celldiv.appendTo(divline);
								        var pokazbut = $('<input />', { type: 'checkbox', id: 'pokaz'+key, value: key, 'name':'pokaz'});
								        pokazbut.appendTo(celldiv);
								        $('<label />', { 'for': 'pokaz'+key, text: value }).appendTo(celldiv);		
								        i++;
				        	});
				        });
			        });
			    });  	
			 });
			 };
			});
		$('#select_all').bind('click',function() {	
			var inp=$('input');
			inp.each(function(i,elem){
//				alert('dfs');
				var str=elem.getAttribute('id');
//				alert(str);
				if (str.indexOf('pokaz')>-1)
				elem.checked=true;
			})
		});