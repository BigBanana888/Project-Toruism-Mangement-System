<template>
	<div class="page">
        <div class="tour-login-container">
            <div class="tour-header">
                <h1>Campus Venue Reservation System</h1>            <div class="auth-switch">
                    <span>Sign in with </span>
                    <router-link to="/login-e">Email</router-link>
                </div>
            </div>
            <el-form 
                label-position="top" 
                :model="item" 
                status-icon 
                :rules="rules" 
                ref="ruleForm"
                class="tour-login-form">
                
                <el-form-item label="Account" prop="username">
                    <el-input v-model="item.username" autocomplete="off">
                        <i slot="prefix" class="el-icon-user"></i>
                    </el-input>
                </el-form-item>

                <el-form-item label="Password" prop="password">
                    <el-input type="password" v-model="item.password" show-password>
                        <i slot="prefix" class="el-icon-lock"></i>
                    </el-input>
                </el-form-item>

                <div class="form-actions">
                    <el-button 
                        type="success" 
                        class="tour-btn"
                        @click="submitForm">
                        Sign In
                        <i class="el-icon-arrow-right"></i>
                    </el-button>
                    
                    <div class="signup-link">
                        New user? 
                        <a @click="toSignUp">Create account</a>
                    </div>
                </div>
            </el-form>
        </div>
	</div>
</template>

<style scoped>
.auth-switch {
    margin-top: 20px;
    color: #4a8c7c;
    font-size: 14px;
}

.auth-switch a {
    color: #1a6f57;
    text-decoration: underline;
    cursor: pointer;
    margin-left: 8px;
}
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

.tour-login-container {
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
    letter-spacing: 1.2px;
}

.tour-header h3 {
    color: #4a8c7c;
    font-size: 16px;
    font-weight: 400;
    letter-spacing: 0.5px;
}

.tour-login-form ::v-deep .el-form-item__label {
    color: #2a6653;
    font-weight: 500;
    font-size: 16px;
    margin-bottom: 8px;
}

.el-input__prefix {
    display: flex;
    align-items: center;
    padding-left: 10px;
    color: #4a8c7c;
}

.form-actions {
    margin-top: 40px;
    text-align: center;
}

.tour-btn {
    width: 100%;
    height: 48px;
    font-size: 16px;
    letter-spacing: 0.5px;
    background: linear-gradient(135deg, #1a6f57, #4a8c7c);
    border: none;
    border-radius: 8px;
    transition: all 0.3s;
}

.tour-btn:hover {
    opacity: 0.9;
    transform: translateY(-2px);
    box-shadow: 0 4px 12px rgba(26, 111, 87, 0.3);
}

.signup-link {
    margin-top: 24px;
    color: #666;
    font-size: 14px;
}

.signup-link a {
    color: #1a6f57;
    cursor: pointer;
    font-weight: 500;
    text-decoration: underline;
    margin-left: 6px;
}
</style>

<script>
import userApis from '@/apis/user.js'
import placeApis from '@/apis/act.js'

export default {
    data() {
        return {
            item: {
                roleId: 2
            },
            rules: {
                username: [
                    { required: true, message: 'Please input username', trigger: 'blur' }
                ],
                password: [
                    { required: true, message: 'Please input password', trigger: 'blur' }
                ]
            }
        }
    },
    methods: {
        toSignUp() {
            this.$router.push("/register")
        },
        submitForm() {
            this.$refs["ruleForm"].validate((valid) => {
                if (valid) {
                    this.login()
                } else {
                    console.log('Validation failed')
                    return false
                }
            })
        },
        login() {
            userApis.loginByPwd(this.item).then(res => {
                if (res.code !== 200) {
                    this.$message.error(res.message)
                    return
                }
                this.$message.success("Login successful")
                this.$store.setStore("user", JSON.stringify(res.data))
                
                switch(res.data.roleId) {
                    case "0":
                        this.$router.push("/admin-index")
                        break
                    default:
                        this.$router.push("/user-index")
                }
            })
        },
        getManagedPlaces(id) {
            placeApis.list({ userId: id }, 4, -1).then(res => {
                this.$store.setStore(id, JSON.stringify(res.data.records))
            })
        }
    }
}
</script>