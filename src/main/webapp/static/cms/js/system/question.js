function timeFormatter(value) {
	return new Date(value).Format("yyyy-MM-dd HH:mm:ss");
}

function answerFormatter(value) {
	if (value == 0) {
		return '<span class="label label-danger">未回复</span>'
	} else if (value == 1) {
		return '<span class="label label-primary">已回复</span>'
	}else if (value == 2) {
		return '<span class="label label-primary">不予回复</span>'
	}
}

var elems = Array.prototype.slice.call(document.querySelectorAll('.js-switch'));

elems.forEach(function(html) {
	var switchery = new Switchery(html, {
		size : 'small'
	});
});

function actionFormatter(value, row, index) {
	return [
		'<a class="normal" href="javascript:void(0)" title="回复">',
		'<i>回复</i>',
		'</a>',	
	].join('');
	
}

window.actionEvents = {
	'click .normal' : function(e, value, row, index) {
		layer_show(row.name, baselocation + '/system/question/'+row.questionId+'/answer'  , 800, 480)
	}
};

function btn_submit(){
	var $form = $("#form");
	$.ajax({
		data : $form.serialize(),
		dataType : 'json',
		type : 'post',
		url : $form.attr('action'),
		success : function(result) {
			if (result.code == 1) {
				parent.layer.msg("回复完成!", {
					shade : 0.3,
					time : 500
				}, function() {
					window.parent.location.reload(); // 刷新父页面
				});
			} else {
				layer.msg(result.message, {
					icon : 2,
					time : 1000
				});
			}
		}
	})
}