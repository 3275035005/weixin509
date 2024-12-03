<template>
  <div class="app-container">
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item label="外刊标题">
        <el-input v-model="dataVo.title" placeholder="请输入外刊标题"/>
      </el-form-item>
      <el-form-item label="外刊分类名称">
        <el-select
          v-model="dataVo.classifiedId"
          placeholder="请选择外刊分类名称">
          <el-option
            v-for="item in classifiedList"
            :key="item.id"
            :label="item.title"
            :value="item.id"/>
        </el-select>
      </el-form-item>
      <el-button type="primary" icon="el-icon-search" @click="getList()">查询</el-button>
      <el-button type="default" @click="resetData()">重置</el-button>
      <el-button type="primary" @click="updateAndSave(null)">新增</el-button>
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
      <el-table-column align="center" label="外刊封面">
        <template slot-scope="scope">
          <img
            style="width: 80px; height: 80px"
            :src="scope.row.image"/>
        </template>
      </el-table-column>
      <el-table-column prop="title" label="外刊标题"/>
      <el-table-column prop="classifiedName" label="外刊分类名称"/>
      <el-table-column prop="createTime" label="发布时间"/>
      <el-table-column label="操作" align="center">
        <template slot-scope="scope">
          <el-button type="primary" size="mini" icon="el-icon-edit" @click="updateAndSave(scope.row)">修改</el-button>
          <el-button type="danger" size="mini" icon="el-icon-delete" @click="removeDataById(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 新增修改弹框 -->
    <el-dialog
      :title="title"
      :visible.sync="dialogVisible"
      width="50%"
    >

      <el-form :model="formData" label-position="left" label-width="120px">
        <el-form-item label="外刊名称">
          <el-input v-model="formData.title" placeholder="请填写外刊名称"/>
        </el-form-item>
        <el-form-item label="外刊分类名称">
          <el-select
            v-model="formData.classifiedId"
            placeholder="请选择外刊分类名称">
            <el-option
              v-for="item in classifiedList"
              :key="item.id"
              :label="item.title"
              :value="item.id"/>
          </el-select>
        </el-form-item>
        <el-form-item label="外刊封面">
          <el-upload
            ref="upload"
            :file-list="fileList"
            action="http://localhost:9999/files/file"
            :on-success="fileHandleSuccess"
            accept="image/*"
            class="avatar-uploader"
          >
            <img v-if="formData.image" :src="formData.image" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>
        <el-form-item label="外刊内容">
          <tinymce v-if="dialogVisible" v-model="formData.content" :height="300"></tinymce>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
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
import { deleteById, insert, update, pageQuery } from '@/api/magezine'
import Tinymce from '@/components/Tinymce'
import { getClassifiedAll } from '@/api/magazineClassified';

export default {
  components: { Tinymce }, // 注册组件
  data() { // 定义变量和初始值
    return {
      list: [], // 查询之后接口返回集合
      dataVo: {}, // 条件封装对象
      page: 1, // 当前页
      limit: 10, // 每页记录数
      total: 100, // 总页数
      dialogVisible: false,
      formData: {},
      title: '',
      fileList: [],
      classifiedList: []
    }
  },
  created() { // 页面渲染之前执行，一般调用method定义方法
    this.getList()
    this.init()
  },
  methods: { // 创建具体的方法, 定义的方法

    init() {
      getClassifiedAll().then(res => {
        this.classifiedList = res.data.row
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
    // 修改和更新功能
    updateAndSave(row) {
      if (row == null) {
        this.title = '新增外刊信息'
        this.formData = {
          image:''
        }
      } else {
        this.title = '修改外刊信息'
        this.formData = row
      }
      this.dialogVisible = true
    },

    // 确认按钮
    handleBtn() {
      // 关闭弹窗
      this.dialogVisible = false
      if (this.formData.id) {
        update(this.formData)
          .then(response => { // 请求成功
            this.$message({
              type: 'success',
              message: '修改成功！'
            })
            this.getList()
          })
      } else {
        insert(this.formData)
          .then(response => { // 请求成功
            this.$message({
              type: 'success',
              message: '新增成功！'
            })
            this.getList()
          })
      }
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

    // 上传头像成功
    fileHandleSuccess(response) {
      this.formData.image = response.data.row;
      this.$refs.upload.clearFiles() //去掉文件列表
    },
    // 清空按按钮执行的方法
    resetData() {
      // 第一步清空条件数据
      this.page = 1 // 当前页
      this.limit = 10// 每页记录数
      this.dataVo = {};
      this.getList()
    }
  }
}
</script>
<style scoped>
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}

.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}

.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
  border-style: dotted;
  margin: 10px 0;
}

.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
</style>
