const setStore = function setStore(key, value) {
    window.localStorage.setItem(key, JSON.stringify(value))
  }
  const getStore = function getStore(key) {
    return JSON.parse(window.localStorage.getItem(key))
  }
  const removeStore = function removeStore(key) {
    window.localStorage.removeItem(key)
  }
  const store = {setStore, getStore, removeStore}
  export default store