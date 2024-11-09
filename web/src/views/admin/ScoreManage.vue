<template>
    <div>
        <div class="pb10px">
            <el-form ref="searchForm" :model="searchForm" inline>

            </el-form>
        </div>
        <div v-if="userInfo.roleType!=='USER'">
            <el-button type="danger" icon="el-icon-remove-outline" @click="batchDelete([])">批量删除</el-button>
        </div>
        <el-table ref="multipleTable"
                  :data="listData"
                  tooltip-effect="dark"
                  style="width: 100%" >
            <el-table-column type="selection" width="55"></el-table-column>
            <el-table-column prop="id" label="ID" width="50"></el-table-column>
            <el-table-column prop="userId" label="学生ID"></el-table-column>
            <el-table-column prop="examId" label="考试ID"></el-table-column>
            <el-table-column prop="score" label="成绩"></el-table-column>
            <!--      <el-table-column prop="userAnswers" label="学生提交的答案"></el-table-column>-->
            <el-table-column prop="name" label="考试名称"></el-table-column>
            <el-table-column prop="user" label="学生姓名"></el-table-column>
            <el-table-column prop="ids" label="题目ids" show-overflow-tooltip></el-table-column>
            <el-table-column prop="isScore" label="是否评分"></el-table-column>

            <el-table-column label="操作" width="400" fixed="right">
                <template slot-scope="scope">

                    <el-button icon="el-icon-edit-outline" @click="score(scope.row)"
                               v-if="
                               (userInfo.roleType==='USER'&&scope.row.isScore==='已评分')
                               ||
                                (userInfo.roleType==='TEACHER')
                                " >
                        {{ userInfo.roleType === "USER" ? "查看答题情况" : "评分" }}
                    </el-button>
                    <el-button type="danger" icon="el-icon-remove-outline"
                               @click="handleDelete(scope.$index, scope.row)" v-if="userInfo.roleType!=='USER'">删除
                    </el-button>
                </template>
            </el-table-column>
        </el-table>

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
</template>

<script>

import utils from "@/utils/utils";

export default {
    data() {
        return {
            //抽屉控制
            isDrawerDialog: false,
            //列表数据
            listData: [],
            //总条数
            total: 0,
            //分页详情
            pageInfo: {
                //当前页
                pageNum: 1,
                //分页大小
                pageSize: 10
            },
            //搜索
            searchForm: {
                name: ""
            },
            formData: {},
            userInfo: utils.getUserInfo(),
        }
    },

    created() {
        this.getPageList()
    },
    methods: {
        score(e) {
            console.log(e)
            //根据角色跳转
            if("USER"===this.userInfo.roleType){
                this.$router.push("/front/examPaper?ids=" + e.ids + "&examName=" + e.name + "&&type=评分&id=" + e.id)
            }else{
                this.$router.push("/examPaper?ids=" + e.ids + "&examName=" + e.name + "&&type=评分&id=" + e.id)
            }
        },
        //获取分页数据
        getPageList() {
            let data = Object.assign(this.searchForm, this.pageInfo)
            this.$axios.get("/score/page", {
                params: data
            }).then(res => {
                this.listData = res.data.list
                this.total = res.data.total
            })
        },
        resetForm() {
            this.$refs['searchForm'].resetFields();
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
        //搜索
        handleSearch() {
            this.pageInfo.pageNum = 1
            this.getPageList()
        },
        //提交数据
        submit() {
            //新增
            if (!this.formData.id) {
                this.$axios.post("/score/add", this.formData).then(res => {
                    if (res.code === 200) {
                        this.isDrawerDialog = false
                        this.$message({
                            message: res.msg,
                            type: 'success'
                        });
                        this.getPageList()
                    }
                })
            } else {
                //更新
                this.$axios.put("/score/update", this.formData).then(res => {
                    if (res.code === 200) {
                        this.isDrawerDialog = false
                        this.$message({
                            message: res.msg,
                            type: 'success'
                        });
                        this.getPageList()
                    }
                })
            }
        },
        //添加数据
        handleAdd() {
            this.formData = {}
            this.isDrawerDialog = true
        },
        //编辑
        handleEdit(index, row) {
            this.formData = Object.assign({}, row)
            this.isDrawerDialog = true
        },
        //单个删除
        handleDelete(index, row) {
            this.batchDelete([row])
        },
        //批量删除
        batchDelete(rows) {
            if (rows.length<=0)  {
                rows = this.$refs.multipleTable.selection;
            }
            let ids = rows.map(item => item.id);
            this.$confirm(`此操作将永久删除ID为[${ids}]的数据, 是否继续?`, '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning',
                center: true
            }).then(() => {
                //批量删除数据
                this.$axios.delete("/score/delBatch", {data: ids}).then(res => {
                    if (res.code === 200) {
                        this.$message({
                            message: res.msg,
                            type: 'success'
                        });
                        this.getPageList()
                    }
                })
            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消删除'
                });
                this.$refs.multipleTable.clearSelection();
            });
        },

    }
};
</script>


