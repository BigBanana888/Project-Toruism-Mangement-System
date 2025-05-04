<template>
    <div class="goodsindex">
        <!-- Select条件 -->
        <el-row :gutter="20" class="goodsindex-queryInfo">
            <el-col :xs="8" :sm="6" :md="6" :lg="4" :xl="4">
                <el-input class="goodsindex-queryInfo-li" v-model="condition.name" clearable size="small" placeholder="Please enter the name of the notice"></el-input>
            </el-col>
            <el-col :xs="6" :sm="4" :md="3" :lg="2" :xl="2">
                <el-button type="primary" class="goodsindex-queryInfo-li" size="small" @click="list">Select</el-button>
               </el-col>
            <el-col :xs="6" :sm="4" :md="3" :lg="2" :xl="2">
                <el-button type="primary" class="goodsindex-queryInfo-li" size="small" @click="toAdd">New Notice </el-button>
            </el-col>
        </el-row>
        <!-- 检索结果 -->
        <el-row :gutter="20" class="goodsindex-list">
            <el-col :span="24">
                <el-table
                    v-loading="isLoading"
                    :header-cell-style="{'text-align':'center'}"
                    :cell-style="{'text-align':'center'}"
                    :data="tableData" border size='small'
                    style="width: 100%">
                    <el-table-column prop="id" show-overflow-tooltip label="id" width="220"></el-table-column>
                    <el-table-column prop="name" show-overflow-tooltip label="name" width="180"></el-table-column>
                    <el-table-column prop="content" show-overflow-tooltip label="content" ></el-table-column>
                    <el-table-column prop="status" show-overflow-tooltip label="status" width="180">
                        <template slot-scope="scope"> 
                            <el-tag v-if="scope.row.status===0" type="success" effect="dark">activate </el-tag>
                            <el-tag v-if="scope.row.status===1" type="danger" effect="dark">deactivate</el-tag>
                            <!-- <el-tag v-if="scope.row.status===2" :type="item.type" effect="dark">{{ item.label }}</el-tag>
                            <el-tag v-if="scope.row.status===3" :type="item.type" effect="dark">{{ item.label }}</el-tag> -->
                        </template>
                    </el-table-column>
                    <el-table-column prop="imageUrl"  width="100" label="image" >
                        <template slot-scope="scope">
                        <img :src="scope.row.imageUrl" width="50" height="50"/>
                        </template>
                    </el-table-column>
                    <el-table-column prop="createdTime" label="Creation Time" width="220"></el-table-column>
                    <el-table-column prop="createdName" label="Creation User" width="180"></el-table-column>
                    <el-table-column prop="modifiedTime" label="Update Time" width="220"></el-table-column>
                    <el-table-column prop="modifiedName" label="Update User" width="180"></el-table-column>
                    <el-table-column fixed="right" label="operation" width="240" align="center">
                        <template slot-scope="scope">
                            <el-button @click="toUpdate(scope.row)" type="warning" size="mini" >Edit</el-button>
                            <el-button @click="toDelete(scope.row)" type="danger" size="mini" >Delete</el-button>
                        </template>
                    </el-table-column>
                </el-table>
            </el-col>
        </el-row>
        <el-dialog :title="editDialogTitle" :visible.sync="editDialogShow" width="1200px" >
            <el-form ref="form" :rules="rules" :model="item" label-width="250px">
                <el-row :gutter="20">
                    <el-col :span="12">
            <el-form-item label="name" prop="name">
                <el-input v-model="item.name"></el-input>
            </el-form-item>
            <el-form-item label="status" prop="status">               
                <el-radio-group v-model="item.status">
                    <el-radio :label="0">activate</el-radio>
                    <el-radio :label="1">deactivate</el-radio>
                </el-radio-group>
            </el-form-item>
        </el-col>
                    <el-col :span="12">
            <el-form-item label="image">
                <el-upload
                class="avatar-uploader"
                action="http://localhost:9001/file/upload"
                :show-file-list="false"
                :on-success="handleAvatarSuccess"
                :before-upload="beforeAvatarUpload">
                <img v-if="item.imageUrl" style="width: 200px;height: 200px;" :src="item.imageUrl" class="avatar">
                <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                </el-upload>
            </el-form-item></el-col>
                </el-row>
            <el-form-item label="content" prop="content">
                <el-input type="textarea" v-model="item.content" :rows="6" resize="none"></el-input>
            </el-form-item>  
            <el-form-item>
                <el-button @click="editDialogShow = false">Cancel</el-button>
                <el-button type="primary" @click="saveOredit">Save</el-button>
            </el-form-item>
            </el-form>
        </el-dialog>
        <!-- 分页 -->
<!-- 修改后的分页组件 -->
<el-pagination
  background
  layout="total, prev, pager, next"
  :current-page.sync="queryInfo.page"
  :page-size="queryInfo.pageSize"
  :total="queryInfo.num"
  @current-change="handleCurrentChange"
  class="pagination-bar">
</el-pagination>
    </div>
</template>

<script>
import noticeApis from '@/apis/notice.js'
export default {
    data(){
        return {
            isLoading:true,
            user: {},
            rules: {
                name: [
                        { required: true, message: 'Please enter the name of the announcement', trigger: 'blur' },
                    ],
                    content: [
                        { required: true, message: 'Please enter a note for the announcement', trigger: 'blur' },
                    ],
                    image: [
                        { required: true, message: 'Please upload an image', trigger: 'blur' },
                    ],
                },
            editDialogTitle:'',
            editDialogShow: false,
            queryInfo:{
                name: '',
                type: '',
                page: 1,
                num:20,
                pageSize: 5
            },
            options: [
            ],
            item:{},
            places:[],
            condition:{},
            tableData: []
        }
    },
    mounted(){
        this.user = JSON.parse(this.$store.getStore("user"))
        this.list()
    },
    methods:{
        handleAvatarSuccess(res, file) {
            this.item.imageUrl = res.data.url
            this.$forceUpdate()
        },
        beforeAvatarUpload(file) {
            const isLt2M = file.size / 1024 / 1024 < 15;
            if (!isLt2M) {
            this.$message.error('The size of the uploaded image cannot exceed 15MB!');
            }
            return isLt2M;
        },
        handleSizeChange(size){
            this.queryInfo.pageSize = size
        },
        handleCurrentChange(current){
            this.queryInfo.page = current
            this.list()
        },
        list(){
            this.isLoading = true
            noticeApis.list(this.condition,this.queryInfo.page,this.queryInfo.pageSize).then(res=>{
                this.tableData = res.data.records
                // this.queryInfo.page = res.data.current
                this.queryInfo.num = res.data.total
                this.isLoading = false
                this.$message.success(res.message)
            })
        },
        toAdd(){
            this.editDialogTitle="Add Notice"
            this.editDialogShow = true
            this.item = {}
        },
        toUpdate(item){
            this.editDialogTitle="Update Notice"
            this.editDialogShow = true
            this.item = JSON.parse(JSON.stringify(item))
        },
        saveOredit(){
            this.$refs["form"].validate((valid) => {
                if (valid) {
                    if(this.editDialogTitle==="Add Notice"){
                        this.save()
                    }else{
                        this.update()
                    }
                } else {
                    console.log('error submit!!');
                    return false;
                }
            });
        },
        save(){
            this.item.createdId = this.user.id
            noticeApis.save(this.item).then(res=>{
                this.$message.success(res.message)
                this.editDialogShow = false
                this.item = {}
                this.editDialogTitle = ""
                this.list()
            })
        },
        update(){
            this.item.modifiedId = this.user.id
            noticeApis.update(this.item).then(res=>{
                this.$message.success(res.message)
                this.editDialogShow = false
                this.item = {}
                this.editDialogTitle = ""
                this.list()
            })
        },
        toDelete(item){
            this.$confirm("Whether to Delete the Notice?", "tip", {
                confirmButtonText: "OK",
                cancelButtonText: "NO",
                type: "warning",
            })
            .then(() => {
                this.deleteById(item.id);
            })
            .catch(() => {
                this.$message({
                type: "info",
                message: "Deletion canceled",
                });
            });
        },
        deleteById(id){
            noticeApis.deleteById(id).then(res=>{
                this.$message.success(res.message)
                this.list()
            })
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
/* Select */
.goodsindex-queryInfo{
    margin-bottom: 10px;
}
.goodsindex-queryInfo-li{
    width: 100%;
    height: auto;
}
/* 列表 */
.goodsindex-list{
    width: 100%;
    height: auto;
    margin-bottom: 20px;
}
/* 分页 */
.pagination-bar {
  margin-top: 20px;
  display: flex;
  justify-content: flex-end;
}
</style>