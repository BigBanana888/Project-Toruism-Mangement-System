<template>
	<div class="content">
		<div class="content-title" >青岛恒星科技学院场地预约系统</div>
	</div>
</template>

<style scoped lang="less">
.content{
	display: flex;
	padding: 10px 20px;
	background-color:  #d0def6 ;
	color: #000;
	box-shadow: 2px 2px 2px 1px rgba(0, 0, 0, 0.2);
	text-align: center;
	justify-content: space-between;
	align-items: center;
	height: 60px;
	.content-title{
        user-select: none;
		font-size: 32px;
		text-align: left;
		width: 20%;
		min-width: 100px;text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.5); 
		width: 500px;
		margin-left: 64px;
	}
	// .content-title:hover{
	// 	cursor: pointer;
	// }
	.content-menus{
		width: 30%;
		min-width: 250px;
		display: flex;
		align-items: center;
		justify-content: space-around;
	}
}
.menus{
	display: flex;
	font-size: 24px;
	text-align: center;
	line-height: 40px;
	justify-content: space-around;
}

.menu-item{
		width: 100px;
		color: #5b5b5b;
	}
	// .menu-item:hover,.menu-item1:hover{
	// 	color: #000;
	// 	cursor: pointer;
	// }
	.menu-item1{
		width: 100px;
		color: #4c4c4c;
	}
</style>

<!-- <script >
export default{
	data(){
		return {
				defaultAvatar: require('@/assets/images/1.png'),
				imageUrl:undefined,
				activateId:-1,
				username:'',
				roleId:1
			}
	},
	computed:{
		subTitle() {
			return this.$route.meta.title
		}
	},
	mounted(){
		const userInfo = this.$store.getStore('user')
		this.username = userInfo.username
		this.roleId = userInfo.roleId
		this.imageUrl = userInfo.imageUrl
	},
	methods:{
		gotoPage (command) {
			if (command === 'logout') {
				this.$store.removeStore('stuCode')
				this.$store.removeStore('userName')
				this.$store.removeStore('roleId')
				this.$store.removeStore('token')
				this.$router.push('/login')
			} else {
				this.$router.push('/' + command)
			}
		}
	}
}
</script> -->