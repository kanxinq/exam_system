
import router from "../router"
import Element from "element-ui";
const tools = {
    //获取认证信息
    getToken(){
        if(tools.isUserLogin()){
            return JSON.parse(localStorage.getItem("systemUser")).token
        }else{
            Element.Message.error("用户信息获取失败，请重新登录")
            //跳转到登录页面
            router.push({path:"/login"})
        }
    },
    //获取用户信息
    getUserInfo(){
        if(tools.isUserLogin()){
            return JSON.parse(localStorage.getItem("systemUser"))
        }else{
            Element.Message.error("用户信息获取失败，请重新登录")
            //跳转到登录页面
            router.push({path:"/login"})
        }
    },

    isUserLogin(){
        return localStorage.getItem("systemUser") !== null
    },
    //检查本地存储中是否存在systemUser项，如果存在则表示用户已登录，返回true，否则返回false
    getRoleMenu(){
        return  JSON.parse(localStorage.getItem("systemRoleMenu"))===null?[]:JSON.parse(localStorage.getItem("systemRoleMenu"))
    },
    //从本地存储中获取systemRoleMenu项，如果该项为null则返回空数组，否则解析并返回该项的值
    stripHTMLTagsWithRegex(html) {
        let tagBody = "(?:<[^>]+>|)";
        let cleanText = html.replace(new RegExp(tagBody, "gi"), "");
        return cleanText;
    },
    //定义一个正则表达式来匹配 HTML 标签，然后使用replace方法将匹配到的标签替换为空字符串，返回去除标签后的文本内容
    isAdmin(){
      return   tools.getUserInfo().roleType==='ADMIN'
    },
//    调用getUserInfo函数获取用户信息，然后检查用户的roleType属性是否为'ADMIN'，如果是则返回true，否则返回false

}

export  default tools
