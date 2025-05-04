<template>
  <div class="dashboard-container">
     <el-card style="margin: 10px 0;">
       <el-row :gutter="20">
         <el-col :span="24">
           <div class="dashboard-title">
             Welcome to TravelMate Admin, {{ user.username }}!
             <div class="sub-title">Your gateway to seamless event management</div>
           </div>
         </el-col>
       </el-row>
     </el-card>
 
     <el-row :gutter="20" class="chart-row">
       <el-col :span="12">
         <div id="locationChart" class="chart-container"></div>
       </el-col>
       <el-col :span="12">
         <div id="budgetChart" class="chart-container"></div>
       </el-col>
       <el-col :span="24">
         <div id="trendChart" class="chart-container"></div>
       </el-col>
     </el-row>
  </div>
 </template>
 
 <script>
 import * as echarts from 'echarts'
 import showApis from '@/apis/show'
 
 export default {
   components: {
   },
   data() {
     return {
       user: {},
       item: {
         blogNum: 0,
         userNum: 0,
         actNum: 0,
         subscribeNum: 0
       },
       locationData: [],
       budgetData: [],
       trendData: []
     }
   },
   mounted() {
     this.user = JSON.parse(this.$store.getStore("user"))
     this.initCharts()
     this.loadData()
   },
   methods: {
     async loadData() {
       try {
         const [locations, budgets, trends] = await Promise.all([
           showApis.selectActLocationHeat(),
           showApis.selectBudgetDistribution(),
           showApis.selectRecentSubscriptions()
         ])
         
         this.locationData = locations.data
         this.budgetData = budgets.data
         this.trendData = trends.data
         
         this.initCharts()
       } catch (error) {
         console.error('Error loading dashboard data:', error)
       }
     },
     initCharts() {
       this.initLocationChart()
       this.initBudgetChart()
       this.initTrendChart()
     },
     initLocationChart() {
       const chart = echarts.init(document.getElementById('locationChart'))
       chart.setOption({
         title: {
           text: 'Top 5 Popular Locations',
           left: 'center'
         },
         tooltip: {
           trigger: 'axis',
           formatter: params => `
             ${params[0].name}<br/>
             Activities: ${params[0].value}
           `
         },
         xAxis: {
           type: 'category',
           data: this.locationData.map(item => item.name),
           axisLabel: {
             rotate: 45
           }
         },
         yAxis: {
           type: 'value',
           name: 'Number of Activities'
         },
         series: [{
           type: 'bar',
           data: this.locationData.map(item => item.value),
           itemStyle: {
             color: '#5470c6'
           },
           emphasis: {
             itemStyle: {
               shadowBlur: 10,
               shadowColor: 'rgba(0,0,0,0.3)'
             }
           }
         }]
       })
     },
     initBudgetChart() {
       const chart = echarts.init(document.getElementById('budgetChart'))
       chart.setOption({
         title: {
           text: 'Budget Distribution',
           left: 'center'
         },
         tooltip: {
           trigger: 'item',
           formatter: params => `
             ${params.name}<br/>
             Activities: ${params.value}<br/>
             Percentage: ${params.percent}%
           `
         },
         xAxis: {
           type: 'category',
           data: this.budgetData.map(item => item.name)
         },
         yAxis: {
           type: 'value',
           name: 'Number of Activities'
         },
         series: [{
           type: 'bar',
           data: this.budgetData.map(item => item.value),
           itemStyle: {
             color: '#91cc75'
           },
           barCategoryGap: '20%'
         }]
       })
     },
     initTrendChart() {
       const chart = echarts.init(document.getElementById('trendChart'))
       chart.setOption({
         title: {
           text: '7-Day Subscription Trend',
           left: 'center'
         },
         tooltip: {
           trigger: 'axis',
           formatter: params => `
             Date: ${params[0].name}<br/>
             Subscriptions: ${params[0].value}
           `
         },
         xAxis: {
           type: 'category',
           data: this.trendData.map(item => item.name)
         },
         yAxis: {
           type: 'value',
           name: 'Daily Subscriptions'
         },
         series: [{
           type: 'line',
           data: this.trendData.map(item => item.value),
           smooth: true,
           lineStyle: {
             width: 3,
             shadowColor: 'rgba(0,0,0,0.2)',
             shadowBlur: 10
           },
           itemStyle: {
             color: '#ee6666'
           }
         }]
       })
     }
   }
 }
 </script>
 
 <style scoped>
 .dashboard-container {
   padding: 20px;
   max-width: 1400px;
   margin: 0 auto;
 }
 
 .dashboard-title {
   font-size: 28px;
   font-weight: 500;
   color: #2c3e50;
 }
 
 .sub-title {
   font-size: 16px;
   color: #7f8c8d;
   margin-top: 8px;
 }
 
 .metric-row {
   margin: 20px 0;
 }
 
 .metric-card {
   margin: 10px 0;
   text-align: left;
   display: flex;
   align-items: center;
   padding: 20px;
 }
 
 .metric-icon {
   font-size: 32px;
   color: #409EFF;
   margin-right: 15px;
 }
 
 .metric-content {
   display: flex;
   flex-direction: column;
 }
 
 .metric-title {
   color: #909399;
   font-size: 14px;
   margin-bottom: 4px;
 }
 
 .metric-value {
   font-size: 24px;
   font-weight: 600;
   color: #303133;
 }
 
 .chart-row {
   margin-top: 30px;
 }
 
 .chart-container {
   width: 100%;
   height: 400px;
   min-width: 300px;
   margin: 20px 0;
   background: #fff;
   border-radius: 8px;
   box-shadow: 0 2px 12px rgba(0,0,0,0.1);
 }
 
 @media (max-width: 768px) {
   .dashboard-title {
     font-size: 22px;
   }
   
   .metric-value {
     font-size: 20px;
   }
   
   .chart-container {
     height: 300px;
   }
 }
 </style>