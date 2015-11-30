var radioBtn;
		$('input').bind('click',function() {
			if  ($(this).attr("value")=="by_year")
	 			 {
					$.get("getYears", function(responseJson) {                 // Execute Ajax GET request on URL of "someservlet" and execute the following function with Ajax response JSON..
						var $select = $("#years");                           // Locate HTML DOM element with ID "someselect".
				        $select.find("input").remove();   
				        $select.find("label").remove();   
				         $.each(responseJson, function(index, item) {               // Iterate over the JSON object.
				        	var container=$('#years');
					        $('<input />', { type: 'radio', id: 'year'+index, value: item , style:'display:inline-block; width:20px'}).appendTo(container);
					        $('<label />', { 'for': 'cb'+index, text: item }).appendTo(container);		
				        });
				    });  	
					$.get("getSp", function(responseJson) {                 // Execute Ajax GET request on URL of "someservlet" and execute the following function with Ajax response JSON...
				        var $select = $("#sp");                           // Locate HTML DOM element with ID "someselect".
				        $select.find("input").remove();
				        $select.find("label").remove();   // Find all child elements with tag name "option" and remove them (just to prevent duplicate options when button is pressed again).
				        $.each(responseJson, function(key, value) {               // Iterate over the JSON object.
					        var container=$('#sp');
					        $('<input />', { type: 'checkbox', id: 'sp'+key, value: value, style:'display:inline-block; width:20px'}).appendTo(container);
					        $('<label />', { 'for': 'sp'+key, text: value }).appendTo(container);		
				        });
				    });  	
					$.get("getGroupPokaz", function(responseJson) {                 // Execute Ajax GET request on URL of "someservlet" and execute the following function with Ajax response JSON...
				        var $select = $("#gp");                           // Locate HTML DOM element with ID "someselect".
				        $select.find("input").remove();    
				        $select.find("label").remove();   // Find all child elements with tag name "option" and remove them (just to prevent duplicate options when button is pressed again).
				        $.each(responseJson, function(key, value) {               // Iterate over the JSON object.
				        var container=$('#gp');
				        var gps= $('<input />', { type: 'checkbox', id: 'gp'+key, value: value, style:'display:inline-block; width:20px' });
				        gps.appendTo(container);
				        $('<label />', { 'for': 'gp'+key, text: value }).appendTo(container);		
				        $(gps).bind('click',function(value){
//				        	    alert(value);
					        	$.get("getPokaz", {id:key}, function(responseJson){
					        		 var $select = $("#pokaz");                           // Locate HTML DOM element with ID "someselect".
								        $select.find("input").remove();
								        $select.find("label").remove();   // Find all child elements with tag name "option" and remove them (just to prevent duplicate options when button is pressed again).
								        $.each(responseJson, function(key, value) {               // Iterate over the JSON object.
									        var container=$('#pokaz');
									        $('<input />', { type: 'checkbox', id: 'pokaz'+key, value: value, style:'display:inline-block; width:20px'}).appendTo(container);
									        $('<label />', { 'for': 'pokaz'+key, text: value }).appendTo(container);		
					        	});
					        });
				        });
				    });  	
	 			 });
		};
		});