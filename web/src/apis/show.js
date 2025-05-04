import request from '../utils/request';

const apis = {
    head: '/show',
    selectActLocationHeat() {
        return request({
             url: this.head + '/selectActLocationHeat',
             method: 'get',
         });
    },
    selectBudgetDistribution() {
        return request({
             url: this.head + '/selectBudgetDistribution',
             method: 'get',
         });
    },
    selectRecentSubscriptions() {
        return request({
             url: this.head + '/selectRecentSubscriptions',
             method: 'get',
         });
    }
}


export default apis;