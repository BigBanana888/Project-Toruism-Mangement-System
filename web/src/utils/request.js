 
import axios from 'axios';
import {Message} from 'element-ui';
// 基本配置
const instance = axios.create({
  baseURL: '/api', // 根据实际情况修改API地址
  timeout: 5000 // 设置超时时间，单位为ms
});
// axios.defaults.baseURL = "/api"
// axios.defaults.timeout = 5000
// 请求拦截器
instance.interceptors.request.use(config => {
  config.headers['Authorization'] = localStorage.getItem('token'); // 设置请求头部分，这里举例使用了localStorage存储的token作为身份标识
  return config;
}, error => {
  return Promise.reject(error);
});
// 响应拦截器
instance.interceptors.response.use(response => {
  const data = response.data;
  // if (data && data.code !== 200) { // 根据接口返回的状态码判断是否有错误
      // alert(`Error code ${data.code}: ${data.message}`); // 自定义错误提示
      // return Promise.reject(new Error(data.message));
      // Message.error(data.message)
      // return Promise.reject(new Error(data.message))
  // }else{
    return data;
  // }
}, error => {
    console.log(error);
    return Promise.reject(error);
});
 
export default instance;
 