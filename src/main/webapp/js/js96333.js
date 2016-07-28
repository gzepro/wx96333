
$(function(){
	
	$("#submit").click(function(){
		postBJ();
	});
	

});

function postBJ(){
	//判断是否为空的代码省
	
	
	$.ajax({
		url : "/postBJ",
		data : {
			mobile : $("#mobile").val(),
			zcode : $("#zcode").val(),
			ztype : $("#ztype").val()
		},
		type:"post",
		dataType:"json",
		success : function(data,status){			
			console.log(data);
			if(data.code != 1 ){
				return ;
			}
			
			$("#h_result").html(data.info);
			$("#show_result").show();
			$("#show_form").hide();
			
		},
		error : function(error){
			
		},beforeSend:function(){
			//设置事件可重复点击
			$("#submit").attr('disabled',"true");
			
		},complete : function(){
			$("#submit").removeAttr("disabled");
		}
	});
}