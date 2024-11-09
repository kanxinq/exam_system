<template>
    <div>
        <el-row>
            <el-col :span="16">
                <div style="width: 95%">
                    <el-carousel height="350px" :interval="10000" class="swiper">
                        <el-carousel-item v-for="item in bannerList" :key="item.id">
<!--                          使用v-for循环遍历bannerList数组，为每个轮播项创建一个轮播图条目-->
                            <a :href="item.link" target="_blank">
<!--                              链接元素，设置链接地址为轮播项的link属性，打开方式为新窗口-->
                                <img :src="item.img" alt="" style="width: 100%">
                            </a>
                        </el-carousel-item>
                    </el-carousel>
                </div>
            </el-col>
            <el-col :span="8">
                <el-divider>系统通知</el-divider>
<!--              分割线组件，显示 “系统通知” 文本-->
                <el-collapse v-model="activeNames" style="height: 300px;overflow-y: auto">
<!--                  Element UI 的折叠面板组件，绑定v-model为activeNames，设置高度为 300px 并开启垂直滚动条-->
                    <el-collapse-item :title="item.name" :name="item.id" v-for="item in noticeList" :key="item.id">
<!--                      使用v-for循环遍历noticeList数组，为每个通知项创建一个折叠面板条目。设置标题为通知项的name属性，名称为通知项的id属性-->
                        <div>发布时间：{{ item.createTime }}</div>
<!--                      显示通知项的发布时间-->
                        <div>发布内容：{{ item.content }}</div>
                    </el-collapse-item>
                </el-collapse>
            </el-col>
        </el-row>
        <div style="margin: 10px 0;padding-bottom: 50px">
            <el-row :gutter="10">
                <el-divider>在线考试系统</el-divider>
<!--              分割线组件，显示 “在线考试系统” 文本-->
                <el-col :span="19">
                    <div>
                        <el-divider content-position="left">最新资讯</el-divider>
<!--                      分割线组件，显示 “最新资讯” 文本，并设置在左侧显示-->
                        <el-card v-for="item in listData" :key="item.id"
                                 style="margin-bottom: 10px;cursor: pointer">
<!--                          使用v-for循环遍历listData数组，为每个新闻项创建一个卡片组件。设置底部边距为 10px，鼠标指针样式为指针-->
                            <div style="display: flex"  @click="$router.push('/front/detail?id=' + item.id)">
<!--                              内部的 div 容器，设置为 flex 布局，点击时跳转到新闻详情页面-->
                                <div style="width: 170px;margin-right: 20px">
                                    <img :src="item.img" alt="" style="width: 170px; height: 110px;display: block">
<!--                                  图片元素，设置图片源为新闻项的img属性，宽度为 170px，高度为 110px，显示方式为块级元素-->
                                </div>
                                <div style="flex: 1">
                                    <div style="font-size: 17px; text-align: center;">{{ item.name }}</div>
<!--                                  显示新闻项的名称-->
                                    <div style=" line-height: 30px">{{ item.removeTag.slice(0, 60) }}……</div>
<!--                                  显示新闻项的内容（去除标签后取前 60 个字符并添加省略号）-->
                                    <div style="margin-top:5px;display: flex;justify-content: space-between">
                                        <span><i class="el-icon-view"></i> {{ item.views }}人看过</span>
<!--                                      显示新闻项的浏览人数，使用图标和文本组合-->
                                        <span><i class="el-icon-timer"></i> {{ item.createTime }}</span>
                                        <span><i class="el-icon-user-solid"></i> {{ item.user }}</span>
                                    </div>
                                </div>
                            </div>
                        </el-card>
                    </div>
                </el-col>
                <el-col :span="5">
                    <el-divider content-position="left">友情链接</el-divider>
                    <el-card>
                        <div style="margin-bottom: 15px" v-for="item in linkList" :key="item.id">
<!--                          使用v-for循环遍历linkList数组，为每个友情链接项创建一个内部的 div 容器，设置底部边距为 15px-->
                            <el-link :href="item.link" target="_blank">{{ item.name }}</el-link>
<!--                          Element UI 的链接组件，设置链接地址为友情链接项的link属性，打开方式为新窗口，显示文本为友情链接项的name属性-->
                        </div>
                    </el-card>
                </el-col>
            </el-row>
        </div>
    </div>
</template>

<script>
import utils from "@/utils/utils";

export default {
    name: "FrontHome",
    data() {
        return {
            bannerList: [],
            //用于存储轮播图数据的数组
            listData: [],
            //用于存储资讯列表数据的数组
            activeNames: ['1'],
            //用于控制折叠面板展开状态的数组，初始值为 ['1']
            noticeList: [],
            //用于存储系统通知数据的数组
            linkList:[]
        //  用于存储友情链接数据的数组
        }
    },
    created() {
        //组件创建时的钩子函数
        this.$axios.get("/banner/list").then(res => {
            console.log(res.data)
            this.bannerList = res.data
        })
        //发送 GET 请求获取轮播图数据，成功后将数据存储到bannerList数组中
        this.getNoticeList()
        //调用getNoticeList方法获取系统通知和友情链接数据
        this.getNewsList()
    //  调用getNewsList方法获取新闻列表数据
    },
    methods: {
        getNoticeList() {
            this.$axios.get("/notice/list").then(res => {
                this.noticeList = res.data
            })
            this.$axios.get("/link/list").then(res => {
                this.linkList = res.data
            })
        },
        //发送 GET 请求获取系统通知数据，存储到noticeList数组中，同时发送请求获取友情链接数据，存储到linkList数组中
        getNewsList() {
            this.$axios.get("/news/page", {
                params: {
                    pageNum:1,
                    pageSize:5
                }
            }).then(res => {
                this.listData = res.data.list
                this.listData.forEach(v => {
                    v.removeTag = utils.stripHTMLTagsWithRegex(v.content)
                })
                this.total = res.data.total
            })
        },
    //  发送 GET 请求获取资讯列表数据，设置分页参数为第一页和每页显示 5 条数据。成功后将数据存储到listData数组中，并对每个新闻项的内容进行处理，去除 HTML 标签后存储到removeTag属性中
    }
}
</script>

<style>
.swiper img {
    width: 100%;
    height: 100%;
}
</style>
