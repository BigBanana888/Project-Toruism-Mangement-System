<template>
    <div class="goodsindex">
      <!-- Search Bar -->
      <el-row :gutter="20" class="filter-bar">
        <el-col :span="8">
          <el-input
            v-model="searchParams.aname"
            placeholder="Search by activity name"
            clearable 
            size="small"
            @keyup.enter.native="loadEvaluations"
            @clear="loadEvaluations"
          >
          </el-input>
        </el-col>
        <el-col :span="4">
          <el-button type="primary" 
          size="small" @click="loadEvaluations">Search</el-button>
        </el-col>
      </el-row>
  
      <!-- Evaluation Table -->
      <el-table
        v-loading="loading"
        :data="evaluations"
        border
        style="width: 100%"
        :header-cell-style="{ background: '#f5f7fa' }"
      >
        <el-table-column prop="aname" label="Activity" width="200" />
        <el-table-column prop="createdName" label="Evaluator" width="150" />
        <el-table-column label="Rating" width="120">
          <template slot-scope="{ row }">
            <el-rate
              v-model="row.rate"
              disabled
              show-score
              :colors="['#99A9BF', '#F7BA2A', '#FF9900']"
              :max="5"
            />
          </template>
        </el-table-column>
        <el-table-column prop="content" label="Content" show-overflow-tooltip />
        <el-table-column label="Created Time" width="220">
          <template slot-scope="{ row }">
            {{ formatDate(row.createdTime) }}
          </template>
        </el-table-column>
        <el-table-column fixed="right" label="Operations" width="240">
          <template slot-scope="{ row }">
            <el-button
              type="primary"
              size="mini"
              @click="showDetail(row)"
            >
          Detail</el-button>
            <el-button
              type="danger"
              size="mini"
              @click="handleDelete(row.id)"
            >
            Delete</el-button>
          </template>
        </el-table-column>
      </el-table>
  
      <!-- Pagination -->
      <el-pagination
        background
        layout="total, prev, pager, next"
        :current-page="pagination.current"
        :page-size="pagination.size"
        :total="pagination.total"
        @current-change="handlePageChange"
        class="pagination-bar"
      />
  
      <!-- Detail Dialog -->
      <el-dialog :title="currentEvaluation.aname" :visible.sync="detailVisible" width="50%">
        <el-descriptions :column="1" border>
          <el-descriptions-item label="Activity">{{ currentEvaluation.aname }}</el-descriptions-item>
          <el-descriptions-item label="Evaluator">{{ currentEvaluation.createdName }}</el-descriptions-item>
          <el-descriptions-item label="Rating">
            <el-rate v-model="currentEvaluation.rate" disabled />
            <span class="rating-text">{{ currentEvaluation.rate }} Stars</span>
          </el-descriptions-item>
          <el-descriptions-item label="Content">{{ currentEvaluation.content }}</el-descriptions-item>
          <el-descriptions-item label="Images" v-if="currentEvaluation.imageUrl">
            <el-image
              style="width: 100px; height: 100px; margin-right: 10px"
              v-for="(img, index) in currentEvaluation.imageUrl.split(',')"
              :key="index"
              :src="img.trim()"
              :preview-src-list="currentEvaluation.imageUrl.split(',')"
              fit="cover"
            />
          </el-descriptions-item>
          <el-descriptions-item label="Created Time">
            {{ formatDate(currentEvaluation.createdTime) }}
          </el-descriptions-item>
        </el-descriptions>
      </el-dialog>
    </div>
  </template>
  
  <script>
  import evaluationApi from '@/apis/eva'
  
  export default {
    data() {
      return {
        loading: false,
        detailVisible: false,
        currentEvaluation: {},
        searchParams: {
          aname: ''
        },
        evaluations: [],
        pagination: {
          current: 1,
          size: 10,
          total: 0
        }
      }
    },
  
    mounted() {
      this.loadEvaluations()
    },
  
    methods: {
      async loadEvaluations() {
        try {
          this.loading = true
          const res = await evaluationApi.list({
            ...this.searchParams
          },
            this.pagination.current,
            this.pagination.size)
          this.evaluations = res.data.records
          this.pagination.total = res.data.total
        } catch (error) {
          this.$message.error('Failed to load evaluations')
        } finally {
          this.loading = false
        }
      },
  
      showDetail(evaluation) {
        this.currentEvaluation = { ...evaluation }
        this.detailVisible = true
      },
  
      handleDelete(id) {
        this.$confirm('This will permanently delete the evaluation. Continue?', 'Warning', {
          confirmButtonText: 'Confirm',
          cancelButtonText: 'Cancel',
          type: 'warning'
        }).then(async () => {
          await evaluationApi.deleteById(id)
          this.$message.success('Evaluation deleted')
          this.loadEvaluations()
        }).catch(() => {
          this.$message.info('Deletion canceled')
        })
      },
  
      handlePageChange(page) {
        this.pagination.current = page
        this.loadEvaluations()
      },
  
      formatDate(date) {
        return date ? new Date(date).toLocaleString() : '--'
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
  }
  
  .pagination-bar {
  margin-top: 20px;
  display: flex;
  justify-content: flex-end; 
}
  
  .rating-text {
    margin-left: 10px;
    vertical-align: middle;
  }
  
  .el-image {
    vertical-align: middle;
  }
  </style>