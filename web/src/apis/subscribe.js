import request from '../utils/request';

const apis = {
    head: '/t-sub',
    
    list(data, currentPage, pageSize) {
        console.log(data ,currentPage ,pageSize)
        return request({
            url: this.head + `/list?currentPage=${currentPage}&pageSize=${pageSize}`,
            method: 'post',
            data,
        });
    },
    listPlace(data ,currentPage ,pageSize) {
        return request({
            url: this.head + `/list-place?currentPage=${currentPage}&pageSize=${pageSize}`,
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
    
    deleteById(id) {
       return request({
            url: this.head + '/delete/' + id,
            method: 'get',
        });
    }
};

export default apis;