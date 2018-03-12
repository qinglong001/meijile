$(function() {
	var token = $('#token').val()
	var templateId = $('#templateId').val()
	var stationId = $('#stationId').val()
	var linkurl = $('#linkurl').val()
	var url = 'http://47.94.136.171/api/'
	var currentStyle = ''

	var vmMuban = new Vue({
		el: '#vmMuban',
		data: {
			editDialog: false, // 编辑文本弹窗开关
			imgDialog: false, // 编辑img弹窗开关
			docked: false, // 弹窗遮罩层
			allSignData: {}, // 所有的内容标志值的列表
			sign: '', // 当前操作的标志
			textHtml: '' // 编辑文本的输入的值
		},
		created: function() {
			var _this = this
			$.ajax({
				method: 'post',
				url: url + 'content/list',
				data: {
					stationId: stationId,
					templateId: templateId
				},
				headers: {
					'Authentication': token
				},
			}).then(res => {
				_this.allSignData = res.body.data
			})
		},
		methods: {
			toggleMenuTool() {
				this.menuTool = !this.menuTool
			},
			closeEditDialog() {
				this.editDialog = false
			},
			closeImgDialog() {
				this.imgDialog = false
			},
			getOneContent() {
				var _this = this
				var formdata = new FormData()
				formdata.append('stationId', stationId)
				formdata.append('templateId', templateId)
				formdata.append('signId', this.sign + '')
				$.ajax({
					method: 'post',
					url: url + 'content/getOneContent',
					data: {
						stationId: stationId,
						templateId: templateId,
						signId: this.sign + '',
					},
					headers: {
						'Authentication': token
					},
				}).then(res => {
					if (res.success) {
						_this.allSignData = Object.assign({}, _this.allSignData, res.body.data)
					}
				})
				// axios.post(url + 'content/getOneContent', formdata, {
				// 	headers: {
				// 		Authentication: token
				// 	}
				// }).then(res => {
				// 	console.log(res)
				// 	_this.allSignData = Object.assign({}, _this.allSignData, res.data.body.data)
				// })
			},
			editImg() {
				var _this = this
				console.log(_this.sign)
				this.$nextTick(function() {
					console.log(_this.sign)
				})
				var formdata = new FormData()
				formdata.append('stationId', stationId)
				formdata.append('templateId', templateId)
				formdata.append('signId', this.sign + '')
				formdata.append('file', this.$refs.avatarInput.files[0])
				formdata.append('contentRemark', 'aa')
				axios.post(url + 'content/addImage', formdata, {
					headers: {
						Authentication: token
					}
				}).then(res => {
					if (res.data.success) {
						_this.imgDialog = false
						_this.getOneContent()
					}
				})
			},
			// 编辑完成 点击确定 提交数据
			editText() {
				var _this = this
				$.ajax({
					method: 'post',
					url: url + 'content/add',
					data: {
						stationId: stationId,
						templateId: templateId,
						signId: this.sign + '',
						contentInfo: this.textHtml,
						contentRemark: ''
					},
					headers: {
						'Authentication': token
					},
				}).then(res => {
					if (res.success) {
						_this.editDialog = false
						_this.getOneContent()
					}
				})
			}
		}
	})


	var editBox = $('<div>', {
		"class": "editBox",
	})
	var editBtn = $('<button>', {
		"class": "editBtn",
		"html": "编辑"
	})
	var imgBtn = $('<button>', {
		"html": "更换图片",
		"class": "imgBtn"
	})
	// var imgUpLoadBox = $('<a>', {
	// 	"class": "a-upload",
	// 	"html": "更换图片"
	// })
	// var imgUpLoadBtn = $('<input>', {
	// 	"class": "imgUpLoadtn",
	// 	"html": "选择图片",
	// 	"type": "file",
	// 	"accept": "image/png"
	// })
	//imgUpLoadBox.append(imgBtn)



	$('.textEditAble').mouseover(function(e) {
		if (isMouseLeaveOrEnter(e, this)) {
			// console.log(window.getComputedStyle(this))
			currentStyle = window.getComputedStyle(this)
			$(this).css({
				'position': 'relative',
				'border': 'solid 1px #000',
				'box-size': 'border-box'
			})
			if ($(this).attr('data-sign')) {
				editBox.empty()
				editBox.append(editBtn)
				$(editBtn).css({
					'position': 'absolute',
					'top': '0px',
					'left': '0px',
				})
				$(this).append(editBtn)
				vmMuban.sign = $(this).attr('data-sign')
			}
		}
	})


	$('.textEditAble').mouseout(function(e) {
		if (isMouseLeaveOrEnter(e, this)) {
			// e.target.style = currentStyle
			$(this).css({
				'border': 'none',
				'box-size': 'content-box'
			})
			$(this).find('.editBtn').remove()
		}
	})


	$(document).on('click', '.editBtn', function(e) {
		e.preventDefault()
		vmMuban.editDialog = true
	})
	$(document).on('click', '.imgBtn', function(e) {
		e.preventDefault()
		vmMuban.imgDialog = true
	})



	$('.imgEditAble').mouseover(function(e) {
		// e.stopPropagation()
		if (isMouseLeaveOrEnter(e, this)) {
			$(this).css({
				'position': 'relative',
				'border': 'solid 1px #000'
			})
			if ($(this).attr('data-sign')) {
				editBox.empty()
				editBox.append(imgBtn)
				$(imgBtn).css({
					'position': 'absolute',
					'top': '0px',
					'left': '0px',
				})
				$(this).append(imgBtn)
				vmMuban.sign = $(this).attr('data-sign')
				console.log(vmMuban.sign)
			}
		}


	})
	$('.imgEditAble').mouseout(function(e) {
		if (isMouseLeaveOrEnter(e, this)) {
			$(this).css({
				'border': 'none'
			})
			$(this).find('.imgBtn').remove()
		}
	})



	function isMouseLeaveOrEnter(e, handler) {
		if (e.type != 'mouseout' && e.type != 'mouseover') return false;　　
		var reltg = e.relatedTarget ? e.relatedTarget : e.type == 'mouseout' ? e.toElement : e.fromElement;
		// if (e.type == 'mouseover') {
		// 	if (reltg.parentNode == handler) {
		// 		return true
		// 	}
		// }
		while (reltg && reltg != handler) reltg = reltg.parentNode;　

		return (reltg != handler);
	};
})