<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<head>
<title>Выбор данных</title>
<meta charset="UTF-8">
<!--  <script type="text/javascript" src="jquery-2.1.4.js"> -->
<!--  </script>  -->
 <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js">
 </script> 
<!--  <script  type="text/javascript"> -->
<!-- //     alert('blaaaa'); -->
<!--  </script> -->
<link type="text/css" href="style.css" rel="stylesheet" >
</head>
<body>
 <!--  <div class="header">Выбрать данные по</div> -->

	<form method="post">
		<fieldset>
			<legend>
				<b>Выбрать данные по:</b>
			</legend>
			<div class="bord">
				<div class="col1">
					<input type="radio" name="data_type" value="by_year"> По
					году
				</div>
				<div class="col2">
					<input type="radio" name="data_type" value="by_sp"> По
					сельскому поселению
				</div>
				<div class="col3">
					<input type="radio" name="data_type" value="by_district">
					По району в целом
				</div>
			</div>
		</fieldset>
		<fieldset>
			<legend>
				<b>Год</b>
			</legend>
			Name: <input type="text" size="20"><br> Email: <input
				type="text" size="20">
		</fieldset>
		<p></p>
		<fieldset>
			<legend>
				<b>Сельское поселение</b>
			</legend>
			Favorite Color: Red: <input type="radio"> Blue: <input
				type="radio"> Green: <input type="radio">
			<p>
				Favorite Toothpaste: Crest: <input type="checkbox">
				Close-Up: <input type="checkbox"> Gleem: <input
					type="checkbox">
			</p>
		</fieldset>
		<p></p>
		<fieldset>
			<legend>
				<b>Группы показателей</b>
			</legend>
			<br> <input type="button"
				onclick="alert('Stop clicking the buttons')" value="Click to Submit">
			<br> <br>
		</fieldset>
		<fieldset>
			<legend>
				<b>Показатели</b>
			</legend>
			<br> <input type="button"
				onclick="alert('Stop clicking the buttons')" value="Click to Submit">
			<br> <br>
		</fieldset>
	</form>
	<select id="someselect"></select>
	<div></div>
	<!--  <div class="layout">
   <div class="col1 cc">Колонка 1</div>
   <div class="col2 cc">Колонка 2</div>
   <div class="col3 cc">Колонка 3</div>
  </div>-->
 <script  type="text/javascript">
//     alert('blaaaa');   
		$('input').bind('click',function() {
			if  ($(this).attr("value")=="by_year")
	 			 {
					$.get("getYears", function(responseJson) {                 // Execute Ajax GET request on URL of "someservlet" and execute the following function with Ajax response JSON...
				        var $select = $("#someselect");                           // Locate HTML DOM element with ID "someselect".
				        $select.find("option").remove();                          // Find all child elements with tag name "option" and remove them (just to prevent duplicate options when button is pressed again).
				        $.each(responseJson, function(key, value) {               // Iterate over the JSON object.
				            $("<option>").val(key).text(value).appendTo($select); // Create HTML <option> element, set its value with currently iterated key and its text content with currently iterated item and finally append it to the <select>.
				        });
				    });  		 
	 			 }
		});
// 	  $(document).on("click", '#by_year', function() {// When HTML DOM "click" event is invoked on element with ID "somebutton", execute the following function...
// 		 alert('dgfhsdfkkjdfh');
// 		/*  if  ($(this).attr("value")=="by_year")
// 			 {
//                alert('HERE!!'); 			 
// 			 } */
// 	     <!--$.get("someservlet", function(responseText) {   // Execute Ajax GET request on URL of "someservlet" and execute the following function with Ajax response text...
// 	        alert(responseText);           // Locate HTML DOM element with ID "somediv" and set its text content with the response text.
// 	     });-->
// 	 }); 
 </script>
    
</body>