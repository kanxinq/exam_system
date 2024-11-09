<template>
    <div class="login-container">
        <el-form :model="formData" :rules="rulesList"
                 status-icon
                 ref="formData"
                 label-position="left"
               >
            <h3 class="title"  style="color: #000">用户密码修改</h3>
            <el-form-item prop="password" label="密码">
                <el-input type="password"
                          v-model="formData.password"
                          auto-complete="off"
                          placeholder="密码"
                ></el-input>
            </el-form-item>
            <el-form-item prop="newPassword"  label="新密码">
                <el-input type="password"
                          v-model="formData.newPassword"
                          auto-complete="off"
                          placeholder="新密码"
                ></el-input>
            </el-form-item>
            <el-form-item style="width:100%;">
                <el-button type="primary" style="width:100%;"  @click="handleSubmit">修改密码</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>
<script>
export default {
    data(){
        return {
            formData: {
                id:"",
                password: '',
                newPassword: '',
            },
            rulesList: {
                password: [{required: true, message: '密码不能为空', trigger: 'blur'}],
                newPassword: [{required: true, message: '新密码不能为空', trigger: 'blur'}],
            },
        }
    },
    created() {
    },
    methods: {
        handleSubmit (){
            this.$axios.put('/sysUser/editPassword',this.formData).then(() => {
                localStorage.clear()
                //清空本地存储。为了确保用户在修改密码后需要重新登录，清除可能存储的旧用户信息和状态
                this.$message({
                    message: '修改成功请重新登录',
                    type: 'success'
                });
                //显示一条成功消息，告知用户修改成功并需要重新登录
                setTimeout(()=>{
                    this.$router.push({path: "/login"})
                },1000)
            })
        }
    }
};
</script>

<style scoped>

.login-container {
    border-radius: 5px;
    width: 350px;
    padding: 35px 35px 15px;
    background: #fff;
    border: 1px solid #eaeaea;
    box-shadow: 0 0 25px #cac6c6;
}

label.el-checkbox.rememberme {
    margin: 0px 0px 15px;
    text-align: left;
}
.title {
    text-align: center;
}
</style>
