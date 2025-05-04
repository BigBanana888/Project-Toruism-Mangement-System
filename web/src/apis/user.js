import request from '../utils/request';

const apis = {
    head: '/t-user',
    
    loginByPwd(data ) {
        return request({
            url: this.head + `/login-username`,
            method: 'post',
            data,
        });
    },
    loginByEmail(data ) {
        return request({
            url: this.head + `/login-email`,
            method: 'post',
            data,
        });
    },
    list(data ,currentPage ,pageSize) {
        return request({
            url: this.head + `/list?currentPage=${currentPage}&pageSize=${pageSize}`,
            method: 'post',
            data,
        });
    },
    
    save(data) {
        return request({
            url: this.head + '/save',
            method: 'post',
            data,
        });
    },
    update(data) {
        return request({
            url: this.head + '/update',
            method: 'post',
            data,
        });
    },
    updatePwd(data) {
        return request({
            url: this.head + '/updatePwd',
            method: 'post',
            data,
        });
    },
    deleteById(id) {
       return request({
            url: this.head + '/delete/' + id,
            method: 'get',
        });
    },
    oneById(id) {
       return request({
            url: this.head + '/one/' + id,
            method: 'get',
        });
    }
};

export default apis;