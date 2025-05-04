<template>
    <el-container class="dashboard-container">
        <el-main>
          <!-- Profile Section -->
          <el-row :gutter="20">
            <el-col :span="8">
              <el-card class="profile-card">
                <div class="avatar-section">
                  <el-upload
                    class="avatar-uploader"
          action="http://localhost:9001/file/upload"
                    :show-file-list="false"
                    :on-success="handleAvatarSuccess"
                  >
                    <img v-if="user.imageUrl" :src="user.imageUrl" class="avatar">
                    <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                  </el-upload>
                  <h3 class="username">{{ user.username }}</h3>
                </div>
                
                <el-divider></el-divider>
                
                <div class="user-stats">
                  <div class="stat-item">
                    <span class="stat-label">Activities</span>
                    <span class="stat-value">{{ user.num }}</span>
                  </div>
                  <div class="stat-item">
                    <span class="stat-label">Rating</span>
                    <span class="stat-value">{{ user.rate/10| toFixedDecimal  }}/5.0</span>
                  </div>
                </div>
              </el-card>
            </el-col>
  
            <el-col :span="16">
              <el-card>
                <el-form :model="user" label-width="120px" label-position="left">
                  <el-form-item label="Nickname">
                    <el-input v-model="user.nickname"></el-input>
                  </el-form-item>
                  
                  <el-form-item label="Gender">
                    <el-radio-group v-model="user.sex">
                      <el-radio :label="0">Male</el-radio>
                      <el-radio :label="1">Female</el-radio>
                    </el-radio-group>
                  </el-form-item>
  
                  <el-form-item label="Birthday">
                    <el-date-picker
                      v-model="user.birth"
                      type="date"
                      value-format="yyyy-MM-dd"
                      style="width: 100%"
                    ></el-date-picker>
                  </el-form-item>
  
                  <el-form-item label="Phone">
                    <el-input v-model="user.phone"></el-input>
                  </el-form-item>
  
                  <el-form-item label="Email">
                    <el-input v-model="user.email" type="email"></el-input>
                  </el-form-item>
  
                  <el-form-item label="Interests">
                    <el-input v-model="user.xq"></el-input>
                  </el-form-item>
  
                  <el-form-item label="Bio">
                    <el-input 
                      v-model="user.js" 
                      type="textarea" 
                      :rows="4"
                      placeholder="Tell us about yourself"
                    ></el-input>
                  </el-form-item>
  
                  <el-form-item>
                    <el-button type="primary" @click="updateProfile">Save Changes</el-button>
                    <el-button @click="openPasswordDialog">Change Password</el-button>
                    <el-button type="primary" @click="$router.push('/myactivate')">My Activates</el-button>
                    <el-button type="primary" @click="$router.push('/my-blog')">My Blogs</el-button>
                    <el-button type="primary" @click="$router.push('/my-com')">My Comments</el-button>
                    <el-button type="danger" @click="logout">Log Out</el-button>
                  </el-form-item>
                </el-form>
              </el-card>
            </el-col>
          </el-row>
          <el-dialog :visible.sync="pwdDialog" title="Change Password">
            <el-form :model="pwdItem" status-icon ref="pwdfrom" label-width="120px" class="demo-ruleForm">
                <el-form-item label="Password" prop="password">
                    <el-input type="password" v-model="pwdItem.password" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="Confirm password" prop="checkPass">
                    <el-input type="password" v-model="pwdItem.checkPass" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button  @click="pwdDialog=false">Cancel</el-button>
                    <el-button type="primary" @click="updatePwd()">Submit</el-button>
                    <el-button type="danger" @click="logout">Log Out</el-button>
                  </el-form-item>
            </el-form>
          </el-dialog>
        </el-main>
    </el-container>
  </template>
  
  <script>
  import userApis from '@/apis/user'
  export default {
    data() {
          return {
            pwdItem: { checkPass: "", password: "" },
            rateValue: 5, // 原始评分值
        user: {
          id: '',
          roleId: '4',
          username: '',
          password: '',
          xq: [],
          js: '',
          imageUrl: '',
          phone: '',
          email: '',
          sex: 0,
          birth: '',
          num: 0,
          rate: 0,
          sf: '',
          ss: '1',
          rep: ''
        },
        pwdDialog: false
      }
    },
  filters: {
    toFixedDecimal(value) {
      return Number(value).toFixed(1); // 输出 "5.0"
    }
  },
    methods: {
      handleAvatarSuccess(res) {
        this.user.imageUrl = res.data.url
      },
      updateProfile() {
            this.user.birth = this.$moment(this.user.birth).format('YYYY-MM-DD hh:mm:ss');
            userApis.update(this.user).then(res=>{
                this.$message.success(res.message)
                this.$store.setStore("user",JSON.stringify(this.user))
            })
        },
        updatePwd() {
   
            this.$refs["pwdfrom"].validate((valid) => {
            if (valid) {
                this.pwdItem.id = this.user.id
                userApis.updatePwd(this.pwdItem).then(res=>{
                    this.$message.success(res.message)
                    this.pwdDialog = false
                })
            } else {
                console.log('error submit!!');
                return false;
            }
            });     
      },
        logout(){
            this.$store.removeStore("user")
            this.$router.push("/login")
        },
      openPasswordDialog() {
        this.pwdDialog = true
      }
    },
    mounted() {
      this.user = JSON.parse(this.$store.getStore("user"))
      this.user.birth = this.$moment(this.user.birth).format('YYYY-MM-DD hh:mm:ss');
    }
  }
  </script>
  
  <style scoped>
  .dashboard-container {
    height: calc( 100vh - 80px);
  }
  
  .main-sidebar {
    background: #304156;
    transition: width 0.3s;
  }
  
  .profile-card {
    text-align: center;
  }
  
  .avatar-uploader >>> .el-upload {
    border: 2px dashed #d9d9d9;
    border-radius: 50%;
    margin: 20px auto;
    width: 120px;
    height: 120px;
  }
  
  .avatar {
    width: 120px;
    height: 120px;
    border-radius: 50%;
  }
  
  .user-stats {
    display: flex;
    justify-content: space-around;
    margin-top: 20px;
  }
  
  .stat-item {
    text-align: center;
  }
  
  .stat-label {
    display: block;
    color: #909399;
  }
  
  .stat-value {
    font-size: 1.2em;
    font-weight: bold;
  }
  </style>