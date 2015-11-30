<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<head>
<title>Выбор данных</title>
<meta charset="UTF-8">
 <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js">
 </script> 
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
		<fieldset id="years">
			<legend>
				<b>Год</b>
			</legend>			
		</fieldset>
		<p></p>
		<fieldset id="sp">
			<legend>
				<b>Сельское поселение</b>
			</legend>			
		</fieldset>
		<p></p>
		<fieldset id = "gp">
			<legend>
				<b>Группы показателей</b>
			</legend>
			
		</fieldset>
		<fieldset id="pokaz">
			<legend>
				<b>Показатели</b>
			</legend>
		</fieldset>
	</form>
	<select id="someselect"></select>
	<div></div>	
	
 <script  type="text/javascript" src="src.js">
 </script>
    
</body>