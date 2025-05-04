<template>
  <div class="chat-container">
    <div class="message-list" ref="messageList">
      <div 
        v-for="(message, index) in sortedMessages"
        :key="index"
        class="message-wrapper"
      >
        <!-- 对方消息 -->
        <div v-if="message.createdId !== currentUser.id" class="other-message">
          <el-avatar :size="40" :src="message.ah"></el-avatar>
          <div class="bubble left">
            <div class="content">{{ message.content }}</div>
            <div class="time">{{ format(message.createdTime) }}</div>
          </div>
        </div>

        <!-- 自己消息 -->
        <div v-else class="self-message">
          <div class="bubble right">
            <div class="content">{{ message.content }}</div>
            <div class="time">{{ format(message.createdTime) }}</div>
          </div>
          <el-avatar :size="40" :src="currentUser.ch"></el-avatar>
        </div>
      </div>
    </div>

    <!-- 输入区域 -->
    <div class="input-area">
      <el-input
        v-model="newMessage"
        type="textarea"
        :rows="3"
        placeholder="输入消息..."
        @keyup.enter.native="sendMessage"
        class="input-box"
      ></el-input>
      <el-button type="primary" @click="sendMessage" class="send-btn">Send</el-button>
    </div>
  </div>
</template>

<script>
import apis from '@/apis/chat'; 
import userapis from '@/apis/user';
import { formatDate } from '@/utils/date'

export default {
  data() {
    return {
      messages: [],
      newMessage: '',
      currentUser: {
        id: '',
        name: '',
        ch: ''
      },
      toUser: null,
    };
  },
  computed: {
    sortedMessages() {
      return [...this.messages].sort((a, b) => 
        new Date(a.createdTime) - new Date(b.createdTime)
      )
    }
  },
  mounted() {
    this.currentUser = JSON.parse(this.$store.getStore('user'))
    this.getSuser()
  },
  methods: {
    getSuser() {
      userapis.oneById(this.$route.query.id).then(res => {
        this.toUser = res.data
        this.loadMessages();
      })  
    },
    async loadMessages() {
      try {
        // 获取双方消息记录
        const [sentMessages, receivedMessages] = await Promise.all([
          // 当前用户Send的消息
          apis.list({
            createdId: this.currentUser.id,
            aid: this.toUser.id
          }, 1, -1),
          // 对方用户Send的消息
          apis.list({
            createdId: this.toUser.id,
            aid: this.currentUser.id
          }, 1, -1)
        ])

        this.messages = [
          ...sentMessages.data.records,
          ...receivedMessages.data.records
        ]
        
        this.scrollToBottom()
      } catch (error) {
        this.$message.error('message load error')
        console.error('load:', error)
      }
    },
    async sendMessage() {
      if (!this.newMessage.trim()) return;
      
      try {
        await apis.save({
          content: this.newMessage,
          aid: this.toUser.id,
          createdId: this.currentUser.id,
        })
        
        this.newMessage = ''
        await this.loadMessages()  // 重新加载保证数据最新
        this.$message.success('message send error')
      } catch (error) {
        this.$message.error('Send error')
        console.error('Send error:', error)
      }
    },
    format(time) {
      return formatDate(time, 'YYYY-MM-DD HH:mm') 
    },
    scrollToBottom() {
      this.$nextTick(() => {
        const container = this.$refs.messageList
        container.scrollTop = container.scrollHeight
      })
    }
  }
};
</script>

<style scoped>
.chat-container {
  height: calc(100vh - 150px);
  display: flex;
  flex-direction: column;
  background: #f1f1f1;
}

.message-list {
  flex: 1;
  overflow-y: auto;
  padding: 20px;
}

.message-wrapper {
  margin-bottom: 20px;
}

/* 对方消息样式 */
.other-message {
  display: flex;
  align-items: flex-start;
  gap: 10px;
}

.left {
  background: white;
  border-radius: 5px 15px 15px 5px;
  position: relative;
  margin-left: 10px;
  max-width: 70%;
}

.left::before {
  content: '';
  position: absolute;
  left: -8px;
  top: 12px;
  border: 8px solid transparent;
  border-right-color: white;
}

/* 自己消息样式 */
.self-message {
  display: flex;
  justify-content: flex-end;
  align-items: flex-start;
  gap: 10px;
}

.right {
  background: #95ec69;
  border-radius: 15px 5px 5px 15px;
  margin-right: 10px;
  max-width: 70%;
}

.bubble {
  padding: 12px;
  box-shadow: 0 2px 5px rgba(0,0,0,0.1);
}

.content {
  font-size: 15px;
  line-height: 1.5;
}

.time {
  font-size: 12px;
  color: #666;
  text-align: right;
  margin-top: 5px;
}

.input-area {
  padding: 15px;
  background: white;
  border-top: 1px solid #ddd;
  display: flex;
  gap: 10px;
}

.input-box {
  flex: 1;
}

.send-btn {
  height: 40px;
}
</style>