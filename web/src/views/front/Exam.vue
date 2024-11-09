<template>
    <div>
        <div style="margin: 10px 0;padding-bottom: 50px">
            <el-row :gutter="10">
                <el-col :span="6" v-for="e in listData" :key="e.id" style="margin-bottom: 10px">
                    <div style="border: 1px solid #ccc; padding-bottom: 10px">
                        <img :src="e.img" alt="" style="width: 100%;height: 150px">
                        <div style="padding: 5px;font-size: 16px;font-weight: bold;">考试题目：{{ e.name }}</div>
                        <div style="padding: 5px">开始时间：{{ e.startTime }}</div>
                        <div style="padding: 5px">结束时间：{{ e.endTime }}</div>
                        <div style="padding: 5px">发布者：{{ e.user }}</div>
                        <div style="padding: 5px">
                            <el-button

                                :disabled="e.isExam==='已考试'"

                                type="primary" @click="$router.push(
                                {
                                path: '/front/examPaper?' +
                                 'ids='+e.question
                                 +'&examName='+e.name
                                 +'&endTime='+e.endTime
                                 +'&type=考试'
                                 +'&id='+e.id
                                 +'&teacherId='+e.userId
                                })"
                            >{{e.isExam}}</el-button>
                        </div>
                    </div>
                </el-col>
            </el-row>

            <div style="margin-top: 20px">
                <el-pagination
                    @current-change="currentChange"
                    @size-change="sizeChange"
                    :page-size="pageInfo.pageSize"
                    :current-page="pageInfo.currentPage"
                    background
                    layout="sizes, prev, pager, next"
                    :total="total">
                </el-pagination>
            </div>

        </div>
    </div>
</template>

<script>
export default {
    name: "",
    data() {
        return {
            //总条数
            total: 0,
            listData: [],
            //分页详情
            pageInfo: {
                //当前页
                pageNum: 1,
                //分页大小
                pageSize: 10
            },
        }
    },
    created() {
        this.getPageList()
    },
    methods: {
        //获取分页数据
        getPageList() {
            this.$axios.get("/exam/page", {
                params: this.pageInfo
            }).then(res => {
                this.listData = res.data.list
                this.total = res.data.total
            })
        },
        //选择分页
        currentChange(e) {
            this.pageInfo.pageNum = e
            this.getPageList()
        },
        //改变分页数量
        sizeChange(e) {
            this.pageInfo.pageSize = e
            this.getPageList()
        },
    }
}
</script>


