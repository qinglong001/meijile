window.onload = function() {
	// var token = $('#token').val()
	// var templateId = $('#templateId').val()
	// var stationId = $('#stationId').val()
	// var linkurl = $('#linkurl').val()
	// var url = 'http://127.0.0.1/api/'

	// var sign = ''
	// var vm = new Vue({
	// 	el: '#vm',
	// 	data: {
	// 		menuTool: false,
	// 		editDialog: false,
	// 		imgDialog: false,
	// 		docked: false,
	// 		allSignData: ''
	// 	},
	// 	created: function() {
	// 		var _this = this
	// 		$.ajax({
	// 			method: 'post',
	// 			url: url + 'content/list',
	// 			data: {
	// 				stationId: stationId,
	// 				templateId: templateId
	// 			},
	// 			headers: {
	// 				'Authentication': token
	// 			},
	// 		}).then(res => {
	// 			_this.allSignData = res.body.data
	// 		})
	// 	},
	// 	methods: {
	// 		toggleMenuTool() {
	// 			this.menuTool = !this.menuTool
	// 		},
	// 		closeEditDialog() {
	// 			this.editDialog = false
	// 		},
	// 		closeImgDialog() {
	// 			this.imgDialog = false
	// 		},
	// 		editImg() {
	// 			var image = new FormData()
	// 			image.append('stationId', this.stationId)
	// 			image.append('templateId', this.templateId)
	// 			image.append('signId', 'nav1')
	// 			image.append('file', this.$refs.avatarInput.files[0])
	// 			image.append('stationId', this.stationId)
	// 		},
	// 		editText() {
	// 			$.ajax({
	// 				method: 'post',
	// 				url: url + 'content/add',
	// 				data: {
	// 					stationId: stationId,
	// 					templateId: templateId,
	// 					signId: sign,
	// 					contentInfo: 'aaa',
	// 					contentRemark: 'info'
	// 				},
	// 				headers: {
	// 					'Authentication': token
	// 				},
	// 			}).then(res => {
	// 				console.log(res)
	// 			})
	// 		}
	// 	}
	// })

	$("#modelContent").load('yellowmoon.html', function() {

	})

}