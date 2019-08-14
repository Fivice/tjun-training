
function controlHandleBtn() {
	var assets = $('#table_asset').bootstrapTable("getSelections");
	$('.li_use').show();
	$('.li_nouse').show();
	$('.li_borrow').show();
	$('.li_revert').show();
	$('.li_transfer').show();
	$('.li_repair').show();
	$('.li_clear').show();
	/*$('#drop1').attr("disabled",'false');
	console.log($('#drop1').attr("disabled"));*/
	
	$('.drop1').show();
	var states = [];
	for (var i = 0; i < assets.length; i++) {
		states.push(assets[i].state);
	}
	 if(assets.length > 0) {
		for (var j = 0; j < states.length; j++) {

			switch (states[j]) {
			case 0:
				$('.li_use').hide();
				$('.li_nouse').hide();
				$('.li_borrow').hide();
				$('.li_revert').hide();
				$('.li_transfer').hide();
				$('.li_repair').hide();
				break;
			case 1:
				$('.li_use').hide();
				$('.li_revert').hide();
				break;
			case 2:
				$('.li_nouse').hide();
				$('.li_revert').hide();
				break;
			case 3:
				$('.li_use').hide();
				$('.li_nouse').hide();
				$('.li_borrow').hide();
				$('.li_revert').hide();
				$('.li_transfer').hide();
				$('.li_repair').hide();
				$('.li_clear').hide();
				/*$('#drop1').attr("disabled",'true');
				console.log($('#drop1').attr("disabled"));*/
				break;
			}
		}

	}

}
/*
 * 领用
 */
function rapid_use() {
	var rows = $('#table_asset').bootstrapTable("getSelections");
	var numbers = "";
	if (rows.length == 0) {
		layer.alert('请选择资产！', {
	   		icon: 5,
	   		title: "提示"
	   		});
	}
	else{	
	    for (var i = 0; i < rows.length; i++) {
		     numbers += rows[i]['barcode'].toString() + ",";
	}
//	console.log(numbers)
	layer_show('新增领用单', path + '/collar/rapid_add.action?barcode=' + numbers,
			900, 480)
	}
}

/*
 * 调拨
 */
function rapid_transfer() {
	var rows = $('#table_asset').bootstrapTable("getSelections");
	var numbers = "";
	if (rows.length == 0) {
		layer.alert('请选择资产！', {
	   		icon: 5,
	   		title: "提示"
	   		});
	}
	else{	
	    for (var i = 0; i < rows.length; i++) {
		     numbers += rows[i]['barcode'].toString() + ",";
	}
//	console.log(numbers)
	layer_show('新增调拨单', path + '/collar/allocation_add_fast.action?barcode=' + numbers,
			900, 480)
	}
}

/*
 * 维修登记
 */
function rapid_repair() {
	var rows = $('#table_asset').bootstrapTable("getSelections");
	var numbers = "";
	if (rows.length == 0) {
		layer.alert('请选择资产！', {
	   		icon: 5,
	   		title: "提示"
	   		});
	}
	else{	
	    for (var i = 0; i < rows.length; i++) {
		     numbers += rows[i]['barcode'].toString() + ",";
	}
//	console.log(numbers)
	layer_show('新增维修登记单', path + '/collar/maintenance_registration.action?barcode=' + numbers,
			900, 480)
	}
}



/*
 * 清理报废
 */
function rapid_clear() {
	var rows = $('#table_asset').bootstrapTable("getSelections");
	var barcodes = "";
	if (rows.length == 0) {
		layer.alert('请选择资产！', {
	   		icon: 5,
	   		title: "提示"
	   		});
	}
	else{	
		 for(var i=0;i<rows.length;i++){
			   barcodes += rows[i]['barcode'].toString()+","
		   };
//		   console.log(barcodes);
		   layer.confirm('确认要清理待报废资产吗？', {
				btn : [ '确定', '取消' ] //按钮
			}, function() {
				$.ajax({
					type : 'POST',
					dataType : 'json',
					 data:{ 
						 barcodes
		                   	},
					url : path +'/scrap/clean.action',
					success : function(result) {
						if (result.code == 1) {
							layer.msg('资产清理报废成功!', {
								icon : 1,
								time : 1000
							}, function() {
	  							window.location.reload(); // 刷新父页面
	  						});
						} else {
							layer.alert(result.message, {
								icon : 2
							});
						}
					}
				})
			});
	}
}
