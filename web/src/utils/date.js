/**
 * 日期格式化工具
 * @param {Date|string|number} inputDate - 可被Date解析的时间格式
 * @param {string} [format='YYYY-MM-DD HH:mm'] - 格式化模板
 * @returns {string} 格式化后的日期字符串
 */
export function formatDate(inputDate, format = 'YYYY-MM-DD HH:mm') {
    if (!inputDate) return ''
    
    const date = new Date(inputDate)
    if (isNaN(date)) return ''
  
    const pad = (n) => n.toString().padStart(2, '0')
    
    return format
      .replace(/YYYY/g, date.getFullYear())
      .replace(/MM/g, pad(date.getMonth() + 1))
      .replace(/DD/g, pad(date.getDate()))
      .replace(/HH/g, pad(date.getHours()))
      .replace(/mm/g, pad(date.getMinutes()))
      .replace(/ss/g, pad(date.getSeconds()))
  }
  