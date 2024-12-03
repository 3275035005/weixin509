<template>
  <div class="app-container">
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item label="单词名称">
        <el-input v-model="dataVo.wordName" placeholder="请输入单词名称"/>
      </el-form-item>
      <el-form-item label="所属题库">
        <el-select
          v-model="dataVo.lexiconId"
          placeholder="请选择所属题库">
          <el-option
            v-for="item in magazineList"
            :key="item.id"
            :label="item.title"
            :value="item.id"/>
        </el-select>
      </el-form-item>
      <el-button type="primary" icon="el-icon-search" @click="getList()">查询</el-button>
      <el-button type="default" @click="resetData()">重置</el-button>
      <el-button type="primary" @click="updateAndSave(null)">新增</el-button>
      <el-button type="primary" @click="importData(null)">导入单词</el-button>
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
      <el-table-column prop="wordName" label="单词名称"/>
      <el-table-column prop="soundmark" label="单词音标"/>
      <el-table-column prop="paraphrase1" label="释义1"/>
      <el-table-column prop="paraphrase2" label="释义2"/>
      <el-table-column prop="phrase" label="短语"/>
      <el-table-column prop="title" label="所属题库"/>
      <el-table-column prop="createTime" label="创建时间"/>
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
        <el-form-item label="单词名称">
          <el-input v-model="formData.wordName" placeholder="请填写单词名称"/>
        </el-form-item>
        <el-form-item label="单词音标">
          <el-input v-model="formData.soundmark" placeholder="请填写单词音标"/>
        </el-form-item>
        <el-form-item label="释义1">
          <el-input v-model="formData.paraphrase1" placeholder="请填写释义1"/>
        </el-form-item>
        <el-form-item label="释义2">
          <el-input v-model="formData.paraphrase2" placeholder="请填写释义2"/>
        </el-form-item>
        <el-form-item label="短语">
          <el-input
            type="textarea"
            :rows="2"
            placeholder="短语"
            v-model="formData.phrase">
          </el-input>
        </el-form-item>
        <el-form-item label="所属题库">
          <el-select
            v-model="formData.lexiconId"
            placeholder="请选择所属题库">
            <el-option
              v-for="item in magazineList"
              :key="item.id"
              :label="item.title"
              :value="item.id"/>
          </el-select>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="handleBtn">确 定</el-button>
      </span>
    </el-dialog>
    <!-- 导入功能 -->
    <el-dialog
      title="导入词库单词"
      :visible.sync="dialogVisible1"
      width="50%"
    >

      <el-form label-width="120px">
        <el-form-item label="选择Excel">
          <el-upload
            ref="upload"
            :auto-upload="false"
            :on-success="fileUploadSuccess"
            :on-error="fileUploadError"
            :disabled="importBtnDisabled"
            :limit="1"
            action="http://localhost:9999/lexicon-info/addLexiconInfo"
            name="file"
            accept=".xlsx"
          >
            <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
            <el-button
              :loading="loading"
              style="margin-left: 10px;"
              size="small"
              type="success" @click="submitUpload">{{fileUploadBtnText}}</el-button>
          </el-upload>
        </el-form-item>
      </el-form>
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
import { getLexiconAll} from '@/api/lexicon'
import { deleteById, insert, update, pageQuery} from '@/api/lexiconInfo'
export default {
  data() { // 定义变量和初始值
    return {
      list: [], // 查询之后接口返回集合
      dataVo: {}, // 条件封装对象
      page: 1, // 当前页
      limit: 10, // 每页记录数
      total: 100, // 总页数
      dialogVisible: false,
      dialogVisible1: false,
      formData: {},
      title: '',
      magazineList: [],
      fileUploadBtnText: '上传数据',
      loading: false,
      importBtnDisabled: false, // 按钮是否禁用,
    }
  },
  created() { // 页面渲染之前执行，一般调用method定义方法
    this.getList()
    this.getInit()
  },
  methods: { // 创建具体的方法, 定义的方法
    getInit() {
      getLexiconAll().then(res => {
        this.magazineList = res.data.list
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
    // 导入文件
    importData(){
      this.dialogVisible1 = true;
    },
    // 上传成功执行的方法
    fileUploadSuccess() {
      this.fileUploadBtnText = '导入成功' // 渲染按钮
      this.loading = false // 是否加载中状态
      // 提示信息
      this.$message({
        type: 'success',
        message: '添加成功'
      })
      this.dialogVisible1 = false
      this.getList()
    },

    // 上传失败执行的方法
    fileUploadError() {
      this.fileUploadBtnText = '导入失败'
      this.loading = false
      this.$message({
        type: 'error',
        message: '添加失败'
      })
      this.dialogVisible1 = false
    },
    // 上传到服务器方法
    submitUpload() {
      this.importBtnDisabled = true;  // 禁用按钮
      this.loading = true; // 是否加载中状态
      this.$refs.upload.submit()
    },

    // 修改和更新功能
    updateAndSave(row) {
      if (row == null) {
        this.title = '新增词库单词信息'
        this.formData = {}
      } else {
        this.title = '修改词库单词信息'
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

    // 清空按按钮执行的方法
    resetData() {
      // 第一步清空条件数据
      this.page = 1// 当前页
      this.limit = 10// 每页记录数
      this.dataVo = {}
      this.getList()
    }
  }
}
</script>
