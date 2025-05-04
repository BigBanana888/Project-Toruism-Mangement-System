<template>
    <div class="goodsindex">
      <!-- Search Criteria -->
      <el-row :gutter="20" class="filter-bar">
        <el-col :xs="8" :sm="6" :md="6" :lg="4" :xl="4">
          <el-input 
            v-model="searchKey"
            placeholder="Search types..."
            clearable 
            size="small"
            @keyup.enter.native="loadData">
          </el-input>
        </el-col>
        <el-col :xs="8" :sm="6" :md="6" :lg="4" :xl="4">
          <el-button 
            type="primary" 
            size="small"
            @click="loadData">
            Search
          </el-button>
        </el-col>
        <el-col :xs="6" :sm="4" :md="3" :lg="2" :xl="2">
          <el-button 
            type="primary" 
            size="small"
            @click="showCreateDialog">
            New Type
          </el-button>
        </el-col>
      </el-row>
  
      <!-- Data Table -->
      <el-table
        v-loading="isLoading"
        :data="typeList"
        border
        size="small"
        style="width: 100%"
        :header-cell-style="{ 'text-align': 'center', background: '#f5f7fa' }"
        :cell-style="{ 'text-align': 'center' }">
        <el-table-column 
          prop="id" 
          label="ID" 
          width="220"
          show-overflow-tooltip/>
        <el-table-column 
          prop="name" 
          label="Type Name" 
          width="180"
          show-overflow-tooltip/>
        <el-table-column 
          prop="remark" 
          label="Description"
          show-overflow-tooltip/>
        <el-table-column 
          prop="createdTime" 
          label="Created Time" 
          width="220"/>
        <el-table-column 
          prop="createdName" 
          label="Creator" 
          width="180"/>
        <el-table-column 
          prop="modifiedTime" 
          label="Updated Time" 
          width="220"/>
        <el-table-column 
          prop="modifiedName" 
          label="Updater" 
          width="180"/>
        <el-table-column 
          fixed="right" 
          label="Actions" 
          width="240" 
          align="center">
          <template slot-scope="scope">
            <el-button 
              @click="editItem(scope.row)" 
              type="warning" 
              size="mini" 
              >
              Edit
            </el-button>
            <el-button 
              @click="deleteItem(scope.row)" 
              type="danger" 
              size="mini" 
              >
              Delete
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <!-- Pagination -->
      <el-pagination
        background
        layout="total, prev, pager, next"
        :current-page="pagination.page"
        :page-size="pagination.pageSize"
        :total="pagination.total"
        @current-change="handlePageChange"
        class="pagination-bar"/>
  
      <!-- Edit Dialog -->
      <el-dialog 
        :title="dialogTitle" 
        :visible.sync="showDialog" 
        width="500px">
        <el-form 
          ref="typeForm"
          :model="currentItem" 
          label-width="140px"
          label-position="left">
          <el-form-item 
            label="Type Name" 
            prop="name"
            :rules="[{ required: true, message: 'Name is required' }]">
            <el-input v-model="currentItem.name"/>
          </el-form-item>
          <el-form-item 
            label="Description" 
            prop="remark">
            <el-input 
              v-model="currentItem.remark"
              type="textarea"
              :rows="4"
              resize="none"/>
          </el-form-item>
          <el-form-item class="dialog-actions">
            <el-button @click="showDialog = false">Cancel</el-button>
            <el-button 
              type="primary" 
              @click="saveItem">
              Save
            </el-button>
          </el-form-item>
        </el-form>
      </el-dialog>
  
    </div>
  </template>
  
  <script>
  import typeApi from '@/apis/type'
  
  export default {
    data() {
          return {
        user:null,
        isLoading: true,
        searchKey: '',
        showDialog: false,
        dialogTitle: '',
        currentItem: {},
        typeList: [],
        pagination: {
          page: 1,
          pageSize: 10,
          total: 0
        }
      }
    },
  
    mounted() {
        this.user = JSON.parse(this.$store.getStore('user'))
      this.loadData()
    },
  
    methods: {
      async loadData() {
        try {
          this.isLoading = true
          const res = await typeApi.list({
            name: this.searchKey
          }, this.pagination.page, this.pagination.pageSize)

          this.typeList = res.data.records
          this.pagination.total = res.data.total
        } finally {
          this.isLoading = false
        }
      },
  
      showCreateDialog() {
        this.dialogTitle = 'New Venue Type'
        this.showDialog = true
        this.currentItem = {}
      },
  
      editItem(item) {
        this.dialogTitle = 'Edit Venue Type'
        this.currentItem = { ...item }
        this.showDialog = true
      },
  
      async saveItem() {
        try {
          await this.$refs.typeForm.validate()
          
            if (this.currentItem.id) {
            this.currentItem.modifiedId = this.user.id
            await typeApi.update(this.currentItem)
          } else {
            this.currentItem.createdId = this.user.id
            await typeApi.save(this.currentItem)
          }
          
          this.$message.success('Operation successful')
          this.showDialog = false
          this.loadData()
        } catch (error) {
          console.error('Save error:', error)
        }
      },
  
      deleteItem(item) {
        this.$confirm('This will permanently delete the type. Continue?', 'Warning', {
          confirmButtonText: 'Confirm',
          cancelButtonText: 'Cancel',
          type: 'warning'
        }).then(async () => {
          await typeApi.deleteById(item.id)
          this.$message.success('Type deleted')
          this.loadData()
        }).catch(() => {
          this.$message.info('Deletion canceled')
        })
      },
  
      handlePageChange(page) {
        this.pagination.page = page
        this.loadData()
      }
    }
  }
  </script>
  
  <style scoped>
  
.goodsindex{
    min-height: 100%;
    padding: 15px;
    box-sizing: border-box;
    background-color:white;
    margin: 20px;
}
  .filter-bar {
    margin-bottom: 20px;
    display: flex;
    gap: 15px;
  }
  
  .el-table {
    margin: 20px 0;
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  }
  
  .pagination-bar {
    margin-top: 20px;
    display: flex;
    justify-content: flex-end;
  }
  
  .dialog-actions {
    margin-top: 30px;
    text-align: right;
  }
  
  .el-form-item {
    margin-bottom: 22px;
  }
  
  .el-input-number {
    width: 100%;
  }
  
  .el-textarea {
    font-family: inherit;
  }
  </style>