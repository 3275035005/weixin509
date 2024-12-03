<template>
  <div class="app-container">
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item label="反馈用户姓名">
        <el-select
          v-model="dataVo.userId"
          placeholder="请选择反馈用户姓名"
        >
          <el-option
            v-for="item in userList"
            :key="item.id"
            :label="item.name"
            :value="item.id"
          />
        </el-select>
      </el-form-item>
      <el-button type="primary" icon="el-icon-search" @click="getList()">查询</el-button>
      <el-button type="default" @click="resetData()">重置</el-button>
    </el-form>
    <!-- 表格 -->
    <el-table

      :data="list"
      border
      fit
      highlight-current-row
      empty-text="暂无数据"
    >

      <el-table-column
        label="序号"
        width="60"
        align="center"
      >
        <template slot-scope="scope">
          {{ (page - 1) * limit + scope.$index + 1 }}  <!--序号,算法-->
        </template>
      </el-table-column>
      <el-table-column prop="userName" label="反馈用户姓名" />
      <el-table-column prop="content" label="反馈内容" />
      <el-table-column prop="createTime" label="反馈时间" />
      <el-table-column prop="status" label="回复状态">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.status == '0'" type="success">未回复</el-tag>
          <el-tag v-if="scope.row.status == '1'" type="error">已回复</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="replyContent" label="回复内容" />
      <el-table-column label="操作" align="center" width="250px">
        <template slot-scope="scope">
          <el-button v-if="scope.row.status === '0'" type="primary" size="mini" icon="el-icon-edit" @click="updateAndSave(scope.row)">回复</el-button>
          <el-button type="danger" size="mini" icon="el-icon-delete" @click="removeDataById(scope.row.id)">删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 新增修改弹框 -->
    <el-dialog
      :title="title"
      :visible.sync="dialogVisible"
      width="30%"
    >

      <el-form :model="formData" label-position="left" label-width="100px">
        <el-form-item label="回复内容">
          <el-input
            v-model="formData.replyContent"
            type="textarea"
            :rows="2"
            placeholder="请填写回复内容"
          />
        </el-form-item>
      </el-form>

      <span slot="footer" class="dialog-footer">
        <el-button @click="cancelBtn">取 消</el-button>
        <el-button type="primary" @click="handleBtn">确 定</el-button>
      </span>
    </el-dialog>
    <!-- 分页 -->
    <el-pagination
      :current-page="page"
      style="padding: 30px 0; text-align: center;"
      :page-size="limit"
      layout="total, prev, pager, next, jumper"
      :total="total"
      @current-change="getList"
    />
  </div>
</template>
<script>
import { pageQuery, deleteById, sendFeedback } from '@/api/feedback'
import { getUserAll } from '@/api/user'
export default {
  data() { // 定义变量和初始值
    return {
      list: [], // 查询之后接口返回集合
      page: 1, // 当前页
      limit: 9, // 每页记录数
      total: 100, // 总页数
      dataVo: {}, // 条件封装对象
      formData: {},
      dialogVisible: false,
      name: '',
      title: '',
      userList: [] // 存储学生列表
    }
  },
  created() { // 页面渲染之前执行，一般调用method定义方法
    this.getList()
    this.init()
  },
  methods: { // 创建具体的方法, 调用teacher.js 定义的方法
    init() {
      getUserAll().then(res => {
        this.userList = res.data.row
      })
    },
    // 查询
    getList(page = 1) {
      this.page = page // 获取用户点击的页码赋值
      pageQuery(this.page, this.limit, this.dataVo)
        .then(response => { // 请求成功
          this.list = response.data.rows.records
          this.total = response.data.rows.total
        })
    },

    // 删除
    removeDataById(id) {
      this.$confirm('此操作将永久删除, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'

      }).then(() => { // 确定执行的方法
        deleteById(id)
          .then(response => { // 删除成功执行的方法
            if (response.success) {
              this.$message({
                type: 'success',
                message: '删除成功！'
              })
              // 刷新表格
              this.getList()
            } else {
              this.$message({
                type: 'error',
                message: '删除失败！'
              })
            }
          })
      })
    },
    // 修改和更新功能
    updateAndSave(row) {
      this.title = '反馈回复'
      this.formData = row
      this.dialogVisible = true
    },
    cancelBtn() {
      this.dialogVisible = false
      this.getList()
    },
    // 确认按钮
    handleBtn() {
      // 关闭弹窗
      this.dialogVisible = false
      sendFeedback(this.formData)
        .then(response => { // 请求成功
          this.$message({
            type: 'success',
            message: '回复成功！'
          })
          this.getList()
        })
    },

    // 清空按按钮执行的方法
    resetData() {
      this.page = 1// 当前页
      this.limit = 10// 每页记录数
      // 第一步清空条件数据
      this.dataVo = {}
      this.getList()
    }
  }
}
</script>
