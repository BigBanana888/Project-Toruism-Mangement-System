<template>
	<div class="page">
        <div class="tour-register-container">
            <div class="tour-header">
                <h1>Campus Venue Reservation System</h1>
            </div>
            <el-form :model="item" status-icon :rules="rules" ref="ruleForm" 
                    label-width="150px" class="tour-register-form">
                <el-form-item label="Student number" prop="username">
                    <el-input v-model="item.username" placeholder="Please enter the campus unified student number">
                        <i slot="prefix" class="el-icon-user"></i>
                    </el-input>
                </el-form-item>
                <el-form-item label="Email" prop="email">
                    <el-input 
                        v-model="item.email" 
                        type="email"
                        placeholder="For account verification"
                        autocomplete="off">
                        <i slot="prefix" class="el-icon-message"></i>
                    </el-input>
                </el-form-item>
                <el-form-item label="Password" prop="password">
                    <el-input type="password" v-model="item.password" show-password>
                        <i slot="prefix" class="el-icon-lock"></i>
                    </el-input>
                </el-form-item>

                <el-form-item label="Confirm password" prop="cpassword">
                    <el-input type="password" v-model="item.cpassword" show-password>
                        <i slot="prefix" class="el-icon-circle-check"></i>
                    </el-input>
                </el-form-item>

                <el-form-item label="Phone" prop="phone">
                    <el-input v-model="item.phone" placeholder="For trip notification">
                        <i slot="prefix" class="el-icon-mobile-phone"></i>
                    </el-input>
                </el-form-item>
                <el-form-item label="Student ID Card"  prop="sf">
                <el-upload
                class="avatar-uploader"
                action="http://localhost:9001/file/upload"
                :show-file-list="false"
                :on-success="handleAvatarSuccess"
                :before-upload="beforeAvatarUpload">
                <img v-if="item.sf" style="width: 200px;height: 200px;" :src="item.sf" class="avatar">
                <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                </el-upload>
            </el-form-item>
                <div class="form-actions">
                    <el-button type="success" class="tour-btn" @click="submitForm">
                        Register now!
                        <i class="el-icon-position"></i>
                    </el-button>
                    <div class="login-link">
                        Already have an account?<a @click="toLogin">Log in now</a>
                    </div>
                </div>
            </el-form>
        </div>
	</div>
</template>
<style scoped>
.page {
    background-image: url('~@/assets/images/bj.jpg'), 
                    linear-gradient(rgba(255,255,255,0.2), rgba(255,255,255,0.2));
    background-blend-mode: lighten;
    background-size: cover;
    background-position: center;
    min-height: 100vh;
    display: flex;
    justify-content: center;
    align-items: center;
}

.tour-register-container {
    background: rgba(255, 255, 255, 0.95);
    width: 600px;
    padding: 40px;
    border-radius: 16px;
    box-shadow: 0 8px 32px rgba(0, 96, 128, 0.15);
    backdrop-filter: blur(8px);
}

.tour-header {
    text-align: center;
    margin-bottom: 32px;
}

.tour-header h1 {
    color: #1a6f57;
    font-size: 28px;
    margin-bottom: 12px;
    font-weight: 600;
    letter-spacing: 1.5px;
}

.tour-header h3 {
    color: #4a8c7c;
    font-size: 16px;
    font-weight: 400;
}

.tour-register-form ::v-deep .el-form-item__label {
    color: #2a6653;
    font-weight: 500;
}

.el-input__prefix {
    display: flex;
    align-items: center;
    padding-left: 10px;
    color: #4a8c7c;
}

.form-actions {
    margin-top: 36px;
    text-align: center;
}

.tour-btn {
    width: 100%;
    height: 48px;
    font-size: 16px;
    background: linear-gradient(135deg, #1a6f57, #4a8c7c);
    border: none;
    border-radius: 8px;
    transition: all 0.3s;
}

.tour-btn:hover {
    opacity: 0.9;
    transform: translateY(-2px);
}

.login-link {
    margin-top: 20px;
    color: #666;
}

.login-link a {
    color: #1a6f57;
    cursor: pointer;
    font-weight: 500;
    text-decoration: underline;
}
</style>

<script>
import userApis from '@/apis/user.js'

export default {
    data(){
        return {
            item: {
                roleId: 2,
                username: '',
            },
            rules: {email: [
                    { 
                        required: true, 
                        message: 'Email is required', 
                        trigger: 'blur' 
                    },
                    { 
                        type: 'email',
                        message: 'Please enter a valid email address',
                        trigger: ['blur', 'change']
                    }
                ],sf: [  
          { 
            required: true, 
            validator: (rule, value, callback) => {
              if (!this.item.sf) {
                callback(new Error('Student ID Card is required'));
              } else {
                callback();
              }
            },
            trigger: 'change'
          }
        ],
                username: [
                    { required: true, message: 'The student number cannot be empty', trigger: 'blur' },
                    { pattern: /^\d{6,12}$/, message: 'Student numbers should be 6-12 digits' }
                ],
                password: [
                    { required: true, message: 'please input password', trigger: 'blur' },
                ],
                cpassword: [
                    { required: true, message: 'please input password again', trigger: 'blur' }
                ],
                phone: [
                    { required: true, message: 'Mobile phone number required', trigger: 'blur' },
                    { pattern: /^1[3-9]\d{9}$/, message: 'Please enter a valid phone number' }
                ]
            }
        }
    },
    methods: {
        handleAvatarSuccess(res, file) {
            this.item.sf = res.data.url
            this.$forceUpdate()
        },
        beforeAvatarUpload(file) {
            const isLt2M = file.size / 1024 / 1024 < 15;
            if (!isLt2M) {
            this.$message.error('The size of the uploaded image cannot exceed 15MB!');
            }
            return isLt2M;
        },
        toLogin(){
            this.$router.push("/login")
        },
        submitForm() {
            this.$refs.ruleForm.validate(valid => {
                if (valid) {
                    if(this.item.password !== this.item.cpassword) {
                        this.$message.error("The two passwords are different")
                        return
                    }
                    this.save()
                }
            })
        },
        save() {
            userApis.save(this.item).then(res => {
                if (res.code === 200) {
                this.$message.success("Registration successful, please login")
                this.toLogin()
                } else {
                    this.$message.error(res.message)
                }
            })
        }
    }
}
</script>