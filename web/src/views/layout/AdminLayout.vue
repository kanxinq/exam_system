<template>
    <el-container style="height: 100%; ">
<!--      创建一个容器元素，设置高度为 100%，以占满整个页面高度-->
        <el-aside width="200px" style="background-color: #000000;overflow-x:hidden">
<!--          侧边栏元素，宽度为 200px，背景颜色为黑色，设置了横向滚动条隐藏（overflow-x:hidden）-->
            <h3 class="title">后台管理系统</h3>
<!--            侧边栏的标题，显示 “后台管理系统”-->
            <el-menu
                background-color="#000000"
                text-color="#fff"
                :default-openeds="['user']" router style="border: none" :default-active="$route.path">
<!--              菜单组件，背景颜色为黑色，文本颜色为白色，设置默认展开的菜单为 “user”，使用路由模式（router），无边框（border: none），默认激活的菜单项与当前路由路径一致（:default-active="$route.path"）-->
                <el-menu-item v-for="(item,i) in roleMenu" v-show="!item.isPage" :key="i" :index="item.path"><i :class="item.iconClass"></i><span slot="title">{{item.title}}</span></el-menu-item>
<!--              使用v-for循环遍历roleMenu数组，为每个非页面类型的菜单项创建一个菜单条目。根据菜单项的iconClass属性设置图标，显示菜单项的标题-->
                <el-submenu index="user"  v-for="item in roleMenu" v-show="item.isPage" :key="item.name">
<!--                  子菜单组件，索引为 “user”，同样使用v-for循环遍历roleMenu数组，为每个页面类型的菜单项创建一个子菜单-->
                    <template slot="title">
                        <i class="el-icon-s-home"></i><span>{{ item.title }}</span>
                    </template>
<!--                  子菜单的标题部分，显示一个图标和菜单项的标题-->
                    <el-menu-item v-for="(current,index) in item.list" :key="index" :index="current.path"> <i :class="current.iconClass"></i><span slot="title">{{current.title}}</span></el-menu-item>
<!--                  为子菜单中的每个页面创建一个菜单项，设置图标和标题-->
                </el-submenu>
            </el-menu>
        </el-aside>
        <el-container>
            <el-header style="text-align: right; font-size: 12px">
                <el-avatar style="width: 30px;height: 30px;position: relative;top: 10px;right: 10px" shape="square" :size="30" :src="userInfo.img"></el-avatar>
                <el-dropdown>
<!--                  下拉菜单的链接部分，显示用户名称和一个下拉箭头图标-->
                    <span class="el-dropdown-link">
                        {{ userInfo.username }}<i class="el-icon-arrow-down el-icon--right"></i>
                    </span>
                        <el-dropdown-menu slot="dropdown">
                        <el-dropdown-item><span @click="editUserInfo">个人信息</span></el-dropdown-item>
                        <el-dropdown-item><span @click="editPassword">修改密码</span></el-dropdown-item>
                        <el-dropdown-item divided><span @click="logout">退出登录</span></el-dropdown-item>
                    </el-dropdown-menu>
<!--                  下拉菜单的内容，包含个人信息、修改密码和退出登录三个选项-->
                </el-dropdown>
            </el-header>
            <el-main>
<!--              主体部分，用于渲染路由对应的视图内容-->
                <router-view/>
<!--              路由视图占位符-->
            </el-main>
        </el-container>
    </el-container>
</template>
<script>
import tools from "@/utils/utils";

export default {
    data() {
        return {
            userInfo:tools.getUserInfo(),
            //初始值通过调用tools.getUserInfo()函数获取
            roleMenu:[]
        //  初始化为一个空数组，用于存储菜单数据
        }
    },
    created() {
    },
    mounted() {
        this.roleMenu=tools.getRoleMenu()
    //  组件挂载后执行的钩子函数，将roleMenu属性设置为通过调用tools.getRoleMenu()函数获取的值，获取菜单数据并填充到数组中
    },
    methods: {
        logout() {
            this.$message({
                message: "退出登录成功，正在跳转",
                type: "success"
            })
            localStorage.removeItem("systemUser")
            //从本地存储中移除名为systemUser的数据项
            localStorage.removeItem("systemRoleMenu")
            //从本地存储中移除名为systemRoleMenu的数据项
            setTimeout(() => {
                this.$router.push({path: "/login"})
            }, 1000)
        },
        //用户退出登录的方法，显示退出成功的消息，移除本地存储中的用户信息和角色菜单信息，并在 1 秒后跳转到登录页面。
        editUserInfo() {
            this.$router.push({path: "/editUserInfo"})
        },
        //跳转到编辑用户信息页面的方法
        editPassword() {
            this.$router.push({path: "/editPassword"})
        //  跳转到修改密码页面的方法
        },
    }
};
</script>

<style>
.el-header {
    background-color: #B3C0D1;
    /*color: #333;*/
    line-height: 60px;
}

.el-aside {
    /*color: #333;*/
}

/*更改下拉框的样式*/
.el-dropdown-link {
    cursor: pointer;
    color: #409EFF;
}

.el-icon-arrow-down {
    font-size: 12px;
}

.title {
    text-align: center;
    color: #fff;
    height: 30px;
    line-height: 30px;
    margin-top: 11px;
}
/*搜索框大小定义*/
.input {
    width: 200px !important;
    margin-right: 10px
}

.mr10px {
    margin-right: 10px;
}

.pb10px {
    padding-bottom: 10px;
}
</style>

