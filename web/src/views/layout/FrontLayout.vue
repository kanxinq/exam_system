<template>
    <div class="main">
        <el-container>
            <el-header style="padding: 0">
<!--              页面头部元素，设置内边距为 0-->
                <div class="container-fluid" style="box-shadow: 5px 0 5px #ccc">
<!--                  具有流体布局类名的 div，设置了一个水平阴影效果-->
                    <div class="container">
                        <el-row class="nav">
                            <el-col :span="4" class="logo">
<!--                              列布局组件，占 4 列宽度，具有logo类名，用于放置 logo-->
                                <div>
                                    <router-link to="/front/index" tag="div">
<!--                                      Vue Router 的路由链接，点击跳转到/front/index路径，标签为 div-->
                                        <img src="@/assets/logo1.png" alt="">
<!--                                      显示一个 logo 图片，路径为@/assets/logo1.png，设置了替代文本-->
                                    </router-link>
                                </div>
                            </el-col>
                            <el-col :span="16">
<!--                              列布局组件，占 16 列宽度-->
                                <div>
                                    <el-menu :default-active="$route.path" class="el-menu-demo" mode="horizontal" router
                                             @select="handleSelect">
<!--                                      设置默认激活的菜单项为当前路由路径（:default-active="$route.path"），类名为el-menu-demo，模式为水平模式（mode="horizontal"），
并绑定了路由（router）和菜单项选择事件（@select="handleSelect"）-->
                                        <el-menu-item index="/front/index">首页</el-menu-item>
<!--                                      菜单条目，索引为/front/index，显示文本为 “首页”-->
                                        <el-menu-item index="/front/news">最新资讯</el-menu-item>
                                        <el-menu-item index="/front/exam">在线考试</el-menu-item>
                                        <el-menu-item index="/front/score">我的成绩</el-menu-item>
                                        <el-menu-item index="/front/about">关于我们</el-menu-item>
                                        <template v-if="!isUserLogin">
<!--                                          根据条件判断是否显示登录和注册菜单项的模板-->
                                            <el-menu-item index="/login">登录</el-menu-item>
                                            <el-menu-item index="/register">去注册</el-menu-item>
                                        </template>
                                    </el-menu>
                                </div>
                            </el-col>
                            <el-col :span="4" class="control">
                                <el-avatar style="width: 30px;height: 30px;position: relative;top: 15px;right: 10px"
                                           shape="square" :size="30" :src="userInfo.img"></el-avatar>
                                <el-dropdown v-if="isUserLogin">
<!--                                  根据条件判断是否显示下拉菜单的组件-->
                                    <span class="el-dropdown-link">{{ userInfo.username }}<i
                                        class="el-icon-arrow-down el-icon--right"></i></span>
<!--                                  下拉菜单的链接部分，显示用户名称和一个下拉箭头图标-->
                                    <el-dropdown-menu slot="dropdown">
<!--                                      下拉菜单的内容部分-->
                                        <el-dropdown-item><span @click="editUserInfo">个人信息</span></el-dropdown-item>
<!--                                      下拉菜单项，点击触发editUserInfo方法，显示文本为 “个人信息”-->
                                        <el-dropdown-item><span @click="editPassword">修改密码</span></el-dropdown-item>
<!--                                      下拉菜单项，点击触发editPassword方法，显示文本为 “修改密码”-->
                                        <el-dropdown-item divided><span @click="logout">退出登录</span>
<!--                                          带有分隔线的下拉菜单项，点击触发logout方法，显示文本为 “退出登录”-->
                                        </el-dropdown-item>
                                    </el-dropdown-menu>
                                </el-dropdown>
                            </el-col>
                        </el-row>
                    </div>
                </div>
            </el-header>
            <div class="container">
                <el-main>
                    <router-view/>
<!--                  Vue Router 的路由视图占位符，用于渲染当前路由对应的组件内容-->
                </el-main>
            </div>
            <div class="container-fluid">
                <el-footer class="footer">
                </el-footer>
            </div>
        </el-container>
    </div>
</template>

<script>
import tools from "@/utils/utils";

export default {
    data() {
        return {
            isUserLogin: tools.isUserLogin(),
            //通过调用tools.isUserLogin()函数获取的值，用于判断用户是否登录
            userInfo: tools.getUserInfo()
        //  通过调用tools.getUserInfo()函数获取的值，包含用户信息
        };
    },
    methods: {
        handleSelect(key, keyPath) {
            console.log(key, keyPath);
        },
        //处理菜单选择事件的方法，打印选中的菜单项和路径
        logout() {
            this.$message({
                message: "退出登录成功，正在跳转",
                type: "success"
            })
            localStorage.removeItem("systemUser")
            localStorage.removeItem("systemRoleMenu")

            setTimeout(() => {
                this.$router.push({path: "/login"})
            }, 1000)
        },
        //用户退出登录的方法，显示退出成功的消息，移除本地存储中的用户信息和角色菜单信息，并在 1 秒后跳转到登录页面。
        editUserInfo() {
            this.$router.push({path: "/front/editUserInfo"})
        },
        //跳转到编辑用户信息页面的方法
        editPassword() {
            this.$router.push({path: "/front/editPassword"})
        //  跳转到修改密码页面的方法
        },
    }
}
</script>


<style scoped>
.container {
    width: 1200px;
    margin: 0 auto;
    /*background: pink;*/

}

.container-fluid {
    width: 100%;
    /*background: #909399;*/
}

.el-menu.el-menu--horizontal {
    border-bottom: none;
}

.el-menu, .el-header {
    background: #fff;
    /*background: #909399;*/
}

.el-footer {
    padding: 10px 0;
    height: auto !important;
}

.el-main {
    /*padding: 20px;*/
    /*box-shadow: 0 0 3px #ccc;*/
    /*margin: 30px 0 110px;*/
}

.main {
    position: relative;
    min-height: 100%;
}

.el-dropdown-link {
    cursor: pointer;
    color: #409EFF;
}

.el-icon-arrow-down {
    font-size: 12px;
}


</style>

<style scoped>
.nav {
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.control {
    display: flex;
    justify-content: right;
}

.logo img {
    display: flex;
    align-items: center;
    width: 100px;
    cursor: pointer;
}

.footer {
    text-align: center;
    color: #fff;
    background: #409EFF;
    position: absolute;
    bottom: 0;
    width: 100%;
}

.footer p {
    line-height: 30px;
    font-size: 14px;

}
</style>
