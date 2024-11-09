<template>
  <div>
    <div class="pb10px">
      <el-form ref="searchForm" :model="searchForm" inline>
        <el-form-item label="网站名称" prop="name">
          <el-input v-model="searchForm.name"  clearable placeholder="请输入网站名称"></el-input>
        </el-form-item>
        <el-form-item label="">
          <el-button type="primary" icon="el-icon-search" @click="handleSearch">搜 索</el-button>
          <el-button icon="el-icon-refresh-left" @click="resetForm">重 置</el-button>
        </el-form-item>
      </el-form>
    </div>
    <el-button type="primary" icon="el-icon-plus" @click="handleAdd">新增</el-button>
    <el-button type="danger" icon="el-icon-remove-outline" @click="batchDelete([])" :disabled="ids.length<=0">批量删除</el-button>
    <el-table ref="multipleTable"
              :data="listData"
              tooltip-effect="dark"
              style="width: 100%"  @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column prop="id" label="ID" width="50"></el-table-column>
      <el-table-column prop="name" label="网站名称"></el-table-column>
      <el-table-column prop="link" label="网站网址"></el-table-column>
      <el-table-column label="操作" width="200" fixed="right">
        <template slot-scope="scope">
          <el-button icon="el-icon-edit-outline" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
          <el-button type="danger" icon="el-icon-remove-outline" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
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
    <el-drawer
        :title="formData.id?'编辑':'新增'"
        :visible.sync="isDrawerDialog"
        size="400px"
        direction="rtl"
        :wrapper-closable="false"
    >
      <div class="demo-drawer_content" style="padding-right: 40px;box-sizing: border-box">
        <el-form :model="formData" label-width="100px" class="demo-ruleForm">
          <slot name="content">
            <el-form-item label="网站名称">
              <el-input v-model="formData.name"></el-input>
            </el-form-item>
            <el-form-item label="网站网址">
              <el-input v-model="formData.link"></el-input>
            </el-form-item>
          </slot>
        </el-form>
      </div>
      <div class="demo-drawer_footer" style="display: flex; justify-content: space-around;">
        <el-button @click="isDrawerDialog=false">取消</el-button>
        <el-button type="primary" @click="submit">提交</el-button>
      </div>
    </el-drawer>
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
      //删除数据的id
      ids: [],
      userInfo:utils.getUserInfo(),
        //删除数据的id
    }
  },

  created() {
    this.getPageList()
  },
  methods: {
    //获取分页数据
    getPageList() {
      let data = Object.assign(this.searchForm, this.pageInfo)
      this.$axios.get("/link/page", {
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
        this.$axios.post("/link/add", this.formData).then(res => {
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
        this.$axios.put("/link/update", this.formData).then(res => {
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
      if (rows.length<=0) {
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
        this.$axios.delete("/link/delBatch", {data: ids}).then(res => {
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
    handleSelectionChange(val) {
      console.log(val)
      //赋值以前先清空
      this.ids = []
      val.forEach(item => {
        this.ids.push(item.id)
      })
    },

  }
};
</script>


