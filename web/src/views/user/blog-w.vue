<template>
  <div class="blog-editor-container">
    <el-form ref="form" :model="blog" label-width="120px">
      <!-- Basic Information -->
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="Blog Title" prop="name">
            <el-input v-model="blog.name" placeholder="Enter Blog title"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="Author" prop="createdName">
            <el-input v-model="user.nickname" disabled></el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <!-- Markdown Editor -->
      <el-form-item label="Content" prop="content">
        <v-md-editor
v-model="blog.content"
:height="600"
left-toolbar="undo redo clear | h bold italic strikethrough quote | ul ol table hr | link image code"
@upload-image="handleImageUpload"
:disabled-menus="[]"
></v-md-editor>
      </el-form-item>

      <!-- Action Buttons -->
      <el-form-item>
        <el-button type="primary" @click="submitForm">Save Blog</el-button>
        <el-button @click="resetForm">Reset</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import axios from 'axios';
import blogApis from '@/apis/blog'
export default {
  mounted() {
    this.user = JSON.parse(this.$store.getStore('user'))
  },
  data() {
    return {
      user:null,
      blog: {
        id: '',
        name: '',
        content: '# Welcome to Your New Blog\nStart writing here...',
        createdTime: Date.now(),
        modifiedTime: Date.now(),
        createdName: 'Current User',
        modifiedName: ''
      }
    };
  },
  methods: {
    async handleImageUpload(event, insertImage, files) {
  try {
    const formData = new FormData();
    files.forEach(file => {
      if (!this.beforeUpload(file)) return; // 添加文件校验
      formData.append('file', file); // 修改字段名为file
    });

    const { data } = await axios.post(
      'http://localhost:9001/file/upload',
      formData,
      {
        headers: {
          'Content-Type': 'multipart/form-data',
        }
      }
    );

    if (data.code === 200) {
      console.log(data)
      insertImage({
        url: data.data.url, 
        desc: 'Uploaded image'
      });
      this.$message.success('图片上传成功');
    }
  } catch (error) {
    this.$message.error('图片上传失败');
    console.error('Upload error:', error);
  }
},

beforeUpload(file) {
  const isImage = /\.(jpeg|jpg|png|gif)$/i.test(file.name);
  const isLt10M = file.size / 1024 / 1024 < 10;

  if (!isImage) {
    this.$message.error('Only JPG/PNG images are supported');
    return false;
  }
  if (!isLt10M) {
    this.$message.error('The image size cannot exceed 10MB');
    return false;
  }
  return true;
},
    submitForm() {
      this.blog.createdId = this.user.id
      blogApis.save(this.blog).then(res => {
        if (res.code === 200) {
          this.$message.success('Blog saved successfully');
        } else {
      this.$message.fail('Blog saved Fail');
        }
      })
    },
    resetForm() {
      this.blog.content = '# Welcome to Your New Blog\nStart writing here...';
      this.$refs.form.resetFields();
    }
  }
};
</script>

<style scoped>
.blog-editor-container {
  padding: 30px;
  max-width: 95%;
  margin: 0 auto;
}

::v-deep .v-md-editor {
  border: 1px solid #EBEEF5;
  border-radius: 4px;
  overflow: hidden;
}

::v-deep .v-md-editor__toolbar {
  background: #f8f9fa;
  border-bottom: 1px solid #EBEEF5;
}

::v-deep .v-md-editor__editor {
  padding: 15px;
}
</style>